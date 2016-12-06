package com.xa.httpunit;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.xa.util.Security;

import junit.framework.TestCase;

public class MailHttpTest extends TestCase {
	private WebConversation wc = new WebConversation();
	
	/**
	 * 测试添加邮件
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public void testInsert() throws IOException, SAXException {
		PostMethodWebRequest request = new PostMethodWebRequest("http://192.168.1.110:8080/mail/insert.htm");
		request.setParameter("buyHandId", "8");
		request.setParameter("title", "dddd");
		request.setParameter("content", "ddsfdsfsdf");
		String sign = Security.getSign(new String[]{
				"buyHandId","title","content"
		});
		request.setParameter("sign", sign);
		WebResponse response = this.wc.getResponse(request);
		String text = response.getText();
		System.out.println(text);
	}
}
