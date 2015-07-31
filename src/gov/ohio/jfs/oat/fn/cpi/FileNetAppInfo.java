package gov.ohio.jfs.oat.fn.cpi;

import com.filenet.api.core.Domain;
import com.filenet.api.core.ObjectStore;

public interface FileNetAppInfo {
	public void getTargetInfo(Domain dom, ObjectStore os, CPILog log);
}
