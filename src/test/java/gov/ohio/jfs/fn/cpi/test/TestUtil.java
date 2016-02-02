package gov.ohio.jfs.fn.cpi.test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

import gov.ohio.jfs.fn.cpi.CPILog;

public class TestUtil {
	public static CPILog getNewTestCPILog() {
		
		Date dateAccessed = new Date();
		SecureRandom random = new SecureRandom();

		CPILog log = new CPILog( "action", "application",dateAccessed, 
				(new BigInteger(130, random)).toString(),
				"targetAccessed", "userAccessed");

		return log;
	}

	public static ArrayList<CPILog> getCPILogList(int max) {
		ArrayList<CPILog> logList = new ArrayList<CPILog>();
		for (int i = 0; i < max; i++) {
			logList.add(getNewTestCPILog());
		}
		return logList;
	}

}
