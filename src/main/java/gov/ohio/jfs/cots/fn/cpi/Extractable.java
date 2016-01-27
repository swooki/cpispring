package gov.ohio.jfs.cots.fn.cpi;

import java.util.ArrayList;

public interface Extractable {
	public ArrayList<CPILog> extract() throws Exception;
}
