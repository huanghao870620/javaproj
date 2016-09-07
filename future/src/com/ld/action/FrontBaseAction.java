package com.ld.action;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ld.model.User;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
public class FrontBaseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4059542149748530706L;

	
	/**
	 * 获取前端登录用户
	 * @return
	 */
	public User getLoginUser(){
		return  (User)ActionContext.getContext().getSession().get(LoginUtil.FRONT_LOGIN_USER);
	}
}
