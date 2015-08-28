package gov.ohio.jfs.oat.fn.cpi;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CSVExporter extends Exporter implements Exportable {

	private Properties props = null;
	private static Logger logger = Logger.getLogger(Object.class);

	public CSVExporter(ApplicationConfig app) {
		super(app);

		props = new Properties();
		try {
			props.load(new FileInputStream(app.getName() + ".properties"));
		} catch (IOException e) {
			System.out.println("Couldn't find the configuration file: "
					+ app.getName() + ".properties");
		}
	}

	@Override
	public void export(ArrayList<CPILog> logs) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(
				props.getProperty("DATE_FORMAT"));

		boolean isAppend = (props.getProperty("MODE").equals("APPEND")) ? true
				: false;

		// Open output file
		FileWriter writer = new FileWriter(this.getApplication().getName()
				+ ".csv", isAppend);

		for (CPILog log : logs) {
			writer.append(log.getApplication() + ',');
			writer.append(log.getUserAccessed() + ',');
			writer.append(formatter.format(log.getDateAccessed()) + ',');
			writer.append(log.getPersonalId().replace(',', '.') + ',');
			writer.append(formatter.format(log.getDateCreated()) + ',');
			writer.append(log.getDocumentAccessed() + '\n');
			writer.flush();

			logger.info(log.getApplication() + ',' + log.getUserAccessed()
					+ ',' + formatter.format(log.getDateAccessed()) + ','
					+ log.getPersonalId().replace(',', '.') + ','
					+ formatter.format(log.getDateCreated()) + ','
					+ log.getDocumentAccessed() + '\n');
		}
		writer.close();
	}
}
