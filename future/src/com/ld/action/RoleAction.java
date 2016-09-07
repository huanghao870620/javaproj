package com.ld.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.RoleDto;
import com.ld.model.Role;
import com.ld.service.RoleService;

@Namespace(value="/back/role")
@ParentPackage(value="struts-login")
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})
public class RoleAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8594914076052953209L;

	@Autowired
	private RoleService<Role> roleService;
	
	private RoleDto dto;
	
	
	public RoleDto getDto() {
		return dto;
	}


	public void setDto(RoleDto dto) {
		this.dto = dto;
	}


	/**
	 * 
	 * @return
	 */
	@Action(value="toList",results={
		@Result(name=SUCCESS, location="/WEB-INF/back/role/role_list.jsp", type="dispatcher")
	})
	public String forwardToList(){
		 return SUCCESS;
	}
	
	
	/**
	 * list 
	 */
	@Action(value="list")
	public void list(){
		this.roleService.queryByAjax(dto);
	}
}
