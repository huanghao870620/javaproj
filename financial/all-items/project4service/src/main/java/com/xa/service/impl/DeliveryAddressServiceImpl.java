package com.xa.service.impl;

import java.io.IOException;
import java.net.SecureCacheResponse;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.Area;
import com.xa.entity.DeliveryAddress;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.AreaMapper;
import com.xa.mapper.DeliveryAddressMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.AreaService;
import com.xa.service.DeliveryAddressService;
import com.xa.service.FileService;
import com.xa.service.GoodsService;
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
public class DeliveryAddressServiceImpl extends BaseServiceImpl<DeliveryAddress, DeliveryAddressMapper>
		implements DeliveryAddressService<DeliveryAddress> {

	
	@Autowired
	private AreaMapper areaMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	/**
	 * 添加收货地址
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String addDeliveryAddress(DeliveryAddress address,
			MultipartFile idCardFrontFile, 
			MultipartFile idCardBackFile,
			FileService<File> fileService,
			HttpServletRequest request,
			String sign) throws IllegalStateException, IOException{
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"name","mobile","areaId","address","isDefault","idCardFrontFile","idCardBackFile","idcard","buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		com.xa.entity.File frontFile = new com.xa.entity.File();
		fileService.uploadFile( idCardFrontFile, PhotoType.CERTIFICATION_FRONT_PHOTO, frontFile);
		address.setCardIdFrontFile(frontFile.getId());
		
		com.xa.entity.File backFile = new com.xa.entity.File();
		fileService.uploadFile( idCardBackFile, PhotoType.CERTIFICATION_BACK_PHOTO, backFile);
		address.setCardIdBackFile(backFile.getId());
		
		this.m.insert(address);
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("id", address.getId())
		;
		return object.toString();
	}
	
	/**
	 * 获取所有地址
	 * @return
	 */
	public String getAllAddress(Long buyerId, AreaService<Area> areaService, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		List<DeliveryAddress> list= this.m.getAddressByBuyerId(buyerId);
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			DeliveryAddress address= list.get(i);
			JSONObject addressObj = new JSONObject();
			String addre= address.getAddress();
			Long id= address.getId();
			Long areaId=  address.getAreaId();
			Long backId= address.getCardIdBackFile();
			Long frontId= address.getCardIdFrontFile();
			
//			Area area= this.areaMapper.selectByPrimaryKey(areaId);
			String fullArea = areaService.getFullArea(areaId);
			File backFile= this.fileMapper.selectByPrimaryKey(backId);
			File frontFile=this.fileMapper.selectByPrimaryKey(frontId);
			
			String idcard=  address.getIdcard();
			Integer isDefault=address.getIsDefault();
			String mobile=address.getMobile();
			String name= address.getName();
			addressObj.accumulate("id", id).accumulate("areaName", fullArea).accumulate("backUriPath", backFile.getUriPath())
			.accumulate("frontUriPath", frontFile.getUriPath()).accumulate("mobile", mobile).accumulate("name", name)
			.accumulate("isDefault", isDefault).accumulate("idcard", idcard).accumulate("addre", addre);
			array.add(addressObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		 return object.toString();
	}
	
	/**
	 * 设置地址为默认
	 * @return
	 */
	public String setAddressDefault(Long addressId,Long buyerId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"addressId","buyerId"
		}))){
			 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		this.m.updateAddressIsNotDefault(buyerId);
		DeliveryAddress address= this.m.selectByPrimaryKey(addressId);
		address.setIsDefault(1);
		this.m.updateByPrimaryKeySelective(address);
		object.accumulate(Constants.SUCCESS,true);
		return object.toString();
	}
	
	/**
	 * 删除地址
	 * @param id
	 * @param sign
	 * @return
	 */
	public String removeAddress(Long id,String sign){
		 JSONObject object = new JSONObject();
		 if(!sign.equals(Security.getSign(new String[]{
			"id"	 
		 }))){
			 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		 }
		 this.m.deleteByPrimaryKey(id);
		 object.accumulate(Constants.SUCCESS, true);
		 return object.toString();
	}
	
	/**
	 * 更改地址信息
	 * @param address
	 * @param sign
	 * @return
	 */
	public String updateAddress(DeliveryAddress address,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"name",	"mobile","areaId","address","id"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		this.m.updateByPrimaryKeySelective(address);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 获取用户默认地址
	 * @return
	 */
	public String getDefaultAddress(Long buyerId, AreaService<Area> areaService,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		DeliveryAddress address= this.m.getDefaultAddressByBuyerId(buyerId);
		if(null == address){
			return object.accumulate(Constants.SUCCESS, true).accumulate(Constants.MSG, "未设置默认地址!").toString();
		}else{
			String addres= address.getAddress();
			Long areaId= address.getAreaId();
			
			Long cbf=  address.getCardIdBackFile();
			Long cff= address.getCardIdFrontFile();
			Long id= address.getId();
			String idCard= address.getIdcard();
			Integer isDefault= address.getIsDefault();
			String mobile= address.getMobile();
			String name= address.getName();
			
			String fullArea= areaService.getFullArea(areaId);
			object.accumulate("address", addres).accumulate("mobile", mobile).accumulate("name", name)
			.accumulate("isDefault", isDefault).accumulate("fullArea", fullArea)
			.accumulate("id", id)
			.accumulate(Constants.SUCCESS, true)
			;
		}
		
		return object.toString();
	}
}
