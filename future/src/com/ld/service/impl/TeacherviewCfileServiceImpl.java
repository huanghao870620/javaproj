package com.ld.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ld.mapper.TeacherviewCfileMapper;
import com.ld.model.TeacherviewCfile;
import com.ld.service.TeacherviewCfileService;

@Service
@Transactional
public class TeacherviewCfileServiceImpl  extends BaseServiceImpl<TeacherviewCfile, TeacherviewCfileMapper>
implements TeacherviewCfileService<TeacherviewCfile> {

}
