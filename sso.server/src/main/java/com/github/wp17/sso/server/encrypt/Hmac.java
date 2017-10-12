package com.github.wp17.sso.server.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Hmac {
	/**
	 * 生成Hmac密钥
	 * 
	 *  MAC系列算法支持表
		算法                   摘要长度      备注 
		HmacMD5     128      BouncyCastle实现 
		HmacSHA1    160      BouncyCastle实现 
		HmacSHA256  256      BouncyCastle实现 
		HmacSHA384  384      BouncyCastle实现 
		HmacSHA512  512      JAVA6实现 
		HmacMD2     128      BouncyCastle实现 
		HmacMD4     128      BouncyCastle实现 
		HmacSHA224  224      BouncyCastle实现 
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static byte[] initHmacKey(String algorithm) throws NoSuchAlgorithmException{
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		SecretKey key = keyGenerator.generateKey();
		
		return key.getEncoded();
	}
	
	/**
	 * 使用HMAC算法加密
	 * @param data
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static byte[] hmac(byte[] data, byte[] key, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException{
		SecretKey secretKey = new SecretKeySpec(key, algorithm);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
}
