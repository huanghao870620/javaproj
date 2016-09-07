package com.ld.chat.web;

import java.util.Collection;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.ld.dto.TeacherViewDto;
/**
 * 进入实盘房间  讲师观点数据推送服务
 * @author gao.ran
 * create time 2016 06 30
 */
public class QuotationPushThread implements Runnable{  
    private final static Logger log = Logger.getLogger(QuotationPushThread.class.getName());  
    private static QuotationPushThread instance = null;
    private final static long time = 600;
    private static QuotationUserManager manager ;// 微博消息用户管理对象
    
    private QuotationPushThread(){}
    public  static synchronized  QuotationPushThread getInstance() {
		if (instance == null) {
			
			manager =QuotationUserManager.getUserManager();
			instance = new QuotationPushThread();
			Thread printerThread = new Thread(instance);
			printerThread.start();
		}
		return instance;
	}
    
    public void run() {
    	while(true){
    		try{
    			Thread.sleep(time);
    			Queue<TeacherViewDto> queue = manager.getMq();
    			while (queue !=null && !queue.isEmpty()) {
    			    TeacherViewDto wm = queue.poll();
    			   //  1  首先发送消息给所有进入 微博聊天房间的用户
    				Collection<UserDtoSessionBinding> collection = QuotationUserManager.getUserManager().getMapping().getBinding().values();
    				QuotationGroupPushMessage gpm = new QuotationGroupPushMessage(collection, wm);
    				gpm.groupSendMessage();
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

