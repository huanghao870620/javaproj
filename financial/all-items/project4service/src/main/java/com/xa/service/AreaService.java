package com.xa.service;

public interface AreaService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getAllArea(String random,String sign);
	
	/**
	 * 获取完整区域
	 * @param areaId
	 * @return
	 */
	public String getFullArea(Long areaId);
}
