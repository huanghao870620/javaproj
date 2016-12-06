package com.ld.chat.web;

import java.util.Collection;
import com.ld.model.WeiboMessage;
/**
 * 实盘房间  实时消息群发
 * @author gao.ran
 *@updated 24-六月-2016 11:44:44
 */
public class QuotationActualPushMessage {

	private Collection<UserDtoSessionBinding> groups;
	private WeiboMessage message;
	private CommonMsgThreadPool pool=CommonMsgThreadPool.getInstance();
	
	public QuotationActualPushMessage(Collection<UserDtoSessionBinding> groups, WeiboMessage message){
		this.groups = groups;
		this.message = message;
	}
	
	/**
	 * 群发消息
	 */
	public void groupSendMessage(){
		
		for(UserDtoSessionBinding binding : this.groups){
			//Thread th = new Thread(new QuotationActualPushMessageActor(binding, this.message));
			//th.start();
			//线程池管理线程
			pool.execute(new QuotationActualPushMessageActor(binding, this.message));
		}
	}
	
}
