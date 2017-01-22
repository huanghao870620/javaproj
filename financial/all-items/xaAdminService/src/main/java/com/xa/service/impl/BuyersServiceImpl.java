package com.xa.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.exceptions.IbatisException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Buyers;
import com.xa.mapper.BuyersMapper;
import com.xa.service.BuyersService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class BuyersServiceImpl extends BaseServiceImpl<Buyers, BuyersMapper> implements BuyersService<Buyers> {

	
	/**
	 * 获取买家信息
	 * @return
	 */
	public String getBuyers(Integer pageNum,Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize,true);
		Page<Buyers> buyerPage=(Page<Buyers>) this.m.findAll();
		List<Buyers> buyers= buyerPage.getResult();
		JSONArray array = new JSONArray();
		for (int i = 0; i < buyers.size(); i++) {
			   JSONObject buyerObj = new JSONObject();
			   Buyers buyer= buyers.get(i);
			   Long id= buyer.getId();
			   Date birthday= buyer.getBirthday();
			   String birthdayStr = null;
			   if(null != birthday){
				   birthdayStr = DateFormatUtils.format(birthday, "yyyy-MM-dd HH:mm:ss");
			   }
			   buyer.getBuyerType();
			   String gender= buyer.getGender();
			   String mobile= buyer.getMobile();
			   String nickName= buyer.getNickname();
			   String signature= buyer.getSignature();
			   buyerObj.accumulate("id", id)
			   .accumulate("gender", gender)
			   .accumulate("mobile", mobile)
			   .accumulate("nickName", nickName)
			   .accumulate("birthday", birthdayStr == null ? "" : birthdayStr)
			   .accumulate("signature", signature)
			   ;
			   array.add(buyerObj);
		}
		 object.accumulate(Constants.TOTAL, buyerPage.getTotal())
		.accumulate(Constants.ROWS, array);
		return object.toString();
	}
	
}
