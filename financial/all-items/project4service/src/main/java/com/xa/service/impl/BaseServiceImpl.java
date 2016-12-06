package com.xa.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.xa.mapper.BaseMapper;
import com.xa.service.BaseServiceInte;

public class BaseServiceImpl<T, M extends BaseMapper<T>> implements
		BaseServiceInte<T> {

	@Autowired
	public M m;

	public int insert(T record) {
		return m.insert(record);
	}
	
	public void update(T record){
		 this.m.updateByPrimaryKey(record);
	}

	

	@Override
	public List<T> findAllByPaging(Object dto) {
		 return this.m.findAllByPaging(dto);
	}

	@Override
	public void delete(Long id) {
		this.m.deleteByPrimaryKey(id);
	}

	@Override
	public T findById(Long id) {
		return this.m.selectByPrimaryKey(id);
	}

	@Override
	public Page<T> queryByAjax(Object dto) {
		return (Page<T>)this.m.findAllByPaging(dto);
	}

}
