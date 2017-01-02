package com.xa.service;

import com.xa.entity.GoodFile;

public interface GoodFileService<T> extends BaseServiceInte<T> {

	
	public String addGoodFile(GoodFile goodFile);
}
