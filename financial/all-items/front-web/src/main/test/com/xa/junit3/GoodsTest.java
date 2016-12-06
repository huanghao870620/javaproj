package com.xa.junit3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xa.entity.Goods;
import com.xa.mapper.GoodsMapper;

import junit.framework.TestCase;

public class GoodsTest extends TestCase {
	private ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:mvc-context.xml");
    private GoodsMapper GoodsMapper;
    
	@Override
	protected void setUp() throws Exception {
		 this.GoodsMapper = (GoodsMapper)ctx.getBean("GoodsMapper");
	}



	@Override
	protected void tearDown() throws Exception {
	}
	
	/**
	 * 测试添加商品
	 */
	public void testAddGoods(){
		Goods good = new Goods();
	}

}
