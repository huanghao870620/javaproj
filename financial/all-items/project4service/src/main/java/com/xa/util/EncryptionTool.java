package com.xa.util;

import java.security.MessageDigest;

import org.springframework.security.providers.encoding.Md5PasswordEncoder;

/**
 * 
 * @author hao.huang
 *
 */
public class EncryptionTool {
	
	/**
	 * 加盐加密
	 * @return
	 */
	public static String addSaltEncrypt(String pw, String salt){
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
	    md5.setEncodeHashAsBase64(false); 
	    return md5.encodePassword(pw, salt);
	}
}
