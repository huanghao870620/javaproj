package com.ld.live;

/**
 * �û�����
 * @author huang.hao
 * ����ģʽ
 */
public class UserManager extends BaseManager<Message>{

	private UserManager(){
		this.mq = new MessageQueue(this);
		this.amq = new AuditMessageQueue(this);
	}
	
	private static UserManager manager = null;
	
	public static synchronized UserManager getUserManager(){
		 if(null == manager){
			 manager = new UserManager();
		 }
		 return manager;
	}
	
	
	private SessionId2CustomerMapping scm = new SessionId2CustomerMapping(); // ǰ̨�û�����
	private SessionId2UserMapping sum = new SessionId2UserMapping(); // ��̨�û�����
	
	private MessageQueue mq = null; // ��Ϣ����
	
	private AuditMessageQueue amq = null; // �����Ϣ����

	/**
	 * ǰ̨�û�����
	 * @return
	 */
	public SessionId2CustomerMapping getScm() {
		return scm;
	}

	public MessageQueue getMq() {
		return mq;
	}

	public AuditMessageQueue getAmq() {
		return amq;
	}

	public SessionId2UserMapping getSum() {
		return sum;
	}
	
	
	
	
	
}
