package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.dto.UserDto;
import com.ld.model.User;

public interface UserMapper extends BaseMapper<User>{

	User findUserByNameAndPass(User user);
	
	User findUser(UserDto dto);
	
	List<User> findTeachersList();

	void deleteByPrimaryKey(BigDecimal userId);
	
	List<UserDto> findCustomerByPaging(UserDto userDto);
}