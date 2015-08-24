package gov.ohio.jfs.oat.fn.cpi;

public class FileNetAppInfoFactory {

	public static FileNetAppInfo getFileNetAppInfo(String applicationName){
		FileNetAppInfo appInfo = null;
		if(applicationName.equals("FN_REDET")) {
			appInfo = new FileNetREDETInfo(); 
		}
		if(applicationName.equals("FN_UCBPC")) {
			appInfo = new FileNetUCBPCInfo(); 
		}
		if(applicationName.equals("FN_AARW")) {
			appInfo = new FileNetAARWInfo(); 
		}
		if(applicationName.equals("FN_ICC")) {
			appInfo = new FileNetICCInfo(); 
		}
		return appInfo; 
	}

}
