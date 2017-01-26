package com.xa.mapper;

import java.util.List;

import com.xa.entity.Activity;

public interface ActivityMapper extends BaseMapper<Activity>{
	
	
	List<Activity> getActivityGoodIdList();
	
	
	Long selectCountByGoodId(Long goodId);
	
}