package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public abstract class Exporter implements Exportable {
	private Application app;
	
	public Application getApplication() {
		return app;
	}

	public void setApplication(Application app) {
		this.app = app;
	}
	
	@Override
	abstract public void export(ArrayList<CPILog> logs) throws Exception;
}
