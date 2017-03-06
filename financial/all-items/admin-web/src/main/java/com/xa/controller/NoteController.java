package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.Classification;
import com.xa.entity.File;
import com.xa.entity.Note;
import com.xa.service.ClassificationService;
import com.xa.service.FileService;
import com.xa.service.NoteService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/note")
public class NoteController extends BaseController {

	@Autowired
	private NoteService<Note> noteService;
	
	@Autowired
	private FileService<File> fileService;
	
	@Autowired
	private ClassificationService<Classification> classificationService;
	
	/**
	 * 
	 * @param note
	 * @param imgFile
	 * @param sign
	 */
	@RequestMapping("addNote")
	public void addNote(Note note, 
			@RequestParam(value = "imgFile", required = false)MultipartFile[] imgFile,
			String sign){
		try {
			this.sendAjaxMsg(this.noteService.addNote(note, imgFile, sign, fileService));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * @param sign
	 * @param pid
	 */
	@RequestMapping("getChildByNoteClassId")
	public void getChildByNoteClassId(String sign,Long pid){
		 try {
			this.sendAjaxMsg(this.classificationService.getChildByNoteClassId(sign, pid));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取晒物信息根据分类
	 * @param pageNum
	 * @param pageSize
	 * @param classId
	 * @return
	 */
	@RequestMapping("getNoteByClassId")
	public void getNoteByClassId(Integer pageNum, Integer pageSize, Long classId,String sign){
		try {
			this.sendAjaxMsg(this.noteService.getNoteByClassId(pageNum, pageSize, classId,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
