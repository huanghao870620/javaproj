package com.ld.chat.web;

import com.ld.live.BaseActor;
import com.ld.model.PrivateMessage;

/**
 * 微博消息推送者
 * @author gaoran
 * @version 1.0
 * @updated 22-六月-2016 13:58:44
 */
public class PrivatePushMessageActor extends BaseActor  implements Runnable {
	
	private UserDtoSessionBinding binding;
	private PrivateMessage message;
	
	public PrivatePushMessageActor(UserDtoSessionBinding binding, PrivateMessage message){
		 this.binding = binding;
		 this.message = message;
	}

	@Override
	public void run() {
		this.binding.sendPrivateMsg(message);
	}

}
