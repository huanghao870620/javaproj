package httpunit;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class PhpDataTest {
	private WebConversation wc = new WebConversation();

	@Before
	public void before(){
		
	}
	
	@After
	public void after(){
		
	}
	
	
	/**
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testRegister() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://www.xunanbuy.com/Register/regDo.html",true);
		req.setParameter("user_name", "dd");
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();	
		System.out.println(msg);
	}
}
