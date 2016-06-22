package gov.ohio.jfs.fn.cpi.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import gov.ohio.jfs.fn.cpi.CPILog;

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
		assertEquals(log.getUserAccessed(), "userAccessed");
	}
}