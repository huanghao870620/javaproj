package com.ld.chat.web;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ld.live.BaseManager;
import com.ld.model.WeiboMessage;

public class WeiboUserManager extends BaseManager<WeiboMessage>{

	private WeiboUserManager(){}
	
	private static WeiboUserManager manager = null;
	
	public static synchronized WeiboUserManager getUserManager(){
		 if(null == manager){
			 manager = new WeiboUserManager();
		 }
		 return manager;
	}
	private PrivateUserMapping mapping = new PrivateUserMapping(); // 用户容器
	private Queue<WeiboMessage> mq = new ConcurrentLinkedQueue<WeiboMessage>();
	private Queue<WeiboMessage> mq_write = new ConcurrentLinkedQueue<WeiboMessage>();
	//private AuditMessageQueue amq = new AuditMessageQueue(); // 审核消息队列

	public PrivateUserMapping getMapping() {
		return mapping;
	}

	public Queue<WeiboMessage>  getMq() {
		return mq;
	}
	
	public Queue<WeiboMessage>  getWriteMq() {
		return mq_write;
	}
	
	
}
