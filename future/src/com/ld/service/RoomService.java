package com.ld.service;

import java.io.File;
import com.ld.dto.RoomConfigDto;
import com.ld.live.Message;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

public interface RoomService<T> extends BaseServiceInte<T> {

	public JSONObject auditMessage();
	public boolean addRoomConfig(ActionContext ctx1,RoomConfigDto dto, File file,String fileName);
	public boolean isExistRoomConfig(ActionContext ctx1);
	public boolean updateRoomConfig(ActionContext ctx1,RoomConfigDto dto, File file,String fileName);
	public void addCommonRoomMsg(Message message);
	
}
