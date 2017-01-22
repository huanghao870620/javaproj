package com.xa.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.xa.entity.AccountTransactionRecords;
import com.xa.mapper.AccountTransactionRecordsMapper;
import com.xa.service.AccountTransactionRecordsService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 钱包记录
 * @author admin
 *
 */
@Service
@Transactional
public class AccountTransactionRecordsServiceImpl
		extends BaseServiceImpl<AccountTransactionRecords, AccountTransactionRecordsMapper>
		implements AccountTransactionRecordsService<AccountTransactionRecords> {

	@Autowired
	private AccountTransactionRecordsMapper accountTransactionRecordsMapper;
	
	/**
	 * 获取钱包日志分页记录
	 * @return
	 */
	public String getRecordsByPaging(int pageNum, int pageSize, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"pageNum","pageSize"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		PageHelper.startPage(pageNum, pageSize, true);
		List<AccountTransactionRecords> atrList = this.accountTransactionRecordsMapper.findAll();
		JSONArray atrArray = new JSONArray();
		for(int i=0; i<atrList.size();i++){
			AccountTransactionRecords atr = atrList.get(i);
			JSONObject atrObj = new JSONObject();
			Long atrId = atr.getId();
			Long money = atr.getMoney();
			String note = atr.getNote();
			Date operationDate = atr.getOperationDate();
			Long typeId = atr.getTypeId();
			Long walletId = atr.getWalletId();
			atrObj.accumulate("atrId", atrId)
			.accumulate("money", money)
			.accumulate("note", note)
			.accumulate("operationDate", operationDate)
			.accumulate("typeId", typeId)
			.accumulate("walletId", walletId);
			atrArray.add(atrObj);
		}
		object.accumulate("success", true).accumulate("data", atrArray);
		return object.toString();
	}
}
