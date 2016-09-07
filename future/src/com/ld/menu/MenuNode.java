package com.ld.menu;

import java.util.ArrayList;
import java.util.List;

import com.ld.model.Menu;

/**
 * 菜单节点
 * @author hao.huang
 * 
 */
public class MenuNode {

	private Integer level; // 菜单级别
	
	private Menu menu;
	
	private List<MenuNode> childs = new ArrayList<MenuNode>(); //子节点
	

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<MenuNode> getChilds() {
		return childs;
	}

	public void setChilds(List<MenuNode> childs) {
		this.childs = childs;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	
	
	
}
