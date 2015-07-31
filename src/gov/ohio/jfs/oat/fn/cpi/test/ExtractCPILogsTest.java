package gov.ohio.jfs.oat.fn.cpi.test;

import static org.junit.Assert.*;
import gov.ohio.jfs.oat.fn.cpi.CPILog;
import gov.ohio.jfs.oat.fn.cpi.ExporterFactory;
import gov.ohio.jfs.oat.fn.cpi.ExtractCPILogs;
import gov.ohio.jfs.oat.fn.cpi.ExtractorFactory;

import java.util.ArrayList;

import org.junit.Test;

public class ExtractCPILogsTest {

	@Test
	public void testExtractCPILogs() {
		ExtractCPILogs ecl = new ExtractCPILogs(
				ExtractorFactory.EXTRACTOR_TYPE_FILENET,
				ExporterFactory.EXPORTER_TYPE_CSV);
		ArrayList<CPILog> logs = ecl.getExtractor().extract();
		ecl.getExporter().export(logs);
	}
}
