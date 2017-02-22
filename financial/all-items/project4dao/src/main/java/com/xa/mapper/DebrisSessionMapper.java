package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.entity.DebrisSession;
/**
 * 
 * @author burgess
 *
 */
public interface DebrisSessionMapper extends BaseMapper<DebrisSession>{
	
	/**
	 * fbsId dsId
	 * 获取开始结束时间
	 * @param map
	 * @return
	 */
	List<DebrisSession> getDsByFbsIdAndDsId(Map<String, Object> map);
	
}