package gov.ohio.jfs.oat.fn.cpi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ExtractCPILogs {

	private Application app;
	private static Logger logger = Logger.getLogger(ExtractCPILogs.class);
	private static Properties properties = new Properties();

	// expecting 3 parameters; application name;
	public ExtractCPILogs(Application app) {
		this.app = app;
	}
	
	public Application getApplication(){
		return this.app;
	}

	public static void main(String[] args) throws Exception {

		DOMConfigurator.configure("log4j.xml");
		

		// The name of property file for the target application should be passed
		// via argument
		if (args.length != 1) {
			throw new Exception(
					"\n\nUsage: ExtractAuditLog.java APPLICATION_NAME\n");
		}
		 String propertyFileName = args[0] + ".properties";
		try {
			properties.load(new FileInputStream(propertyFileName));
		} catch (IOException e) {
			throw new Exception("Couldn't find the configuration file: " + propertyFileName);
		}
		
		ExtractCPILogs ecl = new ExtractCPILogs(new Application(args[0]));
		Extractable extractor = ExtractorFactory.getExtractor(ecl.getApplication());
		ArrayList<CPILog> logs = extractor.extract();
		Exportable exporter = ExporterFactory.getExtractor(ecl.getApplication());
		exporter.export(logs);
	}

	void showLogs(ArrayList<CPILog> logs) {
		for (CPILog log : logs) {
			System.out.println(log.toString());
		}
	}
}
