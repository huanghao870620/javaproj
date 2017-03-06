package com.xa.mapper;

import java.util.List;

import com.xa.entity.NoteFile;

public interface NoteFileMapper extends BaseMapper<NoteFile>{
	
	List<NoteFile> getNoteFileByNoteId(Long noteId);
}