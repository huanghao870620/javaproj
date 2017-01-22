package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.Activity;
import com.xa.service.ActivityService;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService<Activity> activityService;
	
	/**
	 * 获取所有活动
	 * @param random
	 * @param sign
	 */
	@RequestMapping("getActivityShufflingFigure")
	public void getActivityShufflingFigure(String random,String sign){
		 try {
			this.sendAjaxMsg(activityService.getActivityShufflingFigure(random,sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取活动详情
	 * @param id
	 * @param sign
	 */
	@RequestMapping("getActivityById")
	public void getActivityById(Long id, String sign){
		 try {
			this.sendAjaxMsg(this.activityService.getActivityById(id, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
