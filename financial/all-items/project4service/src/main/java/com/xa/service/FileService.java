package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xa.enumeration.PhotoType;

public interface FileService<T> extends BaseServiceInte<T> {

	/**
	 * 上传文件
	 * @param multipartFile
	 * @param type
	 * @param file
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public  void uploadFile(MultipartFile multipartFile,PhotoType type,com.xa.entity.File file) throws IllegalStateException, IOException;
	
	/**
	 * 修改文件
	 * @param multipartFile
	 * @param type
	 * @param file
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public  void editFile(MultipartFile multipartFile,PhotoType type,com.xa.entity.File file) throws IllegalStateException, IOException;
	
	
	public  void uploadFileTest(MultipartFile multipartFile,PhotoType type,com.xa.entity.File file) throws IllegalStateException, IOException;
}
