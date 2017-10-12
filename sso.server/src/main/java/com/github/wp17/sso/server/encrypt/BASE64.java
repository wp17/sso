package com.github.wp17.sso.server.encrypt;

import org.bouncycastle.util.encoders.Base64;

/**
 *  BASE64的加密解密是双向的，可以求反解。
 */
public class BASE64 {

	public static byte[] base64(byte[] data) throws Exception{
		byte[] encoded = Base64.encode(data);
		return encoded;
	}
	
	public static String base64(byte[] data, String charset) throws Exception{
		return new String(base64(data), charset);
	}
	
	public static byte[] base64dec(byte[] data) throws Exception{
		byte[] decoded = Base64.decode(data);
		return decoded;
	}
	
	public static String base64dec(String data, String charset) throws Exception{
		return new String(base64dec(data.getBytes(charset)), charset);
	}
}
