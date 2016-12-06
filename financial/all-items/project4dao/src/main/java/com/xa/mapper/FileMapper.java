package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.entity.File;

public interface FileMapper extends BaseMapper<File>{
   
	List<File> getFileByGoodIdAndTypeId(Map<String,Object> map);
}