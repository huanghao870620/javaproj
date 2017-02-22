package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.entity.DebrisSession;
import com.xa.entity.FbsDs;
/**
 * 
 * @author burgess
 *
 */
public interface FbsDsMapper extends BaseMapper<FbsDs> {
	/**
	 * 获取专场的时间节点
	 * @param fbsId
	 * @return
	 */
	List<DebrisSession> getDebrisSessionByFBSID(Long fbsId);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<DebrisSession> getCronTimeByFbsIdAndDate(Map<String, Object> map);
	
	/**
	 * 
	 * @param fbsId
	 * @return
	 */
	List<String> getAllDateByfbsId(Long fbsId);
	
	/**
	 * 
	 * @param goodId
	 * @return
	 */
	Long  getFBSIDByGoodId(Long goodId);
}