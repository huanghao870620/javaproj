package com.ld.live;

/**
 * 推送未审核消息线程
 * @author huang.hao
 * @version 1.0
 * @created 23-六月-2016 17:32:30
 */
public class PushAuditMachine implements Runnable{
	
	
	private UserManager manager = UserManager.getUserManager();
	private AuditMessageQueue amq = manager.getAmq();  // 未审核消息
	

	/**
	 * 
	 */
	public PushAuditMachine(){}

	/**
	 * 
	 */
	@Override
	public void run() {
		while(true){
			this.amq.distMsg();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}