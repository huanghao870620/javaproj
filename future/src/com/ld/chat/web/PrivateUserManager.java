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
	private PrivateUserMapping mapping = new PrivateUserMapping(); // �û�����
	private Queue<PrivateMessage> mq = new ConcurrentLinkedQueue<PrivateMessage>();
	private Queue<PrivateMessage> mqWrite = new ConcurrentLinkedQueue<PrivateMessage>();
	
	/**
	 * ������һ��д��Ϣ
	 */
	public PrivateMessage mqWritePoll(){
		 return this.mqWrite.poll();
	}
	
	/**
	 * mqWrite ��Ϊ��
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
	 * �������˽��
	 * @param message
	 */
	public void addPrivateMessage(PrivateMessage message){
		this.mq.add(message);
	}
	
	/**
	 * ��Ϣ���� ��Ϊ��
	 * @return
	 */
	public boolean mqIsNotNull(){
		return this.mq != null  && !this.mq.isEmpty();
	}
	
	/**
	 * ������һ����Ϣ
	 * @return
	 */
	public PrivateMessage mqPoll(){
		return this.mq.poll();
	}
	
	/**
	 * ���д��Ϣ����
	 * @param message
	 */
	public void addWriteMq(PrivateMessage message){
		  this.mqWrite.add(message);
	}
	
}
