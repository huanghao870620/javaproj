package com.xa.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.domain.Coupon;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.log.Log;
import com.xa.entity.Coupons;
import com.xa.entity.CouponsBuyer;
import com.xa.mapper.CouponsBuyerMapper;
import com.xa.mapper.CouponsMapper;
import com.xa.service.CouponsBuyerService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class CouponsBuyerServiceImpl extends BaseServiceImpl<CouponsBuyer, CouponsBuyerMapper> implements CouponsBuyerService<CouponsBuyer> {

	@Autowired
	private CouponsMapper couponsMapper;
	
	public String getCouponsByState(Long buyerId, Integer state,Integer pageNum, Integer pageSize, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"state","buyerId","pageNum","pageSize"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("buyerId", buyerId);
		map.put("state", state);
		
		PageHelper.startPage(pageNum, pageSize,true);
		Page<CouponsBuyer> cbPage=(Page<CouponsBuyer>) this.m.findCouponsByState(map);
		List<CouponsBuyer> cbList= cbPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<cbList.size();i++){
			JSONObject couponObj = new JSONObject();
			CouponsBuyer cb= cbList.get(i);
			Long couponsId= cb.getCouponsId();
			Coupons coupon= this.couponsMapper.selectByPrimaryKey(couponsId);
			String name= coupon.getName();
			Date lStart= coupon.getLimitStart();
			Date lEnd= coupon.getLimitEnd();
			 coupon.getClassId();
			coupon.getBrandId();
			coupon.getCountryId();
			coupon.getAllocTypeId();
			float price= coupon.getPrice();
			float sill= coupon.getSill();
			Long cbId = cb.getId();
			String lStartStr= DateFormatUtils.format(lStart, "yyyy-MM-dd HH:mm:ss");
		    String lEndStr= DateFormatUtils.format(lEnd, "yyyy-MM-dd HH:mm:ss");
//			
			couponObj.accumulate("name", name)
			.accumulate("lStartStr", lStartStr)
			.accumulate("lEndStr", lEndStr)
			.accumulate("price", price)
			.accumulate("sill", sill)
			.accumulate("cbId", cbId)
			;
			array.add(couponObj);
		}
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.TOTAL, cbPage.getTotal())
		.accumulate(Constants.ROWS, array);
		return object.toString();
	}
	
	/**
	 * 是否领取新优惠券
	 * @param sign
	 * @return
	 */
	public String ifIGetNewUserCoupons(String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				""
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		return object.toString();
	}
	
}
