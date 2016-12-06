package com.xa.mapper;


import java.util.List;

import com.xa.entity.Goods;
import com.xa.entity.GoodsOrderRelease;

public interface GoodsOrderReleaseMapper extends BaseMapper<GoodsOrderRelease>{
	/**
	 * 通過訂單id獲取商品
	 * @return
	 */
	public List<Goods> getGoodsByOrderId(Long orderId);
}