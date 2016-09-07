package com.ld.service;

import com.ld.dto.CustomerUserDto;

public interface CustomerUserService<T> extends BaseServiceInte<T> {

	public void addCustomerUser(CustomerUserDto dto);
}
