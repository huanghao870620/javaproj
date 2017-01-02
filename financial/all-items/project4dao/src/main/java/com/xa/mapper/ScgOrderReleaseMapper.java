package com.xa.mapper;

import java.util.List;

import com.xa.entity.ScgOrderRelease;

public interface ScgOrderReleaseMapper extends BaseMapper<ScgOrderRelease>{
	
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	 List<ScgOrderRelease> getSCGByOrderId(Long orderId);
}