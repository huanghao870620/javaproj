package com.xa.controller;

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

	@RequestMapping("login")
	public ModelAndView index(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("login"); 
		return mav;
	}
	
	
	@RequestMapping("index")
	public ModelAndView toLogin(){
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
}
