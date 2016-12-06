package com.ld.chat.web;


import java.util.Collection;
import com.ld.model.WeiboMessage;

/**
 * 微博消息群发
 * @author gao.ran
 *@updated 24-六月-2016 11:44:44
 */
public class WeiboGroupPushMessage {

	private Collection<UserDtoSessionBinding> groups;
	private WeiboMessage message;
	
	public WeiboGroupPushMessage(Collection<UserDtoSessionBinding> groups, WeiboMessage message){
		this.groups = groups;
		this.message = message;
	}
	
	/**
	 * 群发消息
	 */
	public void groupSendMessage(){
		
		for(UserDtoSessionBinding binding : this.groups){
			Thread th = new Thread(new WeiboPushMessageActor(binding, this.message));
			th.start();
		}
	}
	
}
