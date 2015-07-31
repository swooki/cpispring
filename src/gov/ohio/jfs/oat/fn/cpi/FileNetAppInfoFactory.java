package gov.ohio.jfs.oat.fn.cpi;

public class FileNetAppInfoFactory {

	public static FileNetAppInfo getFileNetAppInfo(String type){
		FileNetAppInfo appInfo = null;
		if(type.equals("FN_REDET")) {
			appInfo = new FileNetREDETInfo(); 
		}
		if(type.equals("FN_UCBPC")) {
			appInfo = new FileNetUCBPCInfo(); 
		}
		return appInfo; 
	}

}
