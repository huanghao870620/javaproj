package com.ld.chat.web;


import java.util.Collection;
import com.ld.dto.TeacherViewDto;

/**
 * 微博消息群发
 * @author gao.ran
 *@updated 24-六月-2016 11:44:44
 */
public class QuotationGroupPushMessage {

	private Collection<UserDtoSessionBinding> groups;
	private TeacherViewDto message;
	private CommonMsgThreadPool pool=CommonMsgThreadPool.getInstance();
	
	public QuotationGroupPushMessage(Collection<UserDtoSessionBinding> groups, TeacherViewDto message){
		this.groups = groups;
		this.message = message;
	}
	
	/**
	 * 群发消息
	 */
	public void groupSendMessage(){
		
		for(UserDtoSessionBinding binding : this.groups){
			pool.execute(new QuotationPushMessageActor(binding, this.message));
		}
	}
	
}
