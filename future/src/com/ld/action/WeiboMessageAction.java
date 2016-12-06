package com.ld.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.model.WeiboMessage;
import com.ld.service.WeiboMessageService;


@Namespace(value="/")
@ParentPackage(value="struts-front-login")
@InterceptorRefs(value={
		@InterceptorRef(value="frontLoginStack")
})
public class WeiboMessageAction extends FrontBaseAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private WeiboMessageService<WeiboMessage> weiboMessageService;
	
	
	/**
	 * 获取普通房间历史消息
	 */
	@Action(value="getHistoryMsg")
	public void getHistoryMsg(){
		this.sendAjaxMsg(this.weiboMessageService.get50newsRecently());
	}
}
