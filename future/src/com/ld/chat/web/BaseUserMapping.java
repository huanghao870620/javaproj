package com.ld.chat.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ld.live.CustomerSessionBinding;
import com.ld.live.UserSessionBinding;

/**
 * @author huang.hao
 * @version 1.0
 * @created 28-����-2016 14:32:39
 */
public class BaseUserMapping<T> {
	
	protected Map<String, T> binding = new HashMap<String, T>();
	

	public BaseUserMapping(){}
	
	/**
	 * ɾ����̨�û���
	 * @param sessionId
	 */
	public void deleteBySessionId(String sessionId){
		  this.binding.remove(sessionId);
	}
	
	/**
	 * ��ȡ����ǰ̨�û��ͻỰ��
	 * @return
	 */
	public Collection<T> getAllBinding(){
		  return this.binding.values();
	}

}