package com.xa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.BankCard;
import com.xa.entity.BankType;
import com.xa.mapper.BankCardMapper;
import com.xa.mapper.BankCardTypeMapper;
import com.xa.mapper.BankTypeMapper;
import com.xa.service.BankCardService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class BankCardServiceImpl extends BaseServiceImpl<BankCard, BankCardMapper>
		implements BankCardService<BankCard> {
	
	@Autowired
	private BankTypeMapper bankTypeMapper;
	
	@Autowired
	private BankCardTypeMapper bankCardTypeMapper;
	

	/**
	 * 获取银行卡列表
	 * @return
	 */
	public String getBankCardList(Long buyHandId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"buyHandId"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		 List<BankCard> bcList = this.m.getCardByCustomer(buyHandId);
		 JSONArray cardArray = new JSONArray();
		 for(int i=0;i<bcList.size();i++){
			 JSONObject card = new JSONObject();
			  BankCard bc = bcList.get(i);
			  BankType bankType = this.bankTypeMapper.selectByPrimaryKey(bc.getBankTypeId());
			  card.accumulate("bankType", bankType.getName())
			  .accumulate("no", bc.getNo())
			  .accumulate("id", bc.getId())
			  ;
			  cardArray.add(card);
		 }
		 object.accumulate(Constants.SUCCESS, true).accumulate("data", cardArray);
		return object.toString();
	}
	
	/**
	 * 添加银行卡
	 * @return	
	 */
	public String addBankCard(BankCard bankCard, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"bankTypeId", "holderName", "no", "mobile","buyHandId"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		this.m.insert(bankCard);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 删除银行卡
	 * @return
	 */
	public String removeBankCard(Long cardId,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"cardId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		this.m.deleteByPrimaryKey(cardId);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
}
