package com.xa.junit3;

import com.xa.util.Security;

import junit.framework.TestCase;

public class SecurityTest extends TestCase {

	/**
	 * 
	 */
	public void testGetSign(){
		String text = Security.getSign(new String[]{"cdddd","bffff","jdd","addd","sddd"});
		System.out.println(text);
	}
}
