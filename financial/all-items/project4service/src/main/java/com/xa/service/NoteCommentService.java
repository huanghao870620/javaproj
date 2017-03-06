package com.xa.service;

import com.xa.entity.NoteComment;

public interface NoteCommentService<T> extends BaseServiceInte<T> {

	public String addNoteComment(NoteComment noteComment,String sign);
	
	
	public String getNoteCommentByNoteId(Long noteId, String sign) ;
}
