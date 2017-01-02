package com.xa.mapper;


import java.util.List;
import java.util.Map;

import com.xa.entity.OrderRecv;
import com.xa.entity.Orders;

public interface OrderRecvMapper extends BaseMapper<OrderRecv>{
	
	List<Orders> getOrdersByBuyHandId(Map<String, Object> map);
}