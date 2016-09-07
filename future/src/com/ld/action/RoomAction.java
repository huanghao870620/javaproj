package com.ld.action;


import java.io.File;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.ld.dto.RoomConfigDto;
import com.ld.model.WeiboMessage;
import com.ld.service.RoomService;
import com.ld.service.WeiboMessageService;
import com.opensymphony.xwork2.ActionContext;

@Namespace(value="/back/room")
@ParentPackage(value="struts-login")
@InterceptorRefs(value={
		@InterceptorRef(value="loginStack")
})
public class RoomAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Autowired
	private RoomService<?> roomService;
	
	@Autowired
	private WeiboMessageService<WeiboMessage> weiboMessageService;
	
	private RoomConfigDto  rcd;
	
	private File  file; // �ϴ��ļ�����
	
	private String fileFileName;//�ϴ��ļ���
	
	public RoomConfigDto getRcd() {
		return rcd;
	}

	public void setRcd(RoomConfigDto rcd) {
		this.rcd = rcd;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * ��ת����˷���ҳ��
	 * @return
	 */
	@Action(value="forwardSetPrivill", results={
			@Result(name=SUCCESS, location="/WEB-INF/back/room/room_limit.jsp", type=DISPATCHER)
	})
	public String forwardSetPrivill(){
		return SUCCESS;
	}
	
	/**
	 * ��ת��ʵ�̷�������ҳ��
	 * @return
	 */
	@Action(value="configQuotation", results={
			@Result(name=NONE, location="/WEB-INF/back/room/config_quotation.jsp", type=DISPATCHER),
			@Result(name=SUCCESS, location="/WEB-INF/back/room/config_quotation_update.jsp", type=DISPATCHER)
	})
	public String configQuotation(){
		 ActionContext ctx = ActionContext.getContext();
		boolean  isExistConfig =  this.roomService.isExistRoomConfig(ctx);
		if(isExistConfig)
		{
			  return SUCCESS;
		}
		else{
		      return NONE;
		}
	}
	
	
	/**
	 * �ύʵ�̷�������
	 */
	@Action(value="commitQuotationConfig", results={
			@Result(name=SUCCESS, location="/WEB-INF/back/room/config_quotation_update.jsp", type=DISPATCHER),
			@Result(name=ERROR, location="/WEB-INF/back/room/config_quotation.jsp", type=DISPATCHER)
	})
	public String  commitQuotationConfig(){
		 ActionContext ctx = ActionContext.getContext();
		 boolean  dealResult = this.roomService.addRoomConfig(ctx,rcd, file,fileFileName);
		 if(dealResult )
		 {
			 return  SUCCESS;
		 }
		 else{
			 return  ERROR;
		 }
	}
	
	/**
	 * �ύʵ�̷�������
	 */
	@Action(value="updateQuotationConfig", results={
			@Result(name=SUCCESS, location="/WEB-INF/back/room/config_quotation_update.jsp", type=DISPATCHER)
	})
	public String  updateQuotationConfig(){
		 ActionContext ctx = ActionContext.getContext();
		 boolean  dealResult =  this.roomService.updateRoomConfig(ctx, rcd, file, fileFileName);
		 if(dealResult)
		 {
		 this.sendAjaxMsg("更新成功");
		 }
		 else{
			 this.sendAjaxMsg("更新失败,请重新上传图片");
		 }
		 return  SUCCESS;
	}
	
	/**
	 * �г����з��� (���)
	 * @return
	 */
	@Action(value="listCheckRoom", results={
			@Result(name=SUCCESS, location="/WEB-INF/back/room/check_room.jsp", type=DISPATCHER)
	})
	public String listCheckRoom(){
		return SUCCESS;
	}
	
	
	/**
	 * ��ѯ����
	 */
	@Action(value="queryRooms")
	public void queryRooms(){
		Object dto = new Object();
		this.roomService.queryByAjax(dto);
	}
	
	
	/**
	 * ��ת�������Ϣҳ��
	 * @return
	 */
	@Action(value="toAdudit",results={
			@Result(name=SUCCESS, location="/WEB-INF/back/room/assist_review.jsp", type=DISPATCHER)
	})
	public String toAdudit(){
		String ip = ServletActionContext.getRequest().getRemoteAddr();
		ActionContext ctx = ActionContext.getContext();
		ctx.put("ip", ip);
		return SUCCESS;
	}
	
	/**
	 * �����Ϣ
	 */
	@Action(value="auditMessage")
	public void auditMessage(){
		this.sendAjaxMsg(this.roomService.auditMessage().toString());
	}
	

	
}
