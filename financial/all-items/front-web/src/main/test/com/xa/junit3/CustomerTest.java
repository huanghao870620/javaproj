package com.xa.junit3;

import com.xa.deep.Vercode;

import junit.framework.TestCase;

public class CustomerTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	/**
	 * 测试生成验证码
	 */
	public void testGeneCode(){
	  for(int i=0;i<1000;i++){
		  System.out.println(Vercode.getVercode());
	  }
	}
	
}
