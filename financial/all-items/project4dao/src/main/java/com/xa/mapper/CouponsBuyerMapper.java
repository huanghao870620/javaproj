package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.entity.CouponsBuyer;

public interface CouponsBuyerMapper extends BaseMapper<CouponsBuyer>{
	
	  /**
	   * 获取优惠券通过状态
	   * @return
	   */
	  List<CouponsBuyer> findCouponsByState(Map<String, Object> map);
	  
	  /**
	   * 获取兑换记录通过买家id
	   * @param buyerId
	   * @return
	   */
	  List<CouponsBuyer> getCBByBuyerId(Long buyerId);
}