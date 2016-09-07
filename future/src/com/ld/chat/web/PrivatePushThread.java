package com.ld.chat.web;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.ld.model.PrivateMessage;
/**
 * 
 * @author gao.ran
 */
public class PrivatePushThread implements Runnable{  
    private final static Logger log = Logger.getLogger(PrivatePushThread.class.getName());  
    private static PrivatePushThread instance = null;
    private final static long time = 500;
    private static PrivateUserManager manager ;// 微博消息用户管理对象
    private static PrivateUserMapping puMapping = null;
    
    private PrivatePushThread(){}
    public  static synchronized  PrivatePushThread getInstance() {
		if (instance == null) {
			
			manager =PrivateUserManager.getUserManager();
			instance = new PrivatePushThread();
			puMapping = manager.getMapping();
			Thread printerThread = new Thread(instance);
			printerThread.start();
		} 
		return instance;
	}
    
    public void run() {
    	while(true){
    		try{
    			Thread.sleep(time);
    			while (manager.mqIsNotNull()) {
    			    PrivateMessage message = manager.mqPoll();
    			   //  1  首先发送消息给所有进入 微博聊天房间的用户
    				puMapping.groupSendMessage(message);
    				// 2  将消息再次保存到  微博聊天记录队列
    				manager.addWriteMq(message);
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

