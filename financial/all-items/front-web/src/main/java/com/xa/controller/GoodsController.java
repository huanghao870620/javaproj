package com.xa.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xa.convert.DatePropertyEditor;
import com.xa.entity.Goods;
import com.xa.service.GoodsService;
/**
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService<Goods> goodsService;
	
	/**
	 * pageNum 页数  
	 * pageSize 页大小
	 */
	@RequestMapping("getGoods")
	public void getGoods(Integer pageNum, Integer pageSize, String sign){
		 try {
			this.sendAjaxMsg(this.goodsService.getGoodsByPaging(pageNum, pageSize, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 异步添加商品
	 */
	@RequestMapping("addGoodsByAjax")
	public void addGoodsByAjax(@RequestParam(value="goodsAccordingToPositiveFile",required=false) MultipartFile goodsAccordingToPositiveFile,
			@RequestParam(value="backGoodsAccordingToFile",required=false) MultipartFile backGoodsAccordingToFile,
			@RequestParam(value="productProfileFile",required=false) MultipartFile productProfileFile,
			@RequestParam(value="goodsInvoiceFile",required=false) MultipartFile goodsInvoiceFile,
			@RequestParam(value="expressSingleFile",required=false) MultipartFile expressSingleFile,
			Goods goods){
		try {
			this.sendAjaxMsg(this.goodsService.addGoodsByAjax(goodsAccordingToPositiveFile, backGoodsAccordingToFile, productProfileFile, goodsInvoiceFile, expressSingleFile, goods, request));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 买手添加商品
	 */
	@RequestMapping("addGood")
	public void addGood(@RequestParam(value="gatpPhotoFile",required=false) MultipartFile gatpPhotoFile,
			@RequestParam(value="leftPhotoFile",required=false) MultipartFile leftPhotoFile,
			@RequestParam(value="rightPhotoFile",required=false) MultipartFile rightPhotoFile,
			Goods goods,
			String mallName,
			String mallAddress,
			String brandName,
			Long uploadTypeId,
			String sign){
		try {
			this.sendAjaxMsg(this.goodsService.addGood(gatpPhotoFile, 
					leftPhotoFile, 
					rightPhotoFile, 
					request,
					goods,
					mallName, 
					mallAddress,
					brandName, 
					uploadTypeId,
					sign));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@InitBinder
	 protected void initBinder(HttpServletRequest request,
	   ServletRequestDataBinder binder) throws Exception {
//	  binder.registerCustomEditor(Date.class, new DatePropertyEditor(yourDateformat));
	binder.registerCustomEditor(Date.class, new DatePropertyEditor("yyyy/MM/dd"));
//		System.out.println("1");
	 }
}
