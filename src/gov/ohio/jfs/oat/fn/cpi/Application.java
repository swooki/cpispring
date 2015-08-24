package gov.ohio.jfs.oat.fn.cpi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

// The fields are populated from the property files, <Application Name>.properties
public class Application {
	// Field definitions for <Application>.properties file
	public static final String APPLICATION_NAME = "APPLICATION_NAME";
	public static final String EXTRACTOR_TYPE = "EXTRACTOR_TYPE";
	public static final String EXPORTER_TYPE = "EXPORTER_TYPE";
	public static final String OBJECT_STORE_NAME = "OBJECT_STORE_NAME";
	public static final String SOURCE_CLASS_NAME = "SOURCE_CLASS_NAME";
	public static final String DELETE_AFTER_LOG = "DELETE_AFTER_LOG";

	private String name;
	private String extractorType;
	private String exporterType;
	private String objectStoreName;
	private String sourceClassName;
	private boolean deleteAfterLog = false;

	private Properties properties = new Properties();

	private Application() {
	}

	public Application(String name) throws IOException {
		String propertyFileName = name + ".properties";
		try {
			properties.load(new FileInputStream(propertyFileName));
		} catch (IOException e) {
			throw e;
		}

		this.name = name;
		this.extractorType = properties.getProperty(EXTRACTOR_TYPE);
		this.exporterType = properties.getProperty(EXPORTER_TYPE);
		this.objectStoreName = properties.getProperty(OBJECT_STORE_NAME);
		this.sourceClassName = properties.getProperty(SOURCE_CLASS_NAME);
		this.deleteAfterLog = (properties.getProperty(DELETE_AFTER_LOG)
				.toUpperCase().equals("TRUE")) ? true : false;
	}

	public String getName() {
		return name;
	}

	public String getExtractorType() {
		return extractorType;
	}

	public String getExporterType() {
		return exporterType;
	}

	public String getObjectStoreName() {
		return objectStoreName;
	}

	public String getSourceClassName() {
		return sourceClassName;
	}

	public Properties getProperties() {
		return properties;
	}

	public boolean deleteAfterLog() {
		return this.deleteAfterLog;
	}
}
