package com.ld.service;

import java.io.IOException;

import freemarker.template.TemplateException;
import net.sf.json.JSONObject;


public interface LevelService<T> extends BaseServiceInte<T> {

	public void getAllLevel();
	
	public JSONObject getAllLevelByJson() throws IOException, TemplateException;
}
