package com.xa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Role;
import com.xa.mapper.RoleMapper;
import com.xa.service.RoleService;
/**
 * 
 * @author burgess
 *
 */
import com.xa.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleMapper> implements RoleService<Role> {

	
	/**
	 * 添加角色
	 * @param role
	 */
	public void addRole(Role role){
		this.m.insert(role);
	}
	
	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getRoles(Integer pageNum,Integer pageSize){
		JSONObject object = new JSONObject();
	    PageHelper.startPage(pageNum, pageSize, true);
		Page<Role> rolePage=(Page<Role>) this.m.findAll();
		List<Role> roleList= rolePage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<roleList.size();i++){
			Role role= roleList.get(i);
			String name= role.getName();
			Long id= role.getId();
			JSONObject roleObj=new JSONObject();
			roleObj.accumulate("name", name)
			.accumulate("id", id)
			;
			array.add(roleObj);
		}
		object.accumulate(Constants.TOTAL, rolePage.getTotal())
		.accumulate(Constants.ROWS, array)
		;
		return object.toString();
	}
}
