package gov.ohio.jfs.fn.cpi;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.collection.PageIterator;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.IndependentlyPersistableObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.UpdatingBatch;
import com.filenet.api.events.Event;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;

import gov.ohio.jfs.fn.util.CryptoUtils;

public class FileNetExtractor extends Extractor implements Extractable {

	private static Logger logger = Logger.getLogger(FileNetExtractor.class);
	private Connection conn;

	private String action;
	private String applicationName;
	private String CEURI;
	private String username;
	private String encryptedPassword;
	private String stanzaName;
	private String objectStoreName;
	private String sourceClassName;
	private boolean deleteAfterLog;
	private String personalIds;

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return this.action;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setCEURI(String CEURI) {
		this.CEURI = CEURI;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.encryptedPassword = password;
	}

	public void setStanzaName(String stanzaName) {
		this.stanzaName = stanzaName;
	}

	public void setObjectStoreName(String objectStoreName) {
		this.objectStoreName = objectStoreName;
	}

	public void setSourceClassName(String sourceClassName) {
		this.sourceClassName = sourceClassName;
	}

	public void setDeleteAfterLog(boolean deleteAfterLog) {
		this.deleteAfterLog = deleteAfterLog;
	}

	public void setPersonalIds(String personalIds) {
		this.personalIds = personalIds;
	}

	public FileNetExtractor() {
		super();
	}

	@Override
	public ArrayList<CPILog> extract() {
		ArrayList<CPILog> logs = new ArrayList<CPILog>();

		if (!validateParameters()) {
			logger.error("Validateion of parameters failed" + this.toString());
		}

		this.conn = Factory.Connection.getConnection(this.CEURI);
		logger.debug("Connection to filenet has been established.");

		UserContext uc = UserContext.get();
		try {
			uc.pushSubject(UserContext.createSubject(this.conn, this.username,
					CryptoUtils.decrypt(this.encryptedPassword), this.stanzaName));
		} catch (GeneralSecurityException e) {
			logger.error(e.getMessage());
		}

		try {
			Domain dom = Factory.Domain.getInstance(conn, null);
			ObjectStore os = Factory.ObjectStore.getInstance(dom, this.objectStoreName);
			os.refresh();

			logs = fetchLogs(dom, os);

		} finally {
			uc.popSubject();
		}

		return logs;
	}

	private ArrayList<CPILog> fetchLogs(Domain dom, ObjectStore os) {
		ArrayList<CPILog> logs = new ArrayList<CPILog>();

		SearchSQL eventSql = new SearchSQL();
		eventSql.setMaxRecords(this.maxRecords);
		eventSql.setQueryString("SELECT ev.[DateCreated], ev.[Creator], ev.[SourceObjectId]"
				+ "  FROM [Event] ev INNER JOIN [Document] doc ON ev.SourceObjectId = doc.id"
				+ " WHERE IsClass(ev, GetContentEvent)" + "   AND IsClass(doc, " + this.sourceClassName + ")"
				+ "   AND ev.[Creator] <> \'FileNet-P8Admin\'");

		SearchScope eventScope = new SearchScope(os);
		PropertyFilter filters = new PropertyFilter();
		filters.addIncludeProperty(1, null, null, "DateCreated", null);
		filters.addIncludeProperty(1, null, null, "Creator", null);
		filters.addIncludeProperty(1, null, null, "SourceObjectId", null);

		IndependentObjectSet objSet = eventScope.fetchObjects(eventSql, 200, null, true);
		PageIterator pItr = objSet.pageIterator();

		while (pItr.nextPage()) {

			// Define a batch for update
			UpdatingBatch batch = UpdatingBatch.createUpdatingBatchInstance(os.get_Domain(), RefreshMode.NO_REFRESH);

			// Loop through each item in the page

			for (Object obj : pItr.getCurrentPage()) {
				CPILog log = new CPILog();
				Event ev = (Event) obj;

				// Get the values for event class
				log.setAction(this.action);
				log.setApplication(this.applicationName);
				log.setDateAccessed(ev.getProperties().getDateTimeValue("DateCreated"));
				log.setUserAccessed(ev.getProperties().getStringValue("Creator"));
				log.setTargetAccessed(ev.getProperties().getIdValue("SourceObjectId").toString());

				// Get the additional information from document class
				getTargetInfo(dom, os, log);
				logs.add(log);

				if (this.deleteAfterLog) {
					ev.delete();
					batch.add(ev, null);
				}
			} // for

			if (batch.hasPendingExecute())
				batch.updateBatch();
		} // while

		return logs;
	}

	@SuppressWarnings("rawtypes")
	private void getTargetInfo(Domain dom, ObjectStore os, CPILog log) {

		String queryPersonalId = "";
		String[] personalIds = this.personalIds.split(",");

		for (String personalId : personalIds) {
			logger.debug("Personal Id: " + personalId);
		}

		PropertyFilter filters = new PropertyFilter();
		filters.addIncludeProperty(1, null, null, "DateCreated", null);

		queryPersonalId = "";

		for (String personalId : personalIds) {
			filters.addIncludeProperty(1, null, null, personalId, null);
			queryPersonalId += ("doc.[" + personalId + "],");
		}
		logger.debug("queryPersonalId: " + queryPersonalId);

		SearchSQL documentSql = new SearchSQL("SELECT " + queryPersonalId + " doc.[DateCreated]"
				+ "  FROM [Document] doc" + " WHERE doc.[Id] = " + log.getTargetAccessed());
		logger.debug(documentSql);

		SearchScope documentScope = new SearchScope(os);
		IndependentObjectSet documentSet = documentScope.fetchObjects(documentSql, null, filters, false);

		if (!documentSet.isEmpty()) {
			Iterator j = documentSet.iterator();
			IndependentlyPersistableObject doc = (IndependentlyPersistableObject) j.next();
			String targetPersonalId = "";
			for (String personalId : personalIds) {
				String stringValue = doc.getProperties().getStringValue(personalId);
				if (stringValue != null) {
					targetPersonalId += (stringValue.trim() + " ");
				}
			}
			logger.debug("targetPersonalId: " + targetPersonalId);
			log.setPersonalId(targetPersonalId.trim());
		}

	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public String getCEURI() {
		return this.CEURI;
	}

	public boolean getDeleteAfterLog() {
		return this.deleteAfterLog;
	}

	public String getObjectStoreName() {
		return this.objectStoreName;
	}

	public String getPassword() {
		return this.encryptedPassword;
	}

	public String getPersonalIds() {
		return this.personalIds;
	}

	public String getSourceClassName() {
		return this.sourceClassName;
	}

	public String getStanzaName() {
		return this.stanzaName;
	}

	public String getUsername() {
		return this.username;
	}

	private boolean validateParameters() {

		return (action != null && applicationName != null && CEURI != null && username != null
				&& encryptedPassword != null && objectStoreName != null && sourceClassName != null
				&& personalIds != null && this.maxRecords >= 0);
	}

	public String toString() {
		return (super.toString() + "action:" + this.action + ", applicationName:" + this.applicationName + ", CEURI:"
				+ this.CEURI + ", username:" + this.username + ", password:" + this.encryptedPassword
				+ ", objectStoreName:" + this.objectStoreName + ", sourceClassName:" + this.sourceClassName
				+ ", personalIds:" + this.personalIds + ", maxRecords:" + this.maxRecords);
	}

}
