package com.xa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.CustomerOrder;
import com.xa.service.CustomerOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@ContextConfiguration(locations = "classpath:mvc-context.xml")
public class CustomerOrderTest {

	@Autowired
	private CustomerOrderService<CustomerOrder> customerOrderService;
	
	/**
	 * 測試客戶和訂單綁定
	 */
	@Test
	public void testBindingCustomerAndOrders(){
		this.customerOrderService.bindingCustomerAndOrder(1L, new Long[]{1L});
	}
	
	/**
	 * 测试获取订单通过客户id
	 */
	@Test
	public void testGetOrdersByCustomerId(){
//		String json = this.customerOrderService.getOrdersByCustomerId(1L);
//		System.out.println(json);
	}
	
}
