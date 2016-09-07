package com.ld.mapper;

import java.util.List;

import com.ld.dto.RoleDto;
import com.ld.model.Role;
import com.ld.model.User;

public interface RoleMapper extends BaseMapper<Role>{

	/**
	 * ���ݽ�ɫ��ѯ�û�
	 * @param dto
	 * @return
	 */
	List<User> findUserByRole(RoleDto dto); 
}