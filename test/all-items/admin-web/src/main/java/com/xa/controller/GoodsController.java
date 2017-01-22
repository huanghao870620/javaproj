package com.xa.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.convert.DatePropertyEditor;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.service.FileService;
import com.xa.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService<Goods> goodsService;
	
	@Autowired
	private FileService<File> fileService;
	
	/**
	 * @return
	 */
	@RequestMapping("addGoods")
	public ModelAndView addGoods(){
		ModelAndView mav = new ModelAndView("");
		return mav;
	}
	
	/**
	 * @return
	 */
	@RequestMapping("toAddGoods")
	public ModelAndView toAddGoods(){
		ModelAndView mav = new ModelAndView("goods/addGood");
		return mav;
	}
	
	/**
	 * @return
	 */
	@RequestMapping("addGood")
	public ModelAndView addGood(@RequestParam(value = "file", required = false) MultipartFile file,
			@ModelAttribute Goods good,
			ModelMap model){
		ModelAndView mav = new ModelAndView("");
//		
//		try {
//			this.goodsService.addGood(session, file, model,this.request,good);
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return mav;
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
	
	    @RequestMapping("/test")
	    public String test(Date birthday){
	        System.out.println(birthday);
	        return "index";
	    }
	    

	    /**
	     * 获取商品分解分类id
	     * @param classid
	     * @param sign
	     */
	    @RequestMapping("getGoodsByClassifi")
	    public void getGoodsByClassifi(Long classid,Integer pageNum, Integer pageSize, String sign){
	    	try {
				this.sendAjaxMsg(this.goodsService.getGoodsByClassifi(classid,pageNum,pageSize, sign));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	    /**
	     * 获取商品详情
	     * @param id
	     * @param sign
	     */
	    @RequestMapping("getGoodsDetailById")
	    public void getGoodsDetailById(Long id, Long cartId,String sign){
	    	try {
				this.sendAjaxMsg(this.goodsService.getGoodsDetailById(id,cartId, sign));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    /**
	     * 获取商品详情通过id
	     * @param goodId
	     * @param sign
	     */
	    @RequestMapping("getGoodDetailById")
	    public void getGoodDetailById(Long goodId,String sign){
	    	try {
				this.sendAjaxMsg(this.goodsService.getGoodDetailById(goodId, sign));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    /**
	     * 添加商品 - 内部测试
	     */
	    @RequestMapping("addGood4Inner")
	    public void addGood(
	    		Goods good,
	    		@RequestParam(value="bigFile",required=false) MultipartFile bigFile,
	    		@RequestParam(value="smallFile",required=false) MultipartFile smallFile
	    		){
	    	try {
				this.sendAjaxMsg(this.goodsService.addGood(good, bigFile, smallFile, fileService));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    }
	    
		@InitBinder
		 protected void initBinder(HttpServletRequest request,
		   ServletRequestDataBinder binder) throws Exception {
//		  binder.registerCustomEditor(Date.class, new DatePropertyEditor(yourDateformat));
		binder.registerCustomEditor(Date.class, new DatePropertyEditor("yyyy/MM/dd"));
//			System.out.println("1");
		 }
}
