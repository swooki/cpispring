package gov.ohio.jfs.fn.cpi.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gov.ohio.jfs.fn.cpi.CSVExporter;
import gov.ohio.jfs.fn.cpi.Exporter;

public class CSVExporterTest
{

	@Before
	public void setUpCSVExporter() throws Exception {
	}

	@Test
	public void testDefaultIsAppend() throws Exception {
		CSVExporter exporter = new CSVExporter();
		assertFalse(exporter.isAppend());
	}
	
	@Test
	public void testIsAppend() throws Exception {
		CSVExporter exporter = new CSVExporter();
		exporter.setAppend(true);
		assertTrue(exporter.isAppend());
	}
	
	@Test
	public void testDefaultDateFormat(){
		CSVExporter exporter = new CSVExporter();
		assertEquals(exporter.getDateFormat(),Exporter.DEFAULT_DATE_FORMAT);
	}

	@Test
	public void testDateFormat() throws Exception{
		CSVExporter exporter = new CSVExporter();
		exporter.setDateFormat("MM/DD/YYYYHH:mm:ss.SSS");
		assertEquals(exporter.getDateFormat(), "MM/DD/YYYYHH:mm:ss.SSS");
	}

	@Test
	public void testExportWithNoDateFormat() throws Exception{
		CSVExporter exporter = new CSVExporter();
		exporter.setOutputFileName("appName.csv");
		exporter.export(TestUtil.getCPILogList(100));
	}

	@Test
	public void testExportWithNoOutputFileName() throws Exception{
		CSVExporter exporter = new CSVExporter();
		exporter.setDateFormat("MM/DD/YYYYHH:mm:ss.SSS");
		exporter.export(TestUtil.getCPILogList(100));
	}

	@Test
	public void testExportWithNoParameter() throws Exception{
		CSVExporter exporter = new CSVExporter();
		exporter.setDateFormat("MM/DD/YYYYHH:mm:ss.SSS");
		exporter.setAppend(true);
		exporter.export(TestUtil.getCPILogList(100));
	}

	@Test
	public void testExportWithAllRequiredParameters() {
		CSVExporter exporter = new CSVExporter();
		exporter.setDateFormat("MM/DD/YYYYHH:mm:ss.SSS");
		exporter.setOutputFileName("appName.csv");
		exporter.export(TestUtil.getCPILogList(100));
	}
}
