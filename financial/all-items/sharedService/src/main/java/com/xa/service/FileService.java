package com.xa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.xa.enumeration.PhotoType;
/**
 * 
 * @author burgess
 *
 * @param <T>
 */
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
	
	/**
	 * 删除文件
	 * @param id
	 */
	public void removeFile(Long id);
	
}
