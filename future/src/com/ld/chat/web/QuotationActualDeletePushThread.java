package com.ld.chat.web;

import java.util.Collection;
import org.apache.log4j.Logger;
import com.ld.model.WeiboMessage;
/**
 * 讲师点击 客户发来的消息审核后，确认公开，即可发送此条消息到别的客户实时聊天界面
 * @author gao.ran
 */
public class QuotationActualDeletePushThread implements Runnable{  
    private final static Logger log = Logger.getLogger(QuotationActualDeletePushThread.class.getName());  
    private static QuotationActualDeletePushThread instance = null;
    private final static long time = 1500;
    private static QuotationActualUserManager manager ;// 微博消息用户管理对象
    
    private QuotationActualDeletePushThread(){}
    public  static synchronized  QuotationActualDeletePushThread getInstance() {
		if (instance == null) {
			manager =QuotationActualUserManager.getUserManager();
			instance = new QuotationActualDeletePushThread();
			Thread printerThread = new Thread(instance);
			printerThread.start();
		}
		return instance;
	}
    
    public void run() {
    	while(true){
    		try{
    			Thread.sleep(time);
    			while (manager.getMqDelete()!=null && !manager.getMqDelete().isEmpty()) {
    			    WeiboMessage wm = manager.getMqDelete().poll();
    				Collection<UserDtoSessionBinding> collection = QuotationActualUserManager.getUserManager().getMapping().getBinding().values();
    				QuotationActualPushMessage gpm = new QuotationActualPushMessage(collection, wm);
            		gpm.groupSendMessage();
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

