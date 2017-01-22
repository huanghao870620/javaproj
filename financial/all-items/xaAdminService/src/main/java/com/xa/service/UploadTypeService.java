package com.xa.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.xa.service.BaseServiceInte;

public interface UploadTypeService<T> extends BaseServiceInte<T> {
	
	public void getAllUploadType(ModelAndView mav);
	
	public String getAllUploadType(HttpServletRequest request);
	
	
	
}
