package com.ld.action;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.model.MessageBO;
import com.ld.model.User;
import com.ld.service.CustomerLimitService;

@Namespace(value = "/back/customer")
@ParentPackage(value="struts-login")
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})
public class CustomerLimitAction extends BackBaseAction {

	private static final long serialVersionUID = 1L;


	@Autowired
	private CustomerLimitService<User> customerLimitService;
	
	private String limitArrString;


	public String getLimitArrString() {
		return limitArrString;
	}


	public void setLimitArrString(String limitArrString) {
		this.limitArrString = limitArrString;
	}

	/**
	 * 修改客户权限
	 */
	
	@Action(value="toUpdateCustomerLimit")
	public void UpdateCustomerLimits(){
		MessageBO messageBO = this.customerLimitService.updateCustomerLimit();
		writeJson(messageBO);
		
	}
	
	/**
	 * 跳转到客户权限页面
	 * 
	 * @return
	 */
	@Action(value = "toCustomerLimit", results = {
			@Result(name = SUCCESS, location = "/WEB-INF/back/customer/customer_limit.jsp")
	})
	public String forwardCustomerLimit() {
	this.customerLimitService.getAllCustomerLimit();
		return SUCCESS;
	}

	
}
