package com.xa.deep;

import java.util.Random;

public class Vercode {

	public static String  getVercode() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<6; i++){
			 sb.append(random.nextInt(10));
		}
		 return sb.toString();
	}
}
