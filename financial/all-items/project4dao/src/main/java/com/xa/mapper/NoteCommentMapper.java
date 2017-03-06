package com.xa.mapper;

import java.util.List;

import org.apache.poi.poifs.storage.ListManagedBlock;

import com.xa.entity.NoteComment;

public interface NoteCommentMapper extends BaseMapper<NoteComment>{
	
	List<NoteComment> getNoteCommentByNoteId(Long noteId);
}