package gov.ohio.jfs.oat.fn.cpi;

public class ExtractorFactory {
	
	public static final String EXTRACTOR_TYPE_FILENET = "FILENET"; 
	public static final String EXTRACTOR_TYPE_DATABASE = "DB";
	public static final String EXTRACTOR_TYPE_TEST = "TEST";

	public static Extractable getExtractor(Application app){
		Extractable extractor = null;
		if(app.getExtractorType().equals(EXTRACTOR_TYPE_TEST)) {
			extractor = new TestExtractor(app); 
		}
		if(app.getExtractorType().equals(EXTRACTOR_TYPE_FILENET)) {
			extractor = new FileNetExtractor(app); 
		}
		if(app.getExtractorType().equals(EXTRACTOR_TYPE_DATABASE)) {
			extractor = new DBExtractor(app); 
		}
		return extractor; 
	}
}
