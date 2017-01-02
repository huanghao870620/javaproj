package com.xa.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.BuyHand;
import com.xa.entity.Buyers;
import com.xa.entity.Country;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.entity.MobileVercodeLog;
import com.xa.entity.ShoppingCart;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BuyersMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.MobileVercodeLogMapper;
import com.xa.mapper.ShoppingCartMapper;
import com.xa.msg.ChuanglanSMS;
import com.xa.service.BuyHandService;
import com.xa.service.BuyersService;
import com.xa.service.FileService;
import com.xa.service.GoodsService;
import com.xa.util.Constants;
import com.xa.util.EncryptionTool;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class BuyersServiceImpl extends BaseServiceImpl<Buyers, BuyersMapper> implements BuyersService<Buyers> {

	
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	
	@Autowired
	private MobileVercodeLogMapper mobileVercodeLogMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	
	/**
	 * 登录
	 * @return
	 */
	public String login(Buyers buyers,String sign){
		JSONObject object = new JSONObject();
		
		if(!sign.equals(Security.getSign(new String[]{
				"mobile","password"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		buyers.setPassword(EncryptionTool.addSaltEncrypt(buyers.getPassword(), Security.getPasswordSalt()));
		Buyers existBuyer = this.m.findBuyerByMobileAndPass(buyers);
		if(null != existBuyer){
			Date birthDay = existBuyer.getBirthday();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = null;
			if(null != birthDay){
				dateStr = sdf.format(birthDay);
			}
			String gender = existBuyer.getGender();
			Long hp = existBuyer.getHeadPortrait();
			String mobile =  existBuyer.getMobile();
			Long id = existBuyer.getId();
			String nickName = existBuyer.getNickname();
			String signature =  existBuyer.getSignature();
			String hpUriPath = null;
			if(null!=hp){
				File hpFile= this.fileMapper.selectByPrimaryKey(hp);
				hpUriPath = hpFile.getUriPath();
			}
			
			ShoppingCart cart = this.shoppingCartMapper.getCartByBuyerId(id);
			
			object.accumulate(Constants.SUCCESS, true)
			.accumulate("birthDay", dateStr == null ? "":dateStr)
			.accumulate("gender", gender == null ? "":gender)
			.accumulate("headPortrait", hpUriPath ==null ? "" : hpUriPath)
			.accumulate("mobil", mobile == null ? "" : mobile)
			.accumulate("id", id)
			.accumulate("nickName", nickName == null ? "" : nickName)
			.accumulate("signature", signature == null ? "" : signature)
			.accumulate("cartId", cart.getId())
			;
		}else {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "用户不存在!");
		}
		return object.toString();
	}
	
	/**
	 * 注册
	 * @return
	 */
	public String register(Buyers buyer, String vercode, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"mobile","vercode","password"	
		}))){
		 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();	
		}
		
		String encPass = EncryptionTool.addSaltEncrypt(buyer.getPassword(), Security.getPasswordSalt());
		buyer.setPassword(encPass);
		Buyers existBuyer = this.m.findBuyerByMobileAndPass(buyer);
		if(null != existBuyer){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "该手机已注册!").toString();
		}
		
		MobileVercodeLog param = new MobileVercodeLog();
		param.setMobile(buyer.getMobile());
		param.setVercode(vercode);
		List<MobileVercodeLog> mvlList = this.mobileVercodeLogMapper.getVercodeByMobile(param);
		if (mvlList.size() > 0) {
			
		} else {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "验证码错误!");
			return object.toString();
		}
		
		this.m.insert(buyer);
		ShoppingCart cart = new ShoppingCart();
		cart.setBuyerId(buyer.getId());
		this.shoppingCartMapper.insert(cart );
		
		Date birthDay = buyer.getBirthday();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = null;
		if(null != birthDay){
			dateStr = sdf.format(birthDay);
		}
		String gender = buyer.getGender();
		Long hp = buyer.getHeadPortrait();
		String mobile =  buyer.getMobile();
		Long id = buyer.getId();
		String nickName = buyer.getNickname();
		String signature =  buyer.getSignature();
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("birthDay", dateStr == null ? "":dateStr)
		.accumulate("gender", gender == null ? "":gender)
		.accumulate("headPortrait", hp ==null ? "" : hp)
		.accumulate("mobil", mobile == null ? "" : mobile)
		.accumulate("id", id)
		.accumulate("nickName", nickName == null ? "" : nickName)
		.accumulate("signature", signature == null ? "" : signature)
		.accumulate("cartId", cart.getId())
		;
		return object.toString();
	}
	
	/**
	 * 获取验证码
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getVercode(Buyers buyers, String sign) throws ParseException, IOException {
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { "mobile" }))) {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}
		ChuanglanSMS client = new ChuanglanSMS("I9179561", "pKFeRloiIL7d4b");
		String mobile = "86" + buyers.getMobile();
		CloseableHttpResponse response = null;
		String vercode = this.generateVerCode();
		MobileVercodeLog mvl = new MobileVercodeLog();
		mvl.setMobile(buyers.getMobile());
		mvl.setSendTime(new Date());
		mvl.setVercode(vercode);
		this.mobileVercodeLogMapper.insert(mvl);
		response = client.sendInternationalMessage(mobile, "【寻安】您的验证码是：" + vercode);// 发送国际验证码
		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			String entityStr = EntityUtils.toString(response.getEntity());
			JSONObject entityObj = JSONObject.fromObject(entityStr);
			object.accumulate("id", entityObj.get("id")).accumulate(Constants.SUCCESS,
					entityObj.get(Constants.SUCCESS));
		}
		client.close();
		return object.toString();
	}
	
	/**
	 * 生成验证码
	 * 
	 * @return
	 */
	private String generateVerCode() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	/**
	 * 更换手机号
	 * @return
	 */
	public String changeMobile(String vercode, String mobile,Long buyerId, String sign,BuyHandService<BuyHand> buyHandService){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"vercode","mobile"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		
		String methodSign= Security.getSign(new String[] { 
				"mobile","vercode" 
				});
		
		
		if(Constants.SUCCESS.equals(JSONObject.fromObject(buyHandService.verificationCodeAreLegal(mobile, vercode, methodSign)).get(Constants.SUCCESS))){
			Buyers buyers= this.m.selectByPrimaryKey(buyerId);
			buyers.setMobile(mobile);
			this.m.updateByPrimaryKeySelective(buyers);
			object.accumulate(Constants.SUCCESS, true);
		}else{
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "验证码不正确!");
		}
		return object.toString();
	}
	
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
			) throws IllegalStateException, IOException {
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyerId","headPortialFile"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Buyers buyer= this.m.selectByPrimaryKey(buyerId);
		
		com.xa.entity.File headPortraitFile = new com.xa.entity.File();
		if(null == buyer.getHeadPortrait()){// 添加
			fileService.uploadFile(headPortialFile, PhotoType.GOODS_ACCORDING_TO_POSITIVE_TYPE,headPortraitFile);
		}else{ // 修改
			headPortraitFile.setId(buyer.getHeadPortrait());
			fileService.editFile( headPortialFile, PhotoType.GOODS_ACCORDING_TO_POSITIVE_TYPE,headPortraitFile); //商品正面视图
		}
		buyer.setHeadPortrait(headPortraitFile.getId());
		this.m.updateByPrimaryKey(buyer);
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("headPortrialUriPath", headPortraitFile.getUriPath())
		;
		return object.toString();
	}
	
	/**
	 * 修改买家信息
	 * @return
	 */
	public String updateBuyer(Buyers buyer, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"id","nickname","gender","birthday","signature"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG,Msg.NOT_PERMISSION).toString();
		}
		
		this.m.updateByPrimaryKeySelective(buyer);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 修改手机号
	 * @return
	 */
	public String updateMobile(Long buyerId, String newMobile,String vercode, String sign){
		
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyerId","newMobile","vercode"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		MobileVercodeLog mvParam = new MobileVercodeLog();
		mvParam.setMobile(newMobile);
		mvParam.setVercode(vercode);
		List<MobileVercodeLog> mvlList = this.mobileVercodeLogMapper.getVercodeByMobile(mvParam);
		if(mvlList.size()>0){
			Buyers buyer= this.m.selectByPrimaryKey(buyerId);
			buyer.setMobile(newMobile);
			this.m.updateByPrimaryKeySelective(buyer);
		}else{
			object.accumulate(Constants.MSG, "手机或验证码已经过期!");
		}
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("newMobile", newMobile)
		;
		return object.toString();
	}

}
