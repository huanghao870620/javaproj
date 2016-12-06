package com.ld.chat.web;

import java.util.LinkedList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 负责发送讲师观点信息 线程池
 * */
public class CommonMsgThreadPool {
	private static Log log = LogFactory.getLog(CommonMsgThreadPool.class);

	private final PoolWorker[] threadPool;

	private final LinkedList<Runnable> workerList;
	
	private static CommonMsgThreadPool pool;
	
	public static synchronized CommonMsgThreadPool getInstance() {
		if (pool == null) {
			pool = new CommonMsgThreadPool(20);
		}
		return pool;
	}
	/**
	 * 初始化线程池
	 * 
	 * @param initThreadCount:预设的线程数目
	 */
	private CommonMsgThreadPool(int initThreadCount) {

		//this.initThreadCount = initThreadCount;
		workerList = new LinkedList<Runnable>();
		threadPool = new PoolWorker[initThreadCount];

		for (int i = 0; i < threadPool.length; i++) {
			threadPool[i] = new PoolWorker();
			threadPool[i].setName("PushMsg No."+i + " thread ");
			threadPool[i].start();
			log.info(threadPool[i].getName()+" start!!!");
		}
	}

	public void execute(Runnable worker) {
		synchronized (workerList) {
			workerList.addLast(worker);
			workerList.notify();
//			log.info("--------------> add one task");
		}
	}

	private class PoolWorker extends Thread {
		
		public PoolWorker(){}
		
		public void run() {
			Runnable r;
			while (true) {
				synchronized (workerList) {
					while (workerList.isEmpty()) {
						try {
							workerList.wait();
						} catch (InterruptedException ignored) {
						}
					}
					r = (Runnable) workerList.removeFirst();
				}
				try {
					r.run();
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

