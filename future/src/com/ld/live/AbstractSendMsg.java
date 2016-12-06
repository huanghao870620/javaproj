package com.ld.live;

import java.io.IOException;

import javax.websocket.Session;

import org.springframework.web.context.WebApplicationContext;

/**
 * @author huang.hao
 * @version 1.0
 * @created 18-����-2016 17:31:42
 */
public class AbstractSendMsg {
	
	private Session session;
	private Message message;

	public AbstractSendMsg(Session session, Message message){
		this.session = session;
		this.message = message;
	}
	

	
	/**
	 * �����Ѿ���˵���Ϣ
	 */
	public void sendAlreadyAuditMsg(WebApplicationContext ctx) {
		try {
			String msg = this.message.intoAlreadyReviewMessageStr(ctx);
			this.session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����δ��˵���Ϣ
	 */
	public void sendNotAuditMsg(){
		try {
			String msg = this.message.intoNeedReviewMessageStr();
			this.session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}