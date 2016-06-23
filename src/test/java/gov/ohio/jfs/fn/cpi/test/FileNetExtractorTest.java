package gov.ohio.jfs.fn.cpi.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import gov.ohio.jfs.fn.cpi.CPILog;
import gov.ohio.jfs.fn.cpi.FileNetExtractor;
import gov.ohio.jfs.fn.util.AESEncrypter;

public class FileNetExtractorTest {

	@Test
	public void testFileNetExtractorParameters() {
		FileNetExtractor extractor = new FileNetExtractor("CEURI","FileNet-P8Admin","0BE3290D72526431D19F49324FD7CEDF"
				,"FileNetP8WSI", "UC_DEV_OS");
		assertEquals(extractor.getCEURI(),"CEURI");
		assertEquals(extractor.getUsername(), "FileNet-P8Admin");
		assertEquals(extractor.getPassword(), "0BE3290D72526431D19F49324FD7CEDF");
		assertEquals(extractor.getStanzaName(), "FileNetP8WSI");
		assertEquals(extractor.getObjectStoreName(),"UC_DEV_OS");

		extractor.setAction("action");
		assertEquals(extractor.getAction(), "action");
		
		extractor.setApplicationName("application name");
		assertEquals(extractor.getApplicationName(),"application name");
		
		
		extractor.setDeleteAfterLog(true);
		assertTrue(extractor.getDeleteAfterLog());
		
		extractor.setMax(100);
		assertEquals(extractor.getMax(), 100);
		
		extractor.setPersonalIds("LAST_NAME,FIRST_NAME,SSN");
		assertEquals(extractor.getPersonalIds(), "LAST_NAME,FIRST_NAME,SSN");
		
		extractor.setSourceClassName("UIDocument");
		assertEquals(extractor.getSourceClassName(),"UIDocument");
	}

	private void initializeFileNetExtractor(FileNetExtractor extractor){
		extractor.setAction("action");
		extractor.setApplicationName("FN_UIDMS");
		extractor.setDeleteAfterLog(true);
		extractor.setMax(100);
		extractor.setPersonalIds("LAST_NAME,FIRST_NAME,SSN");
		extractor.setSourceClassName("UIDocument");
	}
	@Test
	public void testValidateFileNetExtractor() {
		FileNetExtractor extractor = new FileNetExtractor("CEURI","FileNet-P8Admin","0BE3290D72526431D19F49324FD7CEDF"
				,"FileNetP8WSI", "UC_DEV_OS");
		initializeFileNetExtractor(extractor);
	}

	
	@Test
	public void testExtract() {
		FileNetExtractor extractor = new FileNetExtractor("CEURI","FileNet-P8Admin","0BE3290D72526431D19F49324FD7CEDF"
				,"FileNetP8WSI", "UC_DEV_OS");

		extractor.setMax(100);
		
		initializeFileNetExtractor(extractor);
		ArrayList<CPILog> logList = extractor.extract();
		
		assertTrue(logList.size() >= 0 && logList.size() <= 100);
		
	}
}
