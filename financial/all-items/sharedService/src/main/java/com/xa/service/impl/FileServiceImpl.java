package com.xa.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.File;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.FileMapper;
import com.xa.service.FileService;
import com.xa.util.Config;
import com.xa.util.GenerateFilePath;

/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class FileServiceImpl extends BaseServiceImpl<File, FileMapper> implements FileService<File>{
	
	
	/**
	 * 上传文件
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public  void uploadFile(MultipartFile multipartFile,PhotoType type,com.xa.entity.File file) throws IllegalStateException, IOException{
		String path = Config.getUploadBasePath();
		String sidPhotoFileName = multipartFile.getOriginalFilename();
		GenerateFilePath sidPhotoGenerateFilePath = new GenerateFilePath(type.getValue(), sidPhotoFileName);
		String sidPhotoFileUrl = sidPhotoGenerateFilePath.getUri() ;
		file.setName(sidPhotoFileName);
		file.setPath(path);
		file.setTypeId(type.getValue());
		file.setUriPath(sidPhotoFileUrl);
		this.m.insert(file);
		path = path+"/" + sidPhotoFileUrl;
		java.io.File sidPhotoTargetFile = new java.io.File(path);
		if (!sidPhotoTargetFile.exists()) {
			sidPhotoTargetFile.mkdirs();
		}
		multipartFile.transferTo(sidPhotoTargetFile);
	}
	
	/**
	 * 编辑文件
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public  void editFile(MultipartFile multipartFile,PhotoType type,com.xa.entity.File file) throws IllegalStateException, IOException{
		String path = Config.getUploadBasePath();
		String sidPhotoFileName = multipartFile.getOriginalFilename();
		GenerateFilePath sidPhotoGenerateFilePath = new GenerateFilePath(type.getValue(), sidPhotoFileName);
		String sidPhotoFileUrl = sidPhotoGenerateFilePath.getUri() ;
		
		file.setName(sidPhotoFileName);
		file.setPath(path);
		file.setTypeId(type.getValue());
		file.setUriPath(sidPhotoFileUrl);
		this.m.updateByPrimaryKeySelective(file);
		path = path+"/" + sidPhotoFileUrl;
		java.io.File sidPhotoTargetFile = new java.io.File(path);
		if (!sidPhotoTargetFile.exists()) {
			sidPhotoTargetFile.mkdirs();
		}
		multipartFile.transferTo(sidPhotoTargetFile);
	}
	
	/**
	 * 删除文件
	 */
	public void removeFile(Long id){
		String path = Config.getUploadBasePath();
		File file= this.m.selectByPrimaryKey(id);
		String uriPath = file.getUriPath();
		path = path + "/" + uriPath;
		java.io.File eFile = new java.io.File(path);
		if(eFile.isFile() && eFile.exists()){
			 eFile.delete();
			 this.m.deleteByPrimaryKey(id);
		}
	}

	
}
