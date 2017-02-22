package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.dto.OrderDto;
import com.xa.entity.Orders;

public interface OrdersMapper extends BaseMapper<Orders>{
	
	List<Orders> getOrdersByState(Map<String, Object> map);
	
	Orders findOrderByOrderNo(String orderNo);
	
	Long getOrderCountByGoodId(Map<String, Object> map);
	
	
	List<OrderDto> searchOrder(Map<String, Object> map);
	
	
	OrderDto getOrderDtoByOrderId(Long orderId);
	
	/**
	 * 获取可以使用优惠券的商品id
	 * @param map
	 * @return
	 */
	List<Long> getCanUseCouponsGood(Map<String, Object> map);
	
}