package com.xa.service;

import com.xa.entity.Role;

public interface RoleService<T> extends BaseServiceInte<T>{

	
	public void addRole(Role role);
	
	
	public String getRoles(Integer pageNum,Integer pageSize);
}
