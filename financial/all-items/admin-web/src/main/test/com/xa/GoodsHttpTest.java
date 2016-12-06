package com.xa;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.protocol.UploadFileSpec;

import junit.framework.TestCase;

public class GoodsHttpTest extends TestCase {
	private WebConversation wc = new WebConversation();

	@Override
	protected void setUp() throws Exception {
		HttpUnitOptions.setScriptingEnabled(false);
	}

	@Override
	protected void tearDown() throws Exception {
	}
	
	/**
	 * 测试添加商品
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testAddGoods() throws IOException, SAXException {
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/goods/addGoodsByAjax.htm",true);
		req.setParameter("name", "18217742115");
		req.setParameter("info", "123");
		req.setParameter("capacity", "1");
		req.setParameter("lowestPrice", "2");
		req.setParameter("highestPrice", "3");
		req.setParameter("purchasingPosition", "3");
		req.setParameter("dateOfProduction", "2018-02-12 7:12:03");
		req.setParameter("shelfLife", "32");
		req.setParameter("barCode", "3");

		
		File goodsAccordingToPositiveFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); // 
		File backGoodsAccordingToFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg"); // 
		File productProfileFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg"); // 
		File goodsInvoiceFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg"); // 
		File expressSingleFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg"); //
		
		UploadFileSpec[] goodsAccordingToPositiveUploadFile = {new UploadFileSpec(goodsAccordingToPositiveFile)};
		UploadFileSpec[] backGoodsAccordingToUploadFile = {new UploadFileSpec(backGoodsAccordingToFile)};
		UploadFileSpec[] productProfileUploadFile = {new UploadFileSpec(productProfileFile)};
		UploadFileSpec[] goodsInvoiceUploadFile = {new UploadFileSpec(goodsInvoiceFile)};
		UploadFileSpec[] expressSingleUploadFile = {new UploadFileSpec(expressSingleFile)};
		
		
		req.setParameter("goodsAccordingToPositiveFile", goodsAccordingToPositiveUploadFile);
		req.setParameter("backGoodsAccordingToFile", backGoodsAccordingToUploadFile);
		req.setParameter("productProfileFile", productProfileUploadFile);
		req.setParameter("goodsInvoiceFile", goodsInvoiceUploadFile);
		req.setParameter("expressSingleFile", expressSingleUploadFile);
		
//		req.selectFile("goodsAccordingToPositiveFile", goodsAccordingToPositiveFile);
		
		WebResponse resp = wc.getResponse(req);
		String text = resp.getText();
		System.out.println(text);
	}
	
	/**
	 * @throws SAXException 
	 * @throws IOException 
	 * 
	 */
	public void testDate() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/goods/test.htm",true);
		req.setParameter("birthday", "1990-01-02");
		WebResponse resp = wc.getResponse(req);
		String text = resp.getText();
		System.out.println(text);
	}
}
