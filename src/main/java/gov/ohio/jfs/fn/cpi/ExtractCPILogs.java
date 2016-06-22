package gov.ohio.jfs.fn.cpi;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import gov.ohio.jfs.fn.cpi.exception.NoApplicationConfigurationFileException;
import gov.ohio.jfs.fn.cpi.exception.NoApplicationNameException;

public class ExtractCPILogs {

	private AppConfig appConfig;
	private Extractable extractor;
	private Exportable exporter;
	private static Logger logger = Logger.getLogger(ExtractCPILogs.class);

	// expecting 3 parameters; application name;
	public ExtractCPILogs(AppConfig appConfig) {
		this.appConfig = appConfig;
	}

	public AppConfig getAppConfig() {
		return this.appConfig;
	}

	public void setExporter(Exportable exporter) {
		this.exporter = exporter;

	}

	public void setExtractor(Extractable extractor) {
		this.extractor = extractor;

	}

	public static void main(String[] args) {

		DOMConfigurator.configure("log4j.xml");

		// The name of application should be passed via argument
		if (args.length != 1) {
			logger.error("Usage: " + ExtractCPILogs.class.getSimpleName() + " <APPLICATION_NAME>");
			throw new NoApplicationNameException(
					"\n\nUsage: " + ExtractCPILogs.class.getSimpleName() + ".java <APPLICATION_NAME>\n");
		}
		logger.info("Application Name: " + args[0]);

		AppConfig config;
		try {
			config = new AppConfig(args[0]);
		} catch (IOException e) {
			throw new NoApplicationConfigurationFileException(
					"Couldn't find the configuration file for the application: " + args[0]);
		}

		ExtractCPILogs ecl = new ExtractCPILogs(config);
		ecl.setExtractor(ExtractorFactory.getExtractor(config));
		ecl.setExporter(ExporterFactory.getExporter(config));
		ecl.processLog();
	}

	public void processLog() {
		ArrayList<CPILog> logList = this.extractor.extract();
		logger.info(logList.size() + " logs are extracted.");

		if (logList.size() > 0) {
			exporter.export(logList);
			logger.info(logList.size() + " logs are exported.");
		}
	}

	void showLogs(ArrayList<CPILog> logs) {
		for (CPILog log : logs) {
			System.out.println(log.toString());
		}
	}

}
