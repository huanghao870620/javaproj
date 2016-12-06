package com.ld.service;

import java.util.List;

import com.ld.dto.CourseWareDto;
import com.ld.model.CourseWare;
import com.ld.model.MessageBO;

public interface CourseWareService<T> extends BaseServiceInte<T> {

	//��ҳ��ѯ���׿μ�	
	public List<CourseWare> queryCourseWareByAjax(String courseWareType);
	
	//��ӽ��׿μ�
	public MessageBO addCourseWare(CourseWareDto coursewaredto);
	
	//���½��׿μ���Ϣ
	public MessageBO updateCourseWare(CourseWareDto coursewaredto);
	
	//ɾ�����׿μ���Ϣ
	public MessageBO deleteCourseWare();
	
	//����ID��ѯ���׿μ���Ϣ
	public MessageBO findById();
}
