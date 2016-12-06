package com.xa.junit4;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.BuyHand;
import com.xa.service.BuyHandService;
import com.xa.util.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class CustomerTestForJunit4 {
	
	@Autowired
	private BuyHandService<BuyHand> buyHandService;

	/**
	 * @throws IOException 
	 * @throws ParseException 
	 * 
	 */
	@Test
	public void testGetVercode() throws ParseException, IOException{
		 BuyHand customer = new BuyHand();
		 customer.setMobile("18217742115");
		 this.buyHandService.getVercode(customer,"");
	}
	
	/**
	 * 测试更新客户信息
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testUpdateCustomer() throws IllegalStateException, IOException{
		BuyHand customer = new BuyHand();
		customer.setId(10L);
		customer.setNickname("小红");
		customer.setGender("女");
		customer.setSignature("欢迎光临");
		String text = this.buyHandService.updateCustomer(customer,null,null,"");
		System.out.println(text);
	}
	
	/**
	 * 测试第三方登录
	 */
	@Test
	public void testThirdPartyLogin(){
		String text = this.buyHandService.thirdPartyLogin("oK5yqv0gGicGDG2kAMbBstUVJrN8","");
		System.out.println(text);
	}
	
	/**
	 * 
	 */
	@Test
	public void testVerificationCodeAreLegal() {
		String sign = Security.getSign(new String[]{
				"mobile","vercode" 
		});
		String text = this.buyHandService.verificationCodeAreLegal("17301748932", "123456", sign);
		System.out.println(text);
	}
	
	/**
	 * 测试更换手机号
	 */
	@Test
	public void testReplacePhoneNumber() {
		String sign = Security.getSign(new String[]{
				"vercode","newMobile","oldMobile"
		});
		String text = this.buyHandService.replacePhoneNumber("126537", "17006691494", "17301748932", sign);
		System.out.println(text);
	}
}
