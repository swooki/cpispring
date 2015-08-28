package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public abstract class Exporter implements Exportable {
	private ApplicationConfig app;

	private Exporter() {};
	
	public Exporter(ApplicationConfig app) {
		this.app = app;
	}
	
	public ApplicationConfig getApplication() {
		return app;
	}

	public void setApplication(ApplicationConfig app) {
		this.app = app;
	}
	
	@Override
	abstract public void export(ArrayList<CPILog> logs) throws Exception;
}
