package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.entity.Orders;

public interface OrdersMapper extends BaseMapper<Orders>{
	
	List<Orders> getOrdersByState(Map<String, Object> map);
	
	Orders findOrderByOrderNo(String orderNo);
	
	Long getOrderCountByGoodId(Map<String, Object> map);
}