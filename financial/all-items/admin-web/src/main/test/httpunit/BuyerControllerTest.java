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

public class BuyerControllerTest {
	private WebConversation wc = new WebConversation();

	@Before
	public void before(){
		
	}
	
	@After
	public void after(){
		
	}
	
	@Test
	public void testGetVercode() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/buyers/getVercode.htm",true);
		String sign = Security.getSign(new String[]{
				"mobile"
		});
		req.setParameter("sign", sign);
		req.setParameter("mobile", "18217742115");
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();
		System.out.println(msg);
	}
	
	/**
	 * 测试更新头像
	 * @throws SAXException 
	 * @throws IOException 
	 */
	@Test
	public void  testUpdateHeadPortial() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.48.216/buyers/updateHeadPortial.htm",true);
		req.setParameter("buyerId", "20");
		
		String sign = Security.getSign(new String[]{
				"buyerId","headPortialFile"
		});
		req.setParameter("sign", sign);
		
		File headPortraitFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); //
		UploadFileSpec[] headPortraitUploadFile = {new UploadFileSpec(headPortraitFile)};
		req.setParameter("headPortialFile", headPortraitUploadFile);
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();
		System.out.println(msg);
	}
}
