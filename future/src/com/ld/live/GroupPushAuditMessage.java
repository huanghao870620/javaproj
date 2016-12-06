package com.ld.live;

import java.util.Collection;

/**
 * 群发未审核消息
 * @author huang.hao
 * @version 1.0
 * @updated 23-六月-2016 18:48:25
 */
public class GroupPushAuditMessage {
	
	private Collection<UserSessionBinding> groups;
	private Message message;

	public GroupPushAuditMessage(Collection<UserSessionBinding> groups, Message message){
			this.groups = groups;
			this.message = message;
	}
	
	public void groupSendMessage(){
		for(UserSessionBinding usb : this.groups){
			Thread th = new Thread(new PushAuditMessageActor(usb, this.message));
			th.start();
		}
	}


}