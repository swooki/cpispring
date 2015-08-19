package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public abstract class Extractor implements Extractable {
	private String applicationName;
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	private Extractor() {};
	public Extractor(String applicationName) {
		this.applicationName = applicationName;
	}

	@Override
	abstract public ArrayList<CPILog> extract();

}
