package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Mail;
import com.xa.mapper.MailMapper;
import com.xa.service.MailService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class MailServiceImpl extends BaseServiceImpl<Mail, MailMapper> implements MailService<Mail> {

	/**
	 * 添加邮件
	 * @return
	 */
	public String addMail(Mail mail,String sign){
		JSONObject object = new JSONObject();
		 if(!sign.equals(Security.getSign(new String[]{
			"buyHandId","title","content"	 
		 }))){
			 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		 }
		 this.m.insert(mail);
		 object.accumulate(Constants.SUCCESS, true);
		 return object.toString();
	}
	
}
