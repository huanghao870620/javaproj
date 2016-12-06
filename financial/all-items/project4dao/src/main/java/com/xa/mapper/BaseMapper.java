package com.xa.mapper;

import java.util.List;


public interface BaseMapper<T> {

	 int insert(T record);
	 
	 List<T> findAll();
	 
	 List<T> findAllByPaging(Object dto);
	 
	 void updateByPrimaryKey(T record);
	 
	 void updateByPrimaryKeySelective(T record);
	 
	 void deleteByPrimaryKey(Long id);
	 
	 T selectByPrimaryKey(Long id); 
	 
}
