package com.xa.service;

public interface BrandService<T> extends BaseServiceInte<T> {

	/**
	 * 获取所有品牌
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getBrands(String random,String sign);
}
