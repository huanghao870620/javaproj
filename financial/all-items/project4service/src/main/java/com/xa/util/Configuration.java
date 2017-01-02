package com.xa.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Configuration {
	private static final Logger log = Logger.getLogger(Configuration.class.getName());
	
	private Configuration(){
	}
	
	private static Properties p;
	static {
		p=new Properties();
		//String path =getPath("config\\conf.properties"); windows ��
		String path =getPath("conf.properties");
		
		log.info("path:"+path);
		try {
			FileInputStream in = new FileInputStream(path);
			p.load(in);
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static String getPath(String filename){
		 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		 URL url = classLoader.getResource(filename);
		 String filepath = url.getPath();
		 try {
			filepath =URLDecoder.decode(filepath,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//关键�? �? 
		 System.out.println("filepath"+filepath);
      return filepath;
	}
	public static String getConfigValue(String name) {
		return p.getProperty(name);
	}
	
	
	public static Enumeration<String> getConfigValuesEnum(){
		return (Enumeration<String>) p.propertyNames();
	}
	
	
	public static void main(String[] args) {
		Enumeration<String> ens=getConfigValuesEnum();
		while(ens.hasMoreElements()){
			System.out.println(ens.nextElement());
		}
	}
}