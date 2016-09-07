package com.ld.live;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;

import com.ld.model.User;
import com.ld.model.WeiboMessage;
import com.ld.service.UserListenService;
import com.ld.service.WeiboMessageService;
import com.ld.util.LoginUtil;
import com.ld.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * ����� web socket server
 * @author hao.huang
 * @version 1.0
 * @updated 11-����-2016 13:58:44
 */
//@WebListener(value="/websocket")
@ServerEndpoint(value = "/websocket", configurator=GetHttpSessionConfigurator.class)
public class WebSocketServer {

	
//	@Autowired  
//	private HttpSession session; 
	
	/**
	 * �û�����
	 */
	private UserManager manager = UserManager.getUserManager();
	
	/**
	 * �����Ϣ����
	 */
	private AuditMessageQueue amq = manager.getAmq(); 
	
	/**
	 * ��ȡǰ̨�û���
	 */
	private SessionId2CustomerMapping um = manager.getScm();
	
	@Autowired
	private UserListenService<?> userListenService;
	
	  
	/**
	 * 消息
	 */
	@Autowired
	private WeiboMessageService<WeiboMessage> weiboMessageService;
	
	public WebSocketServer(){}
	
	
	private HandshakeRequest request;
	
	/**
	 * �����Ự
	 * 
	 * @param message
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
		this.request = (HandshakeRequest)config.getUserProperties().get(HttpServletRequest.class.getName());
		User user =(User) httpSession.getAttribute(LoginUtil.FRONT_LOGIN_USER);
		String sessionid = session.getId();
		if(user != null){
			this.um.addCustomerBinding(sessionid, session, user);
		}
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
		
		if(null != objMsg){
			String msg = objMsg.toString(); // ��Ϣ
			if (!StringUtil.isBlank(msg)) {
				WeiboMessage initMsg = new WeiboMessage(msg, this.um.getCustomerBySessionId(session.getId()));
				initMsg.setRoomId(BigDecimal.ONE); // ��ͨ����
				initMsg.setInputTime(new Date()); // ����ʱ��
				
				CustomerSessionBinding csb = this.um.findBySessionId(session.getId());
				User user = csb.getEntity();
				initMsg.setCustomerId(user.getUserId()); // �û�id
				String name = user.getName();
				if(null == name){ // 游客 name 为空
					name = user.getUserName();
				}
				initMsg.setCustomerName(name); // �û����
				initMsg.setCustomerAccount(user.getAccount()); // �ͻ��˻�
				initMsg.setRoomName("普通房间");
				initMsg.setCfileId(user.getUserId());
				initMsg.setLevelId(user.getLevelId()); // ��Ա����
				initMsg.setBasePath("");
				initMsg.setSessionid(session.getId());
				this.weiboMessageService.insert(initMsg);
				csb.sendAlreadyAuditMsg(initMsg,null); // ������Ϣ���Լ�
				this.amq.addMessage(initMsg);
			}
		}
 
	}

	
	/**
	 * ����Ự
	 */
	@OnClose
	public void onClose(Session session) {
		this.um.deleteBySessionId(session.getId());
	}
	
	@OnError
	public void onError(Session session, Throwable thr) {
		
	}
}