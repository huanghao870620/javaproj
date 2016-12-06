package com.ld.chat.web;

import java.util.Collection;
import com.ld.model.WeiboMessage;
/**
 * ʵ�̷���  ʵʱ��ϢȺ��
 * @author gao.ran
 *@updated 24-����-2016 11:44:44
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
	 * Ⱥ����Ϣ
	 */
	public void groupSendMessage(){
		
		for(UserDtoSessionBinding binding : this.groups){
			//Thread th = new Thread(new QuotationActualPushMessageActor(binding, this.message));
			//th.start();
			//�̳߳ع����߳�
			pool.execute(new QuotationActualPushMessageActor(binding, this.message));
		}
	}
	
}
