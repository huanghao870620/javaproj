package com.xa.mapper;

import java.util.List;

import com.xa.entity.User;

public interface UserMapper extends BaseMapper<User>{
	
	List<User> selectUserByUserPass(User user);
}