package com.xa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.config.VelocityConfigurerBeanDefinitionParser;

import com.xa.entity.GoodsOrderRelease;
import com.xa.mapper.OrdersMapper;
import com.xa.service.GoodsOrderReleaseService;

import junit.framework.TestCase;

public class GoodsOrdersReleaseTest extends TestCase {

	private ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:mvc-context.xml");
	private GoodsOrderReleaseService<GoodsOrderRelease> goodsOrderReleaseService;

	@Override
	protected void setUp() throws Exception {
		 this.goodsOrderReleaseService = (GoodsOrderReleaseService)ctx.getBean("goodsOrderReleaseService");
	}



	@Override
	protected void tearDown() throws Exception {
	}
	
	/**
	 * 測試訂單和商品綁定
	 */
	public void testBindingGoodsAndOrders(){
		Long goodsId[] = new Long[]{3L};
		this.goodsOrderReleaseService.bindingOrdersAndGoods(1L, goodsId);
	}
	
	
}
