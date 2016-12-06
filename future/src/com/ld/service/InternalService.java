package com.ld.service;

import java.util.List;

import com.ld.dto.InternalDto;
import com.ld.model.Internal;
import com.ld.model.MessageBO;

public interface InternalService<T> extends BaseServiceInte<T> {

	//分页查询交易内参	
	public List<Internal> queryInternalByAjax(String internalType);
	
	//添加交易内参
	public MessageBO addInternal(InternalDto internalDto);
	
	//更新交易内参信息
	public MessageBO updateInternal(InternalDto internalDto);
	
	//删除交易内参信息
	public MessageBO deleteInternal();
	
	//根据ID查询交易内参信息
	public MessageBO findById();
}
