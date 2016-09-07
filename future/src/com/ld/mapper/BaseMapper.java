package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.page.Page;

public interface BaseMapper<T> {

	 int insert(T record);
	 
	 List<T> findAll();
	 
	 List<T> findAll(Page page);
	 
	 List<T> findAllByPaging(Object dto);
	 
	 void updateByPrimaryKey(T record); // ���¼�¼
	 
	 void delete(BigDecimal id); // ɾ����¼
	 
	 T findById(BigDecimal id); // ����id��ȡ��¼
	 
}
