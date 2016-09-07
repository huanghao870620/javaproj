package com.ld.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author zeng.nian
 *
 */
public class Logs {
	private static Log log;
	
	static{
		try {
			log = LogFactory.getLog(Log.class);
		} catch (Exception e) {
			System.out.println("can't init the Logger,caused by:"+e);
		}
	}
	
	public static Log getLogger(){
		return log;
	}
}
