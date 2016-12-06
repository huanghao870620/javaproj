package com.ld.chat.web;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;

import com.ld.common.Dictionary;
import com.ld.dto.PrivateMsgReplyDto;
import com.ld.dto.TeacherViewDto;
import com.ld.dto.UserDto;
import com.ld.live.BaseManager;
import com.ld.model.PrivateMessage;
import com.ld.model.TeacherView;
import com.ld.model.User;
import com.ld.model.WeiboMessage;
import com.ld.service.TeacherViewService;
import com.ld.util.LoginUtil;
import com.ld.util.StringUtil;

public class PrivateUserMapping extends BaseUserMapping<UserDtoSessionBinding>{

	public Map<String, UserDtoSessionBinding> getBinding() {
		return binding;
	}
	
	/**
	 * ����session id ��ȡ����Ϣ
	 * @param id
	 * @return
	 */
	private UserDtoSessionBinding getBindingBySessionId(String id){
		  return this.binding.get(id);
	}
	
	
	
	
	/**
	 * ��id ���صİ󶨽��Ϊ��
	 * @param id
	 * @return
	 */
	public boolean bindingIsNull(String id){
		return null == this.getBindingBySessionId(id);
	}
	
	/**
	 * ��session id
	 * @param id
	 * @param binding
	 */
	private void bindingSessionIdAndBaseBinding(String id, UserDtoSessionBinding binding){
		this.binding.put(id, binding);
	}
	
	
	
	
	/**
	 * ������Ϣ���Լ����߽�ʦ
	 * @param wm
	 */
	public void sendMsg2SelfOrLecturer(WeiboMessage wm){
		Collection<UserDtoSessionBinding>  needBinds =  new  ArrayList<UserDtoSessionBinding>() ;
		for(UserDtoSessionBinding binding : this.binding.values() ){
			UserDto dto = binding.getUserDto();
			if(dto.isSelf(wm.getUserId()) || dto.isLecturer())
			{
				needBinds.add(binding);
			}
		}
		QuotationActualPushMessage gpm = new QuotationActualPushMessage(needBinds, wm);
		gpm.groupSendMessage();
	}
	
	
	
	
	
	/**
	 * sessionid �� binding
	 * @param user
	 * @param httpSession
	 * @param session
	 */
	public void bindingUser(User user,HttpSession httpSession,Session session,TeacherViewService<TeacherView> teacherViewService){
		if (this.bindingIsNull(session.getId())) {
			UserDto  dto = new UserDto();
			dto.setFourBasicProp(user);
			dto.setCFileId(user.getCFileId());
			if(httpSession.getAttribute(LoginUtil.FRONT_CUSTOMER_ROLEID)!=null)
			{
				dto.setRoleId((BigDecimal) httpSession.getAttribute(LoginUtil.FRONT_CUSTOMER_ROLEID));
			}else{
				dto.setRoleId(new BigDecimal(Dictionary.CUSTOMER_TYPE_ROLE));
			}
			UserDtoSessionBinding isb = new UserDtoSessionBinding(session, dto,teacherViewService);
			this.bindingSessionIdAndBaseBinding(session.getId(),isb);
		}
	}
	
	
	
	/**
	 * ���΢����Ϣ
	 * @param msg
	 * @param session
	 * @param manager
	 */
	public void addWeiboMsg(String msg, Session session, BaseManager<WeiboMessage> manager){
		WeiboMessage  weiboMsg = new WeiboMessage();
		weiboMsg.setMsg(msg);
		weiboMsg.setInputTime(new Date());
		
		UserDtoSessionBinding userSession = this.getBindingBySessionId(session.getId());
		if(userSession!=null)
		{
			UserDto  dto = userSession.getUserDto();
			weiboMsg.setCustomerAccount(dto.getAccount());
			weiboMsg.setCustomerName(dto.getName());
			weiboMsg.setUserId(dto.getUserId());
			weiboMsg.setLevelId(dto.getLevelId());
			weiboMsg.setCfileId(dto.getCFileId());
			weiboMsg.setRoleId(dto.getRoleId());
			//�ж���Ϣ�������Ƿ�Ϊ��ʦ
			if(dto.getRoleId().compareTo(new BigDecimal(5)) == 0){
				weiboMsg.setSenderIsTeacher(false);
			}else{
				weiboMsg.setSenderIsTeacher(true);
			}
		}
		manager.addMq(weiboMsg);
	}
	
	
	
//	/**
//	 * ���΢����Ϣ
//	 * @param msg
//	 * @param sessionid
//	 * @param manager
//	 */
//	public void addWeiboMsg(String msg,String sessionid,WeiboUserManager manager){
//		UserDtoSessionBinding userSession = binding.get(sessionid);
//		WeiboMessage  wm = new WeiboMessage();
//		wm.setContent(msg);
//		wm.setCreateTime(new Date());
//		if(userSession!=null)
//		{
//			UserDto  cd = userSession.getUserDto();
//			wm.setCustomerAccount(cd.getAccount());
//			wm.setCustomerName(cd.getName());
//			wm.setUserId(cd.getUserId());
//			wm.setLevelId(cd.getLevelId());
//			if(cd.getRoleId()!=null){
//			wm.setRoleType(cd.getRoleId());
//			}
//			else{
//				wm.setRoleType(BigDecimal.ZERO);
//			}
//		}
//		manager.addMq(wm);
//	}
	
	
	/**
	 * ���˽��
	 * @param id
	 * @param msg
	 * @param teacherUserId
	 */
	public void addPrivateMessage(String id, String msg, Object teacherUserId,PrivateUserManager manager){
		UserDtoSessionBinding userSession = this.getBindingBySessionId(id);
		if(!this.bindingIsNull(id))
		{
			UserDto  dto = userSession.getUserDto();
			PrivateMessage  wm = new PrivateMessage();
			wm.setContent(msg);
			wm.setCreateTime(new Date());
//			wm.setBasicProp(dto);
//			wm.setCreateUserid(cd.getId());
			if(dto.roleIdIsNotNull()){
//			wm.setRoleType(dto.getRoleId());
			}
			else{
//				wm.setRoleType(BigDecimal.ZERO);
			}
			
			if(null!=teacherUserId)
			{
			String	teacherUserId_str = teacherUserId.toString();
//			wm.setTeacherId(BigDecimal.valueOf(Long.valueOf(teacherUserId_str)));
			}
			manager.addPrivateMessage(wm);
		}
	}
	
