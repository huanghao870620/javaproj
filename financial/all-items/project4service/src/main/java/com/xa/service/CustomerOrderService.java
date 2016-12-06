package com.xa.service;

public interface CustomerOrderService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param buyHandId
	 * @param orders
	 */
	public void bindingCustomerAndOrder(Long buyHandId, Long[] orders);
	
	/**
	 * 获取订单通过客户id
	 * @param buyHandId
	 * @return
	 */
	public String getOrdersByBuyHandId(Long buyHandId, String sign);
}
