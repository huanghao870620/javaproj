package com.ld.action;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.model.Level;
import com.ld.service.LevelService;

import freemarker.template.TemplateException;


public class LevelAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7351799714414354056L;
	
	
	@Autowired
	private LevelService<Level> levelService;
	
	
	/**
	 * 获取所有 level by ajax
	 */
	@Action(value="getAllLevelByAjax")
	public void getAllLevelByAjax(){
		 try {
			this.sendAjaxMsg(this.levelService.getAllLevelByJson().toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

}
