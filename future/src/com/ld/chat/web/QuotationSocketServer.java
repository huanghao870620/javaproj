package com.ld.chat.web;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
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

import net.sf.json.JSONObject;

/**
 * 多空对决  类似微博功能的  websocket
 * @author gao.ran
 */
@ServerEndpoint(value = "/quotationsocket", configurator=GetHttpSessionConfigurator.class)
public class QuotationSocketServer {

	private QuotationUserManager manager = QuotationUserManager.getUserManager();
	private PrivateUserMapping puMapping = manager.getMapping();
	@Autowired
	private TeacherViewService<TeacherView> teacherViewService;
	
	/**
	 * 建立会话
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
	 * 开始通信
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
		Object deleteFlag = param.get("deleteFlag");
		String objId = String.valueOf(param.get("id"));
		if(deleteFlag!=null)
		{
			puMapping.addMessage(manager, objId);
			return;
		}
		
		String objMsg = String.valueOf(param.get("msg"));
		String objadviceId= String.valueOf(param.get("adviceId"));
		String objmineralId= String.valueOf(param.get("mineralId"));
		String cfileId = String.valueOf(param.get("cfileId"));
		
		puMapping.addMessage(objMsg, session, objId, objmineralId, objadviceId, cfileId, manager);
	}

	

	/**
	 * 结束会话
	 */
	@OnClose
	public void onClose(Session session) {
		this.puMapping.deleteBySessionId(session.getId());
	}
	
	@OnError
	public void onError(Session session, Throwable thr) {
		
	}
}