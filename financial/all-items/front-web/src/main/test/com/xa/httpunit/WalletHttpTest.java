package com.xa.httpunit;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import junit.framework.TestCase;

public class WalletHttpTest extends TestCase{
	private WebConversation wc = new WebConversation();

	@Override
	protected void setUp() throws Exception {
		PostMethodWebRequest request = new PostMethodWebRequest("http://192.168.1.110:8080/customer/login.htm");
		request.setParameter("mobile", "17301748932");
		request.setParameter("password", "123456");
		WebResponse response = this.wc.getResponse(request);
		if(response.getResponseCode()==200){
			System.out.println("登录成功");
		}
	}

	@Override
	protected void tearDown() throws Exception {
	}
	
	/**
	 * 获取钱包余额
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public void testBalance() throws IOException, SAXException{
		PostMethodWebRequest request = new PostMethodWebRequest("http://192.168.1.110:8080/customer/getBalance.htm");
		WebResponse response = this.wc.getResponse(request);
		String text = response.getText();
		System.out.println(text);
	}
}
