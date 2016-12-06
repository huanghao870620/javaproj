package com.ld.live;

import org.springframework.web.context.WebApplicationContext;

/**
 * ������Ϣ �Ѿ����ͨ�����Ϣ
 * @author huang.hao
 *
 */
public class PushMachine implements Runnable {
	
	
	private UserManager manager = UserManager.getUserManager();
	private MessageQueue mq = manager.getMq();  // �Ѿ����ͨ�����Ϣ
	
	private WebApplicationContext ctx;
	
	public PushMachine(WebApplicationContext ctx){
		this.ctx = ctx;
	}

	@Override
	public void run() {
		while(true){
			 this.mq.distMsg(this.ctx);
			 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
