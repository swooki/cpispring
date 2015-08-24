package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public class ConsoleExtporter extends Exporter implements Exportable {
	
	public ConsoleExtporter(Application app) {
		super(app);
	}
	@Override
	public void export(ArrayList<CPILog> logs) {
		for (CPILog log : logs)
			System.out.println(log.toString());
	}

}
