package com.ld.live;

/**
 * ������Ϣ���Լ�
 * @author huang.hao
 * @version 1.0
 * @created 27-����-2016 14:46:52
 */
public class PushMessage2MySelf {

	private CustomerSessionBinding csb; // ���Լ�
	
	private Message message;
	
	/**
	 * 
	 * @param csb
	 * @param message
	 */
	public PushMessage2MySelf(CustomerSessionBinding csb, Message message){
			this.csb = csb;
			this.message = message;
	}
	
	
	/**
	 * ������Ϣ���Լ�
	 */
	public void sendMsg2Me(){
		Thread toMeAction = new Thread(new PushMessageActor(this.csb, this.message));
		toMeAction.setPriority(Thread.MAX_PRIORITY);
		toMeAction.start();
		this.csb.sendAlreadyAuditMsg(message,null);
	}


}