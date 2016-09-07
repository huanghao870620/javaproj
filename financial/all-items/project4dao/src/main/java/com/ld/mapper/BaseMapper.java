package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;


public interface BaseMapper<T> {

	 int insert(T record);
	 
	 List<T> findAll();
	 
	 List<T> findAllByPaging(Object dto);
	 
	 void updateByPrimaryKey(T record); // 更新记录
	 
	 void delete(BigDecimal id); // 删除记录
	 
	 T findById(BigDecimal id); // 根据id获取记录
	 
}
