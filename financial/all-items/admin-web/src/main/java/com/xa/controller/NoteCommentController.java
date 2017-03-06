package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.NoteComment;
import com.xa.service.NoteCommentService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/noteComment")
public class NoteCommentController extends BaseController {

	@Autowired
	private NoteCommentService<NoteComment> noteCommentService;
	


	/**
	 * 添加晒物评论
	 * @param noteComment
	 * @param sign
	 */
	@RequestMapping("addNoteComment")
	public void addNoteComment(NoteComment noteComment,String sign){
		 try {
			this.sendAjaxMsg(this.noteCommentService.addNoteComment(noteComment, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取晒物评论
	 * @param noteId
	 * @param sign
	 */
	@RequestMapping("getNoteCommentByNoteId")
	public void getNoteCommentByNoteId(Long noteId, String sign) {
		  try {
			this.sendAjaxMsg(this.noteCommentService.getNoteCommentByNoteId(noteId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
