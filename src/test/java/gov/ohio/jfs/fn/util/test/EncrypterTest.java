package gov.ohio.jfs.fn.util.test;

import static org.junit.Assert.*;

import java.security.GeneralSecurityException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.ohio.jfs.fn.util.Encrypter;
import gov.ohio.jfs.fn.util.UtilConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=UtilConfig.class)
public class EncrypterTest {

	@Autowired
	private Encrypter encrypter;
	
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
