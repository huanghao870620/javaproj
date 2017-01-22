package com.xa.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Security {

	public static String getSign(String []args){
		List<String> list = new ArrayList<String>();
		for(int i=0; i<args.length;i++){
			list.add(args[i]);
		}
		Collections.sort(list);
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<list.size();i++){
			sb.append(list.get(i));
		}
		return EncryptionTool.addSaltEncrypt(sb.toString(),getSalt());
	}
	
	private static String getSalt(){
		return "sY#PKlD71jd*83yjL7#cXAcey%wXOPYgroi1SKDiK@9cV0zm!xSMmzE#5@^Oox*wdoH*f*67nfQ^1JOQSg6eJwvzaZpk#JD&f4t";
	}
	
	/**
	 * 密码盐
	 * @return
	 */
	public static String getPasswordSalt(){
		return "sY#PKlD71jd*83yjL7#cXAcey%5@^Oox*wdoH*fwXOPYgroi1SKDiK@9cV0zm!xSMmzE#*67nfQ^1JOQSg6eJwvzaZpk#JD&f4t";
	}
}
