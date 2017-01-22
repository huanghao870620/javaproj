package com.xa.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.File;
import com.xa.mapper.FileMapper;
import com.xa.service.AdminFileService;
import com.xa.util.freemarker.ProcessFtl;

@Service
@Transactional
public class AdminFileServiceImpl extends BaseServiceImpl<File, FileMapper> implements AdminFileService<File> {

	/**
	 * 
	 * @param modelAndView
	 * @param id
	 */
	public void getFile(ModelAndView modelAndView, Long id){
		 File file= this.m.selectByPrimaryKey(id);
		 modelAndView.addObject("file", file);
	}
	
	/**
	 * 获取大图信息 ftl
	 * @param id
	 * @param request
	 * @return
	 */
	public String getBigPicInfoById(Long id,HttpServletRequest request){
		File file= this.m.selectByPrimaryKey(id);
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("file", file);
		ProcessFtl pFtl = new ProcessFtl(root , "goods/bigPicInfo.ftl", request.getContextPath());
		String msg = pFtl.process();
		//msg = msg.replaceAll("\r\n", "\\\r\n");//转义换行符  
		//msg = msg.replaceAll("\"", "'");  //替换双引号为单引号  
		return msg;
	}
}
