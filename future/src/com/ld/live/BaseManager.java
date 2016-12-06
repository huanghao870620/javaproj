package com.ld.live;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author huang.hao
 * @version 1.0
 * @created 28-����-2016 17:41:52
 */
public class BaseManager<T extends Message> {

	public BaseManager(){}

	protected Queue<T> mq = new ConcurrentLinkedQueue<T>();
	
	/**
	 * ��Ϣ�������
	 * @param message
	 */
	public void addMq(T message){
		 this.mq.add(message);
	}

}