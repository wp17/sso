package com.github.wp17.sso.server.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES-Data Encryption Standard,即数据加密算法。是IBM公司于1975年研究成功并公开发表的。
 * DES算法的入口参数有三个:Key、Data、Mode。
 * 其中 Key为8个字节共64位,是DES算法的工作密钥;
 * Data也为8个字节64位,是要被加密或被解密的数据;
 * Mode为DES的工作方式,有两种:加密 或解密。
 * DES算法把64位的明文输入块变为64位的密文输出块,它所使用的密钥也是64位。 
 */
public class DES {
	private static final String DEFAULT_ALGORITHM = "DES";
	
	/**
	 * @param key
	 * @param algorithm DES、DESede(TripleDES)、AES、Blowfish、RC2、RC4(ARCFOUR)
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static SecretKey toKey(byte[] key, String algorithm) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
		if (algorithm == null) {
			throw new NullPointerException("algorithm should not null");
		}
		
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		
		SecretKey secretKey = null;
		if (algorithm.equals(DEFAULT_ALGORITHM)) {
			DESKeySpec keySpec = new DESKeySpec(key);
			secretKey = keyFactory.generateSecret(keySpec);
		}else {
			secretKey = new SecretKeySpec(key, algorithm);
		}
		
		return secretKey;
	}
	
	private static byte[] initKey(byte[] seed, String algorithm) throws Exception{
		SecureRandom secureRandom = null;
		
		if (null == seed) {
			secureRandom = new SecureRandom();
		}else {
			secureRandom = new SecureRandom(seed);
		}
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		
		keyGenerator.init(secureRandom);
		
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String algorithm) throws Exception{
		SecretKey secretKey = toKey(key, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String algorithm) throws Exception{
		SecretKey secretKey = toKey(key, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
}
