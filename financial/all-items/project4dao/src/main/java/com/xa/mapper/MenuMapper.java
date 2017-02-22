package com.xa.mapper;

import java.util.List;

import com.xa.entity.Menu;

public interface MenuMapper extends BaseMapper<Menu>{
	List<Menu> selectTopMenu(Long pid);
	
	List<Menu> getChildMenu(Long pid);
}