package gov.ohio.jfs.oat.fn.cpi;

public class ExtractorFactory {
	
	public static final String EXTRACTOR_TYPE_FILENET = "filenet"; 
	public static final String EXTRACTOR_TYPE_DATABASE = "db";
	public static final String EXTRACTOR_TYPE_TEST = "test";

	public static Extractable getExtractor(String type){
		Extractable extractor = null;
		if(type.equals(EXTRACTOR_TYPE_TEST)) {
			extractor = new TestExtractor(); 
		}
		if(type.equals(EXTRACTOR_TYPE_FILENET)) {
			extractor = new FileNetExtractor(); 
		}
		if(type.equals(EXTRACTOR_TYPE_DATABASE)) {
			extractor = new DBExtractor(); 
		}
		return extractor; 
	}
}
