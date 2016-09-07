package com.ld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@RequestMapping(value="login",method=RequestMethod.POST)  
	public ModelAndView login(String username, String password){
		if(this.checkParams(new String[]{username,password})){  
            //指定要返回的页面为succ.jsp  
            ModelAndView mav = new ModelAndView("succ");  
            //将参数返回给页面  
            mav.addObject("username",username);  
            mav.addObject("password", password);  
            return mav;  
        }  
		return new ModelAndView("home");
	}
	
	
	 private boolean checkParams(String[] params){  
	        for(String param:params){  
	            if(param==""||param==null||param.isEmpty()){  
	                return false;  
	            }  
	        }  
	        return true;  
	    }  
}
