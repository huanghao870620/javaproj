package com.xa.service;

public interface BankTypeService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param sign
	 * @return
	 */
	public String getAllBank(String sign);
}
