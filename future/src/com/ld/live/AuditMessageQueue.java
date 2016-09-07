package com.ld.live;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �����Ϣ����
 * @author huang.hao
 * @version 1.0
 * @updated 11-����-2016 13:58:43
 */
public class AuditMessageQueue {
	
	private UserManager manager;
	private SessionId2UserMapping um; // ��̨�û�ӳ��

	private List<Message> auditMessages = new ArrayList<Message>(); // �����Ϣ����

	private Map<String, Message> mapMessage = new HashMap<String, Message>(); // key value search
	
	
	public AuditMessageQueue(UserManager manager){
		 this.manager = manager;
		 this.um = this.manager.getSum();
	}
	
	
	/**
	 * ������Ϣ
	 */
	public void distMsg(){
		if(this.auditMessages.size() > 0){
			Message message = this.auditMessages.get(0);
			um.distMsg(message);
			this.auditMessages.remove(message);
		 }
	}
	
	

	/**
	 * �����Ϣ
	 */
	public void addMessage(Message message){
		 synchronized(this.auditMessages){
			 this.addAuditMessage(message);
			// message.setId(new BigDecimal(this.auditMessages.size()));
			 this.mapMessage.put(String.valueOf(message.getId()), message);
		 }
	}
	
	/**
	 * ���δ�����Ϣ
	 * @param message
	 */
	private void addAuditMessage(Message message){
		this.auditMessages.add(message);
	}
	
	
	/**
	 * ���id��ȡmsg
	 * @return
	 */
	public Message findMsg(String id){
		  return this.mapMessage.get(id);
	}
	
	/**
	 * ɾ����Ϣ
	 * @param message
	 */
	public void removeMessage(Message message){
		this.auditMessages.remove(message);
		this.mapMessage.remove(message.getId());
	}
	
}
