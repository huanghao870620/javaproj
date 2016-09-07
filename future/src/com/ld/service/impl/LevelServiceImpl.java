package com.ld.service.impl;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ld.freemarker.ProcessFtl;
import com.ld.mapper.LevelMapper;
import com.ld.mapper.RoleMapper;
import com.ld.model.Level;
import com.ld.model.Role;
import com.ld.service.LevelService;
import com.opensymphony.xwork2.ActionContext;

import freemarker.template.TemplateException;
import net.sf.json.JSONObject;

@Service

public class LevelServiceImpl extends BaseServiceImpl<Level, LevelMapper > implements LevelService<Level> {
	 @Autowired
	 private LevelMapper levelMapper;
	 
	 @Autowired
	 private RoleMapper roleMapper;
	 
	 
	 /**
	  * 获取所有用户等级
	  */
	 public void getAllLevel(){
		 ActionContext ctx = ActionContext.getContext();
		 ctx.put("levels", this.levelMapper.findAll());
	 }
	 
	 
	 /**
	  * 获取所有级别  by ajax
	  * @return
	  * @throws TemplateException 
	  * @throws IOException 
	  */
	 public JSONObject getAllLevelByJson() throws IOException, TemplateException{
		 HttpServletRequest request = ServletActionContext.getRequest();
		 Map<String, Object> root = new HashMap<String,Object>();
		 root.put("levels", this.levelMapper.findAll());
		 ProcessFtl pf = new ProcessFtl(root, "show_level.ftl", request.getContextPath());
		 return new JSONObject()
		 .accumulate("data", pf.process())
		 ;
	 }
	 
	 /**
	  *  显示角色列表
	  */
	 public void putAllRole(){
		  ActionContext ctx = ActionContext.getContext();
		  List<Role> roleList = this.roleMapper.findAll();
		  ctx.put("roleList", roleList);
	 }
}
