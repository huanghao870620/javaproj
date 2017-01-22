package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.BuyHand;
import com.xa.entity.Buyers;
import com.xa.entity.File;
import com.xa.service.BuyHandService;
import com.xa.service.BuyersService;
import com.xa.service.FileService;

@Controller
@RequestMapping("/buyers")
public class BuyersController extends BaseController {

	@Autowired
	private BuyersService<Buyers> buyersService;
	
	@Autowired
	private BuyHandService<BuyHand> buyHandService;
	
	@Autowired
	private FileService<File> fileService;
	

	/**
	 * 获取所有买家信息
	 * @param page
	 * @param rows
	 */
	@RequestMapping("getBuyers")
	public void getBuyers(Integer page,Integer rows){
		try {
			this.sendAjaxMsg(this.buyersService.getBuyers(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("toListBuyer")
	public ModelAndView toListBuyer(){
		ModelAndView modelAndView = new ModelAndView("buyer/buyerList");
		return modelAndView;
	}
	
}

