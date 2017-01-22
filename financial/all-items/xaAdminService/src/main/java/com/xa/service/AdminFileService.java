package com.xa.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface AdminFileService<T> extends BaseServiceInte<T> {

	
	public void getFile(ModelAndView modelAndView, Long id);
	
	public String getBigPicInfoById(Long id,HttpServletRequest request);
}
