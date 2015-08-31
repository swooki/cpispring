package gov.ohio.jfs.ois.fn.util;

import static org.junit.Assert.*;

import java.security.GeneralSecurityException;

import gov.ohio.jfs.oat.fn.cpi.FileNetExtractor;

import org.apache.log4j.Logger;
import org.junit.Test;

public class CryptoUtilsTest {
	private static Logger logger = Logger.getLogger(CryptoUtilsTest.class);

	@Test
	public void testEncrypt() throws GeneralSecurityException {
		// Use the following code to generate an encrypted password.
		String plainText = "filenet";
		System.out.println(plainText);
		 		    
		String encryptedPassword = CryptoUtils.encrypt(plainText);
		System.out.println(encryptedPassword);
			 		
		System.out.println(CryptoUtils.decrypt(encryptedPassword));

		assertEquals(plainText,CryptoUtils.decrypt(encryptedPassword));
	}

}
