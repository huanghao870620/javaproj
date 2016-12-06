package com.ld.chat.web;

import java.util.Collection;

import com.ld.dto.PrivateMsgReplyDto;
import com.ld.dto.TeacherViewMessageDto;
/**
 * 实盘房间  实时消息群发
 * @author gao.ran
 *@updated 24-六月-2016 11:44:44
 */
public class QuotationInteractionPushMessage {

	private Collection<UserDtoSessionBinding> groups;
	private PrivateMsgReplyDto message;
	private CommonMsgThreadPool pool=CommonMsgThreadPool.getInstance();
	
	public QuotationInteractionPushMessage(Collection<UserDtoSessionBinding> groups, PrivateMsgReplyDto message){
		this.groups = groups;
		this.message = message;
	}
	
	/**
	 * 群发消息
	 */
	public void groupSendMessage(){
		
		for(UserDtoSessionBinding  binding : this.groups){
			pool.execute(new QuotationInteractionPushMessageActor(binding, this.message));
		}
	}
	
}
