package com.ld.mapper;

import java.util.List;

import com.ld.dto.TeacherViewDto;
import com.ld.model.TeacherView;

public interface TeacherViewMapper extends BaseMapper<TeacherView>{
	
  List<TeacherView>  getLatestTeacherView(TeacherViewDto teacherViewDto);
  List<TeacherView>  selectGoneData();
}