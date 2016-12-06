package com.ld.chat.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.ld.model.WeiboMessage;
import com.ld.service.WeiboMessageService;

public class WriteWeiboThread implements Runnable{  
    private final static Logger log = Logger.getLogger(WriteWeiboThread.class.getName());  
    private static WriteWeiboThread instance = null;
    private final static long time = 1000;
    private static WeiboUserManager writeManager ;// 微博消息用户管理对象
	@Autowired
	private WeiboMessageService<WeiboMessage> weiboService;
    
    private WriteWeiboThread(){}
    public  static synchronized  WriteWeiboThread getInstance() {
		if (instance == null) {
			
			writeManager =WeiboUserManager.getUserManager();
			instance = new WriteWeiboThread();
			Thread printerThread = new Thread(instance);
			printerThread.start();
		}
		return instance;
	}
    /**
     *  保存微博消息数据
     */
    public void run() {
    	WeiboUserManager manager = WeiboUserManager.getUserManager();
    	while(true){
    		try{
    			Thread.sleep(time);
    			while (manager.getWriteMq()!=null && !manager.getWriteMq().isEmpty()) {
    				WeiboMessage wm = writeManager.getWriteMq().poll();
    				weiboService.insert(wm);
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

