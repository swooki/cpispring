package gov.ohio.jfs.fn.util.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;
import org.junit.Test;

import gov.ohio.jfs.fn.util.CryptoUtils;
import gov.ohio.jfs.fn.util.Encrypter;

public class CryptoUtilsTest {
	Encrypter encrypter = new CryptoUtils();
	
	@Test
	public void testEncrypt() throws GeneralSecurityException {
		// Use the following code to generate an encrypted password.
		String plainText = "helloworld";
		
		String encryptedPassword = encrypter.encrypt(plainText);
		assertNotEquals(plainText, encryptedPassword);
	}
	@Test
	public void testDecrypt() throws GeneralSecurityException {
		String plainText = "helloworld";

		String encryptedPassword = encrypter.encrypt(plainText);
		assertEquals(plainText, encrypter.decrypt(encryptedPassword));
	}

}
