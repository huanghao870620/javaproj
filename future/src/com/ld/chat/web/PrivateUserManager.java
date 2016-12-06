package com.ld.chat.web;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ld.live.BaseManager;
import com.ld.model.PrivateMessage;
/**
 * 
 * @author huang.hao
 *
 */
public class PrivateUserManager extends BaseManager<PrivateMessage>{

	private PrivateUserManager(){}
	
	private static PrivateUserManager manager = null;
	
	public static synchronized PrivateUserManager getUserManager(){
		 if(null == manager){
			 manager = new PrivateUserManager();
		 }
		 return manager;
	}
	private PrivateUserMapping mapping = new PrivateUserMapping(); // 用户容器
	private Queue<PrivateMessage> mq = new ConcurrentLinkedQueue<PrivateMessage>();
	private Queue<PrivateMessage> mqWrite = new ConcurrentLinkedQueue<PrivateMessage>();
	
	/**
	 * 弹出第一个写消息
	 */
	public PrivateMessage mqWritePoll(){
		 return this.mqWrite.poll();
	}
	
	/**
	 * mqWrite 不为空
	 * @return
	 */
	public boolean mqWriteIsNotEmpty(){
		return !this.mqWrite.isEmpty();
	}
	

	public PrivateUserMapping getMapping() {
		return mapping;
	}

	public Queue<PrivateMessage>  getMq() {
		return mq;
	}
	
	
	
	public Queue<PrivateMessage> getMqWrite() {
		return mqWrite;
	}

	/**
	 * 队列添加私信
	 * @param message
	 */
	public void addPrivateMessage(PrivateMessage message){
		this.mq.add(message);
	}
	
	/**
	 * 消息队列 不为空
	 * @return
	 */
	public boolean mqIsNotNull(){
		return this.mq != null  && !this.mq.isEmpty();
	}
	
	/**
	 * 弹出第一个消息
	 * @return
	 */
	public PrivateMessage mqPoll(){
		return this.mq.poll();
	}
	
	/**
	 * 添加写消息队列
	 * @param message
	 */
	public void addWriteMq(PrivateMessage message){
		  this.mqWrite.add(message);
	}
	
}
