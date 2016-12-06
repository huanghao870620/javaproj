package com.ld.util;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Common {
	

	/**
	 * 正则表达式判断字符串是否是数字类型
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){

	    Pattern pattern = Pattern.compile("[0-9]*");

	    return pattern.matcher(str).matches();   

	 }
	/**
	 * 根据格式：yyyy-MM-dd hh:mm:ss 返回 java.util.Date 类型时间　
	 * @return Date
	 */
	public static Date parseDate(String strDate, String pattern) 
    {  
        try {
			return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(  
			        pattern).parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return null;
    }  
	

	/**
	 * 返回当前时间　格式：yyyy-MM-dd hh:mm:ss
	 * @return String
	 */
	public static String fromDateH(Date date){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format1.format(date);
	}

	/** 获取项目根路径 */
	public static  String getWebRoot() {
		
		 ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		URL url = classLoader.getResource("config/conf.properties");
		String filepath = url.getPath();
		filepath = filepath.substring(0, filepath.lastIndexOf("/"));
		filepath = filepath.substring(0, filepath.lastIndexOf("/"));
		filepath = filepath.substring(0, filepath.lastIndexOf("/"));
		return filepath;
	}
	 /**
    *
    * 查询当前日期前(后)x天的日期
    * @param date 当前日期
    * @param day 天数（如果day数为负数,说明是此日期前的天数）
    * @return yyyy-MM-dd
    */
   public static String beforNumDay(Date date, int day) {
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       c.add(Calendar.DAY_OF_YEAR, day);
       return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
   }
   /**
    * 返回时间毫秒数
    * @return
    */
   public static String  getTimeInMillls(){
    Calendar c=Calendar.getInstance();
	long milliseconds=c.getTimeInMillis();
	return  String.valueOf(milliseconds);
   }

	
	public static void main(String[] args) {
			System.out.println(beforNumDay(new Date(),-2) );
	}
	
}
