//package com.ld.sftp;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.SftpException;
//
//
//public class SFTPTest {
//
//    /**
//     * @param args
//     * @throws Exception
//     */
//    public static void main(String[] args)  {
//    	
//        SFTPTest test = new SFTPTest();
//
//        String src = "C:/Users/chang.bl/Desktop/QQͼƬ20160803122322.png"; // �����ļ���
//        String dst = "1./shared/upload/images/QQͼƬ20160803122322.png"; // Ŀ���ļ���
//              
//        SFTPChannel channel = null;
//		  ChannelSftp chSftp = null; 
//	        try {
//	        	channel = test.getSFTPChannel();
//	        	chSftp= channel.getChannel();
//	        	chSftp.put(src,dst);
//		System.out.println(chSftp.ls(dst).toString().split("\\s+")[4]);	
//				chSftp.quit();
//			    channel.closeChannel();
//			} catch (Exception e) {
//				System.out.println("2");
//							}  
//	        finally {
//	        	System.out.println("1");
//				chSftp.quit();
//				try {
//				channel.closeChannel();
//				} catch (Exception e) {
//					System.out.println("3");
//				e.printStackTrace();
//					}
//					           
//					}
//	      
//    }
//}