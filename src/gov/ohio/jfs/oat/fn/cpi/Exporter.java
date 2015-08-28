package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public abstract class Exporter implements Exportable {
	private AppConfig app;

	private Exporter() {};
	
	public Exporter(AppConfig app) {
		this.app = app;
	}
	
	public AppConfig getApplication() {
		return app;
	}

	public void setApplication(AppConfig app) {
		this.app = app;
	}
	
	@Override
	abstract public void export(ArrayList<CPILog> logs) throws Exception;
}
