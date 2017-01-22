package com.xa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xa.entity.Orders;
import com.xa.enumeration.OrdersState;
import com.xa.mapper.FileTypeMapper;
import com.xa.mapper.OrdersMapper;

import junit.framework.TestCase;

public class OrdersTest extends TestCase {

	
	private ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:mvc-context.xml");
	private OrdersMapper ordersMapper;

	@Override
	protected void setUp() throws Exception {
		 this.ordersMapper = (OrdersMapper)ctx.getBean("ordersMapper");
	}



	@Override
	protected void tearDown() throws Exception {
	}

	
	/**
	 * 添加訂單
	 */
	public void testAddOrders(){
		Orders orders = new Orders();
		orders.setState(OrdersState.HAS_BEEN_CANCELLED.getValue());
		this.ordersMapper.insert(orders);
	}
	
}
