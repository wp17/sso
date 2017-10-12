package com.github.wp17.sso.server.util;

import java.util.UUID;

public class AppUtils {
	public static String genToken(String source, String salt){
	    try {
			return EncryptUtils.md5AndHex(source+salt);
		} catch (Exception e) {
			return UUID.randomUUID().toString();
		}
	}
}
