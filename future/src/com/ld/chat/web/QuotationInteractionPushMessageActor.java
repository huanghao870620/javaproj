package com.ld.chat.web;

import com.ld.dto.PrivateMsgReplyDto;
import com.ld.dto.TeacherViewMessageDto;
import com.ld.live.BaseActor;

/**
 * 实盘房间老师与客户消息推送者
 * @author gaoran
 * @updated 08日 07月-2016 13:58:44
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
