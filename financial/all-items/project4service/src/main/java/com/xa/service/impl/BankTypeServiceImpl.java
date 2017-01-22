package com.xa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.BankType;
import com.xa.mapper.BankTypeMapper;
import com.xa.service.BankTypeService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class BankTypeServiceImpl extends BaseServiceImpl<BankType, BankTypeMapper>
		implements BankTypeService<BankType> {

	/**
	 * 获取所有银行类型
	 * @return
	 */
	public String getAllBank(String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		List<BankType> bankTypeList = this.m.findAll();
		JSONArray array = new JSONArray();
		for(int i=0;i<bankTypeList.size();i++){
			BankType bankType = bankTypeList.get(i);
			JSONObject btObject = new JSONObject();
			btObject.accumulate("id", bankType.getId()).accumulate("name", bankType.getName());
			array.add(btObject);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate("data", array);
		return object.toString();
	}
}
