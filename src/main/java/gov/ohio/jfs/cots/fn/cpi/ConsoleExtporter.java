package gov.ohio.jfs.cots.fn.cpi;

import java.util.ArrayList;

public class ConsoleExtporter extends Exporter implements Exportable {
	
	public ConsoleExtporter(AppConfig app) {
		super(app);
	}
	@Override
	public void export(ArrayList<CPILog> logs) {
		for (CPILog log : logs)
			System.out.println(log.toString());
	}

}
