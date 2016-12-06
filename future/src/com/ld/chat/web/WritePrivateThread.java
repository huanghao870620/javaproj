package com.ld.chat.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.ld.model.PrivateMessage;
import com.ld.service.PrivateMessageService;

public class WritePrivateThread implements Runnable{  
    private final static Logger log = Logger.getLogger(WritePrivateThread.class.getName());  
    private static WritePrivateThread instance = null;
    private final static long time = 1000;
    private static PrivateUserManager writeManager ;// 微博消息用户管理对象
	@Autowired
	private PrivateMessageService<PrivateMessage> privateService;
    
    private WritePrivateThread(){}
    public  static synchronized  WritePrivateThread getInstance() {
		if (instance == null) {
			
			writeManager =PrivateUserManager.getUserManager();
			instance = new WritePrivateThread();
			Thread printerThread = new Thread(instance);
			printerThread.start();
		}
		return instance;
	}
    /**
     *  保存微博消息数据
     */
    public void run() {
    	while(true){
    		try{
    			Thread.sleep(time);
    			while ( writeManager.mqWriteIsNotEmpty()) {
    				privateService.insert(writeManager.mqWritePoll());
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

