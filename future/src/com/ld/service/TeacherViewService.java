package com.ld.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

import com.ld.dto.TeacherViewDto;
import com.ld.model.MessageBO;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

public interface TeacherViewService<T> extends BaseServiceInte<T>{
	
	
	public JSONObject  generateTeacherView(ActionContext ctx1,TeacherViewDto dto);
	public JSONObject  deleteTeacherView(ActionContext ctx1,BigDecimal viewId);
    public  String     justifyCurrentRole(ActionContext ctx );
    public  void  getLatestTeacherView(ActionContext ctx);
    //public void deleteTeacherView(BigDecimal  id);
    public JSONObject  dealWithAskTeacher(BigDecimal viewId, String content,BigDecimal questionType);
    public JSONObject  dealWithTeacherReply(ActionContext ctx, BigDecimal msgId,String content);
    public void  getCurrentUserPrivateMessage(ActionContext ctx);
    // 上传讲师观点图片
  	public Map<String,Object> uploadTeacherViewPic(File file,String fileName);
  	// 获取讲师客户
//  	public List<Customer>   getAllTeacherCustomer();
  	// 获取实盘空间配置标题
  	public void   getRoomConfig(ActionContext ctx1);
  	
  	public MessageBO getTeacherViewScreen(TeacherViewDto teacherViewDto);
  	
  	public Map<String,Object> getReplyCount(BigDecimal userId);
    
}
