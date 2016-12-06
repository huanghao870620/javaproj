package com.ld.live;

import javax.websocket.Session;

import com.ld.model.User;



/**
 * �û��Ự��
 * @author hao.huang
 * @version 1.0
 * @updated 11-����-2016 13:58:44
 */
public class CustomerSessionBinding extends BaseSessionBinding<User>{

	public CustomerSessionBinding(Session session, User e){
		  super(session,e);
	}

	public Session getSession() {
		return this.session;
	}
}
