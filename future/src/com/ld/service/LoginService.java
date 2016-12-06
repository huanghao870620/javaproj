package com.ld.service;

import com.ld.dto.UserDto;
import com.ld.model.MessageBO;


public interface LoginService {
	
	public MessageBO login(UserDto userDto);
	public void visitorlogin();
}
