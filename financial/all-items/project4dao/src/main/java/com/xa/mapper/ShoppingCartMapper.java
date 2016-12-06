package com.xa.mapper;

import com.xa.entity.ShoppingCart;

public interface ShoppingCartMapper extends BaseMapper<ShoppingCart>{
	
	/**
	 * 获取购物车通过买家id
	 * @param buyerId
	 * @return
	 */
	ShoppingCart getCartByBuyerId(Long buyerId);
}