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
 * ʵ�̷��� ʵʱ���칦�ܵ� websocket
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
	 * �����Ự
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
	 * ��ʼͨ��
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
			String msg = objMsg.toString(); // ��Ϣ
			if (StringUtils.isNotBlank(msg)) {
				if (customerId_msg == null) {
					puMapping.addWeiboMsg(msg, session, manager);
				}else {
					// ˵����������������� ��ʦ���������� ��Ϣ���ͣ����͸� ����ǰ��Ϣ�Ĵ�����֮������пͻ�����������ʦ
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
	 * �����Ự
	 */
	@OnClose
	public void onClose(Session session) {
		this.puMapping.deleteBySessionId(session.getId());
	}

	@OnError
	public void onError(Session session, Throwable thr) {

	}

}