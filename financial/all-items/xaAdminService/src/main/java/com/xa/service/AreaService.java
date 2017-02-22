package com.xa.service;

import com.xa.entity.Area;
import com.xa.service.BaseServiceInte;

public interface AreaService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getAllArea();
	
	/**
	 * 获取完整区域
	 * @param areaId
	 * @return
	 */
	public String getFullArea(Long areaId);
	
	/**
	 * 添加区域
	 * @param area
	 * @return
	 */
	public String addArea(Area area);
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public String delAreaById(Long id);
	
	/**
	 * 获取完整地址通过区域id
	 * @param areaId
	 * @return
	 */
	public String getFullAddress(Long areaId);
}
