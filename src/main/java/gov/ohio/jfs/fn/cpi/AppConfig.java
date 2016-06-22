package gov.ohio.jfs.fn.cpi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// The fields are populated from the property files, <Application Name>.properties
public class AppConfig {

	private String applicationName;
	private Properties properties;

	// Generic fields in <Application>.properties file
	public static final String ACTION = "ACTION";
	public static final String APPLICATION_NAME = "APPLICATION_NAME";
	public static final String EXTRACTOR_TYPE = "EXTRACTOR_TYPE";
	public static final String EXPORTER_TYPE = "EXPORTER_TYPE";

	// Property names for FileNet Extractor
	public static final String OBJECT_STORE_NAME = "OBJECT_STORE_NAME";
	public static final String SOURCE_CLASS_NAME = "SOURCE_CLASS_NAME";
	public static final String PERSONAL_IDS = "PERSONAL_IDS";
	// ------------------
	public static final String DELETE_AFTER_LOG = "DELETE_AFTER_LOG";
	public static final String DELETE_AFTER_LOG_YES = "YES";
	public static final String DELETE_AFTER_LOG_NO = "NO";

	public static final String CE_URI = "CE_URI";
	public static final String USERID = "USERID";
	public static final String PASSWORD = "PASSWORD";
	public static final String JAAS_STANZA_NAME = "JAAS_STANZA_NAME";
	public static final String MAX_NUM_EVENTS = "MAX_NUM_EVENTS";

	// Property names for CSV Exporter
	public static final String DATE_FORMAT = "DATE_FORMAT";
	public static final String MODE = "MODE";
	public static final String MODE_APPEND = "APPEND";
	public static final String MODE_REPLACE = "REPLACE";

	@SuppressWarnings("unused")
	private AppConfig() {
	}

	public AppConfig(String applicationName) throws IOException {
		this.applicationName = applicationName;
		this.properties = new Properties();
		loadProperties(this.applicationName + ".properties");
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}

	private void loadProperties(String propertyFileName) throws IOException {
		this.properties.load(new FileInputStream(propertyFileName));
	}
}
