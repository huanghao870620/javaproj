package com.xa.service;

public interface IntegralService<T> extends BaseServiceInte<T> {

	
	public String getIntegralByBuyerId(Long buyerId,String sign);
	
	
	public String exchangeCoupons(Long couponsId,Long buyerId,String sign);
	
	/**
	 * 获取兑换记录
	 * @param buyerId
	 * @param sign
	 * @return
	 */
	public String getExchangeHistory(Long buyerId, String sign);
}
