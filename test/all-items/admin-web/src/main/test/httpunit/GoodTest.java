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

public class GoodTest {
	
	
	private WebConversation wc = new WebConversation();

	@Before
	public void before(){
		
	}
	
	@After
	public void after(){
		
	}
	

	/**
	 * 测试更新头像
	 * @throws SAXException 
	 * @throws IOException 
	 */
	@Test
	public void  testUpdateHeadPortial() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/buyers/updateHeadPortial.htm",true);
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
	
	

	@Test
	public void testAddGood(){
		File file = new File("D:\\doc\\xunan\\20161230");
		if(file.isDirectory()){
			File files[]= file.listFiles();
			for(int i=0;i<files.length;i++){
				String path= files[i].getAbsolutePath();
				File childFile[]= files[i].listFiles();
				 for(int j=0;j<childFile.length;j++){
					 String childPath = childFile[j].getAbsolutePath();
//					 System.out.println(childPath);
					 
					 File sFile[]= childFile[j].listFiles();
					 for(int k=0;k<sFile.length;k++){
						 String sPath=  sFile[k].getAbsolutePath();
						 System.out.println(sPath);
					 }
				 }
			}
		}
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testAddGoodOld() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://192.168.1.110:8080/goods/addGood4Inner.htm",true);
		
		req.setParameter("name", "abcdefg");
		req.setParameter("info", "bbb");
		req.setParameter("price", "22.00");
		req.setParameter("classid", "50");
//		req.setParameter("shelfLife", "2016/12/30");
//		req.setParameter("dateOfProduction", "2016/12/30");
//		req.setParameter("addTime", "2016/12/30");
		
		
		File bigFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"); 
		UploadFileSpec[] big = {new UploadFileSpec(bigFile)};
		req.setParameter("bigFile", big);
		
		File smallFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg"); 
		UploadFileSpec[] small = {new UploadFileSpec(smallFile)};
		req.setParameter("smallFile", small);
		
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();
		System.out.println(msg);
	}
}
