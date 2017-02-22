package com.xa.mapper;

import java.util.List;

import com.xa.dto.HotSearchDto;
import com.xa.entity.GoodsSearchRecord;

public interface GoodsSearchRecordMapper extends BaseMapper<GoodsSearchRecord>{
	
	
	List<GoodsSearchRecord> getGSRByBuyerId(Long buyerId);
	
	List<HotSearchDto> getHotSearch();
	
	void deleteGSRByBuyerId(Long buyerId);
}