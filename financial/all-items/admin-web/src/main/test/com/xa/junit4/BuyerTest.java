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

import com.xa.entity.Buyers;
import com.xa.service.BuyersService;
import com.xa.util.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class BuyerTest {

	@Autowired
	private BuyersService<Buyers> buyersService;
	
	/**
	 * 
	 */
	@Test
	public void testGetVercode() {
	 	String sign= Security.getSign(new String[]{
				 "mobile"
		});
	 	
		Buyers buyers = new Buyers();
		buyers.setMobile("18217742115");
		try {
			String text = this.buyersService.getVercode(buyers , sign);
			System.out.println(text);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRegister() {
		String sign = Security.getSign(new String[]{
				"mobile","vercode","password"
		});
		String vercode = "232312";
		Buyers buyer = new Buyers();
		buyer.setMobile("18217742115");
		buyer.setPassword("123456");
		this.buyersService.register(buyer , vercode, sign);
	}
	
	/**
	 * 测试登录
	 */
	@Test
	public void testLogin() {
		Buyers buyers = new Buyers();
		buyers.setMobile("18217742115");
		buyers.setPassword("123456");
		String sign = Security.getSign(new String[]{
				"mobile","password"
		});
		this.buyersService.login(buyers , sign);
	}
}
