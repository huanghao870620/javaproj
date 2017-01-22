package com.xa.service;

import com.xa.entity.GoodFile;
import com.xa.service.BaseServiceInte;

public interface GoodFileService<T> extends BaseServiceInte<T> {

	
	public String addGoodFile(GoodFile goodFile);
}
