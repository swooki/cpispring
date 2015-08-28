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

	private Application targetApplication;

	private Properties fileNetProperties = new Properties();
	private Properties applicationProperties = new Properties();
	private static Logger logger = Logger.getLogger(Object.class);
	private int maxEventNum;

	public void setTargetApplication(Application app) {
		targetApplication = app;
	}

	public FileNetExtractor(Application app) {
		super(app);
		targetApplication = app;

		String propertyFileName = getClass().getSimpleName() + ".properties";

		try {
			fileNetProperties.load(new FileInputStream(propertyFileName));
			applicationProperties.load(new FileInputStream(targetApplication
					.getName() + ".properties"));
		} catch (IOException e) {
			logger.error("Couldn't find the configuration file: "
					+ propertyFileName);
		}
		conn = Factory.Connection.getConnection(fileNetProperties
				.getProperty("CE_URI"));
		maxEventNum = Integer.parseInt(fileNetProperties
				.getProperty("MAX_NUM_EVENTS"));
	}

	@Override
	public ArrayList<CPILog> extract() throws Exception {

		ArrayList<CPILog> logs = new ArrayList<CPILog>();
		int counter = 0;

		FileNetAppInfo appInfo = null;

		if (targetApplication == null) {
			throw new Exception("The target application is not assigned");
		}

		String password;

		try {
			password = CryptoUtils.decrypt(fileNetProperties
					.getProperty("PASSWORD"));
			Subject subject = UserContext.createSubject(conn,
					fileNetProperties.getProperty("USERID"), password,
					fileNetProperties.getProperty("JAAS_STANZA_NAME"));
			UserContext.get().pushSubject(subject);
		} catch (GeneralSecurityException e1) {
			throw e1;
		}

		Domain dom = Factory.Domain.getInstance(conn, null);
		ObjectStore os = Factory.ObjectStore.getInstance(dom,
				targetApplication.getObjectStoreName());
		os.refresh();

		// Extract events that have been created on the given date.
		// Needs to extract only "Get Content"
		SearchSQL eventSql = new SearchSQL();
		eventSql.setMaxRecords(this.maxEventNum);
		eventSql.setQueryString("SELECT ev.[DateCreated], ev.[Creator], ev.[SourceObjectId]"
				+ "  FROM [Event] ev INNER JOIN [Document] doc ON ev.SourceObjectId = doc.id"
				+ " WHERE IsClass(ev, GetContentEvent)"
				+ "   AND IsClass(doc, "
				+ targetApplication.getSourceClassName()
				+ ")"
				+ "   AND ev.[Creator] <> \'FileNet-P8Admin\'");

		SearchScope eventScope = new SearchScope(os);
		PropertyFilter filters = new PropertyFilter();
		filters.addIncludeProperty(1, null, null, "DateCreated", null);
		filters.addIncludeProperty(1, null, null, "Creator", null);
		filters.addIncludeProperty(1, null, null, "SourceObjectId", null);

		IndependentObjectSet objSet = eventScope.fetchObjects(eventSql, 200,
				null, true);
		PageIterator pItr = objSet.pageIterator();

		appInfo = FileNetAppInfoFactory.getFileNetAppInfo(targetApplication
				.getName());

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
					log.setApplication(targetApplication.getName());
					log.setDateAccessed(ev.getProperties().getDateTimeValue(
							"DateCreated"));
					log.setUserAccessed(ev.getProperties().getStringValue(
							"Creator"));
					log.setDocumentAccessed(ev.getProperties()
							.getIdValue("SourceObjectId").toString());

					// Get the additional information from document class
					appInfo.getTargetInfo(dom, os, log);
					logs.add(log);

					if (this.targetApplication.deleteAfterLog() == true) {
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

}
