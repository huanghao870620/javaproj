package com.ld.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ld.model.User;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace(value="/back")
public class BackBaseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8184070711583584495L;
		
	
	
	
	/**
	 * ��ȡ��̨��¼�û�1
	 * @return
	 */
	public User getBackLoginUser(){
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> map = ctx.getSession();
	    return (User)map.get(LoginUtil.BACK_LOGIN_USER);	
	}
	
	
	/**
	 * ���õ�¼�û�
	 */
	public void setBackLoginUser(User user ){
		 ActionContext ctx = ActionContext.getContext();
		 Map<String,Object> session = ctx.getSession();
		 session.put(LoginUtil.BACK_LOGIN_USER, user);
	}

}
