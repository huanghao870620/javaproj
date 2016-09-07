package com.ld.service;

import com.github.pagehelper.Page;
import com.ld.dto.RoleDto;
import com.ld.model.Role;

public interface RoleService<T> extends BaseServiceInte<T> {

	public Page<Role> queryByAjax(RoleDto dto);
	
	public void putRoles();
	
	public String getUserByRole(RoleDto dto);
}
