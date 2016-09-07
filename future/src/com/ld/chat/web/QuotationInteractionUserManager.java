package com.ld.chat.web;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ld.dto.PrivateMsgReplyDto;
import com.ld.dto.TeacherViewMessageDto;
import com.ld.live.BaseManager;

/**
 * 定义实盘房间 客户与讲师之间 用户管理器
 * 
 * @author gao.ran
 *
 */
public class QuotationInteractionUserManager extends BaseManager<PrivateMsgReplyDto>{

	private QuotationInteractionUserManager() {
	}

	private static QuotationInteractionUserManager manager = null;

	public static synchronized QuotationInteractionUserManager getUserManager() {
		if (null == manager) {
			manager = new QuotationInteractionUserManager();
		}
		return manager;
	}

	private PrivateUserMapping mapping = new PrivateUserMapping(); // 用户容器

//	private Queue<TeacherViewMessageDto> mq = new ConcurrentLinkedQueue<TeacherViewMessageDto>();

	public Queue<PrivateMsgReplyDto> getMq() {
		return mq;
	}

	public PrivateUserMapping getMapping() {
		return mapping;
	}

}
