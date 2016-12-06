package com.ld.service;

import java.util.List;

import com.ld.dto.CourseWareDto;
import com.ld.model.CourseWare;
import com.ld.model.MessageBO;

public interface CourseWareService<T> extends BaseServiceInte<T> {

	//分页查询交易课件	
	public List<CourseWare> queryCourseWareByAjax(String courseWareType);
	
	//添加交易课件
	public MessageBO addCourseWare(CourseWareDto coursewaredto);
	
	//更新交易课件信息
	public MessageBO updateCourseWare(CourseWareDto coursewaredto);
	
	//删除交易课件信息
	public MessageBO deleteCourseWare();
	
	//根据ID查询交易课件信息
	public MessageBO findById();
}
