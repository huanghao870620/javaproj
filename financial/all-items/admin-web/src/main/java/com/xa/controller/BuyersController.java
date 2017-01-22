package com.xa.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xa.convert.DatePropertyEditor;
import com.xa.entity.BuyHand;
import com.xa.entity.Buyers;
import com.xa.entity.File;
import com.xa.service.BuyHandService;
import com.xa.service.BuyersService;
import com.xa.service.FileService;

@Controller
@RequestMapping("/buyers")
public class BuyersController extends BaseController {

	@Autowired
	private BuyersService<Buyers> buyersService;
	
	@Autowired
	private BuyHandService<BuyHand> buyHandService;
	
	@Autowired
	private FileService<File> fileService;

	/**
	 * 买家注册
	 * @param buyer
	 * @param sign
	 */
	@RequestMapping("register")
	public void register(Buyers buyer, String vercode,String sign){
		 try {
			this.sendAjaxMsg(this.buyersService.register(buyer,vercode, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param buyers
	 * @param sign
	 */
	@RequestMapping("getVercode")
	public void getVercode(Buyers buyers, String sign){
		try {
			this.sendAjaxMsg(this.buyersService.getVercode(buyers, sign));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户登录 
	 */
	@RequestMapping("login")
	public void login(Buyers buyers,String sign) {
		try {
			this.sendAjaxMsg(this.buyersService.login(buyers, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更换手机号
	 * @param vercode
	 * @param mobile
	 * @param buyerId
	 * @param sign
	 * @param buyHandService
	 */
	public void changeMobile(String vercode, String mobile,Long buyerId, String sign){
		try {
			this.sendAjaxMsg(this.buyersService.changeMobile(vercode, mobile, buyerId, sign, this.buyHandService));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 编辑买家头像
	 * @param buyerId
	 * @param headPortialFile
	 * @param sign
	 * @param fileService
	 * @param request
	 */
	@RequestMapping("updateHeadPortial")
	public void updateHeadPortial(
			Long buyerId,
			@RequestParam(value="headPortialFile",required=false) MultipartFile headPortialFile,
			String sign
			) {
		try {
			this.sendAjaxMsg(this.buyersService.updateHeadPortial(buyerId, headPortialFile, sign, fileService));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


	/**
	 * 修改手机号
	 * @param buyerId
	 * @param newMobile
	 * @param vercode
	 * @param sign
	 */
	@RequestMapping("updateMobile")
	public void updateMobile(Long buyerId, String newMobile,String vercode, String sign){
		try {
			this.sendAjaxMsg(this.buyersService.updateMobile(buyerId, newMobile, vercode, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param buyer
	 * @param sign
	 * @return
	 */
	@RequestMapping("updateBuyer")
	public void updateBuyer(Buyers buyer, String sign){
		 try {
			this.sendAjaxMsg(this.buyersService.updateBuyer(buyer, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@InitBinder
	 protected void initBinder(HttpServletRequest request,
	   ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor("yyyy/MM/dd"));
	 }
	

	/**
	 * 修改买家密码
	 * @param vercode
	 * @param mobile
	 * @param password
	 * @param sign
	 */
	@RequestMapping("updateBuyerPass")
	public void updateBuyerPass(String vercode, String mobile, String password, String sign){
		try {
			this.sendAjaxMsg(this.buyersService.updateBuyerPass(vercode, mobile, password, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

