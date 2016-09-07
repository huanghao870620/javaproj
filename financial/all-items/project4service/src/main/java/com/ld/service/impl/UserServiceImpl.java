package com.ld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.entity.User;
import com.ld.mapper.UserMapper;
import com.ld.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserMapper> implements UserService<User> {

	@Autowired
	private UserMapper userMapper;
	
	
}
