package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public abstract class Extractor implements Extractable {
	private AppConfig application;
	
	public AppConfig getApplicationName() {
		return application;
	}

	private Extractor() {};
	public Extractor(AppConfig application) {
		this.application = application;
	}

	@Override
	abstract public ArrayList<CPILog> extract() throws Exception;

}
