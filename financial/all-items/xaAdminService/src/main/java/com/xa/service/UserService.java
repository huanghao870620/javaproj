package com.xa.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.User;
import com.xa.service.BaseServiceInte;

public interface UserService<T> extends BaseServiceInte<T> {

	public void addUser(User user);
	
	public void login(ModelAndView mav,User user, HttpServletRequest request);
	
	public void logout(ModelAndView mav,HttpServletRequest request);
	
	public String getUsers(Integer pageNum, Integer pageSize);
}
