package gov.ohio.jfs.fn.cpi.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import gov.ohio.jfs.fn.cpi.CPILog;
import gov.ohio.jfs.fn.cpi.FileNetExtractor;
import gov.ohio.jfs.fn.util.CryptoUtils;

public class FileNetExtractorTest {

	@Test
	public void testCreateFileNetExtractor() {
		FileNetExtractor extractor = new FileNetExtractor();
	}
	
	@Test
	public void testFileNetExtractorParameters() {
		FileNetExtractor extractor = new FileNetExtractor();
		extractor.setActions("actions");
		assertEquals(extractor.getActions(), "actions");
		
		extractor.setApplicationName("application name");
		assertEquals(extractor.getApplicationName(),"application name");
		
		extractor.setCEURI("CEURI");
		assertEquals(extractor.getCEURI(),"CEURI");
		
		extractor.setDeleteAfterLog(true);
		assertTrue(extractor.getDeleteAfterLog());
		
		extractor.setMax(100);
		assertEquals(extractor.getMax(), 100);
		
		extractor.setObjectStoreName("UC_DEV_OS");
		assertEquals(extractor.getObjectStoreName(),"UC_DEV_OS");
		
		extractor.setPassword("0BE3290D72526431D19F49324FD7CEDF");
		assertEquals(extractor.getPassword(), "0BE3290D72526431D19F49324FD7CEDF");
		
		extractor.setPersonalIds("LAST_NAME,FIRST_NAME,SSN");
		assertEquals(extractor.getPersonalIds(), "LAST_NAME,FIRST_NAME,SSN");
		
		extractor.setSourceClassName("UIDocument");
		assertEquals(extractor.getSourceClassName(),"UIDocument");
		
		extractor.setStanzaName("FileNetP8WSI");
		assertEquals(extractor.getStanzaName(), "FileNetP8WSI");

		extractor.setUsername("FileNet-P8Admin");
		assertEquals(extractor.getUsername(), "FileNet-P8Admin");
	}

	private void initializeFileNetExtractor(FileNetExtractor extractor){
		extractor.setActions("actions");
		extractor.setApplicationName("FN_UIDMS");
		extractor.setCEURI("http://ax-svc-03202.odjfs.state.oh.us:9080/wsi/FNCEWS40MTOM/");
		extractor.setDeleteAfterLog(true);
		extractor.setMax(100);
		extractor.setObjectStoreName("UC_DEV_OS");
		extractor.setPassword("0BE3290D72526431D19F49324FD7CEDF");
		extractor.setPersonalIds("LAST_NAME,FIRST_NAME,SSN");
		extractor.setSourceClassName("UIDocument");
		extractor.setStanzaName("FileNetP8WSI");
		extractor.setUsername("FileNet-P8Admin");
	}
	@Test
	public void testValidateFileNetExtractor() {
		FileNetExtractor extractor = new FileNetExtractor();
		initializeFileNetExtractor(extractor);
		assertTrue(extractor.validateParameters());
	}

	
	@Test
	public void testExtract() {
		FileNetExtractor extractor = new FileNetExtractor();

		CryptoUtils cryptor = new CryptoUtils();
		extractor.setCryptor(cryptor);
		
		extractor.setMax(100);
		
		initializeFileNetExtractor(extractor);
		ArrayList<CPILog> logList = extractor.extract();
		
		assertTrue(logList.size() >= 0 && logList.size() <= 100);
		
	}
}
