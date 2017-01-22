package com.xa.service;

import javax.servlet.http.HttpServletRequest;

import com.xa.service.BaseServiceInte;

public interface MenuService<T> extends BaseServiceInte<T>{

	public String getMenuForStr(HttpServletRequest request);
}
