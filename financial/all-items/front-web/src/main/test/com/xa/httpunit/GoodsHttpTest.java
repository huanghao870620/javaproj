package com.xa.httpunit;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.protocol.UploadFileSpec;
import com.xa.util.Security;

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
		req.setParameter("dateOfProduction", "4");
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
		
		
		WebResponse resp = wc.getResponse(req);
		String text = resp.getText();
		System.out.println(text);
	}
	
	/**
	 * @throws SAXException 
	 * @throws IOException 
	 * 
	 */
	public void testGetGoods() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/goods/getGoods.htm",true);
		req.setParameter("pageNum", "1");
		req.setParameter("pageSize", "10");
		WebResponse response = wc.getResponse(req);
		String text = response.getText();
		System.out.println(text);
	}
	
	/**
	 * 买手添加商品
	 * @throws SAXException 
	 * @throws IOException 
	 */
    public void testAddGood() throws IOException, SAXException{
    	PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/goods/addGood.htm",true);
    	
    	File leftPhotoFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); // 
		File rightPhotoFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg"); // 
		File gatpPhotoFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg"); // 
		
		UploadFileSpec[] leftPhotoUploadFile = {new UploadFileSpec(leftPhotoFile)};
		UploadFileSpec[] rightPhotoUploadFile = {new UploadFileSpec(rightPhotoFile)};
		UploadFileSpec[] gatpPhotoUploadFile = {new UploadFileSpec(gatpPhotoFile)};
		
		
		
		
		req.setParameter("classid", "49");
		req.setParameter("brandId", "18");
		req.setParameter("name", "dddd");
		req.setParameter("price", "13");
		req.setParameter("capacity", "150");
		req.setParameter("color", "红色");
		req.setParameter("dateOfProduction", "2016/11/29");
		req.setParameter("shelfLife", "2016/11/29");
		req.setParameter("leftPhotoFile", leftPhotoUploadFile);
		req.setParameter("rightPhotoFile", rightPhotoUploadFile);
		req.setParameter("gatpPhotoFile", gatpPhotoUploadFile);
		req.setParameter("mallName", "aa");
		req.setParameter("mallAddress", "bb");
		
		
		String sign= Security.getSign(new String[]{
				"classid", /*分类id*/
				"brandName", /*品牌名称*/
				"name", /*商品名称*/
				"price", /*商品价格*/
				"capacity",  /*规格*/
				"color", /*颜色*/
				"currencyType",/*货币种类*/
				"speciUnit",/*规格单位*/
				"dateOfProduction", /*生产日期*/
				"shelfLife", /*保质期*/
				"leftPhotoFile",/*左侧视图*/
				"rightPhotoFile",/*右侧视图*/
				"gatpPhotoFile",/*正面视图*/
				"mallName",/*商场名称*/
				"mallAddress"/*商场位置*/
		});
		
		req.setParameter("sign", sign);
		WebResponse resp = wc.getResponse(req);
		String text = resp.getText();
		System.out.println(text);
    }
}
