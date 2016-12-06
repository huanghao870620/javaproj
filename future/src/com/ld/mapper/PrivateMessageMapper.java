package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.dto.PrivateMessageDto;
import com.ld.model.PrivateMessage;

public interface PrivateMessageMapper extends BaseMapper<PrivateMessage>{
	
	//获取客户提问过的观点ID集合
	public List<BigDecimal> getViewIdByCreator(PrivateMessageDto privateMessageDto);
	
	//根据私信创建者ID和观点ID获取私信信息集合
	public List<PrivateMessage> getPrivateMessageInfo(PrivateMessage privateMessage);
	
	//讲师获取提问过的客户ID集合
	public List<BigDecimal> getCreatorIdByTeacherId(BigDecimal teacherId);
	
	//根据观点ID获取提问ID集合
	public List<BigDecimal> getPriMsgIdByViewId(BigDecimal viewId);
	
	//检测提问是否存在回复
	public BigDecimal checkExistsReply(PrivateMessage privateMessage);
	
}