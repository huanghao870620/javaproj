package com.xa.mapper;

import java.util.List;

import com.xa.entity.ActivityFile;

public interface ActivityFileMapper extends BaseMapper<ActivityFile>{
	List<ActivityFile> getActivityFileById(Long activityId);
	
}