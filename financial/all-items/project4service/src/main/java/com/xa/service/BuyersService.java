package com.xa.service;

import java.io.IOException;

import org.apache.http.ParseException;

import com.xa.entity.BuyHand;
import com.xa.entity.Buyers;

public interface BuyersService<T> extends BaseServiceInte<T> {

	/**
	 * 买家注册
	 * @param buyer
	 * @param sign
	 * @return
	 */
	public String register(Buyers buyer, String vercode, String sign);
	
	/**
	 * 获取验证码
	 * @param customer
	 * @param sign
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public String getVercode(Buyers buyers,  String sign) throws ParseException, IOException ;
	
	/**
	 * 用户登录
	 * @param buyers
	 * @param sign
	 * @return
	 */
	public String login(Buyers buyers,String sign);
	
	/**
	 * 更换手机号
	 * @param vercode
	 * @param mobile
	 * @param buyerId
	 * @param sign
	 * @param buyHandService
	 * @return
	 */
	public String changeMobile(String vercode, String mobile,Long buyerId, String sign,BuyHandService<BuyHand> buyHandService);
}
