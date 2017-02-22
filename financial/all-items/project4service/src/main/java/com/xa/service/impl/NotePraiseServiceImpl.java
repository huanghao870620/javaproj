package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.NotePraise;
import com.xa.mapper.NotePraiseMapper;
import com.xa.service.NotePraiseService;
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
public class NotePraiseServiceImpl extends BaseServiceImpl<NotePraise, NotePraiseMapper> implements NotePraiseService<NotePraise> {

	/**
	 * 点赞
	 */
	public String addNotePraise(NotePraise notePraise,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"noteId","buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		  this.m.insert(notePraise);
		  object.accumulate(Constants.SUCCESS, true);
		  return object.toString();
	}
	
	/**
	 * 取消点赞
	 * @param notePraise
	 * @param sign
	 * @return
	 */
	public String disabPraise(NotePraise notePraise, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"noteId","buyerId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		this.m.deleteByNoteIdAndBuyerId(notePraise);
		return object.toString();
	}
	
}
