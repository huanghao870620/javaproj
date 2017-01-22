package com.xa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.xa.entity.Classification;
import com.xa.entity.UploadType;
import com.xa.mapper.UploadTypeMapper;
import com.xa.service.UploadTypeService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.freemarker.ProcessFtl;

import net.sf.json.JSONObject;

@Service
@Transactional
public class UploadTypeServiceImpl extends BaseServiceImpl<UploadType, UploadTypeMapper>
		implements UploadTypeService<UploadType> {

	/**
	 * 获取所有上传类型
	 * @param mav
	 */
	public void getAllUploadType(ModelAndView mav){
		  mav.addObject("types", this.m.findAll());
	}
	
	/**
	 * 获取所有上传类型
	 * @param request
	 * @return
	 */
	public String getAllUploadType(HttpServletRequest request){
		List<UploadType> ups= this.m.findAll();
		Map<String, Object> root = new HashMap<String,Object>();
		root.put("ups", ups);
		String msg = new ProcessFtl(root, "classification/getClassifi.ftl", request.getContextPath()).process();
		return msg;
	}
	
	
	
	
}
