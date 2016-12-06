package com.ld.service;

import java.io.File;
import java.util.List;

import com.ld.dto.UserDto;
import com.ld.model.MessageBO;
import com.ld.model.User;

public interface UserService<T> extends BaseServiceInte<T> {
	
	public boolean deteWhetheUserExists(UserDto user);
	
	public User findUser(UserDto user);
	
	public User findUserByUserName(UserDto user);
	
	public MessageBO addUser(UserDto dto);
	
	public MessageBO delete();
	
	public List<User> queryUserListByAjax();
	
	public MessageBO findUserById();
	
	public MessageBO updateUser(UserDto user);
	
	public MessageBO updateUserPass(UserDto userDto);
	
	public MessageBO uploadUserHeadPortrait(File file,String fileName);
	
}
