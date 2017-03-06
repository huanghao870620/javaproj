package com.xa.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.type.TimeOnlyTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Coupons;
import com.xa.entity.CouponsBuyer;
import com.xa.entity.Integral;
import com.xa.enumeration.CouponsState;
import com.xa.mapper.CouponsBuyerMapper;
import com.xa.mapper.CouponsMapper;
import com.xa.mapper.IntegralMapper;
import com.xa.service.IntegralService;
import com.xa.util.Constants;
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
public class IntegralServiceImpl extends BaseServiceImpl<Integral, IntegralMapper>
		implements IntegralService<Integral> {
	
	@Autowired
	private CouponsMapper couponsMapper;
	
	@Autowired
	private CouponsBuyerMapper couponsBuyerMapper;
	

	/**
	 * 獲取積分
	 */
	public String getIntegralByBuyerId(Long buyerId,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Integral integral= this.m.getIntegralByUserId(buyerId);
		Long score= integral.getScore();
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("score", score)
		;
		return object.toString();
	}
	
	/**
	 * 兌換優惠券
	 * @param sign
	 * @return
	 */
	public String exchangeCoupons(Long couponsId,Long buyerId,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"couponsId","buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Integral integral=this.m.getIntegralByUserId(buyerId);
		if(integral == null){
			integral = new Integral();
			integral.setBuyerId(buyerId);
			integral.setScore(0L);
			this.m.insert(integral);
		}
		Long score= integral.getScore();
		Coupons coupons= this.couponsMapper.selectByPrimaryKey(couponsId);
		Long scoreSill= coupons.getScoreSill();
		if(score >= scoreSill){
			score = score - scoreSill;
			integral.setScore(score);
			this.m.updateByPrimaryKeySelective(integral);
			
			CouponsBuyer cb = new CouponsBuyer();
			cb.setCouponsId(couponsId);
			cb.setBuyerId(buyerId);
			cb.setState(CouponsState.NOT_USE.getValue());
			cb.setAddTime(new Date());
			couponsBuyerMapper.insert(cb); 
		}else {
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, "积分不足!").toString();
		}
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 
	 * @param buyerId
	 * @param sign
	 * @return
	 */
	public String getExchangeHistory(Long buyerId, String sign){
		  JSONObject object = new JSONObject();
		  if(!sign.equals(Security.getSign(new String[]{
				  "buyerId"
		  }))){
			  return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		  }
		  List<CouponsBuyer> cbList= this.couponsBuyerMapper.getCBByBuyerId(buyerId);
		  JSONArray array = new JSONArray();
		  for(int i=0;i<cbList.size();i++){
			   JSONObject  cbObject = new JSONObject();
			   CouponsBuyer cb= cbList.get(i);
			   Date addTime= cb.getAddTime();
			   Long cId= cb.getCouponsId();
			   Coupons coupon= this.couponsMapper.selectByPrimaryKey(cId);
				Float sill = coupon.getSill();
				Float price = coupon.getPrice();
				Long id = coupon.getId();

				Date lStart = coupon.getLimitStart();
				Date lEnd = coupon.getLimitEnd();
				Long scoreSill = coupon.getScoreSill();
				String name = coupon.getName();
				
				String addTimeStr = DateFormatUtils.format(addTime, Constants.EXCHANGE_HIS_FORMAT);
				String lStartStr = DateFormatUtils.format(lStart, Constants.COMMON_DATE_FORMAT);
				String lEndStr = DateFormatUtils.format(lEnd, Constants.COMMON_DATE_FORMAT);
				cbObject.accumulate("sill", sill)
						.accumulate("price", price)
						.accumulate("lStartStr", lStartStr)
						.accumulate("lEndStr", lEndStr)
						.accumulate("scoreSill", scoreSill)
						.accumulate("name", name)
						.accumulate("id", id)
						.accumulate("addTime", addTimeStr)
						;
				array.add(cbObject);
		  }
		  object.accumulate(Constants.SUCCESS, true)
		  .accumulate(Constants.DATA, array)
		  ;
		  return object.toString();
	}
}
