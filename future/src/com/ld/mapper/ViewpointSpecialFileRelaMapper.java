package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.model.ViewpointSpecialFileRela;

public interface ViewpointSpecialFileRelaMapper extends BaseMapper<ViewpointSpecialFileRela>{
	
	//根据观点ID查询
	List<ViewpointSpecialFileRela> findListById(BigDecimal id);
	
	//根据观点ID和图片顺序查询
	ViewpointSpecialFileRela findByIdAndSequ(ViewpointSpecialFileRela viewpointSpecialFileRela);
}