package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.dto.PrivateMessageDto;
import com.ld.model.PrivateMessage;

public interface PrivateMessageMapper extends BaseMapper<PrivateMessage>{
	
	//��ȡ�ͻ����ʹ��Ĺ۵�ID����
	public List<BigDecimal> getViewIdByCreator(PrivateMessageDto privateMessageDto);
	
	//����˽�Ŵ�����ID�͹۵�ID��ȡ˽����Ϣ����
	public List<PrivateMessage> getPrivateMessageInfo(PrivateMessage privateMessage);
	
	//��ʦ��ȡ���ʹ��Ŀͻ�ID����
	public List<BigDecimal> getCreatorIdByTeacherId(BigDecimal teacherId);
	
	//���ݹ۵�ID��ȡ����ID����
	public List<BigDecimal> getPriMsgIdByViewId(BigDecimal viewId);
	
	//��������Ƿ���ڻظ�
	public BigDecimal checkExistsReply(PrivateMessage privateMessage);
	
}