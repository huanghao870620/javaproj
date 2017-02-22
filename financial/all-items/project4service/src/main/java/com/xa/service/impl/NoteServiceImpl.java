package com.xa.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Buyers;
import com.xa.entity.File;
import com.xa.entity.Note;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BuyersMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.NoteMapper;
import com.xa.mapper.NotePraiseMapper;
import com.xa.service.FileService;
import com.xa.service.NoteService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class NoteServiceImpl extends BaseServiceImpl<Note, NoteMapper> implements NoteService<Note> {

	@Autowired
	private NoteMapper noteMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private NotePraiseMapper notePraiseMapper;
	
	@Autowired
	private BuyersMapper buyersMapper;
	
	/**
	 * 
	 * @param note
	 * @param sign
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String addNote(Note note, MultipartFile imgFile, String sign,FileService<File> fileService) throws IllegalStateException, IOException{
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"title","content","classId","buyerId","imgFile"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		if(imgFile.getSize() > 0){
			File file = new File();
			fileService.uploadFile(imgFile, PhotoType.NOTE_IMG, file );
			note.setImgId(file.getId());
		}
		
		
		this.m.insert(note);
		return object.toString();
	}
	
	/**
	 * 获取晒物信息
	 * @param pageNum
	 * @param pageSize
	 * @param classId
	 * @return
	 */
	public String getNoteByClassId(Integer pageNum, Integer pageSize, Long classId,String sign){
		 JSONObject object = new JSONObject();
		  if(!sign.equals(Security.getSign(new String[]{
				  "pageNum","pageSize","classId"
		  }))){
			  return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		  }
		  PageHelper.startPage(pageNum, pageSize,true);
		  Page<Note> notePage=(Page<Note>) this.m.getNoteByClassId(classId);
		  List<Note> noteList=notePage.getResult();
		  JSONArray array = new JSONArray();				  
		  for(int i=0;i<noteList.size();i++){
			   Note note= noteList.get(i);
			   JSONObject noteObj = new JSONObject();
			   Date addTime= note.getAddTime();
			   Long clssId= note.getClassId();
			   String content= note.getContent();
			   Long imgId= note.getImgId();
			   String title= note.getTitle();
			   Long buyerId= note.getBuyerId();
			   Buyers buyer= this.buyersMapper.selectByPrimaryKey(buyerId);
			   Long hpId= buyer.getHeadPortrait();
			   File hpFile= this.fileMapper.selectByPrimaryKey(hpId); 
			   String hpUriPath= hpFile.getUriPath();
			   String nickName= buyer.getNickname();
			   File file= this.fileMapper.selectByPrimaryKey(imgId);
			   Long praiseCount= this.notePraiseMapper.getPraiseCountByNoteId(note.getId());
			   String uriPath= file.getUriPath();
			   noteObj.accumulate("title", title)
			   .accumulate("content", content)
			   .accumulate("uriPath", uriPath)
			   .accumulate("praiseCount", praiseCount)
			   .accumulate("hpUriPath", hpUriPath)
			   .accumulate("nickName", nickName)
			   ;
			   array.add(noteObj);
		  }
		  object.accumulate(Constants.SUCCESS, true)
		  .accumulate(Constants.TOTAL, notePage.getTotal())
		  .accumulate(Constants.ROWS, array)
		  ;
		 return object.toString();
	}

	
	
	public void clickPraise(){
		
	}
	
}
