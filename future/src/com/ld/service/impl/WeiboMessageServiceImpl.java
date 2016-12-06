package com.ld.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.freemarker.ProcessFtl;
import com.ld.mapper.UserMapper;
import com.ld.mapper.WeiboMessageMapper;
import com.ld.model.User;
import com.ld.model.WeiboMessage;
import com.ld.service.WeiboMessageService;

/**
 * 
 * @author huang.hao
 *
 */
@Service
@Transactional
public class WeiboMessageServiceImpl  extends BaseServiceImpl<WeiboMessage, WeiboMessageMapper>
implements WeiboMessageService<WeiboMessage> {

	
	@Autowired
	private WeiboMessageMapper weiboMessageMapper;
	
	
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * ������Ϣ
	 * @param message
	 */
	public void updateWeiboMessage(WeiboMessage message){
	}
	
	
	/**
	 * ��ȡ����50����Ϣ
	 */
	public String get50newsRecently(){
		List<WeiboMessage> msgs = this.weiboMessageMapper.getRecentlyFifteenMsg();
		for(int i=0; i<msgs.size(); i++){
			WeiboMessage message = msgs.get(i);
			BigDecimal userid = message.getCustomerId();
			if(null != userid){
				User user = userMapper.findById(userid);
				message.setCfileId(user.getCFileId());
			}
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> root = new HashMap<String,Object>();
		root.put("msgs", msgs);
		String msg = new ProcessFtl(root, "msg/get50Msg.ftl", request.getContextPath()).process(); 
		return msg;
	}
	
}
