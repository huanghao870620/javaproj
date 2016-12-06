package com.xa.service;

public interface CountryService<T> extends BaseServiceInte<T> {

	/**
	 * 获取所有国家
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getAllCountry(String random,String sign);
}
