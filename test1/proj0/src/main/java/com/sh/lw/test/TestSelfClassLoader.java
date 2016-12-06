package com.sh.lw.test;

import java.util.Date;

import com.sh.lw.SelfClassLoad;

/**
 * @author huang.hao
 * @version 1.0
 * @created 14-ÆßÔÂ-2016 17:45:54
 */
public class TestSelfClassLoader {

	public TestSelfClassLoader(){

	}

	
	public static void main(String[] args) throws Exception{
		Class<?> clazz = new SelfClassLoad("D:\\javawork").loadClass("CypherClass");
		Date date = (Date) clazz.newInstance();
		ClassLoader classloader = date.getClass().getClassLoader();
		while(classloader != null){
			classloader = classloader.getParent();
		}
	}

}