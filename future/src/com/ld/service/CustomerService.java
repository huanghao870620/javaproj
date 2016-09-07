package com.ld.service;

import java.util.List;

import com.ld.dto.UserDto;
import com.ld.model.MessageBO;

public interface CustomerService<T> extends BaseServiceInte<T>{
	
	public MessageBO updateCustomer(UserDto userDto);
	
	public MessageBO delete();
	
	public String getAllCustomer();
	
	public MessageBO addCustomer(UserDto userDto);
	
	public List<UserDto> findCustomerByAjax();
	
	public MessageBO findCustomerById();
	
}
