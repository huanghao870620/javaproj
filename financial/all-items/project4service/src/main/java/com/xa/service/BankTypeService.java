package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface BankTypeService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param sign
	 * @return
	 */
	public String getAllBank(String sign);
}
