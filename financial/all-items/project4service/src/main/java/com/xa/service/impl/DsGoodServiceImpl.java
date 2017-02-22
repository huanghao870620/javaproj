package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.DsGood;
import com.xa.mapper.DsGoodMapper;
import com.xa.service.DsGoodService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class DsGoodServiceImpl extends BaseServiceImpl<DsGood, DsGoodMapper> implements DsGoodService<DsGood> {

	/**
	 * 获取商品库存
	 * @param dgId
	 * @param sign
	 * @return
	 */
	public String getDsGood(Long dgId, String sign){
		  JSONObject object = new JSONObject();
		  if(!sign.equals(Security.getSign(new String[]{
				  "dgId"
		  }))){
			  return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		  }
		  DsGood dsGood= this.m.selectByPrimaryKey(dgId);
		  dsGood.getDsId();
		  dsGood.getGoodId();
		  Integer inventroy= dsGood.getInventory();
		  object.accumulate("inventroy", inventroy)
		  .accumulate(Constants.SUCCESS, true)
		  ;
		  return object.toString();
	}
}
