package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.BuyerFocusOn;
import com.xa.mapper.BuyerFocusOnMapper;
import com.xa.service.BuyerFocusOnService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class BuyerFocusOnServiceImpl extends BaseServiceImpl<BuyerFocusOn, BuyerFocusOnMapper>
		implements BuyerFocusOnService<BuyerFocusOn> {

	public String addBuyerFocusOn(BuyerFocusOn buyerFocusOn,String sign){
		  JSONObject object = new JSONObject();
		  if(!sign.equals(Security.getSign(new String[]{
				  ""
		  }))){
			  return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		  }
		  this.m.insert(buyerFocusOn);
		  object.accumulate(Constants.SUCCESS, true);
		  return object.toString();
	}
}
