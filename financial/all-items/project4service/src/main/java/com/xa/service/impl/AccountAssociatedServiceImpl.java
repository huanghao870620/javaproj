package com.xa.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.AccountAssociated;
import com.xa.entity.BuyHand;
import com.xa.mapper.AccountAssociatedMapper;
import com.xa.mapper.BuyHandMapper;
import com.xa.service.AccountAssociatedService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;
/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class AccountAssociatedServiceImpl extends BaseServiceImpl<AccountAssociated, AccountAssociatedMapper>
		implements AccountAssociatedService<AccountAssociated> {

	@Autowired
	private AccountAssociatedMapper accountAssociatedMapper;
	
	@Autowired
	private BuyHandMapper customerMapper;
	
	/**
	 * 关联账号
	 * @return
	 */
	public String associatedAccount(String unionId, String mobile, Long accountType, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"unionId","mobile","accountType"
		}))){
			 object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			 return object.toString();
		}
		List<AccountAssociated> aaList = this.accountAssociatedMapper.selectAccountAssociatedByUnionId(unionId);
		if(aaList.size() > 0){
//			object.accumulate("success", false).accumulate("msg", "此账号已经存在，不能重复关联!");
			object.accumulate(Constants.SUCCESS, true).accumulate(Constants.MSG, "绑定成功!");
			return object.toString();
		}
		BuyHand customerParam = new BuyHand();
		customerParam.setMobile(mobile);
		BuyHand customer = this.customerMapper.selectCustomerByMobile(customerParam).get(0);
		AccountAssociated aa = new AccountAssociated();
		aa.setUnionId(unionId);
		aa.setBuyHandId(customer.getId());
		aa.setAccountTypeId(accountType);
		this.accountAssociatedMapper.insert(aa);
		return object.accumulate("success", true).toString();
	}
	
	/**
	 * 解除账号关联
	 * @return
	 */
	public String dissociated(String unionId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"unionId"	
		}))){
			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION);
			return object.toString();
		}
		this.accountAssociatedMapper.dissociated(unionId);
		object.accumulate("success", true);
		return object.toString();
	}
}
