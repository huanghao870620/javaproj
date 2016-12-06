package com.xa.httpunit;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import junit.framework.TestCase;

public class GoodsOrderReleaseHttpTest extends TestCase {
	private WebConversation wc = new WebConversation();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 测试绑定订单和商品
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testBindingOrdersAndGoods() throws IOException, SAXException{
		WebRequest request = new PostMethodWebRequest("http://192.168.1.110:8080/goodsOrder/bindingOrdersAndGoods.htm");
		request.setParameter("orderId", "1");
		request.setParameter("goodsId", new String[]{"3"});
		WebResponse response = this.wc.getResponse(request);
		String text = response.getText();
		System.out.println(text);
	}
}
