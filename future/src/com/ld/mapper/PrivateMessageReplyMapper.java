package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.model.PrivateMessageReply;

public interface PrivateMessageReplyMapper extends BaseMapper<PrivateMessageReply>{
    
	//����˽��ID��ȡ�ظ���Ϣ����
    public List<PrivateMessageReply> findByPrivateMessageId(BigDecimal privateMessageId);
}