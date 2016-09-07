package com.ld.menu;

import java.util.ArrayList;
import java.util.List;

import com.ld.model.Menu;
import com.ld.service.MenuService;

/**
 * µÝ¹é²Ëµ¥
 * @author hao.huang
 *
 */
public class RecursiveMenu {

	private MenuService<?> menuService;
	private String menuName;
	private List<Menu> breadCrumbs = new ArrayList<Menu>();
	
	public RecursiveMenu(MenuService<?> menuService, String menuName){
		 this.menuService = menuService;
		 this.menuName = menuName;
	}
	
	
	private void execute(){
		 Menu menu = this.menuService.findByName(this.menuName);
		 do{
			 this.breadCrumbs.add(menu);	 
			 menu = (Menu)this.menuService.findById(menu.getParentId());
		 }while(menu != null);
	}
	
	public List<Menu> getBreads(){
		 this.execute();
		 return this.breadCrumbs;
	}
}
