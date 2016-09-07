package com.ld.job;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.ld.live.PushAuditMachine;
import com.ld.live.PushMachine;

/**
 * 
 * @author huang.hao
 *
 */
@Component
public class StartSendingMessageJob implements ApplicationListener<ApplicationEvent>  {
	
	private static boolean isStart = false;

	@Override
	public void onApplicationEvent(ApplicationEvent ae) {
		 if(!isStart){
			 isStart = true;
			 Object o = ae.getSource();
			 WebApplicationContext ctx = (WebApplicationContext) o;
			 System.setProperty("user.timezone","GMT +08");

			 Thread th = new Thread(new PushMachine(ctx),"推送已经审核的消息");
			 th.setPriority(Thread.MAX_PRIORITY);
			 th.start(); // 启动线程
			 
			 
			 Thread tha = new Thread(new PushAuditMachine(), "推送未审核的消息");
			 th.setPriority(Thread.MAX_PRIORITY);
			 tha.start();
		 }
	}




}
