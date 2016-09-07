package com.ld.chat.web;

import com.ld.dto.PrivateMsgReplyDto;
import com.ld.dto.TeacherViewMessageDto;
import com.ld.live.BaseActor;

/**
 * ʵ�̷�����ʦ��ͻ���Ϣ������
 * @author gaoran
 * @updated 08�� 07��-2016 13:58:44
 */
public class QuotationInteractionPushMessageActor extends BaseActor  implements Runnable {
	
	private UserDtoSessionBinding binding;
	private PrivateMsgReplyDto message;
	
	public QuotationInteractionPushMessageActor(UserDtoSessionBinding  binding, PrivateMsgReplyDto message){
		 this.binding = binding;
		 this.message = message;
	}

	@Override
	public void run() {
		this.binding.sendQuotationInteractionMsg(message);
	}

}
