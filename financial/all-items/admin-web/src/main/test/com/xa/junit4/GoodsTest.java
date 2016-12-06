package com.xa.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Goods;
import com.xa.service.GoodsService;
import com.xa.util.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class GoodsTest {
	
	@Autowired
	private GoodsService<Goods> goodsService;

	@Test
	public void testGetGoodsByClassifi(){
		  String sign = Security.getSign(new String[]{
				  "classid"
		  });
		String text=  this.goodsService.getGoodsByClassifi(69L, sign);
		System.out.println(text);
	}
	
}
