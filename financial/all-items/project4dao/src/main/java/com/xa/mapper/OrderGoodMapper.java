package com.xa.mapper;

import com.xa.entity.Goods;
import com.xa.entity.OrderGood;

public interface OrderGoodMapper extends BaseMapper<OrderGood>{
	
	Goods getDetailByOrderId(Long orderId);
}