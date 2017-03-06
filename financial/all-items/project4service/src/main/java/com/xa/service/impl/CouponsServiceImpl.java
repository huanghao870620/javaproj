package com.xa.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Coupons;
import com.xa.enumeration.AllocCouponType;
import com.xa.mapper.CouponsMapper;
import com.xa.service.CouponsService;
import com.xa.service.impl.BaseServiceImpl;
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
public class CouponsServiceImpl extends BaseServiceImpl<Coupons, CouponsMapper> implements CouponsService<Coupons> {

	/**
	 * 
	 * @return
	 */
	public String getCouponsByExchange(String random, String sign) {
		JSONObject object = new JSONObject();
		if (!sign.equals(Security.getSign(new String[] { "random" }))) {
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		List<Coupons> coupons = this.m.findByAllocType(AllocCouponType.EXCHANGE.getValue()); // 兑换优惠券
		JSONArray array = new JSONArray();
		for (int i = 0; i < coupons.size(); i++) {
			JSONObject couponsObj = new JSONObject();
			Coupons c = coupons.get(i);
			Float sill = c.getSill();
			Float price = c.getPrice();
			Long id = c.getId();

			Date lStart = c.getLimitStart();
			Date lEnd = c.getLimitEnd();
			Long scoreSill = c.getScoreSill();
			String name = c.getName();

			String lStartStr = DateFormatUtils.format(lStart, Constants.COMMON_DATE_FORMAT);
			String lEndStr = DateFormatUtils.format(lEnd, Constants.COMMON_DATE_FORMAT);
			couponsObj.accumulate("sill", sill)
					.accumulate("price", price)
					.accumulate("lStartStr", lStartStr)
					.accumulate("lEndStr", lEndStr)
					.accumulate("scoreSill", scoreSill)
					.accumulate("name", name)
					.accumulate("id", id);
			array.add(couponsObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
}
