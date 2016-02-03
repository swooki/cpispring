package gov.ohio.jfs.fn.cpi;

import java.security.GeneralSecurityException;

import gov.ohio.jfs.fn.cpi.test.TestExtractor;
import gov.ohio.jfs.fn.util.CryptoUtils;

public class ExtractorFactory {
	
	public static final String EXTRACTOR_TYPE_FILENET = "FILENET"; 
	public static final String EXTRACTOR_TYPE_TEST = "TEST";

	public static Extractable getExtractor(AppConfig appConfig){
		String extractorType = appConfig.getProperty(AppConfig.EXTRACTOR_TYPE);

		if(extractorType.equals(EXTRACTOR_TYPE_FILENET)) {
			FileNetExtractor fileNetExtractor = new FileNetExtractor();
			fileNetExtractor.setAction(appConfig.getProperty(AppConfig.ACTION));
			fileNetExtractor.setApplicationName(appConfig.getProperty(AppConfig.APPLICATION_NAME));
			fileNetExtractor.setCEURI(appConfig.getProperty(AppConfig.CE_URI));
			fileNetExtractor.setDeleteAfterLog(
					appConfig.getProperty(AppConfig.DELETE_AFTER_LOG).toUpperCase().equals(AppConfig.DELETE_AFTER_LOG_YES));
			fileNetExtractor.setMax(Integer.parseInt(appConfig.getProperty(AppConfig.MAX_NUM_EVENTS)));
			fileNetExtractor.setObjectStoreName(appConfig.getProperty(AppConfig.OBJECT_STORE_NAME));
			fileNetExtractor.setPassword(appConfig.getProperty(AppConfig.PASSWORD));
			fileNetExtractor.setPersonalIds(appConfig.getProperty(AppConfig.PERSONAL_IDS));
			fileNetExtractor.setSourceClassName(appConfig.getProperty(AppConfig.SOURCE_CLASS_NAME));
			fileNetExtractor.setStanzaName(appConfig.getProperty(AppConfig.JAAS_STANZA_NAME));
			fileNetExtractor.setUsername(appConfig.getProperty(AppConfig.USERID));
			
			return fileNetExtractor;
		} else {
			TestExtractor testExtractor = new TestExtractor();
			return testExtractor;
		}
	}

}
