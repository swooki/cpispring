package gov.ohio.jfs.cots.fn.cpi.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;
import org.junit.Test;

import gov.ohio.jfs.cots.fn.util.CryptoUtils;

public class CryptoUtilsTest {
	private static Logger logger = Logger.getLogger(CryptoUtilsTest.class);

	@Test
	public void testEncrypt() throws GeneralSecurityException {
		// Use the following code to generate an encrypted password.
		String plainText = "helloworld";
		String encryptedPassword = CryptoUtils.encrypt(plainText);
		assertNotEquals(plainText, encryptedPassword);
	}
	@Test
	public void testDecrypt() throws GeneralSecurityException {
		String plainText = "helloworld";
		String encryptedPassword = CryptoUtils.encrypt(plainText);
		assertEquals(plainText,CryptoUtils.decrypt(encryptedPassword));
	}

}