package gov.ohio.jfs.cots.fn.cpi.test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import gov.ohio.jfs.cots.fn.cpi.AppConfig;
import gov.ohio.jfs.cots.fn.cpi.CPILog;
import gov.ohio.jfs.cots.fn.cpi.Extractable;

public class TestExtractor implements Extractable {
	private AppConfig appConfig = null;
	private int maxEventNum;

	private TestExtractor() {
	}

	public TestExtractor(AppConfig appConfig) {
		this.appConfig = appConfig;
		maxEventNum = Integer.parseInt(this.appConfig.getProperty(AppConfig.MAX_NUM_EVENTS));

	}

	public ArrayList<CPILog> extract() throws Exception {
		ArrayList<CPILog> logs = new ArrayList<CPILog>();

		for (int i = 0; i < maxEventNum; i++) {
			logs.add(getNewTestCPILog());
		}

		return logs;
	}

	public CPILog getNewTestCPILog() {
		Date dateAccessed = new Date();
		SecureRandom random = new SecureRandom();
		CPILog log = new CPILog("application", "action", dateAccessed, "new BigInteger(130, random).toString(32)",
				"targetAccessed", "userAccessed");

		return log;
	}

}