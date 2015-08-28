package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public class DBExtractor implements Extractable {
	private ApplicationConfig app;
	
	private DBExtractor() {
		
	}
	
	public DBExtractor(ApplicationConfig app) {
		this.app = app;
	}

	@Override
	public ArrayList<CPILog> extract() {
		// TODO Auto-generated method stub
		return null;
	}
}
