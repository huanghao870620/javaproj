package com.xa.service;

public interface DsGoodService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param dgId
	 * @param sign
	 * @return
	 */
	public String getDsGood(Long dgId, String sign);
}
