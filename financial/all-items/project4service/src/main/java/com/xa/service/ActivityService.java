package com.xa.service;

public interface ActivityService<T> extends BaseServiceInte<T> {

	/**
	 * 获取所有活动
	 * @param pageNum
	 * @param pageSize
	 * @param sign
	 * @return
	 */
	public String getActivityShufflingFigure(String random, String sign);
	
	/**
	 * 获取活动详情
	 * @param id
	 * @param sign
	 * @return
	 */
	public String getActivityById(Long id, String sign);
}
