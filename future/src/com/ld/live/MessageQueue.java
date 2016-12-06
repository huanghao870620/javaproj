package com.ld.live;



import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONArray;

/**
 * ��Ϣ����
 * @author hao.huang
 * @version 1.0
 * @updated 11-����-2016 13:58:44
 */
public class MessageQueue {

	private  UserManager manager ; //
	private  SessionId2CustomerMapping um ; // ǰ̨�û�ӳ��
	private List<Message> messages = new ArrayList<Message>(); // ������Ϣ����
	
	
//	private RoomService<Room> roomService;
	
	
	public MessageQueue(UserManager manager/*, RoomService<Room> roomService*/){
		 this.manager = manager;
		 um = this.manager.getScm();
//		 this.roomService = roomService;
	} 
	
	/**
	 * �����Ϣ
	 * @param message
	 */
	public void addMessage(Message message){
		 this.messages.add(message);
	}

	/**
	 * ������Ϣ
	 */
	public void distMsg(WebApplicationContext ctx){
		if(this.messages.size() > 0){
			Message message = this.messages.get(0);
			um.distMsg(message, ctx);
			this.messages.remove(message);
		 }
	}


	/**
	 *  ��Ϣת��Ϊjson��ʽ
	 */
	public JSONArray msg2Json(){
		JSONArray array = new JSONArray();
		for(int i=0; i<this.messages.size(); i++){
			 array.add(this.messages.get(i).getMsg());
		}
		return array;
	}
	
	
	
}
