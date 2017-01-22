package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface ShoppingCartService<T> extends BaseServiceInte<T> {

	/**
	 * 获取购物车通过买家id
	 * @param buyerId
	 * @param sign
	 * @return
	 */
	public String getCartByBuyerId(Long buyerId, String sign) ;
}
