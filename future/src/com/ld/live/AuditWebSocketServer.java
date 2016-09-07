package com.ld.live;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.ld.model.User;
import com.ld.util.LoginUtil;

/**
 * 审核websocket
 * @author huang.hao
 *
 */
@ServerEndpoint(value = "/auditWebsocket", configurator=GetHttpSessionConfigurator.class)
public class AuditWebSocketServer {
	
	private UserManager manager = UserManager.getUserManager();
	
	private MessageQueue mq = manager.getMq();
	
	private SessionId2CustomerMapping mapping = manager.getScm(); // 前台用户容器
	
	private SessionId2UserMapping sum = manager.getSum(); // 后台用户容器
	
	
	public AuditWebSocketServer(){}

	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
		User user =(User) httpSession.getAttribute(LoginUtil.BACK_LOGIN_USER);
		sum.addUserBinding(session.getId(), session, user);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException,
	InterruptedException{
		
	}
	
	@OnClose
	public void onClose(Session session) {
		this.sum.deleteBySessionId(session.getId());
	}
}
