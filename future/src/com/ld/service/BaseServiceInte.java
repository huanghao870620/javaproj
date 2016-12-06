package com.ld.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ld.page.Page;

@Transactional
public interface BaseServiceInte<T> {
     int insert(T record);
     
     Page findAll();
     
     List<T> findAllByPaging(Object dto);
     
     void delete(BigDecimal id);
     
     T findById(BigDecimal id);
     
     com.github.pagehelper.Page<T> queryByAjax(Object dto);
     
}
