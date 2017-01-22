package com.xa.mapper;

import java.util.List;

import com.xa.entity.Coupons;

public interface CouponsMapper extends BaseMapper<Coupons>{
	
	/**
	 * 通过分配类型获取优惠券
	 * @param allocTypeId
	 * @return
	 */
	List<Coupons> findByAllocType(Long allocTypeId);
	
	
	
}