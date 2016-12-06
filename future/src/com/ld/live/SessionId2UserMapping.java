package com.ld.live;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import com.ld.chat.web.BaseUserMapping;
import com.ld.model.User;

/**
 * ����û���
 * @author huang.hao
 *
 */
public class SessionId2UserMapping extends BaseUserMapping<UserSessionBinding>{

	public SessionId2UserMapping(){}
	
	
	
	/**
	 * ��Ӻ�̨�û���
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
	 * �ַ���Ϣ
	 */
	public void distMsg(Message message){
		GroupPushAuditMessage gpm = new GroupPushAuditMessage(this.getAllBinding(), message);
		gpm.groupSendMessage();
	}
	
	/**
	 * ��ȡ���к�̨�û���session��
	 * @return
	 */
	public Collection<UserSessionBinding> getAllBinding(){
		return this.binding.values();
	}
	
	
	
}
