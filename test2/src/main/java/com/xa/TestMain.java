package com.xa;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;


public class TestMain {

	public static void main(String[] args) {
		ChuanglanSMS client = new ChuanglanSMS("I9179561","pKFeRloiIL7d4b"); // 国际手机
//		ChuanglanSMS client = new ChuanglanSMS("N5621831","WetVhfjNGgf898"); // 国内手机
		CloseableHttpResponse response = null;
		try {
			//发送国际验证码
//			response = client.sendInternationalMessage("0014156376028","xxxxx：1234567");
			response = client.sendInternationalMessage("0015108131979","寻安：1234567");
			
			if(response != null && response.getStatusLine().getStatusCode()==200){
				System.out.println(EntityUtils.toString(response.getEntity()));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.close();
	}

}
