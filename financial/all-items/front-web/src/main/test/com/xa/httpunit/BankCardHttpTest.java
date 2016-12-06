package com.xa.httpunit;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.xa.util.Security;

import junit.framework.TestCase;

public class BankCardHttpTest extends TestCase {

	private WebConversation wc = new WebConversation();

	@Override
	protected void setUp() throws Exception {
	}

	@Override
	protected void tearDown() throws Exception {
	}

	/**
	 * @throws SAXException
	 * @throws IOException
	 * 
	 */
	public void testGetBankCardList() throws IOException, SAXException {
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/bankcard/getBankCardList.htm",
				true);

		String sign = Security.getSign(new String[] { "buyHandId" });

		req.setParameter("buyHandId", "8");
		req.setParameter("sign", sign);
		WebResponse response = this.wc.getResponse(req);
		String text = response.getText();
		System.out.println(text);
	}

	public void testAddBankCard() throws IOException, SAXException {
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/bankcard/addBankCard.htm", true);
		String sign = Security.getSign(new String[] {"bankTypeId", "holderName", "no", "mobile","buyHandId" });
		req.setParameter("buyHandId", "1");
		req.setParameter("bankTypeId", "1");
		req.setParameter("no", "56587456564845");
		req.setParameter("mobile", "18217742115");
		req.setParameter("holderName", "黄浩");
		req.setParameter("sign", sign);
		WebResponse response = this.wc.getResponse(req);
		String text = response.getText();
		System.out.println(text);
	}

}
