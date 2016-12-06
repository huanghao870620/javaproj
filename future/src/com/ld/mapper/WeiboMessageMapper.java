package com.ld.mapper;

import java.util.List;

import com.ld.model.WeiboMessage;

public interface WeiboMessageMapper extends BaseMapper<WeiboMessage>{
  
	public List<WeiboMessage> getRecentlyFifteenMsg();
		
}