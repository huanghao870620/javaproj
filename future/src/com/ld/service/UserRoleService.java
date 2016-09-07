package com.ld.service;

import java.util.List;

import com.ld.model.UserRole;

public interface UserRoleService<T> extends BaseServiceInte<T> {

	public void addUserRole(List<UserRole> list);
}
