package com.xa.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public String getMenuForStr(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		List<Menu> topMenus = this.menuMapper.selectTopMenu(43L);
		for (int i = 0; i < topMenus.size(); i++) {
			Menu menu = topMenus.get(i);
			JSONObject obj = new JSONObject();
			this.getChild(menu, obj,request);
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
	private void getChild(Menu menu, JSONObject obj,HttpServletRequest request) {
		obj.accumulate("id", menu.getId()).accumulate("text", this.spellHref(menu,request));
		List<Menu> childMenus = this.menuMapper.getChildMenu(menu.getId());
		if (childMenus.size() > 0) {
			JSONArray childArray = new JSONArray();
			for (int j = 0; j < childMenus.size(); j++) {
				Menu childMenu = childMenus.get(j);
				JSONObject childObj = new JSONObject();
				this.getChild(childMenu, childObj,request);
				childArray.add(childObj);
			}
			obj.accumulate("children", childArray);
		}
	}
	
	
	private String spellHref(Menu menu,HttpServletRequest request){
		   StringBuilder sb = new StringBuilder();
		   if(menuMapper.getChildMenu(menu.getId()).size() > 0){
			   sb.append(menu.getName());
		   }else {
			   sb.append("<span onclick=\"iframesrc($('#content_iframe'),'");
			   sb.append(request.getContextPath());
			   sb.append(menu.getUrl());
			   sb.append("')\">");
			   sb.append(menu.getName());
			   sb.append("</span>");
		   }
		   return sb.toString();
	}

}
