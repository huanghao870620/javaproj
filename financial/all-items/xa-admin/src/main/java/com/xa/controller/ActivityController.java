package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Activity;
import com.xa.entity.Brand;
import com.xa.entity.Classification;
import com.xa.entity.Country;
import com.xa.entity.File;
import com.xa.service.ActivityService;
import com.xa.service.BrandService;
import com.xa.service.ClassificationService;
import com.xa.service.CountryService;
import com.xa.service.FileService;
/**
 * 
 * @author burgess
 *
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService<Activity> activityService;
	
	@Autowired
	private FileService<File> fileService;
	
	@Autowired
	private BrandService<Brand> brandService;
	
	@Autowired
	private CountryService<Country> countryService;
	
	@Autowired
	private ClassificationService<Classification> classificationService;
	
	/**
	 * 跳转到添加活动页面
	 * @return
	 */
	@RequestMapping("toAddActivity")
	public ModelAndView toAddActivity(){
		 ModelAndView modelAndView = new ModelAndView("activity/addActivity");
		 this.brandService.getBrands(modelAndView); //品牌
		 this.classificationService.getFirstClass(modelAndView); //一级分类
		 this.countryService.getAllCountry(modelAndView);
		 return modelAndView;
	}
	
	/**
	 * 添加活动
	 * @param activity
	 * @param imgAdvFile
	 * @param activityDetailFile
	 * @return
	 */
	@RequestMapping("addActivity")
	public ModelAndView addActivity(Activity activity,
			@RequestParam(value = "imgAdvFile", required = false)MultipartFile imgAdvFile,
			@RequestParam(value = "activityDetailFile", required = false)MultipartFile[] activityDetailFile,
			Long ids[]){
		ModelAndView modelAndView = new ModelAndView("redirect:toListActivity.htm");
		try {
			this.activityService.addActivity(activity, imgAdvFile, activityDetailFile, ids,fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
	/**
	 * 去编辑活动
	 * @return
	 */
	@RequestMapping("toEditActivity")
	public ModelAndView toEditActivity(Long id){
		 ModelAndView modelAndView = new ModelAndView("activity/editActivity");
		 this.activityService.getActivity(id, modelAndView);
		 this.brandService.getBrands(modelAndView); //品牌
		 this.classificationService.getFirstClass(modelAndView); //一级分类
		 this.countryService.getAllCountry(modelAndView);
		 return modelAndView;
	}
	
	/**
	 * 编辑活动
	 * @return
	 */
	@RequestMapping("editActivity")
	public ModelAndView editActivity(Activity activity,
			@RequestParam(value = "imgAdvFile", required = false)MultipartFile imgAdvFile,
			Long ids[]){
		ModelAndView modelAndView = new ModelAndView("redirect:toListActivity.htm");
		try {
			this.activityService.editActivity(activity, modelAndView, imgAdvFile, ids, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modelAndView; 
	}
	
	
	/**
	 * 跳转到活动列表页面
	 * @return
	 */
	@RequestMapping("toListActivity")
	public ModelAndView toListActivity(){
		 ModelAndView modelAndView = new ModelAndView("activity/activityList");
		 return modelAndView;
	}
	
	/**
	 * 获取活动列表
	 */
	@RequestMapping("listActivity")
	public void listActivity(Integer page, Integer rows){
		try {
			this.sendAjaxMsg(this.activityService.listActivity(page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除活动
	 * @param id
	 */
	@RequestMapping("deleteActivity")
	public void deleteActivity(Long id){
		try {
			this.sendAjaxMsg(this.activityService.deleteActivityById(id));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取活动详情
	 * @param activityId
	 * @param pageNum
	 * @param pageSize
	 */
	@RequestMapping("getDetailPicByActivityId")
	public void getDetailPicByActivityId(Long activityId,Integer page,Integer rows){
		   try {
			this.sendAjaxMsg(this.activityService.getDetailPicByActivityId(activityId, page, rows));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加活动详情图
	 * @param activityId
	 * @param detailPicFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("addActivityDetailPic")
	public ModelAndView addActivityDetailPic(Long activityId, 
			@RequestParam(value = "detailPicFile", required = false)MultipartFile detailPicFile)
			{
		ModelAndView modelAndView = new ModelAndView("redirect:toEditActivity.htm?id="+activityId);
		try {
			this.activityService.addActivityDetailPic(activityId, detailPicFile, fileService);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	

	/**
	 * 删除活动详情图
	 * @param picId
	 */
	@RequestMapping("deleteActivityDetailPic")
	public void deleteActivityDetailPic(Long afId){
		  try {
			this.sendAjaxMsg(this.activityService.deleteActivityDetailPic(afId, fileService));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
