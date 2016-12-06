package com.xa.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

@Transactional
public interface BaseServiceInte<T> {
     int insert(T record);
     
     List<T> findAllByPaging(Object dto);
     
     void delete(Long id);
     
     T findById(Long id);
     
     Page<T> queryByAjax(Object dto);
     
}
