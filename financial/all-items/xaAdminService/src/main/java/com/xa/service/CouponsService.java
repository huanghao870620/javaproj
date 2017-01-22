package com.xa.service;

import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Coupons;
import com.xa.service.BaseServiceInte;

public interface CouponsService<T> extends BaseServiceInte<T> {

	/**
	 * 添加优惠券
	 * @param modelAndView
	 * @param coupon
	 */
	public void addCoupons(ModelAndView modelAndView, Coupons coupon);
	
	/**
	 * 获取优惠券
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getAllCoupons(Integer pageNum, Integer pageSize);
	
	/**
	 * 编辑优惠券
	 * @param coupons
	 */
	public void editCoupons(Coupons coupons);
	
	/**
	 * 获取优惠券
	 * @param modelAndView
	 * @param id
	 */
	public void getCoupons(ModelAndView modelAndView, Long id);
	
}
