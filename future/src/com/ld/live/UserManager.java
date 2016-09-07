package com.ld.live;

/**
 * 用户容器
 * @author huang.hao
 * 单例模式
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
	
	
	private SessionId2CustomerMapping scm = new SessionId2CustomerMapping(); // 前台用户容器
	private SessionId2UserMapping sum = new SessionId2UserMapping(); // 后台用户容器
	
	private MessageQueue mq = null; // 消息队列
	
	private AuditMessageQueue amq = null; // 审核消息队列

	/**
	 * 前台用户容器
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
