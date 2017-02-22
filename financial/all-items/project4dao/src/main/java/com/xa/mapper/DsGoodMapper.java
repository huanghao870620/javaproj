package com.xa.mapper;

import java.util.List;

import com.xa.entity.DsGood;

public interface DsGoodMapper extends BaseMapper<DsGood>{
	
	List<DsGood> getDSGoodByDSID(Long dsId);
	
	
	DsGood getDGBySCGID(Long scgId);
	
	
	List<DsGood> getDsGoodByGoodId(Long goodId);
}