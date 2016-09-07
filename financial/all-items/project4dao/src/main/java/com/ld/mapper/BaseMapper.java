package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;


public interface BaseMapper<T> {

	 int insert(T record);
	 
	 List<T> findAll();
	 
	 List<T> findAllByPaging(Object dto);
	 
	 void updateByPrimaryKey(T record); // ���¼�¼
	 
	 void delete(BigDecimal id); // ɾ����¼
	 
	 T findById(BigDecimal id); // ����id��ȡ��¼
	 
}
