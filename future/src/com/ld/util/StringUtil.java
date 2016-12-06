package com.ld.util;

public class StringUtil {

	public static boolean isBlank(String str){
		 return null == str || "".equals(str.trim());
	}
	
	public static String isNull(Object obj){
		return obj == null ? null : String.valueOf(obj);
	}
}
