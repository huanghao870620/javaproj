package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Menu;
import com.xa.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

	
	@Autowired
	private MenuService<Menu> menuService;
	
	/**
	 * 
	 */
	@RequestMapping("getMenu")
	public void getMenu(){
		 try {
			this.sendAjaxMsg(this.menuService.getMenuForStr());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
