package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.BuyHand;

public interface BuyHandService<T> extends BaseServiceInte<T> {


	/**
	 * 客户注册
	 * @return
	 */
	 public String register(BuyHand buyHand,
			    MultipartFile sidPhotoFile,
				MultipartFile admNoticeFile,
				MultipartFile passportFile,
				MultipartFile lifePhotoFile,
				HttpSession session,
				HttpServletRequest request,String unionId,Long accountTypeId,String sign) throws IllegalStateException, IOException;
	 
	 
	 /**
	  * 客户登录
	  * @return
	  */
	 public String login(BuyHand buyHand,HttpServletRequest request, String sign);
	 
	 /**
	  * 获取验证码
	  * @param customer
	  * @return
	  * @throws ParseException
	  * @throws IOException
	  */
	 public String getVercode(BuyHand buyHand, String sign) throws ParseException, IOException;
	 
	 /**
	  * 
	  * @param customer
	  * @return
	  */
	 public String updateCustomer(BuyHand buyHand,HttpSession session, HttpServletRequest request, String sign) throws IllegalStateException, IOException;
	 
	 /**
	  * 第三方登录
	  * @param unionId
	  * @return
	  */
	 public String thirdPartyLogin(String unionId, String sign);
	 
	 /**
	  * 
	  * @param mobile
	  * @return
	  */
	 public String verificationCodeAreLegal(String mobile, String vercode, String sign);
	 
	 /**
	  * 
	  * @param buyHandId
	  * @param headPortraitFile
	  * @param request
	  * @param sign
	  * @return
	  * @throws IllegalStateException
	  * @throws IOException
	  */
	 public String updateCustomerHeadPortrait(Long buyHandId, MultipartFile headPortraitFile,
				HttpServletRequest request, String sign) throws IllegalStateException, IOException;
	 
	 /**
	  * 
	  * @param customer
	  * @param sign
	  * @return
	  */
	 public String updateCustomerPassword(BuyHand buyHand, String oldPassword,String sign);
	 
	 /**
	  * 
	  * @param vercode
	  * @param newMobile
	  * @param oldMobile
	  * @param sign
	  * @return
	  */
	 public String replacePhoneNumber(String vercode, String newMobile, String oldMobile, String sign);
	 
	 /**
	  * 新手机号
	  * @param mobile
	  * @param vercode
	  * @param buyHandId
	  * @param sign
	  * @return
	  */
	 public String newMobile(String mobile, String vercode,Long buyHandId, String sign) ;
	 
	 /**
	  * 
	  * @param unionId
	  * @param buyHandId
	  * @param sign
	  * @return
	  */
	 public String isBoundThirdParty(String unionId,Long buyHandId,String sign);
	 
	 /**
	  * 修改密码[登录时]
	  * @param customer
	  * @param vercode
	  * @param sign
	  * @return
	  */
	 public String changePasswordWhenLogin(BuyHand buyHand, String vercode,  String sign) ;
	 
	 /**
	  * 设置支付密码
	  * @param customer
	  * @param sign
	  * @return
	  */
	 public String setPayPassword(BuyHand buyHand, String sign);
	 
	 /**
	  * 更换手机号
	  * @param customer
	  * @param sign
	  * @return
	  */
	 public String replacePhoneNumber(BuyHand buyHand, String sign);
	 
	 /**
	  * 更改签名
	  * @param signature
	  * @param buyHandId
	  * @param session
	  * @param request
	  * @param sign
	  * @return
	  */
	 public String updateSignature(String signature,Long buyHandId , HttpSession session,
				HttpServletRequest request,  String sign);
	 
	 /**
	  * 更改昵称
	  * @param nickname
	  * @param buyHandId
	  * @param session
	  * @param request
	  * @param sign
	  * @return
	  */
	 public String updateNickName(String nickname,Long buyHandId , HttpSession session,
				HttpServletRequest request,  String sign);
}
