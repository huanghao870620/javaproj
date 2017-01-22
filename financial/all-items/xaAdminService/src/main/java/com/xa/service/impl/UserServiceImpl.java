package com.xa.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;

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
			 userObj.accumulate("account", account);
			 array.add(userObj);
		}
		object.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, userPage.getTotal());
		return object.toString();
	}
}
