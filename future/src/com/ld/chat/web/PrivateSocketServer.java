package com.ld.chat.web;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;

import com.ld.live.GetHttpSessionConfigurator;
import com.ld.model.TeacherView;
import com.ld.model.User;
import com.ld.service.TeacherViewService;
import com.ld.util.LoginUtil;
import com.ld.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * ��նԾ�  ����΢�����ܵ�  websocket
 * @author gao.ran
 */
@ServerEndpoint(value = "/privatesocket", configurator=GetHttpSessionConfigurator.class)
public class PrivateSocketServer {

	private PrivateUserManager manager = PrivateUserManager.getUserManager();
	private PrivateUserMapping puMapping = manager.getMapping();
	@Autowired
	private TeacherViewService<TeacherView> teacherViewService;
	
	
	/**
	 * �����Ự
	 * 
	 * @param message
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
		User user =(User) httpSession.getAttribute(LoginUtil.FRONT_LOGIN_USER);
		puMapping.bindingUser(user, httpSession, session,teacherViewService);
	}
	
	
	/**
	 * ��ʼͨ��
	 * 
	 * @param message
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException,
			InterruptedException {
		JSONObject param = JSONObject.fromObject(message);
		Object objMsg = param.get("msg");
		Object teacherUserId = param.get("teacherUserId");
		
		if(null != objMsg){
			String msg = objMsg.toString(); // ��Ϣ
			if (!StringUtil.isBlank(msg)) {
				puMapping.addPrivateMessage(session.getId(), msg, teacherUserId,manager);
			}
		}
	}

	

	/**
	 * �����Ự
	 */
	@OnClose
	public void onClose(Session session) {
		puMapping.deleteBySessionId(session.getId());
	}
}