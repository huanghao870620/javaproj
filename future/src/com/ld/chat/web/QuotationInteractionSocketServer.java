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

import com.ld.dto.PrivateMsgReplyDto;
import com.ld.live.GetHttpSessionConfigurator;
import com.ld.model.TeacherView;
import com.ld.model.User;
import com.ld.service.TeacherViewService;
import com.ld.util.LoginUtil;

import net.sf.json.JSONObject;

/**
 * 实盘房间 讲师与普通客户交互功能的 websocket
 * 
 * @author gao.ran
 */
@ServerEndpoint(value = "/interactionsocket", configurator = GetHttpSessionConfigurator.class)
public class QuotationInteractionSocketServer {

	private QuotationInteractionUserManager manager = QuotationInteractionUserManager.getUserManager();
	private PrivateUserMapping mapping = manager.getMapping();
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
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		User user = (User) httpSession.getAttribute(LoginUtil.FRONT_LOGIN_USER);
		if(user != null){
			mapping.bindingUser(user, httpSession, session,teacherViewService);
		}
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
	public void onMessage(String message, Session session) throws IOException, InterruptedException {
		JSONObject param = JSONObject.fromObject(message);
		PrivateMsgReplyDto privateMsgReplyDto = (PrivateMsgReplyDto) JSONObject.toBean(param,PrivateMsgReplyDto.class);
		mapping.addMqMessage(privateMsgReplyDto,session,manager);
	}

	/**
	 * 结束会话
	 */
	@OnClose
	public void onClose(Session session) {
		this.mapping.deleteBySessionId(session.getId());
	}

	@OnError
	public void onError(Session session, Throwable thr) {

	}
}