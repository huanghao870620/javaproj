package com.xa.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.File;
import com.xa.entity.RealNameAuth;

public interface RealNameAuthService<T> extends BaseServiceInte<T> {

	
	/**
	 * 添加实名认证信息
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String addRealNameAuth(RealNameAuth realNameAuth,
			MultipartFile frontCartPhotoFile,
			MultipartFile backCartPhotoFile,
			FileService<File> fileService,
			String sign) throws IllegalStateException, IOException;
}
