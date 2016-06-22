package gov.ohio.jfs.fn.util;

public interface Encrypter {
	public String encrypt(String message);
	public String decrypt(String encryptedMessage);
}
