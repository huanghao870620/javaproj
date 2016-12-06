package com.ld.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.ld.model.ViewpointSpecialFileRela;

public interface ViewpointSpecialFileRelaMapper extends BaseMapper<ViewpointSpecialFileRela>{
	
	//���ݹ۵�ID��ѯ
	List<ViewpointSpecialFileRela> findListById(BigDecimal id);
	
	//���ݹ۵�ID��ͼƬ˳���ѯ
	ViewpointSpecialFileRela findByIdAndSequ(ViewpointSpecialFileRela viewpointSpecialFileRela);
}