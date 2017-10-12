package com.github.wp17.sso.server.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA {
	private static final String KEY_ALGORITHM = "RSA";
	private final String SIGNATURE_ALGORITHM  = "MD5withRSA";
	
	private final static RSAPublicKey publicKey;
	private final static RSAPrivateKey privateKey;
	
	static{
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(0);
		}
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		publicKey = (RSAPublicKey) keyPair.getPublic();
		privateKey = (RSAPrivateKey) keyPair.getPrivate();
	}
	
	private RSA(){}
	private static final RSA instance = new RSA();
	public static RSA getInstance(){
		return instance;
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 */
	public byte[] sign(byte[] data) throws Exception{
		
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(getPrivateKey());
		
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		PrivateKey prikey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(prikey);
		signature.update(data);
		return signature.sign();
	}
	
	/**
	 * 用公钥校验数字签名
	 */
	public boolean verify(byte[] data, byte[] sign) throws Exception{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(getPublicKey());
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey pubkey = keyFactory.generatePublic(keySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubkey);
		signature.update(data);
		
		return signature.verify(sign);

	} 
	
	public byte[] encryptByPrivateKey(byte[] data) throws Exception{
		byte[] key = getPrivateKey();
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey =keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	public byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception{
		
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	public byte[] encryptByPublicKey(byte[] data) throws Exception {
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(getPublicKey());
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}
	
	public byte[] decryptByPrivateKey(byte[] data) throws Exception{
		//取得私钥
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(getPrivateKey());
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		
		//对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	private byte[] getPrivateKey(){
		return privateKey.getEncoded();
	}
	
	private byte[] getPublicKey(){
		return publicKey.getEncoded();
	}
	
	public String getEncodedPublicKey(){
		return HEX.hexS(publicKey.getEncoded());
	}
	
	public static void genKey() throws NoSuchAlgorithmException{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		
		System.out.println(HEX.hexS(privateKey.getEncoded()));
		System.out.println(HEX.hexS(publicKey.getEncoded()));
	}
	
	public static void main(String[] args) throws Exception {
		genKey();
	}
}
