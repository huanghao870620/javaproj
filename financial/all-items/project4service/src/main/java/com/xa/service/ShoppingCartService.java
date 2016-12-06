package com.xa.service;

public interface ShoppingCartService<T> extends BaseServiceInte<T> {

	/**
	 * 获取购物车通过买家id
	 * @param buyerId
	 * @param sign
	 * @return
	 */
	public String getCartByBuyerId(Long buyerId, String sign) ;
}
