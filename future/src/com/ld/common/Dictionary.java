package com.ld.common;



public class Dictionary {
	
	// 客户类型  属于讲师类型 角色定义 
	public static final String TEACHER_TYPE_ROLE = "1";
	// 客户类型  属于客户类型 角色定义 
	public static final String CUSTOMER_TYPE_ROLE = "5";
	
	//FTP
//	public static final String FTP_SERV_IP = Configuration.getConfigValue("FTP_SERV_IP");
//	public static final int FTP_SERV_PORT = Integer.parseInt(Configuration.getConfigValue("FTP_SERV_PORT"));
//	public static final String FTP_SERV_USER = Configuration.getConfigValue("FTP_SERV_USER");
//	public static final String FTP_SERV_PWD = Configuration.getConfigValue("FTP_SERV_PWD");

	//SFTP
	public static final String SFTP_SERV_IP = Configuration.getConfigValue("SFTP_SERV_IP");
	public static final int SFTP_SERV_PORT = Integer.parseInt(Configuration.getConfigValue("SFTP_SERV_PORT"));
	public static final String SFTP_SERV_USER = Configuration.getConfigValue("SFTP_SERV_USER");
	public static final String SFTP_SERV_PWD = Configuration.getConfigValue("SFTP_SERV_PWD");
	public static final int SFTP_SERV_TIMEOUT = Integer.parseInt(Configuration.getConfigValue("SFTP_SERV_TIMEOUT"));
}
