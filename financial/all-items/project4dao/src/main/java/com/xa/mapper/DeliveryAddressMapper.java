package com.xa.mapper;

import java.util.List;

import com.xa.entity.DeliveryAddress;

public interface DeliveryAddressMapper extends BaseMapper<DeliveryAddress>{
	
	
	List<DeliveryAddress> getAddressByBuyerId(Long buyerId);
	
	void updateAddressIsNotDefault(Long buyerId);
	
	
	DeliveryAddress getDefaultAddressByBuyerId(Long buyerId);
}