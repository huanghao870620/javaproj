package com.ld.chat.web;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.ld.model.WeiboMessage;

public class WeiboPushThread implements Runnable{  
    private final static Logger log = Logger.getLogger(WeiboPushThread.class.getName());  
    private static WeiboPushThread instance = null;
    private final static long time = 100;
    private static WeiboUserManager manager ;// ΢����Ϣ�û��������
    
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
    			   //  1  ���ȷ�����Ϣ�����н��� ΢�����췿����û�
    				Collection<UserDtoSessionBinding> collection = WeiboUserManager.getUserManager().getMapping().getBinding().values();
    				WeiboGroupPushMessage gpm = new WeiboGroupPushMessage(collection, wm);
    				gpm.groupSendMessage();
    				// 2  ����Ϣ�ٴα��浽  ΢�������¼����
    				manager.getWriteMq().add(wm);
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

