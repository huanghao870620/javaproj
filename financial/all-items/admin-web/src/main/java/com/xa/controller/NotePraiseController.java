package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.NotePraise;
import com.xa.service.NotePraiseService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/notePraise")
public class NotePraiseController extends BaseController {

	@Autowired
	private NotePraiseService<NotePraise> notePraiseService;
	
	
	/**
	 * 点赞
	 * @param notePraise
	 * @param sign
	 * @return
	 */
	public void addNotePraise(NotePraise notePraise,String sign){
		try {
			this.sendAjaxMsg(this.notePraiseService.addNotePraise(notePraise, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消点赞
	 * @param notePraise
	 * @param sign
	 * @return
	 */
	public void disabPraise(NotePraise notePraise, String sign){
		try {
			this.sendAjaxMsg(this.notePraiseService.disabPraise(notePraise, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
