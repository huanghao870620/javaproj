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
	 * ������ʽ�ж��ַ����Ƿ�����������
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){

	    Pattern pattern = Pattern.compile("[0-9]*");

	    return pattern.matcher(str).matches();   

	 }
	/**
	 * ���ݸ�ʽ��yyyy-MM-dd hh:mm:ss ���� java.util.Date ����ʱ�䡡
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
	 * ���ص�ǰʱ�䡡��ʽ��yyyy-MM-dd hh:mm:ss
	 * @return String
	 */
	public static String fromDateH(Date date){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format1.format(date);
	}

	/** ��ȡ��Ŀ��·�� */
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
    * ��ѯ��ǰ����ǰ(��)x�������
    * @param date ��ǰ����
    * @param day ���������day��Ϊ����,˵���Ǵ�����ǰ��������
    * @return yyyy-MM-dd
    */
   public static String beforNumDay(Date date, int day) {
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       c.add(Calendar.DAY_OF_YEAR, day);
       return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
   }
   /**
    * ����ʱ�������
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
