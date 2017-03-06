package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.NoteColl;
import com.xa.service.NoteCollService;

@Controller
@RequestMapping("/noteColl")
public class NoteCollController extends BaseController {

	@Autowired
	private NoteCollService<NoteColl> noteCollService;
	

	/**
	 * 收藏笔记
	 * @param noteColl
	 * @param sign
	 */
	@RequestMapping("addNoteColl")
	public void addNoteColl(NoteColl noteColl,String sign){
		try {
			this.sendAjaxMsg(this.noteCollService.addNoteColl(noteColl, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
