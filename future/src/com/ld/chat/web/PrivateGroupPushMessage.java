package com.ld.chat.web;



import java.util.Collection;

import com.ld.model.PrivateMessage;

/**
 * ˽����Ϣ��ϢȺ��
 * @author gao.ran
 *
 */
public class PrivateGroupPushMessage {

	private Collection<UserDtoSessionBinding> groups;
	private PrivateMessage message;
	
	public PrivateGroupPushMessage(Collection<UserDtoSessionBinding> groups, PrivateMessage message){
		this.groups = groups;
		this.message = message;
	}
	
	/**
	 * Ⱥ����Ϣ
	 */
	public void groupSendMessage(){
		
		for(UserDtoSessionBinding binding : this.groups){
			Thread th = new Thread(new PrivatePushMessageActor(binding, this.message));
			th.start();
		}
	}
	
}
