package com.xa.controller;

import java.io.IOException;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.AccountAssociated;
import com.xa.entity.BuyHand;
import com.xa.entity.BuyhandWallet;
import com.xa.entity.CustomerOrder;
import com.xa.service.AccountAssociatedService;
import com.xa.service.BuyHandService;
import com.xa.service.BuyhandWalletService;
import com.xa.service.CustomerOrderService;

@Controller
@RequestMapping("/buyhand")
public class BuyHandController extends BaseController{

	@Autowired
	private BuyHandService<BuyHand> buyHandService;
	
	@Autowired
	private CustomerOrderService<CustomerOrder> customerOrderService;
	
	@Autowired
	private BuyhandWalletService<BuyhandWallet> buyhandWalletService;
	
	@Autowired
	private AccountAssociatedService<AccountAssociated> accountAssociatedService;
	 
	 /**
	  * 用户注册
	  * @return
	  */
	 @RequestMapping(value="register")
	 public void register(@RequestParam(value = "sidPhotoFile", required = false) MultipartFile sidPhotoFile,//学生证照片
			 @RequestParam(value = "admNoticeFile", required = false) MultipartFile admNoticeFile, // 录取通知书
			 @RequestParam(value = "passportFile", required = false) MultipartFile passportFile, // 护照
			 @RequestParam(value = "lifePhotoFile", required = false) MultipartFile lifePhotoFile, //生活照
			 BuyHand customer,
			 String unionId,
			 Long accountTypeId,
			 String countryCode,
			 String sign){
		 try {
			this.sendAjaxMsg(this.buyHandService.register(customer, 
					sidPhotoFile, 
					admNoticeFile, 
					passportFile, 
					lifePhotoFile,
					this.session,
					this.request,
					unionId,
					accountTypeId,
					countryCode,
					sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * 用户登录
	  * @param customer
	  */
	 @RequestMapping(value="login")
	 public void login(BuyHand customer, String sign){
		 try {
			this.sendAjaxMsg(this.buyHandService.login(customer,this.request, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * 获取验证码
	  */
	 @RequestMapping("getVercode")
	 public void getVercode(BuyHand customer,String countryCode,String sign){
		 try {
			this.sendAjaxMsg(this.buyHandService.getVercode(customer,countryCode,sign));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

	 /**
	  * 获取订单通过客户id
	  */
	 @RequestMapping("getOrderByBuyHandId")
	 public void getOrderByBuyHandId(Long buyHandId, String sign){
//		  try {
//			this.sendAjaxMsg(this.customerOrderService.getOrdersByBuyHandId(buyHandId,sign));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	 }
	 
	 /**
	  * 更新客户信息
	  * @param customer
	  */
	 @RequestMapping("updateCustomer")
	 public void updateCustomer(
			 BuyHand customer, String sign) {
		try {
			this.sendAjaxMsg(this.buyHandService.updateCustomer(customer,this.session,this.request,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 /**
	  * 获取账户余额
	  * @param buyHandId
	  */
	 @RequestMapping("getBalance")
	 public void getBalance(Long buyHandId, String sign){
		  try {
			this.sendAjaxMsg(this.buyhandWalletService.getBalance(buyHandId,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * 关联账号
	  */
	 @RequestMapping("associatedAccount")
	 public void associatedAccount(String unionId, String mobile, Long accountType, String sign){
		 try {
			this.sendAjaxMsg(this.accountAssociatedService.associatedAccount(unionId, mobile, accountType,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * 解除绑定
	  */
	 @RequestMapping("dissociated")
	 public void dissociated(String unionId, String sign){
		 try {
			this.sendAjaxMsg(this.accountAssociatedService.dissociated(unionId,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * 第三方登录
	  * @param unionId
	  */
	 @RequestMapping("thirdPartyLogin")
	 public void thirdPartyLogin(String unionId, String sign){
		 try {
			this.sendAjaxMsg(this.buyHandService.thirdPartyLogin(unionId,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * 验证码是否合法
	  */
	 @RequestMapping("verCodeAreLegal")
	 public void verCodeAreLegal(String mobile,String vercode, String sign) {
		  try {
			this.sendAjaxMsg(this.buyHandService.verificationCodeAreLegal(mobile,vercode,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 /**
	  * 更新客户头像
	  */
	 @RequestMapping("updateCustomerHeadPortrait")
	 public void updateCustomerHeadPortrait(Long buyHandId,
			 @RequestParam(value = "headPortraitFile", required = false) MultipartFile headPortraitFile,
			 String sign) {
		try {
			this.sendAjaxMsg(this.buyHandService.updateCustomerHeadPortrait(buyHandId, headPortraitFile, request, sign));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 /**
	  * 更新客户密码
	  */
	 @RequestMapping("updateCustomerPassword")
	 public void updateCustomerPassword(BuyHand customer,String oldPassword,String sign) {
		try {
			this.sendAjaxMsg(this.buyHandService.updateCustomerPassword(customer,oldPassword, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 /**
	  * 更换手机号
	  * @param vercode
	  * @param newMobile
	  * @param oldMobile
	  * @param sign
	  */
	 @RequestMapping("replacePhoneNumber")
	 public void replacePhoneNumber(String vercode, String newMobile, String oldMobile, String sign){
		  try {
			this.sendAjaxMsg(this.buyHandService.replacePhoneNumber(vercode, newMobile, oldMobile, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 

	 /**
	  * 新手机号
	  * @param mobile
	  * @param vercode
	  * @param buyHandId
	  * @param sign
	  */
	 @RequestMapping("newMobile")
	 public void newMobile(String mobile,String vercode, Long buyHandId, String sign) {
		try {
			this.sendAjaxMsg(this.buyHandService.newMobile(mobile, vercode, buyHandId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 /**
	  * 是否绑定第三方账号
	  */
	 @RequestMapping("isBoundThirdParty")
	 public void isBoundThirdParty(String unionId,Long buyHandId,String sign) {
		try {
			this.sendAjaxMsg(this.buyHandService.isBoundThirdParty(unionId, buyHandId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 /**
	  * 
	  * @param customer
	  * @param vercode
	  * @param sign
	  */
	 @RequestMapping("changePasswordWhenLogin")
	 public void changePasswordWhenLogin(BuyHand customer,String vercode,String sign) {
		try {
			this.sendAjaxMsg(this.buyHandService.changePasswordWhenLogin(customer, vercode, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 /**
	  * 设置支付密码
	  */
	 @RequestMapping("setPayPassword")
	 public void setPayPassword(BuyHand customer, String sign) {
		try {
			this.sendAjaxMsg(this.buyHandService.setPayPassword(customer, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 /**
	  * 更换手机号
	  * @param customer
	  * @param sign
	  */
	 @RequestMapping("replacePhoneNumberByPassword")
	 public void replacePhoneNumberByPassword(BuyHand customer, String sign){
		 try {
			this.sendAjaxMsg(this.buyHandService.replacePhoneNumber(customer, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 

	 /**
	  * 更改买手签名
	  * @param signature
	  * @param buyHandId
	  * @param sign
	  */
	 @RequestMapping("updateSignature")
	 public void updateSignature(String signature,Long buyHandId,String sign) {
		try {
			this.sendAjaxMsg(this.buyHandService.updateSignature(signature, buyHandId, session, request, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	 /**
	  * 更改昵称
	  * @param nickname
	  * @param buyHandId
	  * @param sign
	  */
	 @RequestMapping("updateNickName")
	 public void updateNickName(String nickname,Long buyHandId,String sign) {
		 try {
			this.sendAjaxMsg(this.buyHandService.updateNickName(nickname, buyHandId, session, request, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
}
