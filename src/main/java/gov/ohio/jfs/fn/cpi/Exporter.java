package gov.ohio.jfs.fn.cpi;

import java.util.ArrayList;

public abstract class Exporter implements Exportable {

	public static final String DEFAULT_DATE_FORMAT =  "MM/dd/yyyy HH:mm:ss";
	String dateFormat;

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDateFormat(){
		return this.dateFormat;
	}
	
	public Exporter() {
		this.dateFormat = Exporter.DEFAULT_DATE_FORMAT;
	};
	
	abstract public void export(ArrayList<CPILog> logs);
}
