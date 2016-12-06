package com.xa.junit3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xa.entity.CustomerOrder;
import com.xa.mapper.CustomerOrderMapper;

import junit.framework.TestCase;

/**
 * 
 * @author admin
 *
 */
public class CustomerOrderTest extends TestCase{

	private ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:mvc-context.xml");
	private CustomerOrderMapper customerOrderMapper;

	@Override
	protected void setUp() throws Exception {
		 this.customerOrderMapper = (CustomerOrderMapper)ctx.getBean("customerOrderMapper");
	}



	@Override
	protected void tearDown() throws Exception {
	}

	/**
	 * 添加客户订单
	 */
	public void testAddCustomerOrder(){
		CustomerOrder co = new CustomerOrder();
		co.setBuyHandId(11L);
		co.setOrderId(1L);
		this.customerOrderMapper.insert(co);
	}
}
