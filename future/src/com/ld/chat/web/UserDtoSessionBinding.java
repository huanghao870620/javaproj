package com.ld.chat.web;

import java.io.IOException;
import java.util.Map;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;

import com.ld.dto.PrivateMsgReplyDto;
import com.ld.dto.TeacherViewDto;
import com.ld.dto.UserDto;
import com.ld.live.BaseSessionBinding;
import com.ld.model.PrivateMessage;
import com.ld.model.TeacherView;
import com.ld.model.User;
import com.ld.model.WeiboMessage;
import com.ld.service.TeacherViewService;
import com.ld.util.Common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户会话绑定
 * @author  gao.ran
 * @version 1.0
 * @updated 22-六月-2016 15:58:44
 */
public class UserDtoSessionBinding extends BaseSessionBinding<User>{

	private TeacherViewService<TeacherView> teacherViewService;
	
	private UserDto userDto;

	
	public UserDtoSessionBinding(Session session, UserDto userDto,TeacherViewService<TeacherView> teacherViewService){
		 this.session = session;
		 this.userDto = userDto;
		 this.teacherViewService = teacherViewService;
	}
	

	public UserDto getUserDto() {
		return userDto;
	}
	
	/**
	 * 发送消息
	 * @param message
	 */
	
	public void sendWeiboMsg(WeiboMessage msg){
		 JSONObject obj = new JSONObject();
		  obj.accumulate("msg", msg.getMsg());
		  obj.accumulate("customerName", msg.getCustomerName());
		  obj.accumulate("createTime", Common.fromDateH(msg.getInputTime()));
		  obj.accumulate("roleId", msg.getRoleId());
		  obj.accumulate("customerId", msg.getUserId());
		  obj.accumulate("deleteFlag", msg.getIsDelete());
		  obj.accumulate("levelId", msg.getLevelId());
		  obj.accumulate("cfileId", msg.getCfileId());
		 try {
			this.session.getBasicRemote().sendText(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param msg
	 */
	public void sendPrivateMsg(PrivateMessage msg){
		 JSONObject obj = new JSONObject();
		  obj.accumulate("msg", msg.getContent());
		  obj.accumulate("customerName", msg.getCustomerName());
		  obj.accumulate("createTime", Common.fromDateH(msg.getCreateTime()));
//		  obj.accumulate("roleId", msg.getRoleType());
		  obj.accumulate("customerId", msg.getCustomerId());
		 try {
			this.session.getBasicRemote().sendText(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param msg
	 */
	public void sendTeacherViewMsg(TeacherViewDto msg){
		JSONObject obj = new JSONObject();
		obj.accumulate("msg", msg.getViewContent());
		obj.accumulate("cfileId", msg.getCfileId());
		obj.accumulate("adviceId", msg.getAdviceId());
		obj.accumulate("mineralId", msg.getMineralId());
		obj.accumulate("id", msg.getId());
		obj.accumulate("teacherName", msg.getTeacherName());
		obj.accumulate("teacherId", msg.getTeacherId());
		if(msg.getCreateTime()!=null){
			obj.accumulate("createTime", Common.fromDateH(msg.getCreateTime()));
		}
		obj.accumulate("deleteFlag", msg.isDelete());
		obj.accumulate("teacherIconId", msg.getTeacherIconId());
		if(msg.isDelete()){
			addJSONObject(obj);
		}
		try {
			this.session.getBasicRemote().sendText(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param msg
	 */
	public void sendQuotationInteractionMsg(PrivateMsgReplyDto  msg){
		JSONObject obj = JSONObject.fromObject(msg);
		addJSONObject(obj);
		try {
			this.session.getBasicRemote().sendText(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 封装讲师回复消息数量、讲师未回复数量、客户参与数量
	 * @param obj
	 */
	private void addJSONObject(JSONObject obj){
		Map<String,Object> result = teacherViewService.getReplyCount(userDto.getUserId());
		obj.accumulate("replyCount",result.get("replyCount") == null?0:result.get("replyCount"));
		obj.accumulate("customerCount",result.get("customerCount") == null?0:result.get("customerCount"));
		obj.accumulate("unReplyCount",result.get("unReplyCount") == null?0:result.get("unReplyCount"));
	}
}
