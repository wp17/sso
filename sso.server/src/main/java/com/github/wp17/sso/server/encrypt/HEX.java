package com.github.wp17.sso.server.encrypt;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Hex;


public class HEX {
	public static byte[] hex(byte[] data){
		return Hex.encode(data);
	}
	
	public static byte[] hexdec(byte[] data){
		return Hex.decode(data);
	}
	
	public static String hex(String data, String charset) throws UnsupportedEncodingException{
		return Hex.toHexString(data.getBytes(charset));
	}
	
	public static String hexS(byte[] data){
		return Hex.toHexString(data);
	}
	
	public static String hexdec(String hexString, String charset) throws UnsupportedEncodingException{
		return new String(Hex.decode(hexString), charset);
	}
}
