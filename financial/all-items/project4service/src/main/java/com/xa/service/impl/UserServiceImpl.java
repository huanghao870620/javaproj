package com.xa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.User;
import com.xa.mapper.UserMapper;
import com.xa.service.UserService;
import com.xa.service.impl.BaseServiceImpl;
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserMapper> implements UserService<User> {

	@Autowired
	private UserMapper userMapper;
}
