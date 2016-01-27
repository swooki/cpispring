package gov.ohio.jfs.cots.fn.cpi.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import gov.ohio.jfs.cots.fn.cpi.CPILog;

public class CPILogTest {

	@Test
	public void testCreateCPILog() {
		Date dateAccessed = new Date();
		CPILog log = new CPILog("action","application", 
				dateAccessed, "personalId", "targetAccessed", "userAccessed");
		
		assertEquals(log.getAction(), "action");
		assertEquals(log.getApplication(), "application");
		assertEquals(log.getDateAccessed(), dateAccessed);
		assertEquals(log.getPersonalId(), "personalId");
		assertEquals(log.getTargetAccessed(), "targetAccessed");
	}
}