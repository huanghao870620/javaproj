package com.ld.live;

import javax.websocket.Session;

import com.ld.model.User;

/**
 * ��̨�û��Ự��
 * @author huang.hao
 * @version 1.0
 * @created 17-����-2016 19:02:45
 */
public class UserSessionBinding extends BaseSessionBinding<User>{

	public UserSessionBinding(Session session, User user) {
		super(session, user);
	}
}