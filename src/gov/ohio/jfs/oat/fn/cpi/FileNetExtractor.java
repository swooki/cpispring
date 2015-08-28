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
	private ApplicationConfig applicationConfig;

	private Properties fileNetProperties = new Properties();
	private static Logger logger = Logger.getLogger(Object.class);
	private int maxEventNum;

	public FileNetExtractor(ApplicationConfig appConfig) {
		super(appConfig);
		applicationConfig = appConfig;

		conn = Factory.Connection.getConnection(appConfig.getProperty(ApplicationConfig.CE_URI));
		maxEventNum = Integer.parseInt(appConfig.getProperty(ApplicationConfig.MAX_NUM_EVENTS));
	}

	@Override
	public ArrayList<CPILog> extract() throws Exception {

		ArrayList<CPILog> logs = new ArrayList<CPILog>();
		int counter = 0;

		FileNetAppInfo appInfo = null;

		if (applicationConfig == null) {
			throw new Exception("The target application is not assigned");
		}

		String password;

		try {
			password = CryptoUtils.decrypt(this.applicationConfig.getProperty(ApplicationConfig.PASSWORD));
			Subject subject = UserContext.createSubject(conn,
					this.applicationConfig.getProperty(ApplicationConfig.USERID), password,
					this.applicationConfig.getProperty(ApplicationConfig.JAAS_STANZA_NAME));
			UserContext.get().pushSubject(subject);
		} catch (GeneralSecurityException e1) {
			throw e1;
		}

		Domain dom = Factory.Domain.getInstance(conn, null);
		ObjectStore os = Factory.ObjectStore.getInstance(dom,
				applicationConfig.getProperty(ApplicationConfig.OBJECT_STORE_NAME));
		os.refresh();

		// Extract events that have been created on the given date.
		// Needs to extract only "Get Content"
		SearchSQL eventSql = new SearchSQL();
		eventSql.setMaxRecords(this.maxEventNum);
		eventSql.setQueryString("SELECT ev.[DateCreated], ev.[Creator], ev.[SourceObjectId]"
				+ "  FROM [Event] ev INNER JOIN [Document] doc ON ev.SourceObjectId = doc.id"
				+ " WHERE IsClass(ev, GetContentEvent)"
				+ "   AND IsClass(doc, "
				+ applicationConfig.getProperty(ApplicationConfig.SOURCE_CLASS_NAME)
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
					log.setApplication(applicationConfig.getName());
					log.setDateAccessed(ev.getProperties().getDateTimeValue(
							"DateCreated"));
					log.setUserAccessed(ev.getProperties().getStringValue(
							"Creator"));
					log.setDocumentAccessed(ev.getProperties()
							.getIdValue("SourceObjectId").toString());

					// Get the additional information from document class
					getTargetInfo(dom, os, log);
					logs.add(log);

					if (this.applicationConfig
							.getProperty(ApplicationConfig.DELETE_AFTER_LOG)
							.toUpperCase().equals(ApplicationConfig.DELETE_AFTER_LOG_YES)) {
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
		
		PropertyFilter filters = new PropertyFilter();
		filters.addIncludeProperty(1, null, null, "DateCreated", null);
		filters.addIncludeProperty(1, null, null, "FIRST_NAME", null);
		filters.addIncludeProperty(1, null, null, "LAST_NAME", null);

		SearchSQL documentSql = new SearchSQL(
				"SELECT doc.[FIRST_NAME], doc.[LAST_NAME], doc.[DateCreated]"
						+ "  FROM [Document] doc" + " WHERE doc.[Id] = "
						+ log.getDocumentAccessed());
		SearchScope documentScope = new SearchScope(os);
		IndependentObjectSet documentSet = documentScope.fetchObjects(
				documentSql, null, filters, false);

		if (!documentSet.isEmpty()) {

			Iterator j = documentSet.iterator();
			IndependentlyPersistableObject doc = (IndependentlyPersistableObject) j
					.next();

			log.setPersonalId(doc.getProperties().getStringValue("FIRST_NAME")
					.trim()
					+ " "
					+ doc.getProperties().getStringValue("LAST_NAME").trim());
			log.setDateCreated(doc.getProperties().getDateTimeValue(
					"DateCreated"));
		}
	}

}
