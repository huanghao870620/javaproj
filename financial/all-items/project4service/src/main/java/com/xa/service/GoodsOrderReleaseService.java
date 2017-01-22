package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface GoodsOrderReleaseService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param orderId
	 * @param goodsId
	 */
	public String bindingOrdersAndGoods(Long orderId, Long []goodsId);
}
