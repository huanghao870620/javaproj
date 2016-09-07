package com.ld.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.ld.live.MessageQueue;
import com.ld.live.UserManager;
import com.ld.mapper.UserMapper;
import com.ld.model.User;
import com.ld.service.UserListenService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author huang.hao
 *
 */
@Service
public class UserListenServiceImpl extends BaseServiceImpl<User, UserMapper > implements UserListenService<User> {

	
	/**
	 * 获取所有ip
	 * @return
	 */
	public JSONObject getAllIp(){
		UserManager manager = UserManager.getUserManager();
		Set<String> ips = manager.getScm().getAllSessionId();
		String arr[] = new String[ips.size()];
	    ips.toArray(arr);
	    JSONArray array = new JSONArray();
	    for(int i=0; i<arr.length; i++){
	    	array.add(arr[i]);
	    }
	    JSONObject obj = new JSONObject();
	    return obj.accumulate("sessionids", array);
	}
	
	
	/**
	 * 获取所有消息
	 * @return
	 */
	public JSONObject getAllMsg(){
		UserManager manager = UserManager.getUserManager();
		MessageQueue mq = manager.getMq();
		return new JSONObject().accumulate("msgs", mq.msg2Json());
	}
}
