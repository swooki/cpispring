package gov.ohio.jfs.fn.cpi.test;

import java.util.ArrayList;

import org.junit.Test;

import gov.ohio.jfs.fn.cpi.AppConfig;
import gov.ohio.jfs.fn.cpi.CPILog;
import gov.ohio.jfs.fn.cpi.Exportable;
import gov.ohio.jfs.fn.cpi.ExporterFactory;
import gov.ohio.jfs.fn.cpi.ExtractCPILogs;
import gov.ohio.jfs.fn.cpi.Extractable;
import gov.ohio.jfs.fn.cpi.ExtractorFactory;

public class ExtractCPILogsTest {

	@Test
	public void testCreateExtractCPILogs() throws Exception {
		AppConfig appConfig = new AppConfig("FN_UIDMS");
		
		ExtractCPILogs ecl = new ExtractCPILogs(appConfig);

		Extractable extractor = ExtractorFactory.getExtractor(appConfig);
		ecl.setExtractor(extractor);

		Exportable exporter = ExporterFactory.getExporter(appConfig);
		ecl.setExporter(exporter);

		ecl.processLog();
	}

	@Test
	public void testExtractCPILogs() {
		try {
			AppConfig appConfig = new AppConfig("FN_UIDMS");

			ExtractCPILogs ecl = new ExtractCPILogs(appConfig);
			Extractable extractor = ExtractorFactory.getExtractor(ecl.getAppConfig());
			ArrayList<CPILog> logs = extractor.extract();
			Exportable exporter = ExporterFactory.getExporter(ecl.getAppConfig());
			exporter.export(logs);
		} catch (Exception e) {

		}
	}

}