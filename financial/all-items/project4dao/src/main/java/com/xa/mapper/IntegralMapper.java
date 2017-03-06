package com.xa.mapper;

import com.xa.entity.Integral;
/**
 * 
 * @author burgess
 *
 */
public interface IntegralMapper extends BaseMapper<Integral>{
	
	
	Integral getIntegralByUserId(Long buyerId);
}