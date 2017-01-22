package com.xa.mapper;

import java.util.List;

import com.xa.entity.GoodFile;

public interface GoodFileMapper extends BaseMapper<GoodFile>{
	
	
	List<GoodFile> selectGoodFileByGoodId(Long goodId);
}