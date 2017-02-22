package com.xa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.User;
import com.xa.service.UserService;

@Controller
@RequestMapping("/user") 
public class UserController extends BaseController {
	
	@Autowired
	private UserService<User> userService;

	/**
	 * 跳转到登录页面
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toLogin")
	public ModelAndView index(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("login"); 
		return mav;
	}
	
	
	@RequestMapping("index")
	public ModelAndView toLogin(){
		ModelAndView mav = new ModelAndView("index");
//		mav.addObject("", "");
		return mav;
	}
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("login")
	public ModelAndView login(User user){
		ModelAndView mav = new ModelAndView();
		this.userService.login(mav, user,this.request);
		return mav;
	}
	
	/**
	 * 注销
	 * @return
	 */
	@RequestMapping("logout")
	public ModelAndView logout(){
		ModelAndView mav = new ModelAndView();
		this.userService.logout(mav, request);
		return mav;
	}
	

	/**
	 * 获取所有用户
	 * @param pageNum
	 * @param pageSize
	 */
	@RequestMapping("getUsers")
	public void getUsers(Integer page, Integer rows){
		try {
			this.sendAjaxMsg(this.userService.getUsers(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到用户列表页面
	 * @return
	 */
	@RequestMapping("toListUser")
	public ModelAndView toListUser(){
		ModelAndView modelAndView = new ModelAndView("user/userList");
		return modelAndView;
	}
	
	/**
	 * 跳转到用户中心页面
	 * @return
	 */
	@RequestMapping("toUserCenter")
	public ModelAndView toUserCenter(){
		ModelAndView modelAndView = new ModelAndView("userCenter/center");
		this.userService.getUser(modelAndView, request);
		return modelAndView;
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("editUser")
	public ModelAndView editUser(User user){
		ModelAndView modelAndView = new ModelAndView("redirect:toListUser.htm");
		this.userService.editUser(user);
		return modelAndView;
	}
	
	/**
	 * 去验证密码
	 * @return
	 */
	@RequestMapping("toValidatePassword")
	public ModelAndView toValidatePassword(){
		ModelAndView modelAndView = new ModelAndView("userCenter/validatePassword");
		this.userService.getUser(modelAndView, request);
		return modelAndView;
	}
	
	/**
	 * 验证密码
	 * @return
	 */
	@RequestMapping("validatePassword")
	public ModelAndView validatePassword(User user){
		ModelAndView modelAndView = new ModelAndView("userCenter/updatePassword");
		this.userService.validatePassword(modelAndView, user);
		return modelAndView;
	}
	
	
	/**
	 * 去修改密码
	 * @return
	 */
	@RequestMapping("toUpdatePassword")
	public ModelAndView toUpdatePassword(User user){
		ModelAndView modelAndView = new ModelAndView("userCenter/updatePassword");
		this.userService.validatePassword(modelAndView, user);
		return modelAndView;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("updatePass")
	public ModelAndView updatePass(User user, String repeatPass){
		ModelAndView modelAndView = new ModelAndView("redirect:toListUser.htm");
		this.userService.updatePass(modelAndView, user, repeatPass);
		return modelAndView;
	}
	
	/**
	 * 去添加用户
	 * @return
	 */
	@RequestMapping("toAddUser")
	public ModelAndView toAddUser(){
		ModelAndView modelAndView = new ModelAndView("user/addUser");
		return modelAndView;
	}
	
	/**
	 * 添加用户
	 * @return
	 */
	@RequestMapping("addUser")
	public ModelAndView addUser(User user){
		ModelAndView modelAndView = new ModelAndView("redirect:toListUser.htm");
		this.userService.addUser(user);
		return modelAndView;
	}
	
	
	
	
}
