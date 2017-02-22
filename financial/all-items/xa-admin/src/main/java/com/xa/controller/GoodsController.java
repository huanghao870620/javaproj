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
import com.xa.entity.Brand;
import com.xa.entity.Classification;
import com.xa.entity.Country;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.service.AdminFileService;
import com.xa.service.BrandService;
import com.xa.service.ClassificationService;
import com.xa.service.CountryService;
import com.xa.service.FileService;
import com.xa.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService<Goods> goodsService;
	
	@Autowired
	private FileService<File> fileService;
	
	@Autowired
	private BrandService<Brand> brandService;
	
	@Autowired
	private ClassificationService<Classification> classificationService;
	
	@Autowired
	private CountryService<Country> countryService;
	
	@Autowired
	private AdminFileService<File> adminFileService;
	
	
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
		brandService.getBrands(mav); //品牌
		classificationService.getFirstClass(mav); //一级分类
		this.countryService.getAllCountry(mav);
		return mav;
	}
	
	/**
	 * 去编辑商品
	 * @return
	 */
	@RequestMapping("toEditGood")
	public ModelAndView toEditGood(Long id){
		ModelAndView mav = new ModelAndView("goods/editGood");
		brandService.getBrands(mav); //品牌
		this.countryService.getAllCountry(mav);//国家
		classificationService.getFirstClass(mav); //一级分类
		this.goodsService.getGood(mav, id);
		return mav;
	}
	
	/**
	 * @return
	 */
	@RequestMapping("addGood")
	public ModelAndView addGood(
			@RequestParam(value = "bigFile", required = false) MultipartFile bigFile, // 详情图
			@RequestParam(value = "smallFile", required = false) MultipartFile smallFile, //缩略图
			@RequestParam(value = "bigPicFile", required = false) MultipartFile[] bigPicFile, // 商品大图
			@ModelAttribute Goods good,
			ModelMap model){
		ModelAndView mav = new ModelAndView("redirect:listGood.htm");
		try {
			this.goodsService.addGood(good, bigFile, smallFile, bigPicFile,fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 
	 * @param bigFile
	 * @param smallFile
	 * @param thumbFileId
	 * @param good
	 * @param model
	 * @return
	 */
	@RequestMapping("editGood")
	public ModelAndView editGood(
			@RequestParam(value = "bigFile", required = false) MultipartFile bigFile,
			@RequestParam(value = "smallFile", required = false) MultipartFile smallFile,
			Long thumbFileId,
			@ModelAttribute Goods good,
			ModelMap model){
		ModelAndView mav = new ModelAndView("goods/goodsList");
		try {
			this.goodsService.editGood(good, thumbFileId, bigFile, smallFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	
	/**
	 * @return
	 */
	@RequestMapping("listGood")
	public ModelAndView listGood(
			){
		ModelAndView mav = new ModelAndView("goods/goodsList");
		brandService.getBrands(mav); //品牌
		classificationService.getFirstClass(mav); //一级分类
		this.countryService.getAllCountry(mav);
		return mav;
	}
	
	/**
	 * 
	 * @param page
	 * @param rows
	 * @param nameS
	 * @param brandId
	 * @param countryId
	 */
	@RequestMapping("getGoodsByPaging")
	public void getGoodsByPaging(Integer page, Integer rows, String nameS, Long brandId, Long countryId){
		try {
			this.sendAjaxMsg(this.goodsService.getGoodsByPaging(page, rows,nameS,brandId,countryId));
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
//		try {
//			this.sendAjaxMsg(this.goodsService.addGoodsByAjax(goodsAccordingToPositiveFile, backGoodsAccordingToFile, productProfileFile, goodsInvoiceFile, expressSingleFile, goods, request));
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	    @RequestMapping("/test")
	    public String test(Date birthday){
	        System.out.println(birthday);
	        return "index";
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
	     * 删除商品
	     * @param id
	     */
	    @RequestMapping("delGood")
	    public void delGood(Long id){
	    	try {
				this.sendAjaxMsg(this.goodsService.delGoodById(id));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    /**
	     * 获取商品大图
	     * @param goodId
	     * @param pageNum
	     * @param pageSize
	     */
	    @RequestMapping("getBigPic4Good")
	    public void getBigPic4Good(Long goodId,Integer page, Integer rows){
	    	try {
				this.sendAjaxMsg(this.goodsService.getBigPic4Good(goodId, page, rows));
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
//	    	try {
//				this.sendAjaxMsg(this.goodsService.addGood(good, bigFile, smallFile, fileService));
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
	    	
	    }
	    
	    /**
	     * 去编辑商品大图
	     * @return
	     */
	    @RequestMapping("toEditBigPic")
	    public ModelAndView toEditBigPic(Long id){
	    	ModelAndView modelAndView = new ModelAndView("goods/editBigPic");
	    	this.adminFileService.getFile(modelAndView, id);
	    	return modelAndView;
	    }
	    
	    /**
	     * 获取商品大图信息
	     * @param id
	     */
	    @RequestMapping("getBigPicInfoById")
	    public void getBigPicInfoById(Long id){
	    	try {
				this.sendHtml(this.adminFileService.getBigPicInfoById(id, request));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    /**
	     * 添加商品大图
	     * @param goodId
	     * @param bigPicFile
	     */
	    @RequestMapping("addBigPic4Good")
	    public ModelAndView addBigPic4Good(Long goodId,@RequestParam(value="bigPicFile",required=false)  MultipartFile bigPicFile){
	    	 ModelAndView modelAndView = new ModelAndView("redirect:toEditGood.htm?id="+goodId);
	    	 try {
				this.goodsService.addBigPic4Good(goodId, bigPicFile, fileService);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	 return modelAndView;
	    }
	   
	    /**
	     * 删除商品大图
	     * @return
	     */
	    @RequestMapping("delGoodPic")
	    public void delGoodPic(Long gfId){
	    	try {
				this.sendAjaxMsg(this.goodsService.delBigPic(gfId, fileService));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    /**
	     * 
	     * @param page
	     * @param rows
	     * @param nameS
	     * @param brandId
	     * @param countryId
	     */
	    @RequestMapping("getGoodsByDeSession")
	    public void getGoodsByDeSession(Integer page,Integer rows, String nameS, Long brandId, Long countryId){
	    	try {
				this.sendAjaxMsg(this.goodsService.getGoodsByDeSession(page, rows, nameS, brandId, countryId));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    
	    
		@InitBinder
		 protected void initBinder(HttpServletRequest request,
		   ServletRequestDataBinder binder) throws Exception {
			binder.registerCustomEditor(Date.class, new DatePropertyEditor("yyyy/MM/dd"));
		 }
		
		
		
}
