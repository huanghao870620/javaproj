package httpunit;

import java.io.IOException;
import java.lang.reflect.Array;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
/**
 * 
 * @author burgess
 *
 */
public class PhpHttpTest {
	private WebConversation wc = new WebConversation();

	@Before
	public void before(){
		
	}
	
	@After
	public void after(){
		
	}
	
	/**
	 * 预支付
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testReg() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://www.xunanbuy.com/Register/regDo.html",true);
		req.setParameter("user_name", "213ds22s");
		req.setParameter("password", "123456");
		req.setParameter("not_password", "123456");
		req.setParameter("mobile_phone", "18563325336");
		req.setParameter("recommend", "");
		req.setParameter("agree", "1");
		req.setParameter("type", "0");
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();	
		System.out.println(msg);
	}
	
	/**
	 * 解析返回字符串
	 */
	@Test
	public void parseRet(){
		String s="﻿[[\"submits\",true,\"REGISTEREDSUCCESS\",\"/Member/question.html\",\"\"]]";
		String arrs[]= s.split(",");
		System.out.println(arrs[1]);
		String ret=arrs[1];
		System.out.println(ret.equals("true"));
	}
}
