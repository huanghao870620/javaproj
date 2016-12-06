package com.xa.service;

public interface GoodsOrderReleaseService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param orderId
	 * @param goodsId
	 */
	public String bindingOrdersAndGoods(Long orderId, Long []goodsId);
}
