package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface CouponsBuyerService<T> extends BaseServiceInte<T> {

	
	public String getCouponsByState(Long buyerId, Integer state,Integer pageNum, Integer pageSize,String sign);
}
