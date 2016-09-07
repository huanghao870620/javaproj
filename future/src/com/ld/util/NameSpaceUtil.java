package com.ld.util;

/**
 * name space tool
 * @author hao.huang
 *
 */
public class NameSpaceUtil {

	public static final String BACK_NAMESPACE = "/back";
	public static final String FRONT_NAMESPACE = "/front";
	
	
	/**
	 * get back space
	 * @param space
	 * @return
	 */
	public static final String getBackNamespace(String namespace){
		 StringBuilder sb = new StringBuilder(BACK_NAMESPACE);
	     return sb.append(namespace).toString();
	}
	
	/**
	 * get front space 
	 * @param namespace
	 * @return
	 */
	public static String getFrontNamespace(String namespace){
		 StringBuilder sb = new StringBuilder(FRONT_NAMESPACE);
		 return sb.append(namespace).toString();
	}
}
