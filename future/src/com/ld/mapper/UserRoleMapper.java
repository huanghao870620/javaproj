package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.model.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole>{
	
	List<UserRole>  captureRoleByUserId(BigDecimal userId);
}