package gov.ohio.jfs.oat.fn.cpi;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CSVExporter extends Exporter implements Exportable {

	private Application app = null;
	private Properties props = null;
	private static Logger logger = Logger.getLogger(Object.class);

	public CSVExporter() {};
	public CSVExporter(Application app) {
		this.app = app;
		props = new Properties();
		String propertyFileName = app.getName() + ".properties";
		try {
			props.load(new FileInputStream(propertyFileName));
		} catch (IOException e) {
			System.out.println("Couldn't find the configuration file: "
					+ propertyFileName);
		}
	}

	@Override
	public void export(ArrayList<CPILog> logs) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				props.getProperty("SIMPLE_DATE_FORMAT"));
		for (CPILog log : logs) {
			logger.info(log.getApplication() + "," + log.getAction() 
					+ "," + log.getDocumentAccessed() + ","
					+ log.getPersonalId() + "," + log.getUserAccessed() + ","
					+ formatter.format(log.getDateAccessed()) + ","
					+ formatter.format(log.getDateCreated()));
		}
		logger.info(logs.size() + " have been exported.");
	}
}
