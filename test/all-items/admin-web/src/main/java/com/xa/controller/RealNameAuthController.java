package com.xa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xa.entity.File;
import com.xa.entity.RealNameAuth;
import com.xa.service.FileService;
import com.xa.service.RealNameAuthService;

/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/realNameAuth")
public class RealNameAuthController extends BaseController {

	@Autowired
	private RealNameAuthService<RealNameAuth> realNameAuthService;
	
	@Autowired
	private FileService<File> fileService;
	
	/**
	 * 添加实名认证信息
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("addRealNameAuth")
	public void addRealNameAuth(RealNameAuth realNameAuth,
			@RequestParam(value="frontCartPhotoFile",required=false) MultipartFile frontCartPhotoFile,
			@RequestParam(value="backCartPhotoFile",required=false) MultipartFile backCartPhotoFile,
			String sign){
		  try {
			this.sendAjaxMsg(this.realNameAuthService.addRealNameAuth(realNameAuth, frontCartPhotoFile, backCartPhotoFile, fileService, sign));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
