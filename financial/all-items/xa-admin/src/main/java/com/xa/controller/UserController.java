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
	 * 
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
	public void getUsers(Integer pageNum, Integer pageSize){
		try {
			this.sendAjaxMsg(this.userService.getUsers(pageNum, pageSize));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
