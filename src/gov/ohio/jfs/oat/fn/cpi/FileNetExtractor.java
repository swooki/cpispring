package gov.ohio.jfs.oat.fn.cpi;

import gov.ohio.jfs.ois.fn.util.CryptoUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.security.auth.Subject;

import org.apache.log4j.Level;
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

public class FileNetExtractor extends Extractor implements Extractable {

	private Connection conn;
	private AppConfig appConfig;

	private static Logger logger = Logger.getLogger(FileNetExtractor.class);
	private int maxEventNum;

	public FileNetExtractor(AppConfig appConfig) {
		super(appConfig);
		this.appConfig = appConfig;

		conn = Factory.Connection.getConnection(appConfig.getProperty(AppConfig.CE_URI));
		maxEventNum = Integer.parseInt(appConfig.getProperty(AppConfig.MAX_NUM_EVENTS));
	}

	@Override
	public ArrayList<CPILog> extract() throws Exception {

		ArrayList<CPILog> logs = new ArrayList<CPILog>();
		int counter = 0;

		if (appConfig == null) {
			throw new Exception("The configuration of target application is not assigned");
		}

		String password;

		try {
			password = CryptoUtils.decrypt(this.appConfig.getProperty(AppConfig.PASSWORD));
			Subject subject = UserContext.createSubject(conn,
					this.appConfig.getProperty(AppConfig.USERID), password,
					this.appConfig.getProperty(AppConfig.JAAS_STANZA_NAME));
			UserContext.get().pushSubject(subject);
		} catch (GeneralSecurityException e1) {
			throw e1;
		}

		Domain dom = Factory.Domain.getInstance(conn, null);
		ObjectStore os = Factory.ObjectStore.getInstance(dom,
				appConfig.getProperty(AppConfig.OBJECT_STORE_NAME));
		os.refresh();

		// Extract events that have been created on the given date.
		// Needs to extract only "Get Content"
		SearchSQL eventSql = new SearchSQL();
		eventSql.setMaxRecords(this.maxEventNum);
		eventSql.setQueryString("SELECT ev.[DateCreated], ev.[Creator], ev.[SourceObjectId]"
				+ "  FROM [Event] ev INNER JOIN [Document] doc ON ev.SourceObjectId = doc.id"
				+ " WHERE IsClass(ev, GetContentEvent)"
				+ "   AND IsClass(doc, "
				+ appConfig.getProperty(AppConfig.SOURCE_CLASS_NAME)
				+ ")" + "   AND ev.[Creator] <> \'FileNet-P8Admin\'");

		SearchScope eventScope = new SearchScope(os);
		PropertyFilter filters = new PropertyFilter();
		filters.addIncludeProperty(1, null, null, "DateCreated", null);
		filters.addIncludeProperty(1, null, null, "Creator", null);
		filters.addIncludeProperty(1, null, null, "SourceObjectId", null);

		IndependentObjectSet objSet = eventScope.fetchObjects(eventSql, 200,
				null, true);
		PageIterator pItr = objSet.pageIterator();

		while (pItr.nextPage()) {

			// Define a batch for update
			UpdatingBatch batch = UpdatingBatch.createUpdatingBatchInstance(
					os.get_Domain(), RefreshMode.NO_REFRESH);

			// Loop through each item in the page

			for (Object obj : pItr.getCurrentPage()) {
				try {
					counter++;
					CPILog log = new CPILog();
					Event ev = (Event) obj;

					// Get the values for event class
					log.setApplication(appConfig.getName());
					log.setDateAccessed(ev.getProperties().getDateTimeValue(
							"DateCreated"));
					log.setUserAccessed(ev.getProperties().getStringValue(
							"Creator"));
					log.setDocumentAccessed(ev.getProperties()
							.getIdValue("SourceObjectId").toString());

					// Get the additional information from document class
					getTargetInfo(dom, os, log);
					logs.add(log);

					if (this.appConfig
							.getProperty(AppConfig.DELETE_AFTER_LOG)
							.toUpperCase().equals(AppConfig.DELETE_AFTER_LOG_YES)) {
						ev.delete();
						batch.add(ev, null);
					}

				} catch (Exception e) {
					throw e;
				}
			} // for

			if (batch.hasPendingExecute())
				batch.updateBatch();
		}// while

		return logs;
	}

	public void getTargetInfo(Domain dom, ObjectStore os, CPILog log) {
		
		String queryPersonalId = "";
		String[] personalIds = appConfig.getProperty(AppConfig.PERSONAL_ID).split(",");
		
		for(String personalId: personalIds){
			logger.debug("Personal Ids: " + personalId);
		}
	
		PropertyFilter filters = new PropertyFilter();
		filters.addIncludeProperty(1, null, null, "DateCreated", null);

		queryPersonalId = "";

		for(String personalId: personalIds){
			filters.addIncludeProperty(1, null, null, personalId, null);
			queryPersonalId += ("doc.[" + personalId + "],");
		}
		logger.debug("queryPersonalId: " + queryPersonalId);
		

		SearchSQL documentSql = new SearchSQL(
				"SELECT " + queryPersonalId + " doc.[DateCreated]"
						+ "  FROM [Document] doc" + " WHERE doc.[Id] = "
						+ log.getDocumentAccessed());
		logger.debug(documentSql);
		
		SearchScope documentScope = new SearchScope(os);
		IndependentObjectSet documentSet = documentScope.fetchObjects(
				documentSql, null, filters, false);

		if (!documentSet.isEmpty()) {

			Iterator j = documentSet.iterator();
			IndependentlyPersistableObject doc = (IndependentlyPersistableObject) j
					.next();
			String tmp = "";
			
			for(String personalId: personalIds){
				tmp += (doc.getProperties().getStringValue(personalId).trim() + " ");
			}
			logger.debug("tmp: " + tmp);
			log.setPersonalId(tmp.trim());
			log.setDateCreated(doc.getProperties().getDateTimeValue(
					"DateCreated"));
		}
	}

}
