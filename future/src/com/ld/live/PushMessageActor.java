package com.ld.live;

import org.springframework.web.context.WebApplicationContext;

/**
 * ��Ϣ������
 * @author hao.huang
 * @version 1.0
 * @updated 11-����-2016 13:58:44
 */
public class PushMessageActor extends BaseActor implements Runnable {
	
	private CustomerSessionBinding binding;
	private Message message;
	private WebApplicationContext ctx;
	
	
	public PushMessageActor(CustomerSessionBinding binding, Message message){
		 this.binding = binding;
		 this.message = message;
	}
	
	public PushMessageActor(CustomerSessionBinding binding, Message message, WebApplicationContext ctx){
		 this.binding = binding;
		 this.message = message;
		 this.ctx = ctx;
	}


	@Override
	public void run() {
		this.binding.sendAlreadyAuditMsg(message, ctx);
	}

}
