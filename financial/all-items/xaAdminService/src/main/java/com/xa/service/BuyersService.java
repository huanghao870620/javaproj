package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface BuyersService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getBuyers(String nameS, Integer pageNum,Integer pageSize);
}
