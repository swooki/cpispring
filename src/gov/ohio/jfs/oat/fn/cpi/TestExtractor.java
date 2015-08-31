package gov.ohio.jfs.oat.fn.cpi;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestExtractor implements Extractable {
	private AppConfig appConfig = null;
	private int maxEventNum;

	private TestExtractor() {
	}

	public TestExtractor(AppConfig appConfig) {
		this.appConfig = appConfig;
		maxEventNum = Integer.parseInt(this.appConfig
				.getProperty(AppConfig.MAX_NUM_EVENTS));

	}

	@Override
	public ArrayList<CPILog> extract() throws Exception {
		ArrayList<CPILog> logs = new ArrayList<CPILog>();

		for (int i = 0; i < maxEventNum; i++) {
			logs.add(getNewCPILog());
		}

		return logs;
	}

	private CPILog getNewCPILog() {
		  SecureRandom random = new SecureRandom();

		SimpleDateFormat dummyId = new SimpleDateFormat("mmddyyyyHHMMSSS");

		CPILog log = new CPILog();
		log.setAction("Retrieve");
		log.setApplication(this.appConfig.getName());
		log.setDateAccessed(new Date());
		log.setDateCreated(new Date());
		log.setDocumentAccessed("DOC");
		log.setPersonalId(new BigInteger(130, random).toString(32));
		log.setUserAccessed("KWONS");

		return log;
	}
}