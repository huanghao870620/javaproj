package com.xa.service;

import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.AllocType;
import com.xa.service.BaseServiceInte;

public interface AllocTypeService<T> extends BaseServiceInte<T> {

	public void addAllocType(AllocType allocType);
	
	public String listAllocType(Integer pageNum, Integer pageSize);
	
	public void getAllocType(ModelAndView modelAndView, Long id);
	
	public void updateAllocType(AllocType allocType);
	
	public String delAllocType(Long id);
	
	public void getAllAllocType(ModelAndView modelAndView);
}
