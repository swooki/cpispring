package gov.ohio.jfs.cots.fn.cpi.test;

import java.util.ArrayList;

import org.junit.Test;

import gov.ohio.jfs.cots.fn.cpi.AppConfig;
import gov.ohio.jfs.cots.fn.cpi.CPILog;
import gov.ohio.jfs.cots.fn.cpi.Exportable;
import gov.ohio.jfs.cots.fn.cpi.ExporterFactory;
import gov.ohio.jfs.cots.fn.cpi.ExtractCPILogs;
import gov.ohio.jfs.cots.fn.cpi.Extractable;
import gov.ohio.jfs.cots.fn.cpi.ExtractorFactory;

public class ExtractCPILogsTest {

	@Test
	public void testExtractCPILogs() {
		try {
			ExtractCPILogs ecl = new ExtractCPILogs(new AppConfig("FN_UIDMS"));
			Extractable extractor = ExtractorFactory.getExtractor(ecl.getAppConfig());
			ArrayList<CPILog> logs = extractor.extract();
			Exportable exporter = ExporterFactory.getExtractor(ecl.getAppConfig());
			exporter.export(logs);
		} catch (Exception e) {

		}
	}
}
