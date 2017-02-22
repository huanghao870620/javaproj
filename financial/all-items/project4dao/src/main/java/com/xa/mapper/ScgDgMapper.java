package com.xa.mapper;

import java.util.List;
import java.util.Map;

import com.xa.entity.ScgDg;
/**
 * 
 * @author burgess
 *
 */
public interface ScgDgMapper extends BaseMapper<ScgDg>{
	
	
	List<ScgDg> getSDBySCGIDAndDgId(Map<String, Object> map);
}