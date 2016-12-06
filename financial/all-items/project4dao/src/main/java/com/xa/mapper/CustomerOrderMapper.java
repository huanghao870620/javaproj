package com.xa.mapper;


import java.util.List;

import com.xa.entity.CustomerOrder;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder>{
	List<CustomerOrder> getOrdersByBuyHandId(Long buyHandId);
}