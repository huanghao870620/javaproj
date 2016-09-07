package com.ld.live;

/**
 * ����δ�����Ϣ��
 * @author huang.hao
 * @version 1.0
 * @created 24-����-2016 8:54:52
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