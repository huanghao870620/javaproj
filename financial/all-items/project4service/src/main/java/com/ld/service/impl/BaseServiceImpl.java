package com.ld.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.ld.mapper.BaseMapper;
import com.ld.service.BaseServiceInte;

public class BaseServiceImpl<T, M extends BaseMapper<T>> implements
		BaseServiceInte<T> {

	@Autowired
	public M m;

	/**
	 * 添加
	 */
	public int insert(T record) {
		return m.insert(record);
	}
	
	/**
	 * 更新
	 * @param record
	 */
	public void update(T record){
		 this.m.updateByPrimaryKey(record);
	}

	

	/**
	 * 分页查询
	 */
	@Override
	public List<T> findAllByPaging(Object dto) {
		 return this.m.findAllByPaging(dto);
	}

	@Override
	public void delete(BigDecimal id) {
		this.m.delete(id);
	}

	@Override
	public T findById(BigDecimal id) {
		return this.m.findById(id);
	}

	@Override
	public Page<T> queryByAjax(Object dto) {
		return (Page<T>)this.m.findAllByPaging(dto);
	}

}
