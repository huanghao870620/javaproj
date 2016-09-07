package com.ld.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ld.entity.User;
import com.ld.service.UserService;

@Controller
@RequestMapping("/user") 
public class UserController extends BaseController {
	
	@Autowired
	private UserService<User> userService;

	@RequestMapping("index")
	public ModelAndView index(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("home"); 
		return mav;
	}
	
	
	/**
	 * Ìø×ªµ½µÇÂ¼Ò³Ãæ
	 * @return
	 */
	@RequestMapping("toLogin")
	public ModelAndView toLogin(){
		ModelAndView mav = new ModelAndView("user/login");
		return mav;
	}
	
}
