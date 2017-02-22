package com.xa.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.User;
import com.xa.service.BaseServiceInte;
/**
 * 
 * @author burgess
 *
 * @param <T>
 */
public interface UserService<T> extends BaseServiceInte<T> {

	public void addUser(User user);
	
	public void login(ModelAndView mav,User user, HttpServletRequest request);
	
	public void logout(ModelAndView mav,HttpServletRequest request);
	
	public String getUsers(Integer pageNum, Integer pageSize);
	
	
	public void getUser(ModelAndView modelAndView,HttpServletRequest request);
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void editUser(User user);
	
	/**
	 * 验证密码
	 * @param user
	 * @return
	 */
	public void validatePassword(ModelAndView modelAndView,User user);
	
	/**
	 * 修改密码
	 * @param modelAndView
	 * @param user
	 * @param repeatPass
	 */
	public void updatePass(ModelAndView modelAndView, User user, String repeatPass);
}
