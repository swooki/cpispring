package gov.ohio.jfs.fn.cpi;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CSVExporter extends Exporter implements Exportable {

	private static Logger logger = Logger.getLogger(CSVExporter.class);
	private boolean isAppend;
	private String outputFileName;
	
	public void setAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}

	public boolean isAppend() {
		return this.isAppend;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;

	}

	public CSVExporter() {
		super();
	}
	
	@Override
	public void export(ArrayList<CPILog> logs){
		logger.info("CSV Exporter is selected.");
		SimpleDateFormat formatter = new SimpleDateFormat(this.getDateFormat());

		logger.info("Date Format:" + this.getDateFormat());
		logger.info("Is Append:" + this.isAppend());

		// Open output file
		FileWriter writer = null;
		try {
			writer = new FileWriter(this.outputFileName, this.isAppend);

			for (CPILog log : logs) {
				writer.append(log.getAction() + ',');
				writer.append(log.getApplication() + ',');
				writer.append(formatter.format(log.getDateAccessed()) + ',');
				writer.append(log.getPersonalId().replace(',', '.') + ',');
				writer.append(log.getTargetAccessed() + ',');
				writer.append(log.getUserAccessed() + '\n');

				logger.debug(log.getAction() + ',' + log.getApplication() + ','
						+ formatter.format(log.getDateAccessed()) + ',' + log.getPersonalId() + ','
						+ log.getTargetAccessed() + ',' + log.getUserAccessed());
			}
			writer.flush();

		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
