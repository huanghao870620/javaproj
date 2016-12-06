package com.ld.service;

import java.util.List;

import com.ld.dto.InternalDto;
import com.ld.model.Internal;
import com.ld.model.MessageBO;

public interface InternalService<T> extends BaseServiceInte<T> {

	//��ҳ��ѯ�����ڲ�	
	public List<Internal> queryInternalByAjax(String internalType);
	
	//��ӽ����ڲ�
	public MessageBO addInternal(InternalDto internalDto);
	
	//���½����ڲ���Ϣ
	public MessageBO updateInternal(InternalDto internalDto);
	
	//ɾ�������ڲ���Ϣ
	public MessageBO deleteInternal();
	
	//����ID��ѯ�����ڲ���Ϣ
	public MessageBO findById();
}
