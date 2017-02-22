package com.xa.service;

import com.xa.entity.ShoppingCartGoods;
import com.xa.service.BaseServiceInte;

public interface ShoppingCartGoodsService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param scg
	 * @param sign
	 * @return
	 */
	public String addGoodsToCart(ShoppingCartGoods scg,Long dgId, String sign);
	
	/**
	 * 
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getAllCartGoods(Long cartId,Integer pageNum,Integer pageSize, String sign);
	
	/**
	 * 删除购物车中的商品
	 * @param scgId
	 * @param sign
	 * @return
	 */
	public String removeSCG(Long scgId, String sign);
}
