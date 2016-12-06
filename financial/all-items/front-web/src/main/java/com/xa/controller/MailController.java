package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Mail;
import com.xa.service.MailService;

@Controller
@RequestMapping("/mail")
public class MailController extends BaseController {

	@Autowired
	private MailService<Mail> mailService;
	
	/**
	 * 添加邮件
	 */
	@RequestMapping("insert")
	public void insert(Mail mail,String sign){
		 try {
			this.sendAjaxMsg(this.mailService.addMail(mail, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
