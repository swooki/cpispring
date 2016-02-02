package gov.ohio.jfs.fn.cpi;

import java.util.ArrayList;

public abstract class Extractor implements Extractable {
	protected int maxRecords;

	public void setMax(int max) {
		this.maxRecords = max;
	}
	
	public int getMax(){
		return this.maxRecords;
	}
	
	protected Extractor() {};
	abstract public ArrayList<CPILog> extract();
 
}
