package com.xa.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.AccountAssociated;
import com.xa.entity.BuyHand;
import com.xa.entity.Country;
import com.xa.entity.MobileVercodeLog;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.AccountAssociatedMapper;
import com.xa.mapper.BuyHandMapper;
import com.xa.mapper.CountryMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.MobileVercodeLogMapper;
import com.xa.msg.ChuanglanSMS;
import com.xa.service.BuyHandService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.EncryptionTool;
import com.xa.util.GenerateFilePath;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class BuyHandServiceImpl extends BaseServiceImpl<BuyHand, BuyHandMapper>
		implements BuyHandService<BuyHand> {

	@Autowired
	private BuyHandMapper buyHandMapper;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private MobileVercodeLogMapper mobileVercodeLogMapper;

	@Autowired
	private AccountAssociatedMapper accountAssociatedMapper;
	
	@Autowired
	private CountryMapper countryMapper;

	/**
	 * 注册
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@Override
	public String register(BuyHand customer, // 客户信息
			MultipartFile sidPhotoFile, // 学生证照片
			MultipartFile admNoticeFile, // 录取通知书
			MultipartFile passportFile, // 护照
			MultipartFile lifePhotoFile, // 生活照
			HttpSession session,
			HttpServletRequest request, 
			String unionId,
			Long accountTypeId,
			String countryCode,
			String sign) throws IllegalStateException, IOException {
		boolean success = false;
		JSONObject object = new JSONObject();
		String localSign = Security.getSign(new String[] { "mobile", "password", "gender", "sidPhotoFile",
				"admNoticeFile", "passportFile", "lifePhotoFile","email","idNumber","passportNo","inputId",
				"unionId","accountTypeId","countryCode"});
		if (!localSign.equals(sign)) {
			object.accumulate(Constants.SUCCESS, success).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}
		List<BuyHand> existsCustomer = this.buyHandMapper.selectCustomerByMobile(customer);
		if (existsCustomer.size() > 0) {
			success = false;
			object.accumulate(Constants.SUCCESS, success).accumulate(Constants.MSG, "此手机号已经注册，不能重复注册!");
			return object.toString();
		}

		String path = session.getServletContext().getRealPath("upload");
		String sidPhotoFileName = sidPhotoFile.getOriginalFilename();// 学生照
		String lifePhotoFileName = lifePhotoFile.getOriginalFilename(); // 生活照
		String passportFileName = passportFile.getOriginalFilename(); // 护照
		String admNoticeFileName = admNoticeFile.getOriginalFilename(); // 录取通知书

		
		
		GenerateFilePath sidPhotoGenerateFilePath = new GenerateFilePath(PhotoType.SID_PHOTO_TYPE.getValue(), sidPhotoFileName);
		GenerateFilePath lifePhotoGenerateFilePath = new GenerateFilePath(PhotoType.LIFE_PHOTO_FILE_TYPE.getValue(), lifePhotoFileName);
		GenerateFilePath passportGenerateFilePath = new GenerateFilePath(PhotoType.PASSPORT_FILE_TYPE.getValue(), passportFileName);
		GenerateFilePath admNoticeGenerateFilePath = new GenerateFilePath(PhotoType.ADM_NOTICE_FILE_TYPE.getValue(), admNoticeFileName);
		
		String contextPath = request.getContextPath();
		String sidPhotoFileUrl = contextPath + "/upload/" + sidPhotoGenerateFilePath.getUri() ;
		String lifePhotoFileUrl = contextPath + "/upload/" + lifePhotoGenerateFilePath.getUri();
		String passportFileUrl = contextPath + "/upload/" + passportGenerateFilePath.getUri() ;
		String admNoticeFileUrl = contextPath + "/upload/" + admNoticeGenerateFilePath.getUri() ;

		com.xa.entity.File sidPhoto = new com.xa.entity.File(); // 学生证照片
		sidPhoto.setName(sidPhotoFileName);
		sidPhoto.setPath(path);
		sidPhoto.setTypeId(PhotoType.SID_PHOTO_TYPE.getValue());
		sidPhoto.setUriPath(sidPhotoFileUrl);
		this.fileMapper.insert(sidPhoto);

		com.xa.entity.File lifePhoto = new com.xa.entity.File(); // 生活照
		lifePhoto.setName(lifePhotoFileName);
		lifePhoto.setPath(path);
		lifePhoto.setTypeId(PhotoType.LIFE_PHOTO_FILE_TYPE.getValue());
		lifePhoto.setUriPath(sidPhotoFileUrl);
		this.fileMapper.insert(lifePhoto);

		com.xa.entity.File passport = new com.xa.entity.File(); // 护照
		passport.setName(passportFileName);
		passport.setPath(path);
		passport.setTypeId(PhotoType.PASSPORT_FILE_TYPE.getValue());
		passport.setUriPath(passportFileUrl);
		this.fileMapper.insert(passport);

		com.xa.entity.File admNotice = new com.xa.entity.File(); // 录取通知书
		admNotice.setName(admNoticeFileName);
		admNotice.setPath(path);
		admNotice.setTypeId(PhotoType.ADM_NOTICE_FILE_TYPE.getValue());
		admNotice.setUriPath(admNoticeFileUrl);
		this.fileMapper.insert(admNotice);

		customer.setSidPhoto(sidPhoto.getId()); // 学生证照片
		customer.setLifePhoto(lifePhoto.getId()); // 生活照
		customer.setPassport(passport.getId()); // 护照
		customer.setAdmNotice(admNotice.getId()); // 录取通知书

		File sidPhotoTargetFile = new File(path, sidPhotoFileName);
		if (!sidPhotoTargetFile.exists()) {
			sidPhotoTargetFile.mkdirs();
		}

		File lifePhotoTargetFile = new File(path, lifePhotoFileName);
		if (!lifePhotoTargetFile.exists()) {
			lifePhotoTargetFile.mkdirs();
		}

		File passportTargetFile = new File(path, passportFileName);
		if (!passportTargetFile.exists()) {
			passportTargetFile.mkdirs();
		}

		File admNoticeTargetFile = new File(path, admNoticeFileName);
		if (!admNoticeTargetFile.exists()) {
			admNoticeTargetFile.mkdirs();
		}

		sidPhotoFile.transferTo(sidPhotoTargetFile);
		lifePhotoFile.transferTo(lifePhotoTargetFile);
		passportFile.transferTo(passportTargetFile);
		admNoticeFile.transferTo(admNoticeTargetFile);

		int intensity = this.getIntensityOfPassword(customer.getPassword());
		String password = EncryptionTool.addSaltEncrypt(customer.getPassword(), Security.getPasswordSalt());
		customer.setPassword(password);
		customer.setAddTime(new Date());
		
		customer.setIntensity(intensity);
		
		// 添加国家代码
		Country country= this.countryMapper.findCountryByCode(countryCode);
		customer.setCountryId(country.getId());
		this.buyHandMapper.insert(customer);
		
		//第三方标识不为空
		if(!StringUtils.isEmpty(unionId)){
			 AccountAssociated record = new AccountAssociated();
			 record.setAccountTypeId(accountTypeId);
			 record.setBuyhandId(customer.getId());
			 record.setUnionId(unionId);
			this.accountAssociatedMapper.insert(record );
		}
		
		
		return object.accumulate(Constants.SUCCESS, true)
				.accumulate("buyHandId", customer.getId())
				.accumulate("sidPhotoFileUrl", sidPhotoFileUrl)
				.accumulate("lifePhotoFileUrl", lifePhotoFileUrl)
				.accumulate("passportFileUrl", passportFileUrl)
				.accumulate("admNoticeFileUrl", admNoticeFileUrl)
				.accumulate("inputId", customer.getInputId())
				.toString();
	}

	/**
	 * 登录
	 */
	@Override
	public String login(BuyHand customer, HttpServletRequest request, String sign) {
		int intensity = this.getIntensityOfPassword(customer.getPassword());
		String password = EncryptionTool.addSaltEncrypt(customer.getPassword(), Security.getPasswordSalt());
		customer.setPassword(password);
		List<BuyHand> list = this.buyHandMapper.selectCustomerByCustomerAndPass(customer);
		BuyHand resultCustomer = null;
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { "mobile", "password" }))) {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}
		if (list.size() > 0) {
			resultCustomer = list.get(0);
			request.getSession().setAttribute(Constants.LOGIN_CUSTOMER, resultCustomer);
			Long admNoticeFileId = resultCustomer.getAdmNotice(); // 录取通知书
			Long lifePhotoFileId = resultCustomer.getLifePhoto(); // 生活照
			Long passportFileId = resultCustomer.getPassport(); // 护照
			Long sidPhotoFileId = resultCustomer.getSidPhoto(); // 学生照
			Long headPortraitFileId = resultCustomer.getHeadPortrait(); //头像
			Long countryId= resultCustomer.getCountryId();//国家id
			com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
			com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
			com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
			com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
			com.xa.entity.File headPortraitFile = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
			String headPortraitFileUriPath = null;
			if(null != headPortraitFile){
				headPortraitFileUriPath = headPortraitFile.getUriPath();
			}
			Country country= this.countryMapper.selectByPrimaryKey(countryId);
			this.fillRetJSONObject(object,
					resultCustomer,
					intensity,
					headPortraitFileUriPath,
					admNoticeFile, 
					sidPhotoFile,
					passportFile, 
					lifePhotoFile, 
					country);
		} else {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "不存在此用户!");
			return object.toString();
		}
		return object.toString();
	}
	
	/**
	 * 获取买家信息
	 * @return
	 */
	private void fillRetJSONObject(JSONObject object,
			BuyHand buyHand,
			int intensity,
			String headPortraitFileUriPath,
			com.xa.entity.File admNoticeFile,
			com.xa.entity.File sidPhotoFile,
			com.xa.entity.File passportFile,
			com.xa.entity.File lifePhotoFile,
			Country country){
		object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", buyHand.getId())
		.accumulate("intensity", intensity)
		.accumulate("mobile", buyHand.getMobile() == null ? "" : buyHand.getMobile())
		.accumulate("nickname", buyHand.getNickname() == null ? "" : buyHand.getNickname())
		.accumulate("gender", buyHand.getGender() == null ? "" : buyHand.getGender())
		.accumulate("signature", buyHand.getSignature() == null ? "" : buyHand.getSignature())
		.accumulate("headPortrait",
				headPortraitFileUriPath == null ? "" : headPortraitFileUriPath)
		.accumulate("admNoticeFilePath",
				admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
		.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
		.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
		.accumulate("lifePhotoFilePath",
				lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
		.accumulate("inputId", buyHand.getInputId())
		.accumulate("countryCode", country.getCountryCode())
		;
	}

	/**
	 * 获取密码强度 (0:低,1:中,2:高)
	 * 
	 * @return
	 */
	private int getIntensityOfPassword(String password) {
		int digit = 0, letter = 0, schar = 0, kindAll = 0, kindDigit = 0, kindletter = 0, kindSchar = 0;
		char[] scharArr = { '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '=', '+', '.', ',', '>',
				'<' };

		char[] digitArr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		char[] letterArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		char[] arr = password.toCharArray();
		if (arr.length < 6) {
			return 0;
		}

		for (int i = 0; i < arr.length; i++) {

			// 特殊字符
			for (int j = 0; j < scharArr.length; j++) {
				if (arr[i] == scharArr[j]) {
					schar++;
					break;
				}
			}

			// 数字
			for (int j = 0; j < digitArr.length; j++) {
				if (arr[i] == digitArr[j]) {
					digit++;
					break;
				}
			}

			// 字母
			for (int j = 0; j < letterArr.length; j++) {
				if (arr[i] == letterArr[j]) {
					letter++;
					break;
				}
			}

		}

		if (digit > 0) {
			kindDigit = 1;
		}

		if (letter > 0) {
			kindletter = 1;
		}

		if (schar > 0) {
			kindSchar = 1;
		}

		kindAll = kindDigit + kindletter + kindSchar;

		if (kindAll == 1) {
			return 0;
		} else if (kindAll == 2) {
			return 1;
		} else if (kindAll == 3) {
			return 2;
		}

		return 0;
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getVercode(BuyHand customer,String countryCode, String sign) throws ParseException, IOException {
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { 
				"mobile","countryCode" 
				}))) {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}
		ChuanglanSMS client = new ChuanglanSMS("I9179561", "pKFeRloiIL7d4b");
		String mobile = countryCode + customer.getMobile();
		CloseableHttpResponse response = null;
		String vercode = this.generateVerCode();
		MobileVercodeLog mvl = new MobileVercodeLog();
		mvl.setMobile(customer.getMobile());
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
	 * 验证码是否正确
	 * 
	 * @return
	 */
	public String vercodeIsCorrect(String vercode, Long buyHandId) {
		return null;
	}

	/**
	 * 更新客户信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public String updateCustomer(BuyHand customer,  HttpSession session,
			HttpServletRequest request, String sign) throws IllegalStateException, IOException {
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { "id","nickname", "signature" }))) {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}

		this.buyHandMapper.updateByPrimaryKeySelective(customer);
		BuyHand resultCustomer = this.buyHandMapper.selectByPrimaryKey(customer.getId());
		Long admNoticeFileId = resultCustomer.getAdmNotice(); // 录取通知书
		Long lifePhotoFileId = resultCustomer.getLifePhoto(); // 生活照
		Long passportFileId = resultCustomer.getPassport(); // 护照
		Long sidPhotoFileId = resultCustomer.getSidPhoto(); // 学生照
		Long headPortraitFileId = resultCustomer.getHeadPortrait();//头像
		Long countryId= resultCustomer.getCountryId();//国家id
		com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
		com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
		com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
		com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
		com.xa.entity.File headPortraitFileTarget = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
		Country country= this.countryMapper.selectByPrimaryKey(countryId);
		String headPortraitFileUriPath = null;
		if(null != headPortraitFileTarget){
			headPortraitFileUriPath = headPortraitFileTarget.getUriPath();
		}
		object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", resultCustomer.getId())
		.accumulate("signature", resultCustomer.getSignature() == null ? "" : resultCustomer.getSignature())
				.accumulate("mobile", resultCustomer.getMobile() == null ? "" : resultCustomer.getMobile())
				.accumulate("nickname", resultCustomer.getNickname() == null ? "" : resultCustomer.getNickname())
				.accumulate("gender", resultCustomer.getGender() == null ? "" : resultCustomer.getGender())
				.accumulate("headPortrait", headPortraitFileUriPath == null ? "" : headPortraitFileUriPath)
				.accumulate("admNoticeFilePath", admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
				.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
				.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
				.accumulate("lifePhotoFilePath", lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
				.accumulate("countryCode", country.getCountryCode())
				.accumulate("inputId", resultCustomer.getInputId());
		return object.toString();
	}
	
	/**
	 * 更改买手签名
	 * @return
	 */
	public String updateSignature(String signature,Long buyHandId , HttpSession session,
			HttpServletRequest request,  String sign) {
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { "buyHandId", "signature" }))) {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}

		BuyHand buyHand = new BuyHand();
		buyHand.setSignature(signature);
		buyHand.setId(buyHandId);
		this.buyHandMapper.updateByPrimaryKeySelective(buyHand);
		BuyHand resultCustomer = this.buyHandMapper.selectByPrimaryKey(buyHandId);
		Long admNoticeFileId = resultCustomer.getAdmNotice(); // 录取通知书
		Long lifePhotoFileId = resultCustomer.getLifePhoto(); // 生活照
		Long passportFileId = resultCustomer.getPassport(); // 护照
		Long sidPhotoFileId = resultCustomer.getSidPhoto(); // 学生照
		Long headPortraitFileId = resultCustomer.getHeadPortrait();//头像
		Long countryId= resultCustomer.getCountryId();
		Country country= this.countryMapper.selectByPrimaryKey(countryId);
		com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
		com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
		com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
		com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
		com.xa.entity.File headPortraitFileTarget = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
		String headPortraitFileUriPath = null;
		if(null != headPortraitFileTarget){
			headPortraitFileUriPath = headPortraitFileTarget.getUriPath();
		}
		object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", resultCustomer.getId())
		.accumulate("signature", resultCustomer.getSignature() == null ? "" : resultCustomer.getSignature())
				.accumulate("mobile", resultCustomer.getMobile() == null ? "" : resultCustomer.getMobile())
				.accumulate("nickname", resultCustomer.getNickname() == null ? "" : resultCustomer.getNickname())
				.accumulate("gender", resultCustomer.getGender() == null ? "" : resultCustomer.getGender())
				.accumulate("headPortrait", headPortraitFileUriPath == null ? "" : headPortraitFileUriPath)
				.accumulate("admNoticeFilePath", admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
				.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
				.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
				.accumulate("lifePhotoFilePath", lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
				.accumulate("inputId", resultCustomer.getInputId())
				.accumulate("countryCode", country.getCountryCode())
				;
		 return object.toString();
	}
	
	/**
	 * 更改昵称
	 * @return
	 */
	public String updateNickName(String nickname,Long buyHandId , HttpSession session,
			HttpServletRequest request,  String sign) {
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { "buyHandId", "nickname" }))) {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}

		BuyHand buyHand = new BuyHand();
		buyHand.setSignature(nickname);
		buyHand.setId(buyHandId);
		this.buyHandMapper.updateByPrimaryKeySelective(buyHand);
		BuyHand resultCustomer = this.buyHandMapper.selectByPrimaryKey(buyHandId);
		Long admNoticeFileId = resultCustomer.getAdmNotice(); // 录取通知书
		Long lifePhotoFileId = resultCustomer.getLifePhoto(); // 生活照
		Long passportFileId = resultCustomer.getPassport(); // 护照
		Long sidPhotoFileId = resultCustomer.getSidPhoto(); // 学生照
		Long headPortraitFileId = resultCustomer.getHeadPortrait();//头像
		Long countryId= resultCustomer.getCountryId();
		com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
		com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
		com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
		com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
		com.xa.entity.File headPortraitFileTarget = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
		String headPortraitFileUriPath = null;
		if(null != headPortraitFileTarget){
			headPortraitFileUriPath = headPortraitFileTarget.getUriPath();
		}
		Country country= this.countryMapper.selectByPrimaryKey(countryId);
		object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", resultCustomer.getId())
		.accumulate("signature", resultCustomer.getSignature() == null ? "" : resultCustomer.getSignature())
				.accumulate("mobile", resultCustomer.getMobile() == null ? "" : resultCustomer.getMobile())
				.accumulate("nickname", resultCustomer.getNickname() == null ? "" : resultCustomer.getNickname())
				.accumulate("gender", resultCustomer.getGender() == null ? "" : resultCustomer.getGender())
				.accumulate("headPortrait", headPortraitFileUriPath == null ? "" : headPortraitFileUriPath)
				.accumulate("admNoticeFilePath", admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
				.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
				.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
				.accumulate("lifePhotoFilePath", lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
				.accumulate("inputId", resultCustomer.getInputId())
				.accumulate("countryCode", country.getCountryCode())
				;
		 return object.toString();
	
	}

	/**
	 * 更新客户头像
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public String updateCustomerHeadPortrait(Long buyHandId, MultipartFile headPortraitFile,
			HttpServletRequest request, String sign) throws IllegalStateException, IOException {
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"buyHandId","headPortraitFile"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		
		String path = request.getSession().getServletContext().getRealPath("upload");
		String headPortraitFileUrl = null;
		String headPortraitFileName = headPortraitFile.getOriginalFilename();// 客户头像
		
		GenerateFilePath generateFilePath = new GenerateFilePath(PhotoType.HEAD_PORTRAIT.getValue(), headPortraitFileName);
		
		String contextPath = request.getContextPath();
		String fileSpace =  "/"+generateFilePath.getFileSpace();
		headPortraitFileUrl = contextPath + "/upload/" + generateFilePath.getUri() ;

		com.xa.entity.File headPortrait = new com.xa.entity.File(); // 头像照片
		headPortrait.setName(generateFilePath.getFileName());
		headPortrait.setPath(path);
		headPortrait.setTypeId(PhotoType.HEAD_PORTRAIT.getValue());
		headPortrait.setUriPath(headPortraitFileUrl);
		this.fileMapper.insert(headPortrait);

		File sidPhotoTargetFile = new File(path + fileSpace, generateFilePath.getFileName());
		if (!sidPhotoTargetFile.exists()) {
			sidPhotoTargetFile.mkdirs();
		}
		headPortraitFile.transferTo(sidPhotoTargetFile);
		BuyHand customer = this.buyHandMapper.selectByPrimaryKey(buyHandId);
		customer.setHeadPortrait(headPortrait.getId());
		this.buyHandMapper.updateByPrimaryKeySelective(customer);
		Long admNoticeFileId = customer.getAdmNotice(); // 录取通知书
		Long lifePhotoFileId = customer.getLifePhoto(); // 生活照
		Long passportFileId = customer.getPassport(); // 护照
		Long sidPhotoFileId = customer.getSidPhoto(); // 学生照
		Long headPortraitFileId = customer.getHeadPortrait();//头像
		Long countryId=customer.getCountryId();
		Country country= this.countryMapper.selectByPrimaryKey(countryId);
		com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
		com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
		com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
		com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
		com.xa.entity.File headPortraitFileTarget = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
		object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", customer.getId())
		.accumulate("mobile", customer.getMobile() == null ? "" : customer.getMobile())
		.accumulate("nickname", customer.getNickname() == null ? "" : customer.getNickname())
		.accumulate("gender", customer.getGender() == null ? "" : customer.getGender())
		.accumulate("headPortrait", headPortraitFileTarget.getUriPath() == null ? "" : headPortraitFileTarget.getUriPath())
		.accumulate("admNoticeFilePath", admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
		.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
		.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
		.accumulate("lifePhotoFilePath", lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
		.accumulate("inputId", customer.getInputId())
		.accumulate("countryCode", country.getCountryCode())
		;
		return object.toString();
	}

	/**
	 * 第三方登录
	 * 
	 * @return
	 */
	public String thirdPartyLogin(String unionId, String sign) {
		
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { "unionId" }))) {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}
		List<AccountAssociated> accountList = this.accountAssociatedMapper.selectAccountAssociatedByUnionId(unionId);
		if (accountList.size() > 0) {
			BuyHand customer = this.buyHandMapper.selectByPrimaryKey(accountList.get(0).getBuyhandId());
			int intensity = this.getIntensityOfPassword(customer.getPassword());
			Long admNoticeFileId = customer.getAdmNotice(); // 录取通知书
			Long lifePhotoFileId = customer.getLifePhoto(); // 生活照
			Long passportFileId = customer.getPassport(); // 护照
			Long sidPhotoFileId = customer.getSidPhoto(); // 学生照
			Long headPortraitFileId = customer.getHeadPortrait();
			Long countryId= customer.getCountryId();
			Country country= this.countryMapper.selectByPrimaryKey(countryId);
			com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
			com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
			com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
			com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
			com.xa.entity.File headPortraitFile = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
			String headPortraitFileUri = null;
			if(null != headPortraitFile){
				headPortraitFileUri = headPortraitFile.getUriPath();
			}
			object.accumulate("buyHandId", customer.getId())
					.accumulate("intensity", intensity)
					.accumulate("mobile", customer.getMobile() == null ? "" : customer.getMobile())
					.accumulate("nickname", customer.getNickname() == null ? "" : customer.getNickname())
					.accumulate("gender", customer.getGender() == null ? "" : customer.getGender())
					.accumulate("headPortrait", headPortraitFileUri== null ? "" : headPortraitFileUri)
					.accumulate("admNoticeFilePath",
							admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
					.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
					.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
					.accumulate("lifePhotoFilePath",
							lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
					.accumulate("inputId", customer.getInputId())
					.accumulate("countryCode", country.getCountryCode())
					;
		} else {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "此账号未注册!");
			return object.toString();
		}
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}

	/**
	 * 验证码是否合法
	 * 
	 * @return
	 */
	public String verificationCodeAreLegal(String mobile,String vercode, String sign) {
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { 
				"mobile","vercode" 
				}))) {
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		MobileVercodeLog param = new MobileVercodeLog();
		param.setMobile(mobile);
		param.setVercode(vercode);
		List<MobileVercodeLog> mvlList = this.mobileVercodeLogMapper.getVercodeByMobile(param);
		if (mvlList.size() > 0) {
			object.accumulate(Constants.SUCCESS, true);
			return object.toString();
		} else {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "验证码错误!");
		}
		return object.toString();
	}
	
	/**
	 * 更新客户密码
	 * @return
	 */
	public String updateCustomerPassword(BuyHand customer,String oldPassword, String sign){
		int intensity = this.getIntensityOfPassword(customer.getPassword());
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			 "id","password","mobile","oldPassword"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		String oldMd5Password = EncryptionTool.addSaltEncrypt(oldPassword, Security.getPasswordSalt());
		BuyHand dbCustomer = this.m.selectByPrimaryKey(customer.getId());
		if(!oldMd5Password.equals(dbCustomer.getPassword())){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "原密码输入错误!").toString();
		}
		
		
		String md5Password = EncryptionTool.addSaltEncrypt(customer.getPassword(), Security.getPasswordSalt());
		customer.setPassword(md5Password);
		customer.setIntensity(intensity);
		this.buyHandMapper.updateByPrimaryKeySelective(customer);
		
		Long admNoticeFileId = dbCustomer.getAdmNotice(); // 录取通知书
		Long lifePhotoFileId = dbCustomer.getLifePhoto(); // 生活照
		Long passportFileId = dbCustomer.getPassport(); // 护照
		Long sidPhotoFileId = dbCustomer.getSidPhoto(); // 学生照
		Long headPortraitFileId = dbCustomer.getHeadPortrait();
		Long countryId= dbCustomer.getCountryId();
		Country country= this.countryMapper.selectByPrimaryKey(countryId);
		com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
		com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
		com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
		com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
		com.xa.entity.File headPortraitFile = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
		String headPortraitFileUri = null;
		if(null != headPortraitFile){
			headPortraitFileUri = headPortraitFile.getUriPath();
		}
		object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", dbCustomer.getId())
		.accumulate("intensity", intensity)
		.accumulate("mobile", dbCustomer.getMobile() == null ? "" : dbCustomer.getMobile())
		.accumulate("nickname", dbCustomer.getNickname() == null ? "" : dbCustomer.getNickname())
		.accumulate("gender", dbCustomer.getGender() == null ? "" : dbCustomer.getGender())
		.accumulate("headPortrait",
				headPortraitFileUri == null ? "" : headPortraitFileUri )
		.accumulate("admNoticeFilePath",
				admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
		.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
		.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
		.accumulate("lifePhotoFilePath",
				lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
		.accumulate("inputId", dbCustomer.getInputId())
		.accumulate("countryCode", country.getCountryCode())
		;
		return object.toString();
	}

	/**
	 * 更换手机号
	 * @return
	 */
	public String replacePhoneNumber(String vercode, String newMobile, String oldMobile, String sign) {
		int intensity = this.getIntensityOfPassword(newMobile);
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"vercode","newMobile","oldMobile"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		MobileVercodeLog mvParam = new MobileVercodeLog();
		mvParam.setMobile(oldMobile);
		mvParam.setVercode(vercode);
		List<MobileVercodeLog> mvlList = this.mobileVercodeLogMapper.getVercodeByMobile(mvParam);
		if(mvlList.size()>0){
			BuyHand param = new BuyHand();
			param.setMobile(oldMobile);
			 List<BuyHand> custList = this.buyHandMapper.selectCustomerByMobile(param);
			 if(custList.size() > 0){
				 BuyHand customer = custList.get(0);
				 customer.setMobile(newMobile);
				 this.buyHandMapper.updateByPrimaryKeySelective(customer);
				 Long admNoticeFileId = customer.getAdmNotice(); // 录取通知书
					Long lifePhotoFileId = customer.getLifePhoto(); // 生活照
					Long passportFileId = customer.getPassport(); // 护照
					Long sidPhotoFileId = customer.getSidPhoto(); // 学生照
					Long headPortraitFileId = customer.getHeadPortrait(); //头像
					Long countryId= customer.getCountryId();
					Country country = this.countryMapper.selectByPrimaryKey(countryId);
					com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
					com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
					com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
					com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
					com.xa.entity.File headPortraitFile = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
					String headPortraitFileUriPath = null;
					if(null != headPortraitFile){
						headPortraitFileUriPath = headPortraitFile.getUriPath();
					}
					
					object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", customer.getId())
					.accumulate("intensity", intensity)
					.accumulate("mobile", newMobile == null ? "" : newMobile)
					.accumulate("nickname", customer.getNickname() == null ? "" : customer.getNickname())
					.accumulate("gender", customer.getGender() == null ? "" : customer.getGender())
					.accumulate("headPortrait",
							headPortraitFileUriPath == null ? "" : headPortraitFileUriPath)
					.accumulate("admNoticeFilePath",
							admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
					.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
					.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
					.accumulate("lifePhotoFilePath",
							lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
					.accumulate("inputId", customer.getInputId())
					.accumulate("countryCode", country.getCountryCode())
					;
					return object.toString();
			 }else{
				 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "不存在此手机号!").toString();
			 }
		}else{
			object.accumulate(Constants.MSG, "手机或验证码已经过期!");
		}
		
		
		return object.accumulate(Constants.SUCCESS, false).toString();
	}
	
	/**
	 * 找回密码
	 * @return
	 */
	public String retrievePassword() {
		JSONObject object = new JSONObject();
		return object.toString();
	}
	
	/**
	 * 新手机号
	 * @return
	 */
	public String newMobile(String mobile, String vercode,Long buyHandId, String sign) {
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"mobile","vercode","buyHandId"	
		}))){
		  return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();	
		}
		MobileVercodeLog param = new MobileVercodeLog();
		param.setMobile(mobile);
		param.setVercode(vercode);
		List<MobileVercodeLog> mvlList = this.mobileVercodeLogMapper.getVercodeByMobile(param);
		if(mvlList.size()>0){
			BuyHand customer = this.buyHandMapper.selectByPrimaryKey(buyHandId);
			customer.setMobile(mobile);
			this.buyHandMapper.updateByPrimaryKeySelective(customer);
			
			
			Long admNoticeFileId = customer.getAdmNotice(); // 录取通知书
			Long lifePhotoFileId = customer.getLifePhoto(); // 生活照
			Long passportFileId = customer.getPassport(); // 护照
			Long sidPhotoFileId = customer.getSidPhoto(); // 学生照
			Long headPortraitFileId = customer.getHeadPortrait(); //头像
			Long countryId= customer.getCountryId();
			Country country= this.countryMapper.selectByPrimaryKey(countryId);
			com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
			com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
			com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
			com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
			com.xa.entity.File headPortraitFile = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
			String headPortraitFileUriPath = null;
			if(null != headPortraitFile){
				headPortraitFileUriPath = headPortraitFile.getUriPath();
			}
		 
		 object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", customer.getId())
			.accumulate("intensity", customer.getIntensity())
			.accumulate("mobile", customer.getMobile() == null ? "" : customer.getMobile())
			.accumulate("nickname", customer.getNickname() == null ? "" : customer.getNickname())
			.accumulate("gender", customer.getGender() == null ? "" : customer.getGender())
			.accumulate("headPortrait",
					headPortraitFileUriPath == null ? "" : headPortraitFileUriPath)
			.accumulate("admNoticeFilePath",
					admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
			.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
			.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
			.accumulate("lifePhotoFilePath",
					lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
			.accumulate("inputId", customer.getInputId())
			.accumulate("countryCode", country.getCountryCode())
			;
			
			return object.toString();
		}else {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "验证码输入错误!");
		}
		return object.toString();
	}
	
	/**
	 * 是否绑定第三方
	 * @return
	 */
	public String isBoundThirdParty(String unionId,Long buyHandId,String sign) {
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"unionId","buyHandId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		AccountAssociated param = new AccountAssociated();
		param.setUnionId(unionId);
		param.setBuyhandId(buyHandId);
		List<AccountAssociated> acList = this.accountAssociatedMapper.selectAssociatedByBuyHandIdAndUnionId(param );
		if(acList.size() > 0){
			return object.accumulate(Constants.SUCCESS, true).toString();
		}else {
			return object.accumulate(Constants.SUCCESS, false).toString();
		}
	}
	
	/**
	 * 修改密码[登录时]
	 * @return
	 */
	public String changePasswordWhenLogin(BuyHand customer, String vercode,  String sign) {
		int intensity = this.getIntensityOfPassword(customer.getPassword());
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"mobile","vercode","password"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		MobileVercodeLog param = new MobileVercodeLog();
		param.setMobile(customer.getMobile());
		param.setVercode(vercode);
		List<MobileVercodeLog> mvlList = this.mobileVercodeLogMapper.getVercodeByMobile(param);
		if(mvlList.size()>0){
			String password = EncryptionTool.addSaltEncrypt(customer.getPassword(), Security.getPasswordSalt());
			 List<BuyHand> custList = this.buyHandMapper.selectCustomerByMobile(customer);
			 if(null != custList && custList.size() > 0){
				 BuyHand resultCustomer = custList.get(0);
				 resultCustomer.setPassword(password);
				 this.buyHandMapper.updateByPrimaryKeySelective(resultCustomer);
				 
				 
				 Long admNoticeFileId = resultCustomer.getAdmNotice(); // 录取通知书
					Long lifePhotoFileId = resultCustomer.getLifePhoto(); // 生活照
					Long passportFileId = resultCustomer.getPassport(); // 护照
					Long sidPhotoFileId = resultCustomer.getSidPhoto(); // 学生照
					Long headPortraitFileId = resultCustomer.getHeadPortrait(); //头像
					Long countryId= resultCustomer.getCountryId();
					Country country= this.countryMapper.selectByPrimaryKey(countryId);
					com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
					com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
					com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
					com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
					com.xa.entity.File headPortraitFile = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
					String headPortraitFileUriPath = null;
					if(null != headPortraitFile){
						headPortraitFileUriPath = headPortraitFile.getUriPath();
					}
				 
				 object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", resultCustomer.getId())
					.accumulate("intensity", intensity)
					.accumulate("mobile", resultCustomer.getMobile() == null ? "" : resultCustomer.getMobile())
					.accumulate("nickname", resultCustomer.getNickname() == null ? "" : resultCustomer.getNickname())
					.accumulate("gender", resultCustomer.getGender() == null ? "" : resultCustomer.getGender())
					.accumulate("headPortrait",
							headPortraitFileUriPath == null ? "" : headPortraitFileUriPath)
					.accumulate("admNoticeFilePath",
							admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
					.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
					.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
					.accumulate("lifePhotoFilePath",
							lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
					.accumulate("inputId", resultCustomer.getInputId())
					.accumulate("countryCode", country.getCountryCode())
					;
				 return object.toString();
			 }else {
				return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "此手机号不存在!").toString();
			}
		}else {
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "验证码输入错误!");
		}
		return object.toString();
	}
	
	/**
	 * 设置支付密码
	 * @return
	 */
	public String setPayPassword(BuyHand customer, String sign) {
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"payPassword","id"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		customer.setPayPassword(EncryptionTool.addSaltEncrypt(customer.getPayPassword(), Security.getPasswordSalt()));
		this.buyHandMapper.updateByPrimaryKeySelective(customer);
		return object.accumulate(Constants.SUCCESS, true).toString();
	}
	
	/**
	 * 更换手机号
	 * @return
	 */
	public String replacePhoneNumber(BuyHand customer, String sign) {
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"inputId","passportNo","email","mobile","password","id"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		
		customer.setPassword(EncryptionTool.addSaltEncrypt(customer.getPassword(), Security.getPasswordSalt()));
		List<BuyHand> cuList = this.buyHandMapper.selectCustomerByEmailAndInputIdAndPassport(customer);
		if(cuList.size() > 0){
			BuyHand resultCustomer = cuList.get(0);
			 resultCustomer.setMobile(customer.getMobile());
			 this.buyHandMapper.updateByPrimaryKeySelective(resultCustomer);
			 Long admNoticeFileId = resultCustomer.getAdmNotice(); // 录取通知书
				Long lifePhotoFileId = resultCustomer.getLifePhoto(); // 生活照
				Long passportFileId = resultCustomer.getPassport(); // 护照
				Long sidPhotoFileId = resultCustomer.getSidPhoto(); // 学生照
				Long headPortraitFileId = resultCustomer.getHeadPortrait(); //头像
				Long countryId= resultCustomer.getCountryId();
				Country country= this.countryMapper.selectByPrimaryKey(countryId);				
				com.xa.entity.File admNoticeFile = this.fileMapper.selectByPrimaryKey(admNoticeFileId);
				com.xa.entity.File sidPhotoFile = this.fileMapper.selectByPrimaryKey(sidPhotoFileId);
				com.xa.entity.File passportFile = this.fileMapper.selectByPrimaryKey(passportFileId);
				com.xa.entity.File lifePhotoFile = this.fileMapper.selectByPrimaryKey(lifePhotoFileId);
				com.xa.entity.File headPortraitFile = this.fileMapper.selectByPrimaryKey(headPortraitFileId);
				String headPortraitFileUriPath = null;
				if(null != headPortraitFile){
					headPortraitFileUriPath = headPortraitFile.getUriPath();
				}
			 
				int intensity = this.getIntensityOfPassword(resultCustomer.getPassword());
			 object.accumulate(Constants.SUCCESS, true).accumulate("buyHandId", resultCustomer.getId())
				.accumulate("intensity", intensity)
				.accumulate("mobile", resultCustomer.getMobile() == null ? "" : resultCustomer.getMobile())
				.accumulate("nickname", resultCustomer.getNickname() == null ? "" : resultCustomer.getNickname())
				.accumulate("gender", resultCustomer.getGender() == null ? "" : resultCustomer.getGender())
				.accumulate("headPortrait",
						headPortraitFileUriPath == null ? "" : headPortraitFileUriPath)
				.accumulate("admNoticeFilePath",
						admNoticeFile.getUriPath() == null ? "" : admNoticeFile.getUriPath())
				.accumulate("sidPhotoFilePath", sidPhotoFile.getUriPath() == null ? "" : sidPhotoFile.getUriPath())
				.accumulate("passportFilePath", passportFile.getUriPath() == null ? "" : passportFile.getUriPath())
				.accumulate("lifePhotoFilePath",
						lifePhotoFile.getUriPath() == null ? "" : lifePhotoFile.getUriPath())
				.accumulate("inputId", resultCustomer.getInputId())
				.accumulate("countryCode", country.getCountryCode())
				;
			 return object.toString();
		}else {
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "不存在此用户！").toString();
		}
	}
}
