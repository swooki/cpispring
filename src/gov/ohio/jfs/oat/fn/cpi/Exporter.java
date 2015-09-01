package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public abstract class Exporter implements Exportable {
	private AppConfig appConfig;

	private Exporter() {};
	
	public Exporter(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	public AppConfig getApplication() {
		return appConfig;
	}

	public void setApplication(AppConfig app) {
		this.appConfig = app;
	}
	
	@Override
	abstract public void export(ArrayList<CPILog> logs) throws Exception;
}
