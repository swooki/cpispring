package gov.ohio.jfs.fn.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils{
	
	public static final String AES = "AES";

	/**
	 * encrypt a value and generate a keyfile if the keyfile is not found then a
	 * new one is created
	 * 
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static String encrypt(String value) throws GeneralSecurityException {
		SecretKeySpec sks = getSecretKeySpec();
		Cipher cipher = Cipher.getInstance(CryptoUtils.AES);
		cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
		byte[] encrypted = cipher.doFinal(value.getBytes());
		return byteArrayToHexString(encrypted);
	}

	/**
	 * decrypt a value
	 * 
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static String decrypt(String message)
			throws GeneralSecurityException {
		SecretKeySpec sks = getSecretKeySpec();
		Cipher cipher = Cipher.getInstance(CryptoUtils.AES);
		cipher.init(Cipher.DECRYPT_MODE, sks);
		byte[] decrypted = cipher.doFinal(hexStringToByteArray(message));
		return new String(decrypted);
	}

	private static SecretKeySpec getSecretKeySpec()
			throws NoSuchAlgorithmException {
		byte[] key = readKeyFile();
		SecretKeySpec sks = new SecretKeySpec(key, CryptoUtils.AES);
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
	
	public static void main(String[] args){
		try {
			System.out.println(CryptoUtils.decrypt(args[0]));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
}
