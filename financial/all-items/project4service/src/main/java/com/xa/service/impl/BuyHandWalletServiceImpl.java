package com.xa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.BuyhandWallet;
import com.xa.mapper.BuyhandWalletMapper;
import com.xa.service.BuyhandWalletService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class BuyHandWalletServiceImpl extends BaseServiceImpl<BuyhandWallet, BuyhandWalletMapper> implements BuyhandWalletService<BuyhandWallet> {

	@Autowired
	private BuyhandWalletMapper buyhandWalletMapper;
	
	/**
	 *  获取客户余额
	 */
	public String getBalance(Long buyHandId, String sign){
		   JSONObject object = new JSONObject();
		   if(!sign.equals(Security.getSign(new String[]{
				   "buyHandId"
		   }))){
			   object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			   return object.toString();
		   }
		   
		   BuyhandWallet wallet = this.buyhandWalletMapper.getWalletByBuyHandId(buyHandId);
		   if(null == wallet){
			 object.accumulate("success", true).accumulate("balance", 0.00);   
			 return object.toString();
		   }else {
			
		   }
		   object.accumulate("success", true)
		    .accumulate("balance", wallet.getBalance());
		   return object.toString();
	}
	
//	public String withdrawal() {
//		
//	}
}
