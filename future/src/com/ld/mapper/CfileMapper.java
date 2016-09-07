package com.ld.mapper;

import java.util.List;

import com.ld.model.Cfile;

public interface CfileMapper extends BaseMapper<Cfile>{
	List<Cfile> batchFindByCfileIdList(List<String> id); // 批量查询记录
	 void batchRemoveCfileById(List<String> id); // 批量删除记录
}