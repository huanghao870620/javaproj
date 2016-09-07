package com.ld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.ld.dto.RoleDto;
import com.ld.mapper.RoleMapper;
import com.ld.model.Role;
import com.ld.model.User;
import com.ld.service.RoleService;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleMapper> implements RoleService<Role> {

	@Autowired	
	private RoleMapper roleMapper;
	
	
	public Page<Role> queryByAjax(RoleDto dto){
		  return super.queryByAjax(dto);
	}
	
	
	/**
	 * 添加权限列表
	 */
	public void putRoles(){
		 ActionContext ctx = ActionContext.getContext();
		 ctx.put("roles", this.roleMapper.findAll());
	}
	
	
	/**
	 * 获取所有用户通过角色
	 */
	public String getUserByRole(RoleDto dto){
		 List<User> userList = this.roleMapper.findUserByRole(dto);
		 
		 JSONArray array = new JSONArray();
		 for(int i=0; i<userList.size(); i++){
			 User user = userList.get(i);
			 JSONObject obj = new JSONObject();
			 obj.accumulate("userName", user.getUserName()).accumulate("userId", user.getUserId());
			 array.add(obj);
		 }
		 return array.toString();
	}
}
