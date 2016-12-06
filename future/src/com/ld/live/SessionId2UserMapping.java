package com.ld.live;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import com.ld.chat.web.BaseUserMapping;
import com.ld.model.User;

/**
 * 后端用户绑定
 * @author huang.hao
 *
 */
public class SessionId2UserMapping extends BaseUserMapping<UserSessionBinding>{

	public SessionId2UserMapping(){}
	
	
	
	/**
	 * 添加后台用户绑定
	 * @param sid
	 * @param session
	 * @param user
	 */
	public void addUserBinding(String sid,Session session, User user){
		  UserSessionBinding isbOld = this.binding.get(sid);
		  if(null == isbOld){
			  UserSessionBinding usb = new UserSessionBinding(session, user);
			  this.binding.put(sid, usb);
		  }
	}
	
	/**
	 * 分发消息
	 */
	public void distMsg(Message message){
		GroupPushAuditMessage gpm = new GroupPushAuditMessage(this.getAllBinding(), message);
		gpm.groupSendMessage();
	}
	
	/**
	 * 获取所有后台用户和session绑定
	 * @return
	 */
	public Collection<UserSessionBinding> getAllBinding(){
		return this.binding.values();
	}
	
	
	
}
