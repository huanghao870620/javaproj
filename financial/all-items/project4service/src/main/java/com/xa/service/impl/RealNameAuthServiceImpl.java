package com.xa.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.File;
import com.xa.entity.RealNameAuth;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.RealNameAuthMapper;
import com.xa.service.FileService;
import com.xa.service.RealNameAuthService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONObject;

@Service
@Transactional
public class RealNameAuthServiceImpl extends BaseServiceImpl<RealNameAuth, RealNameAuthMapper>
		implements RealNameAuthService<RealNameAuth> {

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
			String sign) throws IllegalStateException, IOException{
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"name","cartNo","buyerId","frontCartPhotoFile","backCartPhotoFile"		
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		com.xa.entity.File frontFile = new com.xa.entity.File();
		fileService.uploadFile(frontCartPhotoFile, PhotoType.CERTIFICATION_FRONT_PHOTO,frontFile); //身份证正面照
		
		com.xa.entity.File backFile = new com.xa.entity.File();
		fileService.uploadFile(backCartPhotoFile, PhotoType.CERTIFICATION_BACK_PHOTO,backFile); //身份证正面照
		
		this.m.insert(realNameAuth);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
}
