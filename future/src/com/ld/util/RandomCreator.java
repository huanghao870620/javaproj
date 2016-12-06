package com.ld.util;

import java.util.Random;

public class RandomCreator {

	public static int WIDTH = 60;

	public static int HEIGHT = 20;

	public static String createRandom() {
		String str = "0123456789qwertyuiopasdfghjklzxcvbnm";
		char[] rands = new char[4];
		Random random = new Random();
		
		for (int i = 0; i < 4; i++) {
			rands[i] = str.charAt(random.nextInt(36));
		}
		
		return new String(rands);
	}

}
