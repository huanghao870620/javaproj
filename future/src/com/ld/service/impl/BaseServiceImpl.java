package com.ld.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ld.mapper.BaseMapper;
import com.ld.page.Page;
import com.ld.page.PageOut;
import com.ld.service.BaseServiceInte;
import com.opensymphony.xwork2.ActionContext;

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
	 * 查询所有
	 */
	@Override
	public Page findAll() {
		ActionContext ctx = ActionContext.getContext();
		Page page = (Page)ctx.get("page");
		
		PageOut po = new PageOut(page, this.m.findAll());
		ctx.put("po", po);
		return page;
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
	public com.github.pagehelper.Page<T> queryByAjax(Object dto) {
		return (com.github.pagehelper.Page<T>)this.m.findAllByPaging(dto);
	}

}
