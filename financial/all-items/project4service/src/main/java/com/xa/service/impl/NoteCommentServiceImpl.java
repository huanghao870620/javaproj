package com.xa.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.Buyers;
import com.xa.entity.File;
import com.xa.entity.NoteComment;
import com.xa.mapper.BuyersMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.NoteCommentMapper;
import com.xa.service.NoteCommentService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class NoteCommentServiceImpl extends BaseServiceImpl<NoteComment, NoteCommentMapper>
		implements NoteCommentService<NoteComment> {
	
	@Autowired
	private BuyersMapper buyersMapper;
	
	@Autowired
	private FileMapper fileMapper;

	/**
	 * 
	 * @param noteComment
	 * @param sign
	 * @return
	 */
	public String addNoteComment(NoteComment noteComment,String sign){
		 JSONObject object = new JSONObject();
		 if(!sign.equals(Security.getSign(new String[]{
				 "noteId","buyerId","content"
		 }))){
			 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		 }
		 noteComment.setAddTime(new Date());
		 this.m.insert(noteComment);
		 object.accumulate(Constants.SUCCESS, true);
		 return object.toString();
	}
	
	/**
	 * 获取晒物笔记评论
	 * @param noteId
	 * @param sign
	 * @return
	 */
	public String getNoteCommentByNoteId(Long noteId, String sign) {
		 JSONObject object = new JSONObject();
		 if(!sign.equals(Security.getSign(new String[]{
				 "noteId"
		 }))){
			 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		 }
		 List<NoteComment> ncList= this.m.getNoteCommentByNoteId(noteId);
		 JSONArray array = new JSONArray();
		 for(int i=0;i<ncList.size();i++){
			 JSONObject ncObject=new JSONObject();
			 NoteComment nComment= ncList.get(i);
			 Date addTime= nComment.getAddTime();
			 Long buyerId= nComment.getBuyerId();
			 String content= nComment.getContent();
			 String addTimeStr= DateFormatUtils.format(addTime, Constants.COMMON_DATE_FORMAT);
			 Buyers buyer= this.buyersMapper.selectByPrimaryKey(buyerId);
			 Long hp= buyer.getHeadPortrait();
			 String nickName= buyer.getNickname();
			 File hpFile= this.fileMapper.selectByPrimaryKey(hp);
			 if(null != hpFile){
				 String hpUriPath= hpFile.getUriPath();
				 ncObject.accumulate("hpUriPath", hpUriPath);
			 }
			 ncObject.accumulate("content", content)
			 .accumulate("nickName", nickName)
			 .accumulate("addTime", addTimeStr)
			 ;
			 array.add(ncObject);
		 }
		 object.accumulate(Constants.SUCCESS, true)
		 .accumulate(Constants.DATA, array)
		 ;
		 return object.toString();
	}
}
