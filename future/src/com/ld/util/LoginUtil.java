package com.ld.util;

import java.util.List;

import com.ld.model.CourseWare;
import com.ld.model.CustomerLevelLimitRela;
import com.ld.sftp.SftpUtil;

public class LoginUtil {

	/**
	 * 后端登录用户
	 */
	public static final String BACK_LOGIN_USER  = "backLoginUser";
	
	/**
	 * 前端登录用户
	 */
	public static final String FRONT_LOGIN_USER = "frontLoginUser";
	
	/**
	 * 前端登录用户 角色类型
	 */
	public static final String FRONT_CUSTOMER_ROLEID = "frontCustomerRoleId";
	
	/**
	 * 登录页面
	 */
	public static final String NO_LOGIN = "noLogin";
	
	/**
	 *游客权限 
	 */
    public static CustomerLevelLimitRela CUSLIMITRELA;
    /**
	 *课件信息
	 */
    public static List<CourseWare> COURSEWARELIST;

}
