package com.xa.service;

public interface FastBuySessionService<T> extends BaseServiceInte<T> {

	/**
	 * 获取所有秒杀专场
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getFBS(String random, String sign);
	
	/**
	 * 获取秒杀专场详情
	 * @param fsbId
	 * @param sign
	 * @return
	 */
	public String getFBSDetail(Long fsbId,String sign);
}
