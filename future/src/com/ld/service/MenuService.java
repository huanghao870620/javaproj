package com.ld.service;

import com.ld.model.Menu;

public interface MenuService<T> extends BaseServiceInte<T>{

	public void fillMenu(StringBuilder sb, String basepath);
	
	public void initUserMenu();
	
	public Menu findByName(String menuName);
}
