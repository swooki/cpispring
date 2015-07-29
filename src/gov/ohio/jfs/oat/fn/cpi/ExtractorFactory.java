package gov.ohio.jfs.oat.fn.cpi;

public class ExtractorFactory {
	
	public Extractable getExtractor(String type){
		Extractable extractor = null;
		if(type.equals("Test")) {
			extractor = new TestExtractor(); 
		}
		if(type.equals("FileNet")) {
			extractor = new FileNetExtractor(); 
		}
		if(type.equals("DB")) {
			extractor = new DBExtractor(); 
		}
		return extractor; 
	}
}
