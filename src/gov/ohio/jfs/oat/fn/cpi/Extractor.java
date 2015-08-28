package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public abstract class Extractor implements Extractable {
	private ApplicationConfig application;
	
	public ApplicationConfig getApplicationName() {
		return application;
	}

	private Extractor() {};
	public Extractor(ApplicationConfig application) {
		this.application = application;
	}

	@Override
	abstract public ArrayList<CPILog> extract() throws Exception;

}
