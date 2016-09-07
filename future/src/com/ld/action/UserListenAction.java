package com.ld.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.service.UserListenService;

@Namespace(value="/front")
public class UserListenAction extends FrontBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8216898131568916611L;
	
	
	@Autowired
	private UserListenService<?> userListenService;
	
	
	/**
	 * 获取ip
	 */
	@Action(value = "getAllIp")
	public void listen(){
		this.sendAjaxMsg(this.userListenService.getAllIp().toString());
	}
	
	/**
	 * 获取所有消息
	 */
	@Action(value = "getAllMsg")
	public void getAllMsg(){
		this.sendAjaxMsg(this.userListenService.getAllMsg().toString());
	}
	
	
	/**
	 * 
	 */
	@Action(value = "toListenePage", results = {
			@Result(name = "success", location = "/WEB-INF/front/watch/view_ip.jsp", type = "dispatcher") })
	public String toListenePage(){
		 return SUCCESS;
	}

}
