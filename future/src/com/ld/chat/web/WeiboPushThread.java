package com.ld.chat.web;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.ld.model.WeiboMessage;

public class WeiboPushThread implements Runnable{  
    private final static Logger log = Logger.getLogger(WeiboPushThread.class.getName());  
    private static WeiboPushThread instance = null;
    private final static long time = 100;
    private static WeiboUserManager manager ;// 微博消息用户管理对象
    
    private WeiboPushThread(){}
    public  static synchronized  WeiboPushThread getInstance() {
		if (instance == null) {
			
			manager =WeiboUserManager.getUserManager();
			instance = new WeiboPushThread();
			Thread printerThread = new Thread(instance);
			printerThread.start();	
		}
		return instance;
	}
    
    public void run() {
    	while(true){
    		try{
    			Thread.sleep(time);
    			while (manager.getMq()!=null && !manager.getMq().isEmpty()) {
    			    WeiboMessage wm = manager.getMq().poll();
    			   //  1  首先发送消息给所有进入 微博聊天房间的用户
    				Collection<UserDtoSessionBinding> collection = WeiboUserManager.getUserManager().getMapping().getBinding().values();
    				WeiboGroupPushMessage gpm = new WeiboGroupPushMessage(collection, wm);
    				gpm.groupSendMessage();
    				// 2  将消息再次保存到  微博聊天记录队列
    				manager.getWriteMq().add(wm);
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

