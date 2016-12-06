package com.xa.mapper;

import java.util.List;

import com.xa.entity.Area;

public interface AreaMapper extends BaseMapper<Area>{
	
	List<Area> findAreaByPid(Long pid);
}