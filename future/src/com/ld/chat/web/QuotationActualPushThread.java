package com.ld.chat.web;

import java.util.Queue;

import org.apache.log4j.Logger;

import com.ld.model.WeiboMessage;

/**
 * 
 * @author huang.hao
 *
 */
public class QuotationActualPushThread implements Runnable{  
    private final static Logger log = Logger.getLogger(QuotationActualPushThread.class.getName());  
    private static QuotationActualPushThread instance = null;
    private final static long time = 100;
    private static QuotationActualUserManager manager ;// 微博消息用户管理对象
    
    private QuotationActualPushThread(){}
    public  static synchronized  QuotationActualPushThread getInstance() {
		if (instance == null) {
			manager =QuotationActualUserManager.getUserManager();
			instance = new QuotationActualPushThread();
			new Thread(instance).start();
		}
		return instance;
	}
    
    public void run() {
    	while(true){
    		try{
    			Thread.sleep(time);
    			Queue<WeiboMessage> mq = manager.getMq();
    			while (mq!=null && ! mq.isEmpty()) {
    			    WeiboMessage wm = mq.poll();
    			    PrivateUserMapping aquMapping = manager.getMapping();
    				
    				if(wm.isSenderIsTeacher()){
    					//如果当前发送消息的  客户角色    是老师，则发送消息给所有进入 实盘房间的客户,
    					aquMapping.groupSendMessage(wm);
    				}else{
    					//如果当前当前发送消息的    客户角色  是普通客户，则发送消息给自己 和进入实盘房间所有老师
    					aquMapping.sendMsg2SelfOrLecturer(wm);
    				}
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

