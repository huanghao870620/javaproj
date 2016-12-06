package com.ld.chat.web;

import java.util.Queue;

import com.ld.dto.TeacherViewDto;
import com.ld.live.BaseManager;

public class QuotationUserManager extends BaseManager<TeacherViewDto>{

	private QuotationUserManager(){}
	
	private static QuotationUserManager manager = null;
	
	public static synchronized QuotationUserManager getUserManager(){
		 if(null == manager){
			 manager = new QuotationUserManager();
		 }
		 return manager;
	}
	private PrivateUserMapping mapping = new PrivateUserMapping(); // ÓÃ»§ÈÝÆ÷
//	private Queue<TeacherViewDto> mq = new ConcurrentLinkedQueue<TeacherViewDto>();

	public PrivateUserMapping getMapping() {
		return mapping;
	}

	public Queue<TeacherViewDto>  getMq() {
		return mq;
	}
	
}
