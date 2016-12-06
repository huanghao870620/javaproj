package com.xa.httpunit;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Base64.Decoder;
import java.util.Dictionary;

import org.xml.sax.SAXException;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.protocol.UploadFileSpec;
import com.xa.util.Security;

import junit.framework.TestCase;

public class HelloHttpTest extends TestCase {
	
	private WebConversation wc = new WebConversation();

	@Override
	protected void setUp() throws Exception {
		HttpUnitOptions.setScriptingEnabled(false);
	}

	@Override
	protected void tearDown() throws Exception {
	}
	
	/**
	 * 注册
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testRegister() throws IOException, SAXException {
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.102:8080/front-web/customer/register.htm",true);
		req.setHeaderField("Accept-Language", "zh-cn");
		String gender = "男";
		 
		req.setParameter("mobile", "18217742115");
		req.setParameter("password", "123");
		req.setParameter("nickname", "小明");
		req.setParameter("gender",  gender);
		req.setParameter("signature", "欢迎光临");
		req.setParameter("qrCodeLinks", "http://www.baidu.com");
		req.setParameter("email", "1175210752@qq.com");
		req.setParameter("idNumber", "431087498789213023");
		req.setParameter("passportNo", "3123213123");
		req.setParameter("inputId", "424212345");
		
		File sidPhotoFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); // 
		File admNoticeFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg"); // 
		File passportFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg"); // 
		File lifePhotoFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg"); // 
		
		UploadFileSpec[] sidPhotoUploadFile = {new UploadFileSpec(sidPhotoFile)};
		UploadFileSpec[] admNoticeUploadfFile = {new UploadFileSpec(admNoticeFile)};
		UploadFileSpec[] passportUploadFile = {new UploadFileSpec(passportFile)};
		UploadFileSpec[] lifePhotoUploadFile = {new UploadFileSpec(lifePhotoFile)};
		
		
		req.setParameter("sidPhotoFile", sidPhotoUploadFile);
		req.setParameter("admNoticeFile", admNoticeUploadfFile);
		req.setParameter("passportFile", passportUploadFile);
		req.setParameter("lifePhotoFile", lifePhotoUploadFile);
		
	
		String sign = Security.getSign(new String[] { "mobile", "password", "gender", "sidPhotoFile",
				"admNoticeFile", "passportFile", "lifePhotoFile","email","idNumber","passportNo","inputId" });
		
		req.setParameter("sign", sign);
		WebResponse resp = wc.getResponse(req);
		String text = resp.getText();
		String cookie = resp.getHeaderField("set-cookie");
		System.out.println(text);
		System.out.println(cookie);
	}
	
	/**
	 * 登录
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public void testLogin() throws IOException, SAXException{
		WebRequest request = new PostMethodWebRequest("http://192.168.1.102:8080/front-web/customer/login.htm");
		request.setParameter("mobile", "18217742115");
		request.setParameter("password", "123");
		WebResponse response = this.wc.getResponse(request);
		String text = response.getText();
		System.out.println(text);
	}

	/**
	 * 获取验证码
	 * @throws SAXException 
	 * @throws IOException 
	 */
	public void testGetvercode() throws IOException, SAXException{
		WebRequest request = new PostMethodWebRequest("http://192.168.1.110:8080/customer/getVercode.htm");
		request.setParameter("mobile", "17301748932");
		WebResponse response = this.wc.getResponse(request);
		String text = response.getText();
		System.out.println(text);
	}
}
