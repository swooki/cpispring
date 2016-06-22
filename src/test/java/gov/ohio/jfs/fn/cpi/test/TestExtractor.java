package gov.ohio.jfs.fn.cpi.test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

import gov.ohio.jfs.fn.cpi.CPILog;
import gov.ohio.jfs.fn.cpi.Extractable;

public class TestExtractor implements Extractable {
	private int maxLogNum;

	public TestExtractor() {
		super();
	}

	public void setMaxEventNum(int maxLogNum) {
		this.maxLogNum = maxLogNum;
	}
	
	public int getMaxEventNum() {
		return this.maxLogNum;
	}

	public ArrayList<CPILog> extract(){
		ArrayList<CPILog> logs = new ArrayList<CPILog>();

		for (int i = 0; i < maxLogNum; i++) {
			logs.add(getNewTestCPILog());
		}

		return logs;
	}

	public CPILog getNewTestCPILog() {
		Date dateAccessed = new Date();
		SecureRandom random = new SecureRandom();
		CPILog log = new CPILog("application", "action", dateAccessed, new BigInteger(130, random).toString(32),
				"targetAccessed", "userAccessed");

		return log;
	}

}