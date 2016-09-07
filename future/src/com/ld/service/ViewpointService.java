package com.ld.service;

import java.io.File;
import java.util.List;

import com.ld.dto.ViewpointDto;
import com.ld.model.MessageBO;
import com.ld.model.Viewpoint;

public interface ViewpointService<T> extends BaseServiceInte<T> {

	//分页查询磊丹观点
	public List<Viewpoint> queryViewpointByAjax();
	
	//添加磊丹观点
	public MessageBO addViewpoint(ViewpointDto ViewpointDto);
	
	//删除磊丹观点
	public MessageBO delViewpoint();
	
	//根据ID查询磊丹观点
	public MessageBO findViewpointById();
	
	//编辑磊丹观点
	public MessageBO updateViewpoint(ViewpointDto ViewpointDto);
	
	//上传磊丹观点图片
	public MessageBO uploadViewpointPic(File file,String fileName);
	
}
