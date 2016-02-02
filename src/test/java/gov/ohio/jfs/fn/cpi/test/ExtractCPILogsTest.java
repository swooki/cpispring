package gov.ohio.jfs.fn.cpi.test;

import java.util.ArrayList;

import org.junit.Test;

import gov.ohio.jfs.fn.cpi.AppConfig;
import gov.ohio.jfs.fn.cpi.CPILog;
import gov.ohio.jfs.fn.cpi.Exportable;
import gov.ohio.jfs.fn.cpi.Exporter;
import gov.ohio.jfs.fn.cpi.ExporterFactory;
import gov.ohio.jfs.fn.cpi.ExtractCPILogs;
import gov.ohio.jfs.fn.cpi.Extractable;
import gov.ohio.jfs.fn.cpi.ExtractorFactory;

public class ExtractCPILogsTest {

	@Test
	public void testCreateExtractCPILogs() {
		AppConfig appConfig = new AppConfig("FN_UIDMS");
		
		ExtractCPILogs ecl = new ExtractCPILogs(appConfig);
		Extractable extractor = ExtractorFactory.getExtractor(appConfig);
		ecl.setExtractor(extractor);

		Exporter exporter = ExporterFactory.getExporter(appConfig));
		
	}

	@Test
	public void testExtractCPILogs() {
		try {
			AppConfig appConfig = new AppConfig("FN_UIDMS");

			ExtractCPILogs ecl = new ExtractCPILogs(appConfig);
			Extractable extractor = ExtractorFactory.getExtractor(ecl.getAppConfig());
			ArrayList<CPILog> logs = extractor.extract();
			Exportable exporter = ExporterFactory.getExtractor(ecl.getAppConfig());
			exporter.export(logs);
		} catch (Exception e) {

		}
	}

}