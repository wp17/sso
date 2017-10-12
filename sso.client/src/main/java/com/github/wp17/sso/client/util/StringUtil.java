package com.github.wp17.sso.client.util;

public class StringUtil {

	public static boolean isEmpty(String arg){
		if (null == arg) {
			return true;
		}
		
		if (arg.trim().equals("")) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isNotEmpty(String arg){
		return !isEmpty(arg);
	}
	
	public static boolean notNullAndEqual(String arg1, String arg2){
		if(null == arg1 || null == arg2){
			return false;
		}
		
		return arg1.equals(arg2);
	}
	
	public static boolean isEqual(String arg1, String arg2){
		if(null == arg1 && null == arg2){
			return true;
		}
		if (null != arg1) {
			return arg1.equals(arg2);
		}
		
		if (null != arg2) {
			return arg2.equals(arg1);
		}
		
		return false;
	}
}
