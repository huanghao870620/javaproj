package com.ld.service;


import com.ld.model.MessageBO;

public interface CustomerLimitService<T> extends BaseServiceInte<T>{
	
	public MessageBO updateCustomerLimit();
	
	public void getAllCustomerLimit();

	
}
