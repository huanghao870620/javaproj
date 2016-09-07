package com.ld.mapper;

import java.util.List;

import com.ld.model.CourseWare;

public interface CourseWareMapper extends BaseMapper<CourseWare>{
	List<CourseWare> queryAllCourseWare(); 
}