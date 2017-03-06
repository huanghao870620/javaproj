package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface CouponsService<T> extends BaseServiceInte<T> {

	
	public String getCouponsByExchange(String random,String sign);
}
