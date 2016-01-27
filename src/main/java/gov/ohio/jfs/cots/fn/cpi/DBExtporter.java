package gov.ohio.jfs.cots.fn.cpi;

import java.util.ArrayList;

public class DBExtporter extends Exporter implements Exportable {
	// TODO Database exporter needs to be implemented.

	public DBExtporter(AppConfig app) {
		super(app);
	}

	@Override
	public void export(ArrayList<CPILog> logs) throws Exception {
		System.out.println("DB Exporter is selected.");

	}

}
