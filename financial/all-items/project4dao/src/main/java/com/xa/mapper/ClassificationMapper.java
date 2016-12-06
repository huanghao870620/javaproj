package com.xa.mapper;


import java.util.List;

import com.xa.entity.Classification;

public interface ClassificationMapper extends BaseMapper<Classification>{
	
	/**
	 * 根据父id获取分类
	 * @param pid
	 * @return
	 */
	List<Classification> findClassByPid(Long pid);
}