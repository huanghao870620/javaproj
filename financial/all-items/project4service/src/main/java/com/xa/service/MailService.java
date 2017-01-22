package com.xa.service;

import com.xa.entity.Mail;
import com.xa.service.BaseServiceInte;

public interface MailService<T> extends BaseServiceInte<T> {

	/**
	 * 
	 * @param mail
	 * @param sign
	 * @return
	 */
	public String addMail(Mail mail,String sign);
}
