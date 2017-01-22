package httpunit;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.protocol.UploadFileSpec;
import com.xa.util.Security;

public class DeliveryAddressTest {
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
	public void testAddAddress() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.48.216/deliveryAddress/addDeliveryAddress.htm",true);
		req.setParameter("buyerId", "20");

		String sign = Security.getSign(new String[]{
				"buyerId","headPortialFile"
		});
		req.setParameter("sign", sign);

		File headPortraitFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); //
		UploadFileSpec[] idCardFrontFile = {new UploadFileSpec(headPortraitFile)};
		req.setParameter("idCardFrontFile", idCardFrontFile);
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();
		System.out.println(msg);
	}
	
}
