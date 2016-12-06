package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.model.PrivateMessageReply;

public interface PrivateMessageReplyMapper extends BaseMapper<PrivateMessageReply>{
    
	//根据私信ID获取回复信息集合
    public List<PrivateMessageReply> findByPrivateMessageId(BigDecimal privateMessageId);
}