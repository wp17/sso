package com.github.wp17.sso.server.encrypt;


import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * PBE——Password-based encryption（基于密码加密）。
 * 其特点在于口令由用户自己掌管，不借助任何物理媒体；
 * 采用随机数（盐）杂凑多重加密等方法保证数据的安全性。
 * 支持以下算法 PBEWithMD5AndDES PBEWithMD5AndTripleDES PBEWithSHA1AndDESede PBEWithSHA1AndRC2_40
 */
public class PBE {
	
	private static Key genKey(char[] password, byte[] salt, String algorithm, int iterationCount) throws InvalidKeySpecException, NoSuchAlgorithmException{
		PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		return keyFactory.generateSecret(keySpec);
	}

	public static byte[] encrypt(byte[] data, char[] password, byte[] salt, String algorithm, int iterationCount) throws Exception{
		Cipher cipher = Cipher.getInstance(algorithm);
		
		Key key = genKey(password, salt, algorithm, iterationCount);
		PBEParameterSpec spec = new PBEParameterSpec(salt, iterationCount);
		
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		return cipher.doFinal(data);
	}
	
	public static byte[] decrypt(byte[] data, char[] password, byte[] salt, String algorithm, int iterationCount) throws Exception{
		Cipher cipher = Cipher.getInstance(algorithm);
		
		Key key = genKey(password, salt, algorithm, iterationCount);
		PBEParameterSpec spec = new PBEParameterSpec(salt, iterationCount);
		
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		return cipher.doFinal(data);
	}
}
