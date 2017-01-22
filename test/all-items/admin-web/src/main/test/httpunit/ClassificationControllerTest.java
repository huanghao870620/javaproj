package httpunit;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.xa.util.Security;

public class ClassificationControllerTest {

	private WebConversation wc = new WebConversation();

	@Before
	public void before(){
		
	}
	
	@After
	public void after(){
		
	}
	
//	/*PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/customer/updateCustomer.htm",true);
//		req.setParameter("id", "8");
//		req.setParameter("nickname", "fffd");
//		
//		String sign = Security.getSign(new String[]{
//				"id",/*"headPortraitFile",*/"nickname","signature"
//		});
//		req.setParameter("sign", sign);
//		
//		File headPortraitFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); //
//		UploadFileSpec[] headPortraitUploadFile = {new UploadFileSpec(headPortraitFile)};
////		req.setParameter("headPortraitFile", headPortraitUploadFile);
//		WebResponse response = wc.getResponse(req);
//		String msg = response.getText();
//		System.out.println(msg);*/
	
	/**
	 * 
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testGetAllClassification() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.102:8080/admin-web/classification/getAllClassification.htm",true);
		String sign = Security.getSign(new String[]{
				"random"
		});
		req.setParameter("sign", sign);
		req.setParameter("random", "22");
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();
		System.out.println(msg);
	}
}
