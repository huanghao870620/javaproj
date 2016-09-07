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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ld.live.GetHttpSessionConfigurator;
import com.ld.model.TeacherView;
import com.ld.model.User;
import com.ld.model.WeiboMessage;
import com.ld.service.TeacherViewService;
import com.ld.util.LoginUtil;

import net.sf.json.JSONObject;

/**
 * 实盘房间 实时聊天功能的 websocket
 * 
 * @author gao.ran
 */
@ServerEndpoint(value = "/quotationactualsocket", configurator = GetHttpSessionConfigurator.class)
public class QuotationActualSocketServer {

	private QuotationActualUserManager manager = QuotationActualUserManager.getUserManager();
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
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		User user = (User) httpSession.getAttribute(LoginUtil.FRONT_LOGIN_USER);
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
	public void onMessage(String message, Session session) throws IOException, InterruptedException {

		JSONObject param = JSONObject.fromObject(message);
		Object objMsg = param.get("msg");
		Object customerId_msg = param.get("customerId_msg");
		Object deleteFlag = param.get("deleteFlag");

		if (null != objMsg) {
			String msg = objMsg.toString(); // 消息
			if (StringUtils.isNotBlank(msg)) {
				if (customerId_msg == null) {
					puMapping.addWeiboMsg(msg, session, manager);
				}else {
					// 说明传入过来的数据是 讲师点击公开后的 消息推送，推送给 除当前消息的创建者之外的所有客户，不包括讲师
					String createTime = (String)param.get("createTime");
					String customerName = (String)param.get("customerName");
					String roleId_msg = (String) param.get("roleId_msg");
					String levelId = (String) param.get("levelId");
					WeiboMessage weiboMsg = new WeiboMessage();
					if (deleteFlag == null) {
						manager.addPublicMsg(weiboMsg, msg, createTime, customerId_msg.toString(), customerName,roleId_msg,levelId);
					} else {
						manager.addDeleteMsg(weiboMsg, msg, createTime, customerId_msg.toString());
					}
				}
			}
		}
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