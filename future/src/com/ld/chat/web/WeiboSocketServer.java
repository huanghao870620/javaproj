package com.ld.chat.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.UserDto;
import com.ld.live.GetHttpSessionConfigurator;
import com.ld.model.TeacherView;
import com.ld.model.User;
import com.ld.model.WeiboMessage;
import com.ld.service.TeacherViewService;
import com.ld.util.LoginUtil;
import com.ld.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 多空对决  类似微博功能的  websocket
 * @author gao.ran
 */
@ServerEndpoint(value = "/weibosocket", configurator=GetHttpSessionConfigurator.class)
public class WeiboSocketServer {

	private WeiboUserManager manager = WeiboUserManager.getUserManager();
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
		String msg = param.get("msg").toString();
			if (!StringUtil.isBlank(msg)) {
				puMapping.addWeiboMsg(msg, session, manager);
			}
	}

	

	/**
	 * 结束会话
	 */
	@OnClose
	public void onClose(Session session) {
		puMapping.deleteBySessionId(session.getId());
	}
}