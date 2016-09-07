package com.ld.live;

/**
 * 推送未审核消息者
 * @author huang.hao
 * @version 1.0
 * @created 24-六月-2016 8:54:52
 */
public class PushAuditMessageActor extends BaseActor implements Runnable{
	
	private UserSessionBinding usb;
	private Message message;

	public PushAuditMessageActor(UserSessionBinding usb, Message message){
		this.usb = usb;
		this.message = message;
	}

	@Override
	public void run() {
//		this.usb.sendAlreadyAuditMsg(message);
		this.usb.sendNotAuditMsg(message);
	}
	
	


}