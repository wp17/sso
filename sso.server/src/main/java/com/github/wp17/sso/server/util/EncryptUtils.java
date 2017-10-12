package com.github.wp17.sso.server.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.github.wp17.sso.server.encrypt.BASE64;
import com.github.wp17.sso.server.encrypt.Digest;
import com.github.wp17.sso.server.encrypt.HEX;
import com.github.wp17.sso.server.encrypt.Hmac;

/**
 *  MD5、SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串，通常用来校验数据在传输过程中是否被修改。
 *  其中HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。 
 *  单向加密的用途主要是为了校验数据在传输过程中是否被修改。
 *
 **  按 照RFC2045的定义，Base64被定义为：Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。
	 * （The Base64 Content-Transfer-Encoding is designed to represent arbitrary sequences of octets in a form that need not be humanly readable.）
	 *  常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
	 *  
	 *  BASE加密后产生的字节位数是8的倍数，如果不够位数以=符号填充。 
 *
 ** MD5 -- message-digest algorithm 5 （信息-摘要算法）缩写，广泛用于加密和解密技术，常用于文件校验。
	 * 不管文件多大，经过MD5后都能生成唯一的MD5值，常用来验证文件是否被修改过。
 *
 *http://www.open-open.com/lib/view/open1397274257325.html
 */
public class EncryptUtils {
	
	public static byte[] md5AndBase64(byte[] message) throws Exception{
		return BASE64.base64(Digest.md5(message));
	}
	
	public static byte[] md5AndHex(byte[] message) throws Exception{
		return HEX.hex(Digest.md5(message));
	}
	
	public static String md5AndBase64(String message, String charset) throws Exception{
		byte[] bytes = Digest.md5(message.getBytes(charset));
		return BASE64.base64(bytes, charset);
	}
	
	public static String md5AndHex(String message) throws Exception{
		return HEX.hexS(Digest.md5(message.getBytes()));
	}
	
	/**
	 * 将HMACMD5算法加密结果转换为十六进制字符串
	 * https://www.bouncycastle.org/latest_releases.html
	 */
	public static String hmacToHex(byte[] data, byte[] key, String algorithm) throws InvalidKeyException, NoSuchAlgorithmException{
		return HEX.hexS(Hmac.hmac(data, key, algorithm));
	}
	
	public static void main(String[] args) throws Exception {
		testHmac();
	}
	
	public static void testHmac() throws Exception{
		String message = "wang鹏";
		
		byte[] key1 = Hmac.initHmacKey("HmacMD5");
		String result1 = hmacToHex(message.getBytes(), key1, "HmacMD5");
		System.out.println(result1);
		
		byte[] key2 = Hmac.initHmacKey("HmacMD5");
		String result2 = hmacToHex(message.getBytes(), key2, "HmacMD5");
		System.out.println(result2);
	}
}
