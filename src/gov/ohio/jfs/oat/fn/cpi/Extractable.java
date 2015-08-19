package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public interface Extractable {
	public ArrayList<CPILog> extract() throws Exception;
}
