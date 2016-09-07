package com.ld.mapper;

import com.ld.model.Menu;

public interface MenuMapper extends BaseMapper<Menu>{

	
	Menu findMenuByName(String menuName);
}