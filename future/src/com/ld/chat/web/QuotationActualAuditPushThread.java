package com.ld.chat.web;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ld.common.Dictionary;
import com.ld.dto.UserDto;
import com.ld.model.WeiboMessage;
/**
 * 讲师点击 客户发来的消息审核后，确认公开，即可发送此条消息到别的客户实时聊天界面
 * @author gao.ran
 */

public class QuotationActualAuditPushThread implements Runnable{  
    private final static Logger log = Logger.getLogger(QuotationActualAuditPushThread.class.getName());  
    private static QuotationActualAuditPushThread instance = null;
    private final static long time = 300;
    private static QuotationActualUserManager manager ;// 微博消息用户管理对象
    
    private QuotationActualAuditPushThread(){}
    public  static synchronized  QuotationActualAuditPushThread getInstance() {
		if (instance == null) {
			
			manager =QuotationActualUserManager.getUserManager();
			instance = new QuotationActualAuditPushThread();
			Thread printerThread = new Thread(instance);
			printerThread.start();
		}
		return instance;
	}
    
    public void run() {
    	while(true){
    		try{
    			Thread.sleep(time);
    			while (manager.getMqAudit()!=null && !manager.getMqAudit().isEmpty()) {
    			    WeiboMessage wm = manager.getMqAudit().poll();
    				Collection<UserDtoSessionBinding> collection = QuotationActualUserManager.getUserManager().getMapping().getBinding().values();
    				Collection<UserDtoSessionBinding>  needBinds =  new  ArrayList<UserDtoSessionBinding>() ;
    				    for(UserDtoSessionBinding binding : collection ){
    						UserDto  dto =  binding.getUserDto();
    						boolean  isSelf = dto.getUserId().equals(wm.getUserId());
    						boolean  isTeacher = dto.getRoleId()!=null && dto.getRoleId().toString().equals(Dictionary.TEACHER_TYPE_ROLE);
    					    //不是自己也不是老师, 才符合需要发送的对象
    						if(!isSelf && !isTeacher )
    						{
    							needBinds.add(binding);
    						}
    					}
    					QuotationActualPushMessage gpm = new QuotationActualPushMessage(needBinds, wm);
        				gpm.groupSendMessage();
    			}
   			 }
    		catch (InterruptedException e) {
    			log.error("AppleReqNotification.run()", e);
    		}
    	}
    }
}  

