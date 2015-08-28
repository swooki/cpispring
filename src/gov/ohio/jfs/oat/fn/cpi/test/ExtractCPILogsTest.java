package gov.ohio.jfs.oat.fn.cpi.test;

import static org.junit.Assert.*;
import gov.ohio.jfs.oat.fn.cpi.AppConfig;
import gov.ohio.jfs.oat.fn.cpi.CPILog;
import gov.ohio.jfs.oat.fn.cpi.Exportable;
import gov.ohio.jfs.oat.fn.cpi.ExporterFactory;
import gov.ohio.jfs.oat.fn.cpi.ExtractCPILogs;
import gov.ohio.jfs.oat.fn.cpi.Extractable;
import gov.ohio.jfs.oat.fn.cpi.ExtractorFactory;

import java.util.ArrayList;

import org.junit.Test;

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
