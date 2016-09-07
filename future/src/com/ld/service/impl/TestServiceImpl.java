package com.ld.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ld.mapper.UserMapper;
import com.ld.model.User;
import com.ld.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public void do0() {
		User user = new User();
		user.setUserId(new BigDecimal("2"));
		user.setPassword("3333");
		user.setUserName("ะกร๗");
		this.userMapper.insert(user);
	}

}