	/**
	 * �����Ϣ
	 * @param replyTimeTeacher
	 * @param teacherId
	 * @param customerIdMsg
	 * @param msg
	 * @param viewContent
	 * @param viewId
	 * @param adviceId
	 * @param mineralId
	 * @param createTimeTv
	 * @param cfileId
	 * @param teacherName
	 * @param id
	 * @param session
	 * @param manager
	 */
	public void addMqMessage(PrivateMsgReplyDto pmrd,Session session,QuotationInteractionUserManager manager){
//		if (pmrd.getReplyTime() != null) {
//			/*�İ潲ʦ�ظ���ʼ*/
//			dto.setReplyName(pmrd.getReplyName());
//			dto.setReplyTime(pmrd.getReplyTime());
//			dto.setReplyHeadId(pmrd.getReplyHeadId());
//			dto.setReplyContent(pmrd.getReplyContent());
//			dto.setReplyViewId(pmrd.getReplyViewId());
//			dto.setReplierId(pmrd.getReplierId());
//			/*�İ潲ʦ�ظ�����*/
//		} else {
//			dto.setTeacherIconId(pmrd.getTeacherIconId());
//			dto.set
//		}
		manager.addMq(pmrd);
	}
	
	/**
	 * �����Ϣ
	 * @param objMsg
	 * @param session
	 * @param objId
	 * @param objmineralId
	 * @param objadviceId
	 * @param cfileId
	 * @param manager
	 */
	public void addMessage(String objMsg, Session session, String objId, String objmineralId, String objadviceId, String cfileId, QuotationUserManager manager){
		if(null != objMsg){
			String msg = objMsg.toString(); // ��Ϣ
			if (!StringUtil.isBlank(msg)) {
				String sessionid = session.getId();
				UserDtoSessionBinding userSession = this.getBindingBySessionId(sessionid);
				TeacherViewDto  viewDto = new TeacherViewDto();
				viewDto.setViewContent(msg);
				if(userSession!=null)
				{
					UserDto  userDto = userSession.getUserDto();
					viewDto.setTeacherId(userDto.getUserId());
					viewDto.setTeacherName(userDto.getName());
					viewDto.setId(new BigDecimal (String.valueOf(objId)));
					viewDto.setCreateTime(new Date());
					viewDto.setMineralId(String.valueOf(objmineralId));
					viewDto.setAdviceId(String.valueOf(objadviceId));
					viewDto.setIsDelete(false);
					if(cfileId!=null && !String.valueOf(cfileId).equals("null"))
					{
						viewDto.setCfileId(new BigDecimal(String.valueOf(cfileId)));
					}
					viewDto.setTeacherIconId(userDto.getCFileId());
				}
				manager.addMq(viewDto);
			}
		}
	}
	
	/**
	 * �����Ϣ
	 * @param manager
	 * @param objId
	 */
	public void addMessage(QuotationUserManager manager, String objId){
		TeacherViewDto  viewDto = new TeacherViewDto();
		viewDto.setId(new BigDecimal (String.valueOf(objId)));
		viewDto.setDelete(true);
		manager.addMq(viewDto);
	}
	
	/**
	 * Ⱥ����Ϣ
	 * @param message
	 */
	public void groupSendMessage(PrivateMessage message){
		PrivateGroupPushMessage gpm = new PrivateGroupPushMessage(this.getAllBinding(), message);
		gpm.groupSendMessage();
	}
	
	
	/**
	 * Ⱥ����Ϣ
	 */
	public void groupSendMessage(WeiboMessage wm){
		QuotationActualPushMessage gpm = new QuotationActualPushMessage(this.binding.values(), wm);
		gpm.groupSendMessage();
	}
	
}
