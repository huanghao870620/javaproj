package com.ld.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

@Transactional
public interface BaseServiceInte<T> {
     int insert(T record);
     
     List<T> findAllByPaging(Object dto);
     
     void delete(BigDecimal id);
     
     T findById(BigDecimal id);
     
     Page<T> queryByAjax(Object dto);
     
}
