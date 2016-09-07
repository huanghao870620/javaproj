package com.ld.chat.web;

import java.math.BigDecimal;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.Session;

import com.ld.dto.UserDto;
import com.ld.live.BaseManager;
import com.ld.model.WeiboMessage;
import com.ld.util.Common;
/**
 * 
 * @author huang.hao
 *
 */
public class QuotationActualUserManager extends BaseManager<WeiboMessage>{

	private QuotationActualUserManager(){}
	
	private static QuotationActualUserManager manager = null;
	
	public static synchronized QuotationActualUserManager getUserManager(){
		 if(null == manager){
			 manager = new QuotationActualUserManager();
		 }
		 return manager;
	}
	
	private PrivateUserMapping  mapping = new PrivateUserMapping(); // 用户容器
	
	private Queue<WeiboMessage> mqAudit = new ConcurrentLinkedQueue<WeiboMessage>();// 主要是用于讲师 设置 普通用户发的消息为"公开"
	private Queue<WeiboMessage> mqDelete = new ConcurrentLinkedQueue<WeiboMessage>();// 主要是用于讲师 删除消息的队列数据发送

	

	public Queue<WeiboMessage> getMqAudit() {
		return mqAudit;
	}



	public Queue<WeiboMessage> getMqDelete() {
		return mqDelete;
	}



	public PrivateUserMapping  getMapping() {
		return mapping;
	}



	public Queue<WeiboMessage>  getMq() {
		return mq;
	}
	
	
	
	/**
	 * 添加公开消息
	 * @param message
	 * @param msg
	 * @param createTime
	 * @param customerId_msg
	 * @param customerName
	 */
	public void addPublicMsg(WeiboMessage message, String msg, String createTime, String customerId_msg, String customerName,String roleId_msg,String levelId){
		message.setMsg(msg);
		message.setInputTime(Common.parseDate(createTime, "yyyy-MM-dd hh:mm:ss"));
		message.setUserId(new BigDecimal(customerId_msg.toString()));
		message.setCustomerName(customerName);
		message.setLevelId(new BigDecimal(levelId));
		message.setRoleId(new BigDecimal(roleId_msg));
		this.addMqAudit(message);
	}
	
	/**
	 * 添加未审核消息
	 * @param message
	 */
	private void addMqAudit(WeiboMessage message){
		this.mqAudit.add(message);
	}
	
	/**
	 * 添加讲师删除实时消息 
	 * @param message
	 * @param msg
	 * @param createTime
	 * @param customerId_msg
	 */
	public void addDeleteMsg(WeiboMessage message, String msg, String createTime, String customerId_msg){
		message.setMsg(msg);
		message.setIsDelete(true);
		message.setInputTime(Common.parseDate(createTime, "yyyy-MM-dd hh:mm:ss") );
		message.setUserId(new BigDecimal(customerId_msg.toString()));
		this.addMqDelete(message);
	}
	
	/**
	 * 添加需要删除的消息
	 * @param message
	 */
	public void addMqDelete(WeiboMessage message){
		this.mqDelete.add(message);
	}
}
