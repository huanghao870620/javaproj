package com.xa.httpunit;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.protocol.UploadFileSpec;
import com.xa.util.Security;

import junit.framework.TestCase;

public class BuyHandHttpTest extends TestCase{

	private WebConversation wc = new WebConversation();

	@Override
	protected void setUp() throws Exception {
	}

	@Override
	protected void tearDown() throws Exception {
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testUpdateCustomer() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/customer/updateCustomer.htm",true);
		req.setParameter("id", "8");
		req.setParameter("nickname", "fffd");
		
		String sign = Security.getSign(new String[]{
				"id",/*"headPortraitFile",*/"nickname","signature"
		});
		req.setParameter("sign", sign);
		
		File headPortraitFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); //
		UploadFileSpec[] headPortraitUploadFile = {new UploadFileSpec(headPortraitFile)};
//		req.setParameter("headPortraitFile", headPortraitUploadFile);
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();
		System.out.println(msg);
	}
	
	/**
	 * @throws SAXException 
	 * @throws IOException 
	 * 
	 */
	public void testLogin() throws IOException, SAXException {
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.102:8080/front-web/customer/login.htm",true);
		req.setParameter("mobile", "18217742115");
		req.setParameter("password", "123");
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();
		System.out.println(msg);
	}
	
	/**
	 * @throws SAXException 
	 * @throws IOException 
	 * 
	 */
	public void testI18n() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/customer/test.htm",true);
		req.setParameter("locale", "en_US");
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();
		System.out.println(msg);
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testGetOrderByBuyHandId() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.102:8080/front-web/customer/getOrderByBuyHandId.htm",true);
		WebResponse response = wc.getResponse(req);
		String text = response.getText();
		System.out.println(text);
	}
	
	/**
	 * @throws SAXException 
	 * @throws IOException 
	 * 
	 */
	public void testUpdateBuyhandHeadPortrait() throws IOException, SAXException {
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.102:8080/front-web/buyhand/updateCustomerHeadPortrait.htm",true);
		File headPortraitFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); //
		UploadFileSpec[] headPortraitUploadFile = {new UploadFileSpec(headPortraitFile)};
		req.setParameter("headPortraitFile", headPortraitUploadFile);
		
		String sign = Security.getSign(new String[]{
				"buyHandId","headPortraitFile"	
			});
		
		req.setParameter("buyHandId", "2");
		req.setParameter("sign", sign);
		WebResponse response = wc.getResponse(req);
		String text = response.getText();
		System.out.println(text);
	}
}
