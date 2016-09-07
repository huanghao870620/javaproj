package com.ld.live;

import java.io.IOException;

import javax.websocket.Session;

import org.springframework.web.context.WebApplicationContext;

import com.ld.model.AbstractUser;

/**
 * @author huang.hao
 * @version 1.0
 * @created 17-����-2016 19:14:32
 */
public class BaseSessionBinding<T extends AbstractUser> {

	protected Session session;
	protected Message msg;

	private T entity;

	public BaseSessionBinding(Session session, T entity) {
		this.session = session;
		this.entity = entity;
	}

	public BaseSessionBinding() {

	}

	public T getEntity() {
		return this.entity;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	
	

	
	
	public void sendAlreadyAuditMsg(Message message,WebApplicationContext ctx) {
		this.setMsg(message);
		
		AbstractSendMsg send = new AbstractSendMsg(session, message);
		send.sendAlreadyAuditMsg(ctx);
	}
	
	/**
	 * 
	 * @param message
	 */
	public void sendNotAuditMsg(Message message){
		this.setMsg(message);
		AbstractSendMsg send = new AbstractSendMsg(session, message);
		send.sendNotAuditMsg();
	}

	
	

}