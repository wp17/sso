package com.github.wp17.sso.server.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
	
	public static byte[] md5(byte[] data) throws Exception{
		return digest(data, "MD5");
	}
	
	/**
	 * @param data
	 * @param algorithm SHA\SHA1\SHA-1\SHA-256\SHA-384\SHA-512
	 * @return digest
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] sha(byte[] data, String algorithm) throws NoSuchAlgorithmException{
		return digest(data, algorithm);
	}
	
	public static byte[] digest(byte[] data, String algorithm) throws NoSuchAlgorithmException{
		MessageDigest d = MessageDigest.getInstance(algorithm);
		d.update(data);
		return d.digest();
	}
}
