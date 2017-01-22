package com.xa.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.AllocType;
import com.xa.entity.Coupons;
import com.xa.mapper.AllocTypeMapper;
import com.xa.mapper.CouponsMapper;
import com.xa.service.CouponsService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class CouponsServiceImpl extends BaseServiceImpl<Coupons, CouponsMapper> implements CouponsService<Coupons> {

	
	@Autowired
	private AllocTypeMapper allocTypeMapper;
	
	/**
	 * 添加优惠券
	 */
	public void addCoupons(ModelAndView modelAndView, Coupons coupon){
		 this.m.insert(coupon);
		 modelAndView.setViewName("redirect:toListCoupons.htm");
	}
	
	/**
	 * 列出优惠券
	 * @return
	 */
	public String getAllCoupons(Integer pageNum, Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize, true);
		Page<Coupons> coupons = (Page<Coupons>)this.m.findAll();
		List<Coupons> couponList= coupons.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<couponList.size(); i++){
			JSONObject couponObj = new JSONObject();
			 Coupons coupon= couponList.get(i);
			 Long id= coupon.getId();
			 float price= coupon.getPrice();
			 String note=coupon.getNote();
			 float sill= coupon.getSill();
			 Integer state= coupon.getState();
			 Date limitStart= coupon.getLimitStart();
			 Date limitEnd= coupon.getLimitEnd();
			 Long allocTypeId= coupon.getAllocTypeId();
			 AllocType allocType= this.allocTypeMapper.selectByPrimaryKey(allocTypeId);
			 String allocTypeName= allocType.getName();
			 String name= coupon.getName();
			 String lStart= DateFormatUtils.format(limitStart, "yyyy-MM-dd HH:mm:ss");
			 String lEnd= DateFormatUtils.format(limitEnd, "yyyy-MM-dd HH:mm:ss");
			 couponObj.accumulate("id", id)
			 .accumulate("price", price)
			 .accumulate("name", name)
			 .accumulate("allocTypeName", allocTypeName)
			 .accumulate("note", note)
			 .accumulate("sill", sill)
			 .accumulate("lStart", lStart)
			 .accumulate("lEnd", lEnd)
			 ;
			 array.add(couponObj);
		}
		object.accumulate(Constants.SUCCESS, true)
		.accumulate(Constants.TOTAL, coupons.getTotal())
		.accumulate(Constants.ROWS, array)
		;
		return object.toString();
	}
	
	/**
	 * 编辑优惠券
	 */
	public void editCoupons(Coupons coupons){
		 this.m.updateByPrimaryKeySelective(coupons);
	}
	
	/**
	 * 
	 * @param modelAndView
	 * @param id
	 */
	public void getCoupons(ModelAndView modelAndView, Long id){
		  Coupons coupons= this.m.selectByPrimaryKey(id);
		  modelAndView.addObject("coupons", coupons);
		  
		  Date limitStart= coupons.getLimitStart();
		  Date limitEnd= coupons.getLimitEnd();
		  String lStart= DateFormatUtils.format(limitStart, "yyyy-MM-dd HH:mm:ss");
		  String lEnd= DateFormatUtils.format(limitEnd, "yyyy-MM-dd HH:mm:ss");
		  modelAndView.addObject("lStart",lStart);
		  modelAndView.addObject("lEnd",lEnd);
	}
	
}
