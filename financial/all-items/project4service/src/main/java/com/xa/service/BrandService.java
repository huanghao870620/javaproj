package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface BrandService<T> extends BaseServiceInte<T> {

	/**
	 * 获取所有品牌
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getBrands(String random,String sign);
	
	/**
	 * 根据品牌id获取商品
	 * @param brandId
	 * @param sign
	 * @return
	 */
	public String getGoodsByBrandId(Long brandId,String nameS,Long buyerId,Integer pageNum,Integer pageSize, String sign);
}
