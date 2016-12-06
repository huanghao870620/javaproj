package com.ld.chat.web;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ld.dto.PrivateMsgReplyDto;
import com.ld.dto.UserDto;
/**
 * <!-- 启动实盘房间  客户与讲师 交互 消息推送线程 -->  
 * 2016-07-08
 * @author gao.ran
 *
 */
public class QuotationInteractionPushThread implements Runnable{  
    private final static Logger log = Logger.getLogger(QuotationInteractionPushThread.class.getName());  
    private static QuotationInteractionPushThread instance = null;
    private final static long time = 1000;
    private static QuotationInteractionUserManager manager ;
    
    private QuotationInteractionPushThread(){}
    public  static synchronized  QuotationInteractionPushThread getInstance() {
		if (instance == null) {
			manager =QuotationInteractionUserManager.getUserManager();
			instance = new QuotationInteractionPushThread();
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
    				PrivateMsgReplyDto  wm = manager.getMq().poll();
    				Collection<UserDtoSessionBinding> collection = QuotationInteractionUserManager.getUserManager().getMapping().getBinding().values();
    				Collection<UserDtoSessionBinding>  needBinds =  new  ArrayList<UserDtoSessionBinding>() ;
					for(UserDtoSessionBinding binding : collection ){
						UserDto  dto =  binding.getUserDto();
						if(dto.getUserId().equals(wm.getReplierId())  ||
						   dto.getUserId().equals(wm.getQuesCreatorId()) ||
						   dto.getUserId().equals(wm.getTeacherId())){
							needBinds.add(binding);
						}
					}
    				//仅发送给该私信相关讲师和用户
    				QuotationInteractionPushMessage gpm = new QuotationInteractionPushMessage(needBinds, wm);
        			gpm.groupSendMessage();
    				
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

