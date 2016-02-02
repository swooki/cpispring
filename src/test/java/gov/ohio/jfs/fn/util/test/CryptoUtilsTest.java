package gov.ohio.jfs.fn.util.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;
import org.junit.Test;

import gov.ohio.jfs.fn.util.CryptoUtils;
import gov.ohio.jfs.fn.util.Cryptor;

public class CryptoUtilsTest {
	private static Logger logger = Logger.getLogger(CryptoUtilsTest.class);

	@Test
	public void testEncrypt() throws GeneralSecurityException {
		// Use the following code to generate an encrypted password.
		String plainText = "helloworld";
		Cryptor cryptor = new CryptoUtils();
		
		String encryptedPassword = cryptor.encrypt(plainText);
		assertNotEquals(plainText, encryptedPassword);
	}
	@Test
	public void testDecrypt() throws GeneralSecurityException {
		String plainText = "helloworld";
		Cryptor cryptor = new CryptoUtils();

		String encryptedPassword = cryptor.encrypt(plainText);
		assertEquals(plainText,cryptor.decrypt(encryptedPassword));
	}

}
