package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;


public class ExtractCPILogs {
	
	ExtractorFactory factory;

	public ExtractCPILogs(ExtractorFactory factory) {
		super();
		this.factory = factory;
	}
	
	public static void main(String[] args) {
		ExtractCPILogs ecl = new ExtractCPILogs(new ExtractorFactory());
		ArrayList<CPILog> logs = ecl.factory.getExtractor("Test").extract();
		ecl.showLogs(logs);
	}
	
	void showLogs(ArrayList<CPILog> logs) {
		for(CPILog log : logs) {
			System.out.println(log.toString());
		}
	}
}
