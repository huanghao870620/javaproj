package com.ld.action;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.RoleDto;
import com.ld.dto.UserDto;
import com.ld.model.MessageBO;
import com.ld.model.Role;
import com.ld.model.User;
import com.ld.service.RoleService;
import com.ld.service.UserService;
import com.opensymphony.xwork2.ActionContext;

@Namespace(value="/back/user")
@ParentPackage(value="struts-login")
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})
public class BackUserAction extends BackBaseAction {

	private static final long serialVersionUID = 7430718321657513359L;
	
	@Autowired
	private UserService<User> userService;
	
	@Autowired
	private RoleService<Role> roleService;
	
	private UserDto userDto;
	
	private RoleDto roleDto;
	
	//新增、编辑用户标识
	private int flag = 0;
	
	//新增、编辑用户标识值
	private String operation;
	
	private File file; //上传文件对象
	
	private String fileFileName;//上传文件名
	

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
	public RoleDto getRoleDto() {
		return roleDto;
	}

	public void setRoleDto(RoleDto roleDto) {
		this.roleDto = roleDto;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 *注销用户
	 * @return
	 */
	@Action(value="logout", results={
			@Result(name=SUCCESS, location="/WEB-INF/front/login.jsp", type=DISPATCHER)
	})
	public String logout(){
		ActionContext ctx = ActionContext.getContext();
		ctx.getSession().clear();
		return SUCCESS;
	}

	
	/**
	 *  跳转到添加用户页面
	 * @return
	 */
	@Action(value="toAddUser",results={
			@Result(name=SUCCESS, location="/WEB-INF/back/user/user_add.jsp")
	})
	public String toAddUser(){
		//获取角色列表
		this.roleService.putRoles();
		this.setOperation("添加");
		return SUCCESS;
	}
	
	
	/**
	 * 添加用户
	 * @return
	 */
	@Action(value="addUser")
	public void addUser(){
		MessageBO messageBO = this.userService.addUser(userDto);
		writeJson(messageBO);
	}
	
	
	/**
	 * 跳转到列表页面
	 * @return
	 */
	@Action(value="toList",results={
			@Result(name=SUCCESS, location="/WEB-INF/back/user/user_list.jsp")
	})
	public String toList(){
		return SUCCESS;
	}
	
	
	/**
	 * 获取用户列表数据
	 */
	@Action(value="list")
	public void list(){
		this.userService.queryUserListByAjax();
	}
	
	/**
	 * 跳转到用户编辑页面
	 * @return
	 */
	@Action(value="toEditUser",results={
			@Result(name=SUCCESS,location="/WEB-INF/back/user/user_add.jsp")
	})
	public String toEditUser(){
		MessageBO messageBO = this.userService.findUserById();
		userDto = (UserDto) messageBO.getObj();
		//获取角色列表
		this.roleService.putRoles();
		this.setFlag(1);
		this.setOperation("编辑");
		return SUCCESS;
	}
	
	/**
	 * 更新用户信息
	 */
	@Action(value="updateUser")
	public void updateUser(){
		MessageBO messageBO = this.userService.updateUser(userDto);
		writeJson(messageBO);
	}
	
	/**
	 * ɾ���û�
	 */
	@Action(value="deleteUser")
	public void deleteUser(){
		MessageBO messageBO =this.userService.delete();
		writeJson(messageBO);
	}
	
	/**
	 * ��ת���޸�����ҳ��
	 * @return
	 */
	@Action(value="toUpdatePass",results={
			@Result(name=SUCCESS,location="/WEB-INF/back/user/user_password_update.jsp")
	})
	public String toUpdatePass(){
		MessageBO messageBO = this.userService.findUserById();
		userDto = (UserDto) messageBO.getObj();
		return SUCCESS;
	}
	
	/**
	 * �޸�����
	 */
	@Action(value="updatePassword")
	public void updatePassword(){
		MessageBO messageBO = this.userService.updateUserPass(userDto);
		writeJson(messageBO);
	}
	
	/**
	 * �ϴ��û�ͷ��
	 */
	@Action(value="uploadUserHeadPortrait")
	public void uploadUserHeadPortrait(){
		if(file != null){
			if(file.isFile()){
				MessageBO result = this.userService.uploadUserHeadPortrait(file,fileFileName);
				writeJson(result);
			}			
		}
	}
}
