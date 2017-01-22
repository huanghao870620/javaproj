package com.xa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Menu;
import com.xa.mapper.MenuMapper;
import com.xa.service.MenuService;
import com.xa.service.impl.BaseServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 菜单
 * @author 黄浩
 *
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuMapper> implements MenuService<Menu> {

	@Autowired
	private MenuMapper menuMapper;

	/**
	 * 获取菜单
	 */
	@Override
	public String getMenuForStr() {
		JSONArray array = new JSONArray();
		List<Menu> topMenus = this.menuMapper.selectTopMenu();
		for (int i = 0; i < topMenus.size(); i++) {
			Menu menu = topMenus.get(i);
			JSONObject obj = new JSONObject();
			this.getChild(menu, obj);
			array.add(obj);
		}
		return array.toString();
	}

	/**
	 * 获取孩子
	 * 
	 * @param menu
	 * @param obj
	 */
	private void getChild(Menu menu, JSONObject obj) {
		obj.accumulate("id", menu.getId()).accumulate("text", this.spellHref(menu));
		List<Menu> childMenus = this.menuMapper.getChildMenu(menu.getId());
		if (childMenus.size() > 0) {
			JSONArray childArray = new JSONArray();
			for (int j = 0; j < childMenus.size(); j++) {
				Menu childMenu = childMenus.get(j);
				JSONObject childObj = new JSONObject();
				this.getChild(childMenu, childObj);
				childArray.add(childObj);
			}
			obj.accumulate("children", childArray);
		}
	}
	
	
	private String spellHref(Menu menu){
		   StringBuilder sb = new StringBuilder();
		   sb.append("<a href=\"");
		   sb.append(menu.getUrl());
		   sb.append("\">");
		   sb.append(menu.getName());
		   sb.append("</a>");
		   return sb.toString();
	}

}
