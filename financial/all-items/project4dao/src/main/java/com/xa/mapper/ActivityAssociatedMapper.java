package com.xa.mapper;

import java.util.List;

import com.xa.dto.AssoActivityDto;
import com.xa.entity.ActivityAssociated;
/**
 * 
 * @author burgess
 *
 */
public interface ActivityAssociatedMapper extends BaseMapper<ActivityAssociated>{
	
	List<ActivityAssociated> getAssoByActivityId(Long activityId);
	
	void deleteASByActivityId(Long activityId);
	
	List<AssoActivityDto> getCheckBrandByActivityId(Long activityId);
	
	List<AssoActivityDto> getCheckCountryByActivityId(Long activityId);
}