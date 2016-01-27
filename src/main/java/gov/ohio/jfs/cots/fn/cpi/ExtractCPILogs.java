package gov.ohio.jfs.cots.fn.cpi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ExtractCPILogs {

	private AppConfig app;
	private static Logger logger = Logger.getLogger(ExtractCPILogs.class);
	private static Properties properties = new Properties();

	// expecting 3 parameters; application name;
	public ExtractCPILogs(AppConfig app) {
		this.app = app;
	}

	public AppConfig getAppConfig() {
		return this.app;
	}

	public static void main(String[] args) throws Exception {

		DOMConfigurator.configure("log4j.xml");

		// The name of property file for the target application should be passed
		// via argument
		if (args.length != 1) {
			logger.error("Usage: ExtractAuditLog.java <APPLICATION_NAME>");
			throw new Exception(
					"\n\nUsage: ExtractAuditLog.java <APPLICATION_NAME>\n");
		}
		logger.info("Application Name: " + args[0]);
		
		String propertyFileName = args[0] + ".properties";
		logger.debug(propertyFileName);
		try {
			properties.load(new FileInputStream(propertyFileName));
		} catch (IOException e) {
			throw new Exception("Couldn't find the configuration file for the application: "
					+ propertyFileName);
		}
		logger.info("Application Property File: " + propertyFileName);

		AppConfig config = new AppConfig(args[0]);
		ExtractCPILogs ecl = new ExtractCPILogs(new AppConfig(args[0]));
		Extractable extractor = ExtractorFactory.getExtractor(ecl.getAppConfig());
		ArrayList<CPILog> logs = extractor.extract();
		logger.info(logs.size() + " logs are extracted.");
		
		if (logs.size() > 0) {
			Exportable exporter = ExporterFactory.getExtractor(ecl
					.getAppConfig());
			exporter.export(logs);
			logger.info(logs.size() + " logs are exported.");
		}
	}

	void showLogs(ArrayList<CPILog> logs) {
		for (CPILog log : logs) {
			System.out.println(log.toString());
		}
	}
}
