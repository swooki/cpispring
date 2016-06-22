package gov.ohio.jfs.fn.cpi.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import gov.ohio.jfs.fn.cpi.AppConfig;

public class AppConfigTest {

	@Test
	public void testCreation() throws IOException{
		AppConfig appConfig = new AppConfig("FN_UIDMS");
		assertEquals(appConfig.getApplicationName(), "FN_UIDMS");
	}
	
	@Test 
	public void testGetProperty()  throws IOException{
		AppConfig appConfig = new AppConfig("FN_UIDMS");
		assertEquals(appConfig.getProperty(appConfig.getApplicationName()), "FN_UIDMS");
	}
}
