package com.xa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.NoteColl;
import com.xa.mapper.NoteCollMapper;
import com.xa.service.NoteCollService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class NoteCollServiceImpl extends BaseServiceImpl<NoteColl, NoteCollMapper>
		implements NoteCollService<NoteColl> {

	
	public String addNoteColl(NoteColl noteColl,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				""
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		 this.m.insert(noteColl);
		 object.accumulate(Constants.SUCCESS, true);
		 return object.toString();
	}
}
