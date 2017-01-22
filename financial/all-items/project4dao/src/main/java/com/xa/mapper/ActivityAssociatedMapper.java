package com.xa.mapper;

import java.util.List;

import com.xa.entity.ActivityAssociated;

public interface ActivityAssociatedMapper extends BaseMapper<ActivityAssociated>{
	
	List<ActivityAssociated> getAssoByActivityId(Long activityId);
}