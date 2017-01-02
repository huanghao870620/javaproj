package com.xa.junit4;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.ShoppingCart;
import com.xa.service.ShoppingCartService;
import com.xa.util.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class ShoppingCartTest {

	@Autowired
	private ShoppingCartService<ShoppingCart> shoppingCartService;
	
	
	@Test
	public void testGetCartByBuyerId(){
		 Long buyerId=7L;
		String sign =Security.getSign(new String[]{
					"buyerId"
			});
		String text =this.shoppingCartService.getCartByBuyerId(buyerId, sign);
		System.out.println(text);
	}
	
	
	@Test
	public void test0(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr= sdf.format(new Date());
		System.out.println(timeStr);
	}
	
}
