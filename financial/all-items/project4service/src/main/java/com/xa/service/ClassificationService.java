package com.xa.service;

import com.xa.service.BaseServiceInte;

public interface ClassificationService<T> extends BaseServiceInte<T> {

	/**
	 * 获取所有分类
	 * @return
	 */
	public String getAllClassification(String random,String sign);
	
	/**
	 * 根据父id获取子分类
	 * @param sign
	 * @param pid
	 * @return
	 */
	public String getChildByClassId(String sign,Long pid);
}
