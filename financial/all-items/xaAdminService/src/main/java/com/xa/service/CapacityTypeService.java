package com.xa.service;

import com.xa.entity.CapacityType;

public interface CapacityTypeService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param capacityType
	 */
	public void addCapacityType(CapacityType capacityType);
	
	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getCapacityTypes(Integer pageNum,Integer pageSize);
}
