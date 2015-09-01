package gov.ohio.jfs.oat.fn.cpi;

public class ExtractorFactory {
	
	public static final String EXTRACTOR_TYPE_FILENET = "FILENET"; 
	public static final String EXTRACTOR_TYPE_TEST = "TEST";

	public static Extractable getExtractor(AppConfig app){
		Extractable extractor = null;
		String extractorType = app.getProperty(AppConfig.EXTRACTOR_TYPE);
		
		if(extractorType.equals(EXTRACTOR_TYPE_TEST)) {
			extractor = new TestExtractor(app); 
		}
		if(extractorType.equals(EXTRACTOR_TYPE_FILENET)) {
			extractor = new FileNetExtractor(app); 
		}
		return extractor; 
	}
}
