package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.Area;
import com.xa.entity.DeliveryAddress;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.service.BaseServiceInte;
import com.xa.service.FileService;

public interface DeliveryAddressService<T> extends BaseServiceInte<T> {

	/**
	 * 添加收货地址
	 * @param address
	 * @param sign
	 * @return
	 */
	public String addDeliveryAddress(DeliveryAddress address,
			MultipartFile idCardFrontFile,
			MultipartFile idCardBackFile,
			FileService<File> fileService,
			HttpServletRequest request,
			String sign) throws IllegalStateException, IOException;
	
	/**
	 * 获取所有地址
	 * @param random
	 * @param sign
	 * @return
	 */
	public String getAllAddress(Long buyerId,AreaService<Area> areaService, String sign);
	
	/**
	 * 设为默认地址
	 * @param addressId
	 * @param buyerId
	 * @param sign
	 * @return
	 */
	public String setAddressDefault(Long addressId,Long buyerId, String sign);
	
	/**
	 * 删除地址
	 * @param id
	 * @param sign
	 * @return
	 */
	public String removeAddress(Long id,String sign);
	
	/**
	 * 修改地址
	 * @param address
	 * @param sign
	 * @return
	 */
	public String updateAddress(DeliveryAddress address,
			MultipartFile idCardFrontFile, 
			MultipartFile idCardBackFile,
			FileService<File> fileService,
			String sign) throws IllegalStateException, IOException;
	
	/**
	 * 获取默认地址
	 * @param buyerId
	 * @param areaService
	 * @param sign
	 * @return
	 */
	public String getDefaultAddress(Long buyerId, AreaService<Area> areaService,String sign);
}
