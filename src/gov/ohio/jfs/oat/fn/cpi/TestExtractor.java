package gov.ohio.jfs.oat.fn.cpi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestExtractor implements Extractable {

	@Override
	public ArrayList<CPILog> extract()  {
		ArrayList<CPILog> logs = new ArrayList<CPILog>();
		
		logs.add( getNewCPILog());
		logs.add( getNewCPILog());
		logs.add( getNewCPILog());
		logs.add( getNewCPILog());

		return logs;
	}

	private static CPILog getNewCPILog() {
		SimpleDateFormat formatter = new SimpleDateFormat("mmddyyyyHHMMSSS");
		
		CPILog log = new CPILog();
		log.setAction("Retrieve");
		log.setApplication("FN_REDET");
		log.setDateAccessed(new Date());
		log.setDateCreated(new Date());
		log.setDocumentAccessed("DOC");
		log.setPersonalId( formatter.format(new Date()));
		log.setUserAccessed("KWONS");

		return log;
	}
}
