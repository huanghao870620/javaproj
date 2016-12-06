package com.xa.mapper;

import java.util.List;

import com.xa.entity.MobileVercodeLog;

public interface MobileVercodeLogMapper extends BaseMapper<MobileVercodeLog>{
	
	List<MobileVercodeLog> getVercodeByMobile(MobileVercodeLog mobileVercodeLog);
}