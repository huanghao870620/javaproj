package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.UploadType;
import com.xa.service.UploadTypeService;

@Controller
@RequestMapping("/uploadType")
public class UploadTypeController extends BaseController {

	@Autowired
	private UploadTypeService<UploadType> uploadTypeService;
	
	/**
	 * 获取所有上传类型
	 */
	@RequestMapping("getAllUploadTypes")
	public void getAllUploadTypes(){
		 try {
			this.sendHtml(this.uploadTypeService.getAllUploadType(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
