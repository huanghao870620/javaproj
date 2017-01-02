package httpunit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * 
 * @author burgess
 *
 */
public class PhpRegTest {

	private WebClient webClient = new WebClient();
	
	@Before
	public void before(){
//		webClient.getOptions().setJavaScriptEnabled(true);  //启用JS解析器
//		webClient.getOptions().setRedirectEnabled(true);
//		//webClient.getOptions().setCssEnabled(false);  //禁用css
//		webClient.getOptions().setTimeout(10000); //1000ms timeout
	}
	
	/**
	 * 测试注册
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	@Test
	public void testReg() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		HtmlPage page= webClient.getPage("http://www.xunanbuy.com/Register.html");
		DomNodeList<HtmlElement>  domNodeList=page.getElementsByTagName("form");
		int s=domNodeList.size();
		HtmlForm form = (HtmlForm)domNodeList.get(0);
		 final HtmlSubmitInput button =form.getElementById("submits");
		 final HtmlTextInput username = form.getInputByName("user_name");  
	     final HtmlPasswordInput password1= form.getInputByName("password"); 
	     final HtmlPasswordInput password2= form.getInputByName("not_password"); 
	     final HtmlTextInput mobilePhone= form.getInputByName("mobile_phone"); 
	     final HtmlCheckBoxInput agree = form.getInputByName("agree");
	     final HtmlHiddenInput type = form.getInputByName("type");
	     
	     username.setValueAttribute("abcd123");
	     password1.setValueAttribute("123");
	     password2.setValueAttribute("123");
	     mobilePhone.setValueAttribute("15963325441");
	     type.setValueAttribute("0");
	     agree.setChecked(true);
//	     agree.click();
		Page page2= button.click();
		WebResponse resp=page2.getWebResponse();
		String text= resp.getContentAsString();
		int status=resp.getStatusCode();
		System.out.println(status);
		System.out.println(text);
	}
	
	
	public  void testHtmlPage() throws Exception{
		//新建webclient对象，相当于浏览器
		final WebClient  WebClient = new WebClient();
		//构造一个URL，为待访问的地址
		URL url = new URL("http://zhixing.court.gov.cn/search");
		HtmlPage page= (HtmlPage)WebClient.getPage(url);
		System.out.println(page.getTitleText());
		}
	
		public  void exescript()throws Exception{
		
		HtmlPage page= webClient.getPage("http://zhixing.court.gov.cn/search");
		System.out.println(page.asXml());
		System.out.println("---------------------------------");
		ScriptResult result = page.executeJavaScript("document.getElementById(\"pname\").value=\"陈旭光\";$('#searchForm').submit();");
		HtmlPage page2 = (HtmlPage)result.getNewPage();
		HtmlPage framepage = (HtmlPage)page2.getFrameByName("contentFrame").getEnclosedPage();
		System.out.println(framepage.asXml());
		}
		

}
