package httpunit;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.xa.util.Security;

public class OrderTest {
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
	public void testUnifiedorder() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("https://api.mch.weixin.qq.com/pay/unifiedorder",true);
		req.setParameter("appid", "wx4a8459702c7f3962");
//		req.setParameter("attach", "支付测试");
		req.setParameter("body", "APP test");
		req.setParameter("mch_id", "1417930102");
		req.setParameter("nonce_str", "1add1a30ac87aa2db72f57a2375d8fec");
		req.setParameter("notify_url", "http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");
		req.setParameter("out_trade_no", "1415659990");
		req.setParameter("spbill_create_ip", "14.23.150.211");
		req.setParameter("total_fee", "1");
		req.setParameter("trade_type", "APP");
		req.setParameter("sign", "69DC7D68EA076DF2BB1F87780FF91D78");
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();	
		 msg=new String(msg.getBytes("iso-8859-1"),"utf-8");
//		String charset= response.getCharacterSet();
//		System.out.println(charset);
		System.out.println(msg);
	}
	
	/**
	 * 获取签名
	 */
	@Test
	public void testGetSign(){
		Map<String, String> map = new LinkedHashMap<String,String>();
		map.put("appid", "wx4a8459702c7f3962");
		map.put("body", "test");
		map.put("device_info", "1000");
		map.put("mch_id", "10000100");
		map.put("nonce_str", "ibuaiVcKdpRxkhJA");
		
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(String key : map.keySet()){
			sb.append(key).append("=").append(map.get(key));
			if(i!=map.keySet().size()-1){
				sb.append("&");
			}
			i++;
		}
		sb.append("&").append("key=").append("1c60fcc3d0c0c313ea2b292f45d44267");
		System.out.println(sb.toString());
		String sign= new Md5PasswordEncoder().encodePassword(sb.toString(), null).toUpperCase();
		System.out.println(sign);
	}
	
	@Test
	public void testGetOrdersByState() throws IOException, SAXException{
		PostMethodWebRequest req = new PostMethodWebRequest("http://127.0.0.1:8080/order/getOrdersByState.htm",true);
		req.setParameter("state", "1");
		String sign = Security.getSign(new String[]{
				"state"
		});
		req.setParameter("sign", sign);
		WebResponse response = wc.getResponse(req);
		String msg = response.getText();	
		System.out.println(msg);
	}
}
