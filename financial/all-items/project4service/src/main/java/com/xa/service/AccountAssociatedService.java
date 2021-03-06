package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface AccountAssociatedService<T> extends BaseServiceInte<T> {

	public String associatedAccount(String unionId, String mobile, Long accountType, String sign);
	
	/**
	 * 
	 * @param unionId
	 * @return
	 */
	public String dissociated(String unionId, String sign);
}
