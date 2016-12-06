package com.ld.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.UserDto;
import com.ld.mapper.CustomerLevelLimitRelaMapper;
import com.ld.mapper.UserMapper;
import com.ld.mapper.UserRoleMapper;
import com.ld.model.CustomerLevelLimitRela;
import com.ld.model.MessageBO;
import com.ld.model.User;
import com.ld.model.UserRole;
import com.ld.service.LoginService;
import com.ld.service.UserService;
import com.ld.util.LoginUtil;
import com.ld.util.Logs;
import com.opensymphony.xwork2.ActionContext;

@Transactional
@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	UserService<User> userService;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	UserRoleMapper userRoleMapper;
	
	@Autowired
	CustomerLevelLimitRelaMapper customerLevelLimitRelaMapper;
	
	
	public MessageBO login(UserDto userDto) {
		MessageBO messageBO = null;
		
		try {
			boolean bo = validateUser(userDto);
			if(bo){
				HttpSession session = ServletActionContext.getRequest().getSession();
				String checkCode = userDto.getCheckCode();
				String sessionCheckCode = (String)session.getAttribute("checkCode");
				if(StringUtils.isBlank(checkCode)||StringUtils.isBlank(sessionCheckCode)){
					messageBO = new MessageBO("0","验证码为空!");
					return messageBO;
				}
				if(!StringUtils.equals(checkCode, sessionCheckCode)){
					messageBO = new MessageBO("0","您输入的验证码不正确!");
					return messageBO;
				}
				//根据用户名、密码查询用户是否存在
				User user = userService.findUser(userDto);
				if(user!=null){
					ActionContext ctx = ActionContext.getContext();
					ctx.getSession().put(LoginUtil.FRONT_LOGIN_USER, user);
					List<UserRole>  urList = userRoleMapper.captureRoleByUserId(user.getUserId());
					CustomerLevelLimitRela  cusLimitRela = customerLevelLimitRelaMapper.findById(user.getLevelId());
					boolean isSpeak = false;
					boolean isBackUser = true;
					String roleId ="";
					if(urList!=null&&urList.size()>0){
						for(UserRole ur:urList)
						{
							 roleId = String.valueOf(ur.getRoleid());
							if(StringUtils.equals("3", String.valueOf(ur.getRoleid()))){
								//助理秘书
								user.setLevelId(new BigDecimal(10));
							}
							if(StringUtils.equals("5", String.valueOf(ur.getRoleid()))){
								isBackUser = false;
							}
							if(StringUtils.equals("3", String.valueOf(ur.getRoleid()))||StringUtils.equals("5", String.valueOf(ur.getRoleid()))){
								isSpeak = true;
							}
							ctx.getSession().put(LoginUtil.FRONT_CUSTOMER_ROLEID,ur.getRoleid());
						}
					}
					ctx.getSession().put("isSpeak", isSpeak);
					ctx.getSession().put("isBackUser", isBackUser);
					ctx.getSession().put("cusLimitRela", cusLimitRela);
					ctx.getSession().put("user", user);
					ctx.getSession().put("roleId", roleId);
					//更新最后登录时间
					user.setLastLoginTime(new Date());
					userMapper.updateByPrimaryKey(user);
					
					messageBO = new MessageBO("-1","登录成功!");
				}else{
					messageBO = new MessageBO("0","用户名或密码错误!");
				}
			}else{
				Logs.getLogger().info("用户名或密码为空");
				messageBO = new MessageBO("0","请输入用户名密码!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("LoginServiceImpl.login error",e);
			messageBO = new MessageBO("0","登录失败,请稍后再试!");
		}
		return messageBO;
	}
	
	/**
	 * 验证用户名密码是否为空
	 * @param userDto
	 * @return
	 */
	private boolean validateUser(UserDto userDto){
		if(userDto == null){
			return false;
		}
		if(StringUtils.isBlank(userDto.getUserName())){
			return false;
		}
		if(StringUtils.isBlank(userDto.getPassword())){
			return false;
		}
		return true;
	}
	
	
	public String getRemortIP(HttpServletRequest request) {  
		  if (request.getHeader("x-forwarded-for") == null) {  
		   return request.getRemoteAddr();  
		  }  
		  return request.getHeader("x-forwarded-for");  
		 }  
	

//游客登陆
	public void visitorlogin() {
		String ip =  this.getRemortIP(ServletActionContext.getRequest());
		ActionContext ctx = ActionContext.getContext();
		ctx.put("ip", ip);
		if(!LoginUtil.COURSEWARELIST.equals( ctx.getApplication().get("courseWareList")) ){//课件信息
			ctx.getApplication().put("courseWareList", LoginUtil.COURSEWARELIST);
		}
		if(!LoginUtil.CUSLIMITRELA.equals(ctx.getSession().get("cusLimitRela"))){
			ctx.getSession().put("cusLimitRela", LoginUtil.CUSLIMITRELA);
		}
		if (ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER) == null) {
			User user = new User();
			user.setUserName(randomNumber());
			user.setLevelId(new BigDecimal("0"));
			
//			ctx.getSession();
//			HttpSession session = ServletActionContext.getRequest().getSession();
			
			ctx.getSession().put("isSpeak", true);
			ctx.getSession().put("isBackUser", false);
			ctx.getSession().put("roleId", "5");// 客户
			ctx.getSession().put("user", user);
			ctx.getSession().put(LoginUtil.FRONT_LOGIN_USER, user);
			
			
//			session.setAttribute("isSpeak", true);
//			session.setAttribute("isBackUser", false);
//			session.setAttribute("roleId", "5");// 客户
//			session.setAttribute("user", user);
//			session.setAttribute(LoginUtil.FRONT_LOGIN_USER, user);
		}
	}
	
	//随机数
	public String randomNumber(){
		    int max=100000;
	        int min=1;
	        Random random = new Random();
	        String rNumber ="YK"+ (random.nextInt(max)%(max-min+1) + min);
		return rNumber;
	}
	
}
