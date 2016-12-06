package com.ld.service.impl;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ld.mapper.MenuMapper;
import com.ld.menu.GenerateMenu;
import com.ld.menu.GenerateMenuString;
import com.ld.model.Menu;
import com.ld.service.MenuService;
import com.opensymphony.xwork2.ActionContext;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuMapper> implements MenuService<Menu> {

	@Autowired
	private MenuMapper menuMapper;
	
	
	/**
	 * ���˵�
	 */
	public void fillMenu(StringBuilder sb, String basepath){
		List<Menu> menuList = this.menuMapper.findAll();
		GenerateMenu gm = new GenerateMenu(menuList);
		new GenerateMenuString(gm.getNodeMap(),sb, basepath);
	}
	
	
	/**
	 * ��ʼ���û��˵�
	 */
	public void initUserMenu(){
		StringBuilder sb = new StringBuilder();
		ActionContext ctx = ActionContext.getContext();
		this.fillMenu(sb, ServletActionContext.getRequest().getContextPath());
		ctx.getSession().put("menuHtml", sb.toString());
	}
	
	/**
	 * ���ݲ˵����ƻ�ȡ�˵�
	 * @param menuName
	 * @return
	 */
	public Menu findByName(String menuName){
		  return this.menuMapper.findMenuByName(menuName);
	}
	
	
}
