package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.BuyHand;
import com.xa.entity.Buyers;
import com.xa.entity.File;

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
	
	/**
	 * 修改头像
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String updateHeadPortial(
			Long buyerId,
			MultipartFile headPortialFile,
			String sign,
			FileService<File> fileService
			) throws IllegalStateException, IOException ;
	
	/**
	 * 修改手机号
	 * @param buyerId
	 * @param newMobile
	 * @param vercode
	 * @param sign
	 * @return
	 */
	public String updateMobile(Long buyerId, String newMobile,String vercode, String sign);
	
	/**
	 * 修改买家信息
	 * @param buyer
	 * @param sign
	 * @return
	 */
	public String updateBuyer(Buyers buyer, String sign);
}
