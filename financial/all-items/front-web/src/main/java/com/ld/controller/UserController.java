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
            //ָ��Ҫ���ص�ҳ��Ϊsucc.jsp  
            ModelAndView mav = new ModelAndView("succ");  
            //���������ظ�ҳ��  
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
