package gov.ohio.jfs.oat.fn.cpi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestExtractor implements Extractable {
	private ApplicationConfig app = null;

	private TestExtractor() {
	}

	public TestExtractor(ApplicationConfig app) {
		this.app = app;
	}

	@Override
	public ArrayList<CPILog> extract() throws Exception {
		ArrayList<CPILog> logs = new ArrayList<CPILog>();

		logs.add(getNewCPILog());
		logs.add(getNewCPILog());
		logs.add(getNewCPILog());
		logs.add(getNewCPILog());

		return logs;
	}

	private CPILog getNewCPILog() {
		SimpleDateFormat formatter = new SimpleDateFormat("mmddyyyyHHMMSSS");

		CPILog log = new CPILog();
		log.setAction("Retrieve");
		log.setApplication(this.app.getName());
		log.setDateAccessed(new Date());
		log.setDateCreated(new Date());
		log.setDocumentAccessed("DOC");
		log.setPersonalId(formatter.format(new Date()));
		log.setUserAccessed("KWONS");

		return log;
	}
}