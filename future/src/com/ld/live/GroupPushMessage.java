package com.ld.live;

import java.util.Collection;

import org.springframework.web.context.WebApplicationContext;

/**
 * ��ϢȺ��
 * @author huang.hao
 *
 */
public class GroupPushMessage {

	private Collection<CustomerSessionBinding> groups;
	private Message message;
	
	public GroupPushMessage(Collection<CustomerSessionBinding> groups, Message message){
		this.groups = groups;
		this.message = message;
	}
	
	
	/**
	 * Ⱥ����Ϣ
	 */
	public void groupSendMessage(){
		
		for(CustomerSessionBinding binding : this.groups){
			if(this.isSelf(binding, message)){
				continue;
			}
			Thread th = new Thread(new PushMessageActor(binding, this.message));
			th.start();
		}
	}
	
	/**
	 * Ⱥ����Ϣ
	 */
	public void groupSendMessage(WebApplicationContext ctx){
		
		for(CustomerSessionBinding binding : this.groups){
			if(this.isSelf(binding, message)){
				continue;
			}
			Thread th = new Thread(new PushMessageActor(binding, this.message,ctx));
			th.start();
		}
	}
	
	
	/**
	 * 是不是自己
	 * @return
	 */
	private boolean isSelf(CustomerSessionBinding csb,  Message message){
		return csb.getSession().getId().equals(message.getSessionid());
	}
	
	/**
	 * 是不是游客
	 * @return
	 */
	private boolean isTourists(CustomerSessionBinding csb){
		 return null == csb.getEntity().getUserId();
	}
}
