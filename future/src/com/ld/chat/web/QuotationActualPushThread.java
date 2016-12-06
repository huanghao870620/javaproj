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
    private static QuotationActualUserManager manager ;// ΢����Ϣ�û��������
    
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
    					//�����ǰ������Ϣ��  �ͻ���ɫ    ����ʦ��������Ϣ�����н��� ʵ�̷���Ŀͻ�,
    					aquMapping.groupSendMessage(wm);
    				}else{
    					//�����ǰ��ǰ������Ϣ��    �ͻ���ɫ  ����ͨ�ͻ���������Ϣ���Լ� �ͽ���ʵ�̷���������ʦ
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

