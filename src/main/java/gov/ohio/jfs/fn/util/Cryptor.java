package gov.ohio.jfs.fn.util;

import java.security.GeneralSecurityException;

public interface Cryptor {
	public String encrypt(String plainMessage) throws GeneralSecurityException;
	public String decrypt(String encryptedMessage)throws GeneralSecurityException ;
}
