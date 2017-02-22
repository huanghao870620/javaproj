package com.xa.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.convert.DatePropertyEditor;
import com.xa.entity.AllocType;
import com.xa.entity.Brand;
import com.xa.entity.Country;
import com.xa.entity.Coupons;
import com.xa.service.AllocTypeService;
import com.xa.service.BrandService;
import com.xa.service.CountryService;
import com.xa.service.CouponsService;
import com.xa.util.Constants;

/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/coupons")
public class CouponController extends BaseController {

	@Autowired
	private CouponsService<Coupons> couponsService;	
	
	@Autowired
	private BrandService<Brand> brandService;
	
	@Autowired
	private CountryService<Country> countryService;
	
	@Autowired
	private AllocTypeService<AllocType> allocTypeService;
	
	/**
	 * 跳转到添加优惠券页面
	 * @param coupon
	 * @return
	 */
	@RequestMapping("toAddCoupons")
	public ModelAndView toAddCoupons(){
		ModelAndView modelAndView = new ModelAndView("coupons/addCoupon");
		this.brandService.getBrands(modelAndView);
		this.countryService.getAllCountry(modelAndView);
		this.allocTypeService.getAllAllocType(modelAndView);
		return modelAndView;
	}
	
	/**
	 * 添加优惠券
	 * @param coupon
	 * @return
	 */
	@RequestMapping("addCoupons")
	public ModelAndView addCoupons(Coupons coupon){
		ModelAndView modelAndView = new ModelAndView();
		this.couponsService.addCoupons(modelAndView, coupon);
		return modelAndView;
	}
	
	/**
	 * 跳转到优惠券列表页面
	 * @return
	 */
	@RequestMapping("toListCoupons")
	public ModelAndView toListCoupons(){
		ModelAndView modelAndView = new ModelAndView("coupons/couponList");
		return modelAndView;
	}
	
	/**
	 * 列出优惠券
	 */
	@RequestMapping("listCoupons")
	public void listCoupons(Integer page,Integer rows){
		try {
			this.sendAjaxMsg(this.couponsService.getAllCoupons(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 编辑优惠券
	 * @return
	 */
	@RequestMapping("editCoupons")
	public ModelAndView editCoupons(Coupons coupons){
		ModelAndView modelAndView = new ModelAndView("redirect:toListCoupons.htm");
		this.couponsService.editCoupons(coupons);
		return modelAndView;
	}
	

	/**
	 * 去编辑优惠券
	 * @return
	 */
	@RequestMapping("toEditCoupons")
	public ModelAndView toEditCoupons(Long id){
		 ModelAndView modelAndView = new ModelAndView("coupons/editCoupon");
		 this.couponsService.getCoupons(modelAndView, id);
		 this.brandService.getBrands(modelAndView);
		 this.countryService.getAllCountry(modelAndView);
		 this.allocTypeService.getAllAllocType(modelAndView);
		 return modelAndView;
	}
	
	
	@InitBinder
	 protected void initBinder(HttpServletRequest request,
	    ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor(Constants.DEFAULT_DATE_FORMAT));
	 }
}
