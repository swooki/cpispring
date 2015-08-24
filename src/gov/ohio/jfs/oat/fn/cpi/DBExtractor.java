package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public class DBExtractor implements Extractable {
	private Application app;
	
	private DBExtractor() {
		
	}
	
	public DBExtractor(Application app) {
		this.app = app;
	}

	@Override
	public ArrayList<CPILog> extract() {
		// TODO Auto-generated method stub
		return null;
	}
}
