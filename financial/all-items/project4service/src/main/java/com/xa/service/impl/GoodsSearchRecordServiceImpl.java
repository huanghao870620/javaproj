package com.xa.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import com.xa.entity.GoodsSearchRecord;
import com.xa.mapper.GoodsSearchRecordMapper;
import com.xa.service.GoodsSearchRecordService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class GoodsSearchRecordServiceImpl extends BaseServiceImpl<GoodsSearchRecord, GoodsSearchRecordMapper>
		implements GoodsSearchRecordService<GoodsSearchRecord> {

	
	public void addSearchRecord(GoodsSearchRecord record){
		record.setAddTime(new Date());
		this.m.insert(record);
	}
	
	
	public String delGSR(Long buyerId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		this.m.deleteGSRByBuyerId(buyerId);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
}
