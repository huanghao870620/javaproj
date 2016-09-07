package com.ld.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ld.common.Dictionary;
import com.ld.model.User;
import com.ld.model.UserRole;

public class UserDto extends User{

	private static final long serialVersionUID = -1770747269500570508L;

	private String dupPassword; // 重复密码
	
	private String checkCode;//验证码
	
	private String levelName;//会员等级名称
	
	private List<UserRole> roleList;//角色
	private BigDecimal roleId;
	
	public UserDto(){}
	

	public BigDecimal getRoleId() {
		return roleId;
	}
	
	/**
	 * 角色id不为空
	 * @return
	 */
	public boolean roleIdIsNotNull(){
		return this.roleId != null;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public String getDupPassword() {
		return dupPassword;
	}

	public void setDupPassword(String dupPassword) {
		this.dupPassword = dupPassword;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public List<UserRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UserRole> roleList) {
		this.roleList = roleList;
	}
	
	
	/**
	 * 是不是讲师
	 * @return
	 */
	public boolean isLecturer(){
		return this.roleId !=null  && this.roleId.toString().equals(Dictionary.TEACHER_TYPE_ROLE);
	}
	
	
	/**
	 * 设置四个基本属性
	 * @param user
	 */
	public void setFourBasicProp(User user){
		this.setName(user.getName());
		this.setNickName(user.getNickName());
		this.setUserId(user.getUserId());
		this.setLevelId(user.getLevelId());
	}
}
