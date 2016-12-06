package com.xa.service;

import com.xa.entity.DeliveryAddress;

public interface DeliveryAddressService<T> extends BaseServiceInte<T> {

	/**
	 * 添加收货地址
	 * @param address
	 * @param sign
	 * @return
	 */
	public String addDeliveryAddress(DeliveryAddress address,String sign);
}
