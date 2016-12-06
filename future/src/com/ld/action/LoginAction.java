/**
 * 
 */
package com.ld.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.UserDto;
import com.ld.model.MessageBO;
import com.ld.service.LoginService;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author zeng.nian
 *
 */
@Namespace(value="/login")
@ParentPackage(value="struts-login")
public class LoginAction extends FrontBaseAction {

	private static final long serialVersionUID = -3455211003163024265L;
	
	@Autowired
	private LoginService loginService;
	
	private UserDto userDto;

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	/**
	 * 跳转到登录页面
	 * @return
	 */
	@Action(value="toLogin",results={
			@Result(name=SUCCESS, location="/WEB-INF/front/index.jsp", type=DISPATCHER)
	})
	public String toLogin(){
		this.loginService.visitorlogin();
		return  SUCCESS;
	}

	/**
	 * 用户登录
	 * @return
	 */
	@Action(value="login")
	public void login(){
		MessageBO messageBO = this.loginService.login(userDto);
		writeJson(messageBO);
	}
	
	
	/**
	 * 注销
	 * @return
	 */
	@Action(value="logout", results={
			@Result(name=SUCCESS, location="/login/toLogin.htm", type=REDIRECT)
	})
	public String logout(){
		ActionContext.getContext().getSession().remove(LoginUtil.FRONT_LOGIN_USER);
		ActionContext.getContext().getSession().remove(LoginUtil.FRONT_CUSTOMER_ROLEID);
		ActionContext.getContext().getSession().clear();
		return SUCCESS;
	}

}
