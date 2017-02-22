package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Role;

import com.xa.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	
	@Autowired
	private RoleService<Role> roleService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping("toAddRole")
	public ModelAndView toAddRole(){
		ModelAndView modelAndView = new ModelAndView("role/addRole");
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("addRole")
	public ModelAndView addRole(Role role){
		ModelAndView modelAndView = new ModelAndView("redirect:toListRole.htm");
		this.roleService.addRole(role);
		return modelAndView;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("toListRole")
	public ModelAndView toListRole(){
		ModelAndView modelAndView = new ModelAndView("role/roleList");
		return modelAndView;
	}
	
	/**
	 * 
	 */
	@RequestMapping("listRole")
	public void listRole(Integer page,Integer rows){
		try {
			this.sendAjaxMsg(this.roleService.getRoles(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
