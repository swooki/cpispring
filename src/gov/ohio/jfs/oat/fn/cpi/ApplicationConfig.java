package gov.ohio.jfs.oat.fn.cpi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

// The fields are populated from the property files, <Application Name>.properties
public class ApplicationConfig {

	// Generic fields in <Application>.properties file
	public static final String APPLICATION_NAME = "APPLICATION_NAME";
	public static final String EXTRACTOR_TYPE = "EXTRACTOR_TYPE";
	public static final String EXPORTER_TYPE = "EXPORTER_TYPE";

	//	Property names for FileNet Extractor
	public static final String OBJECT_STORE_NAME = "OBJECT_STORE_NAME";
	public static final String SOURCE_CLASS_NAME = "SOURCE_CLASS_NAME";
	public static final String PERSONAL_ID = "PERSONAL_ID";
	//------------------
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
	
	private String name;

	private Properties properties = new Properties();

	private ApplicationConfig() {
		}

	public ApplicationConfig(String name) throws IOException {
		this.name = name;
		try {
			properties.load(new FileInputStream(name + ".properties"));
		} catch (IOException e) {
			throw e;
		}
	}

	public String getName() {
		return name;
	}

	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
}
