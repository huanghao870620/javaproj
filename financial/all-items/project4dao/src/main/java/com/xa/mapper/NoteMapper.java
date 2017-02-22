package com.xa.mapper;

import java.util.List;

import com.xa.entity.Note;

public interface NoteMapper extends BaseMapper<Note>{
	
	List<Note> getNoteByClassId(Long classId);
}