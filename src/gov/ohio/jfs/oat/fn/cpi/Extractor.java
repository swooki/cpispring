package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public abstract class Extractor implements Extractable {
	private Application application;
	
	public Application getApplicationName() {
		return application;
	}

	private Extractor() {};
	public Extractor(Application application) {
		this.application = application;
	}

	@Override
	abstract public ArrayList<CPILog> extract() throws Exception;

}
