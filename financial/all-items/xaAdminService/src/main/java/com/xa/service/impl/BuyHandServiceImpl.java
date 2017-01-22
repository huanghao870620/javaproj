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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

import net.sf.json.JSONArray;
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
	
	
	public String getBuyhands(Integer pageNum,Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize,true);
		Page<BuyHand> buyHandPage= (Page<BuyHand>) this.m.findAll();
		List<BuyHand> buyHands= buyHandPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<buyHands.size(); i++){
			  BuyHand buyHand= buyHands.get(i);
			  JSONObject buyHandObj = new JSONObject();
			  Date addTime= buyHand.getAddTime();
			   buyHand.getAdmNotice();
			  buyHand.getCountryId();
			  String email= buyHand.getEmail();
			  String gender= buyHand.getGender();
			  buyHand.getHeadPortrait();
			  String idNumber= buyHand.getIdNumber();
			  String inputId= buyHand.getInputId();
			  buyHand.getIntensity();
			  buyHand.getLifePhoto();
			  buyHand.getLifePhoto();
			  String mobile= buyHand.getMobile();
			  String nickName= buyHand.getNickname();
			  String passportNo= buyHand.getPassportNo();
			  buyHand.getQrCodeLinks();
			  String signature= buyHand.getSignature();
			  
			  buyHandObj.accumulate("email", email)
			  .accumulate("gender", gender)
			  .accumulate("idNumber", idNumber)
			  .accumulate("inputId", inputId)
			  .accumulate("mobile", mobile)
			  .accumulate("nickName", nickName)
			  .accumulate("passportNo", passportNo)
			  .accumulate("signature", signature)
			  ;
			  array.add(buyHandObj);
		}
		
		object.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, buyHandPage.getTotal());
		return object.toString();
	}

}
