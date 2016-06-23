package gov.ohio.jfs.fn.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AESEncrypter implements Encrypter {

	public static final String AES = "AES";

	/**
	 * encrypt a value and generate a keyfile if the keyfile is not found then a
	 * new one is created
	 * 
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public String encrypt(String value) {
		SecretKeySpec sks;
		byte[] encrypted = null;
		try {
			sks = getSecretKeySpec();
			Cipher cipher = Cipher.getInstance(AESEncrypter.AES);
			cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
			encrypted = cipher.doFinal(value.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteArrayToHexString(encrypted);
	}

	/**
	 * decrypt a value
	 * 
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public String decrypt(String message) {
		SecretKeySpec sks;
		byte[] decrypted = null;
		try {
			sks = getSecretKeySpec();
			Cipher cipher = Cipher.getInstance(AESEncrypter.AES);
			cipher.init(Cipher.DECRYPT_MODE, sks);
			decrypted = cipher.doFinal(hexStringToByteArray(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(decrypted);
	}

	private static SecretKeySpec getSecretKeySpec() throws NoSuchAlgorithmException {
		byte[] key = readKeyFile();
		SecretKeySpec sks = new SecretKeySpec(key, AESEncrypter.AES);
		return sks;
	}

	private static byte[] readKeyFile() {
		return hexStringToByteArray("773894D23454582C4EF56424F96B15A3");
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	private static byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}
}
