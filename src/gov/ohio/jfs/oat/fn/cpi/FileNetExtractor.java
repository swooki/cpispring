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

public class FileNetExtractor implements Extractable {

	private Connection conn;

	private Properties properties = new Properties();

	private static Logger logger = Logger.getLogger(Object.class);

	private String objectStoreName;
	private String applicationName;
	private String sourceClassName;
	private int maxEventNum = 1000;

	private Exportable exporter = null;

	public void setExporter(Exportable exporter) {
		this.exporter = exporter;
	}

	public FileNetExtractor() {
		super();
		logger.setLevel(Level.DEBUG);

		String propertyFileName = getClass().getSimpleName() + ".properties";

		try {
			properties.load(new FileInputStream(propertyFileName));
		} catch (IOException e) {
			System.out.println("Couldn't find the configuration file: "
					+ propertyFileName);
		}
		conn = Factory.Connection.getConnection(properties
				.getProperty("CE_URI"));
		objectStoreName = properties.getProperty("OS");
		applicationName = properties.getProperty("APPLICATION_NAME");
		sourceClassName = properties.getProperty("SOURCE_CLASS_NAME");
	}

	@Override
	public ArrayList<CPILog> extract() {
		
		ArrayList<CPILog> logs = new ArrayList<CPILog>();
		int counter = 0;
		FileNetAppInfo appInfo = null;
		
		String password;

		try {
			password = CryptoUtils.decrypt(properties.getProperty("PASSWORD"));
			Subject subject = UserContext.createSubject(conn,
					properties.getProperty("USERID"), password,
					properties.getProperty("JAAS_STANZA_NAME"));
			UserContext.get().pushSubject(subject);
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Domain dom = Factory.Domain.getInstance(conn, null);
		ObjectStore os = Factory.ObjectStore.getInstance(dom, objectStoreName);
		os.refresh();

		// Extract events that have been created on the given date.
		// Needs to extract only "Get Content"
		SearchSQL eventSql = new SearchSQL();
		eventSql.setMaxRecords(this.maxEventNum);
		eventSql.setQueryString("SELECT ev.[DateCreated], ev.[Creator], ev.[SourceObjectId]"
				+ "  FROM [Event] ev INNER JOIN [Document] doc ON ev.SourceObjectId = doc.id"
				+ " WHERE IsClass(ev, GetContentEvent)"
				+ "   AND IsClass(doc, "
				+ this.sourceClassName
				+ ")"
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
			UpdatingBatch batch = UpdatingBatch.createUpdatingBatchInstance(
					os.get_Domain(), RefreshMode.NO_REFRESH);

			// Loop through each item in the page
			for (Object obj : pItr.getCurrentPage()) {
				try {
					counter++;
					CPILog log = new CPILog();
					Event ev = (Event) obj;

					// Get the values for event class
					log.setApplication(this.applicationName);
					log.setDateAccessed(ev.getProperties().getDateTimeValue("DateCreated"));
					log.setUserAccessed(ev.getProperties().getStringValue("Creator"));
					log.setDocumentAccessed(ev.getProperties().getIdValue("SourceObjectId").toString());

					// Get the additional information from document class
					appInfo = FileNetAppInfoFactory.getFileNetAppInfo(applicationName);
					appInfo.getTargetInfo(dom, os, log);
					
					logs.add(log);

					if (logger.getEffectiveLevel() != Level.DEBUG) {
						ev.delete();
						batch.add(ev, null);
					}

				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
					e.printStackTrace();
					continue;
				}
			} // for

			if (batch.hasPendingExecute())
				batch.updateBatch();
		}// while

		return logs;
	}

}
