package com.xa.mapper;


import java.util.List;
import java.util.Map;

import com.xa.entity.Goods;
import com.xa.entity.ShoppingCartGoods;

public interface ShoppingCartGoodsMapper extends BaseMapper<ShoppingCartGoods>{
	/**
	 * 
	 * @param cartId
	 * @return
	 */
	List<Goods> getGoodsByCartId(Long cartId);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	ShoppingCartGoods getShoppingCartGoodsByGoodIdAndCartId(Map<String,Object> map);
	
	/**
	 * 
	 * @param cartId
	 * @return
	 */
	List<ShoppingCartGoods> getSCGByCartId(Long cartId);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	ShoppingCartGoods getSCGByCartIdAndGoodId(Map<String, Object> map);
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<ShoppingCartGoods> getSCGByGoodIdAndShoppingCartId(Map<String, Object> map);
	
	
	
	
}