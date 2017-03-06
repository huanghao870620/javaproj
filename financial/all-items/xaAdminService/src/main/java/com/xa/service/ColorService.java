package com.xa.service;

import com.xa.entity.Color;

public interface ColorService<T> extends BaseServiceInte<T> {

	public void addColor(Color color);
	
	
	public String listColor(Integer pageNum, Integer pageSize);
}
