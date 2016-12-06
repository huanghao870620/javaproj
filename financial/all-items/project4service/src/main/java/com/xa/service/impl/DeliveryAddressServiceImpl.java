package com.xa.service.impl;

import java.security.Signature;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.DeliveryAddress;
import com.xa.mapper.DeliveryAddressMapper;
import com.xa.service.DeliveryAddressService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class DeliveryAddressServiceImpl extends BaseServiceImpl<DeliveryAddress, DeliveryAddressMapper>
		implements DeliveryAddressService<DeliveryAddress> {

	
	/**
	 * 添加收货地址
	 * @return
	 */
	public String addDeliveryAddress(DeliveryAddress address,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"name","mobile","areaId","address","isDefault"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		this.m.insert(address);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
}
