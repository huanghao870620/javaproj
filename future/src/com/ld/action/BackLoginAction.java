package com.ld.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.UserDto;
import com.ld.model.Menu;
import com.ld.service.MenuService;

@Namespace(value="/back")
@ParentPackage(value="struts-login")
/*
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})*/
public class BackLoginAction extends BackBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5321539606971727832L;

	
	@Autowired
	private MenuService<Menu> menuService;

	private UserDto user;

	private String checkCode;

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	
	
	
	/**
	 * 后台首页
	 * @return
	 */
	@Action(value="backIndex", results={
			@Result(name=SUCCESS, location="/WEB-INF/back/admin/back_index.jsp", type="dispatcher")
	})
	public String backIndex(){
		this.menuService.initUserMenu();
		return SUCCESS;
	}
	
	/**
	 * 后台登录
	 * @return
	 */
	public String login(){
		return SUCCESS;
	}
	
	/**
	 * 跳转到后台登录页面
	 * @return
	 */
	@Action(value="", results={
			@Result(name=SUCCESS, location="", type=DISPATCHER)
	})
	public String toLogin(){
		return SUCCESS;
	}

}
