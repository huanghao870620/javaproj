package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.Area;
import com.xa.entity.DeliveryAddress;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.service.AreaService;
import com.xa.service.DeliveryAddressService;
import com.xa.service.FileService;
import com.xa.service.GoodsService;

@Controller
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController extends BaseController {

	
	@Autowired
	private DeliveryAddressService<DeliveryAddress> deliveryAddressService;
	
	@Autowired
	private FileService<File> fileService;
	
	@Autowired
	private AreaService<Area> areaService;

	/**
	 * 添加收货地址
	 * @param address
	 * @param sign
	 */
	@RequestMapping("addDeliveryAddress")
	public void addDeliveryAddress(DeliveryAddress address,
			@RequestParam(value="idCardFrontFile",required=false) MultipartFile idCardFrontFile,
			@RequestParam(value="idCardBackFile",required=false) MultipartFile idCardBackFile,
			String sign) {
		 try {
			this.sendAjaxMsg(this.deliveryAddressService.addDeliveryAddress(address,
					idCardFrontFile,
					idCardBackFile,
					fileService,
					this.request,
					sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有地址
	 * @param random
	 * @param sign
	 */
	@RequestMapping("getAllAddress")
	public void getAllAddress(Long buyerId, String sign){
		try {
			this.sendAjaxMsg(this.deliveryAddressService.getAllAddress(buyerId,this.areaService, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设为默认地址
	 * @param addressId
	 * @param buyerId
	 * @param sign
	 */
	@RequestMapping("setAddressDefault")
	public void setAddressDefault(Long addressId,Long buyerId, String sign){
		 try {
			this.sendAjaxMsg(this.deliveryAddressService.setAddressDefault(addressId, buyerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除地址
	 * @param id
	 * @param sign
	 */
	@RequestMapping("removeAddress")
	public void removeAddress(Long id,String sign){
		try {
			this.sendAjaxMsg(this.deliveryAddressService.removeAddress(id, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 修改地址信息
	 * @param address
	 * @param sign
	 */
	@RequestMapping("updateAddress")
	public void updateAddress(DeliveryAddress address,String sign){
	    try {
			this.sendAjaxMsg(this.deliveryAddressService.updateAddress(address, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	

	/**
	 * 获取默认地址
	 * @param buyerId
	 * @param sign
	 */
	@RequestMapping("getDefaultAddress")
	public void getDefaultAddress(Long buyerId, String sign){
		try {
			this.sendAjaxMsg(this.deliveryAddressService.getDefaultAddress(buyerId, areaService, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
