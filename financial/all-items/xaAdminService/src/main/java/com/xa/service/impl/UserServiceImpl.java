package com.xa.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;

import org.apache.poi.poifs.crypt.EcmaDecryptor;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.User;
import com.xa.mapper.UserMapper;
import com.xa.service.UserService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.EncryptionTool;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserMapper> implements UserService<User> {

	
	/**
	 * 添加用户
	 * @param user
	 */
	public void addUser(User user){
		String encPass = EncryptionTool.addSaltEncrypt(user.getPassword(), Security.getPasswordSalt());
		user.setPassword(encPass);
		this.m.insert(user);
	}
	
	/**
	 * 登录
	 * @param mav
	 */
	public void login(ModelAndView mav,User user,HttpServletRequest request){
		 String encPass = EncryptionTool.addSaltEncrypt(user.getPassword(), Security.getPasswordSalt());
		 user.setPassword(encPass);
		 List<User> users= this.m.selectUserByUserPass(user);
		 if(users.size()>0){
			 User u= users.get(0);
//			 mav.addObject("user", u);
			 request.getSession().setAttribute("user", u);
			 mav.setViewName("redirect:index.htm");
		 }else {
			 //不存在此用户
			 mav.setViewName("redirect:toLogin.htm");
		}
	}
	
	/**
	 * 注销
	 */
	public void logout(ModelAndView mav,HttpServletRequest request){
		  mav.setViewName("redirect:toLogin.htm");
		  request.getSession().removeAttribute("user");
		  request.getSession().invalidate();
	}
	
	/**
	 * 获取用户
	 */
	public String getUsers(Integer pageNum, Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize, true);
		Page<User> userPage = (Page<User>) this.m.findAll();
		List<User> userList= userPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0; i<userList.size(); i++){
			 JSONObject userObj = new JSONObject();
			 User user= userList.get(i);
			 String account = user.getAccount();
			 String email = user.getEmail();
			 userObj.accumulate("account", account)
			 .accumulate("email", email)
			 ;
			 array.add(userObj);
		}
		object.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, userPage.getTotal());
		return object.toString();
	}
	
	/**
	 * 获取用户信息
	 * @param modelAndView
	 * @param request
	 */
	public void getUser(ModelAndView modelAndView,HttpServletRequest request){
		User user=(User) request.getSession().getAttribute("user");
		user=this.m.selectByPrimaryKey(user.getId());
		modelAndView.addObject("user",user);
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void editUser(User user){
		this.m.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 验证密码
	 * @param user
	 * @return
	 */
	public void validatePassword(ModelAndView modelAndView,User user){
		 User userDb= this.m.selectByPrimaryKey(user.getId());
		 String encPass = EncryptionTool.addSaltEncrypt(user.getPassword(), Security.getPasswordSalt());
		 if(encPass.equals(userDb.getPassword())){
		 }else {
			 modelAndView.addObject(Constants.MSG, "密码不正确!");
			 modelAndView.setViewName("userCenter/validatePassword");
		 }
	}
	
	/**
	 * 修改密码
	 * @param user
	 * @param repeatPass
	 */
	public void updatePass(ModelAndView modelAndView, User user, String repeatPass){
		
		if(!user.getPassword().equals(repeatPass)){
			modelAndView.addObject(Constants.MSG, "两次密码输入不一致!");
			modelAndView.setViewName("userCenter/updatePassword");
			return;
		}
		
		String encPass = EncryptionTool.addSaltEncrypt(user.getPassword(), Security.getPasswordSalt());
		user.setPassword(encPass);
		this.m.updateByPrimaryKeySelective(user);
	}
}
