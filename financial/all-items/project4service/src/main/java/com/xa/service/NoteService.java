package com.xa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.File;
import com.xa.entity.Note;

public interface NoteService<T> extends BaseServiceInte<T>{

	/**
	 * 添加晒物笔记
	 * @param note
	 * @param sign
	 * @return
	 */
	public String addNote(Note note, MultipartFile []imgFile, String sign,FileService<File> fileService) throws IllegalStateException, IOException;
	
	/**
	 * 获取晒物信息
	 * @param pageNum
	 * @param pageSize
	 * @param classId
	 * @return
	 */
	public String getNoteByClassId(Integer pageNum, Integer pageSize, Long classId,String sign);
}
