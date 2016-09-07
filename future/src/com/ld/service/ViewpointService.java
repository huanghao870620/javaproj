package com.ld.service;

import java.io.File;
import java.util.List;

import com.ld.dto.ViewpointDto;
import com.ld.model.MessageBO;
import com.ld.model.Viewpoint;

public interface ViewpointService<T> extends BaseServiceInte<T> {

	//��ҳ��ѯ�ڵ��۵�
	public List<Viewpoint> queryViewpointByAjax();
	
	//����ڵ��۵�
	public MessageBO addViewpoint(ViewpointDto ViewpointDto);
	
	//ɾ���ڵ��۵�
	public MessageBO delViewpoint();
	
	//����ID��ѯ�ڵ��۵�
	public MessageBO findViewpointById();
	
	//�༭�ڵ��۵�
	public MessageBO updateViewpoint(ViewpointDto ViewpointDto);
	
	//�ϴ��ڵ��۵�ͼƬ
	public MessageBO uploadViewpointPic(File file,String fileName);
	
}
