package com.ld.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ld.common.Dictionary;
import com.ld.model.User;
import com.ld.model.UserRole;

public class UserDto extends User{

	private static final long serialVersionUID = -1770747269500570508L;

	private String dupPassword; // �ظ�����
	
	private String checkCode;//��֤��
	
	private String levelName;//��Ա�ȼ�����
	
	private List<UserRole> roleList;//��ɫ
	private BigDecimal roleId;
	
	public UserDto(){}
	

	public BigDecimal getRoleId() {
		return roleId;
	}
	
	/**
	 * ��ɫid��Ϊ��
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
	 * �ǲ��ǽ�ʦ
	 * @return
	 */
	public boolean isLecturer(){
		return this.roleId !=null  && this.roleId.toString().equals(Dictionary.TEACHER_TYPE_ROLE);
	}
	
	
	/**
	 * �����ĸ���������
	 * @param user
	 */
	public void setFourBasicProp(User user){
		this.setName(user.getName());
		this.setNickName(user.getNickName());
		this.setUserId(user.getUserId());
		this.setLevelId(user.getLevelId());
	}
}
