package gov.ohio.jfs.fn.cpi;

import java.util.ArrayList;
/**
 * NewExtractable defines the operations to extract the CPILogs 
 * @author KWONS
 * 
 * NewExtractable can extract CPILogs upto Integer.MAX_VALUE.
 *
 */
public interface NewExtractable {
	public void setMax(Integer max);
	public Integer getMax();
	public ArrayList<CPILog> extract() throws Exception;
	public ArrayList<CPILog> extract(Integer max) throws Exception;
}
