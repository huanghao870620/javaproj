package com.ld.service;

import net.sf.json.JSONObject;

public interface UserListenService<T> extends BaseServiceInte<T> {

	public JSONObject getAllIp();
	
	public JSONObject getAllMsg();
}
