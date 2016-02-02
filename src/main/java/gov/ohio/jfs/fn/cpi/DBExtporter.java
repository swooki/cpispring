package gov.ohio.jfs.fn.cpi;

import java.util.ArrayList;

public class DBExtporter extends Exporter implements Exportable {

	public DBExtporter() {
		super();
	}

	@Override
	public void export(ArrayList<CPILog> logs){
		System.out.println("DB Exporter is selected.");
	}

}
