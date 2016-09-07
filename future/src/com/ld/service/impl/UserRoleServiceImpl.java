package com.ld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.mapper.UserRoleMapper;
import com.ld.model.UserRole;
import com.ld.service.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, UserRoleMapper>
		implements UserRoleService<UserRole> {

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	
	/**
	 *  ��Ӷ���û���ɫ
	 */
	public void addUserRole(List<UserRole> list){
		  for(int i=0; i<list.size(); i++){
			  this.userRoleMapper.insert(list.get(i));
		  }
	}
}
