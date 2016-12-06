package com.xa.junit4;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Goods;
import com.xa.service.GoodsService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class GoodsTestForJunit4 {

	@Autowired
	private GoodsService<Goods> goodsService;
	
	
	/**
	 * 商品查询接口
	 */
	@Test
	public void testGetGoodsByPaging(){
		 this.goodsService.getGoodsByPaging(1, 10,"");
	}
	
	@Test
	public void testAddGood(){
		Goods good = new Goods();
		good.setBrandId(1L);
		good.setCapacity(20L);
		good.setClassid(31L);
		good.setColor("红色");
		good.setDateOfProduction(new Date());
		good.setPrice(30L);
		String mallName="aaa";
		String mallAddress="bbb";
		String text = "";
//		try {
//			text = this.goodsService.addGood(null, null, null,  null, good , mallName, mallAddress, "");
//			System.out.println(text);
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
