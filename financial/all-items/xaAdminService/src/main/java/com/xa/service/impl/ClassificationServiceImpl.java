package com.xa.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.apache.xalan.templates.ElemExsltFuncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.Classification;
import com.xa.entity.File;
import com.xa.enumeration.PhotoType;
import com.xa.entity.Classification;
import com.xa.mapper.ClassificationMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.ClassificationService;
import com.xa.service.FileService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.freemarker.ProcessFtl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class ClassificationServiceImpl extends BaseServiceImpl<Classification, ClassificationMapper> implements ClassificationService<Classification> {

	
	@Autowired
	private FileMapper fileMapper;
	
	/**
	 * 获取一级分类
	 * @param mav
	 */
	public void getFirstClass(ModelAndView mav){
		 mav.addObject("classifis4first", this.m.findClassByPid(-1L));
	}
	
	/**
	 * 获取分类通过id
	 * @param mav
	 */
	public void fillClassById(ModelAndView mav,Long classid){
		 Classification classifi = this.m.selectByPrimaryKey(classid);
		 Long imgId= classifi.getImgId();
		 File file= this.fileMapper.selectByPrimaryKey(imgId);
		 mav.addObject("classifi", classifi);
		 mav.addObject("file", file);
	}
	
	/**
	 * 获取分类
	 */
	public String getClassbyPid(Long pid, HttpServletRequest request){
		List<Classification> clsList= this.m.findClassByPid(pid);
		Map<String, Object> root = new HashMap<String,Object>();
		root.put("clsList", clsList);
		String msg = new ProcessFtl(root, "classification/getClassifi.ftl", request.getContextPath()).process();
		return msg;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public String getClassificationForStr() {
		JSONArray array = new JSONArray();
		List<Classification> topClassifications = this.m.findClassByPid(-1L);
		for (int i = 0; i < topClassifications.size(); i++) {
			Classification classification = topClassifications.get(i);
			JSONObject obj = new JSONObject();
			this.getChild(classification, obj);
			array.add(obj);
		}
		return array.toString();
	}

	/**
	 * 获取孩子
	 * 
	 * @param classification
	 * @param obj
	 */
	private void getChild(Classification classification, JSONObject obj) {
		obj.accumulate("id", classification.getId()).accumulate("text", this.spellHref(classification));
		List<Classification> childClassifications = this.m.findClassByPid(classification.getId());
		if (childClassifications.size() > 0) {
			JSONArray childArray = new JSONArray();
			for (int j = 0; j < childClassifications.size(); j++) {
				Classification childClassification = childClassifications.get(j);
				JSONObject childObj = new JSONObject();
				this.getChild(childClassification, childObj);
				childArray.add(childObj);
			}
			obj.accumulate("children", childArray);
		}
	}
	
	
	private String spellHref(Classification classification){
		   StringBuilder sb = new StringBuilder();
		   sb.append(classification.getName());
//		   if(m.findClassByPid(classification.getId()).size() > 0){
//		   }else {
//			   sb.append(classification.getName());
//		   }
		   return sb.toString();
	}
	
	/**
	 * 添加分类
	 * @param classification
	 * @param multipartFile
	 * @param fileService
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String addClass(Classification classification,MultipartFile multipartFile,FileService<File> fileService) 
			throws IllegalStateException, IOException{
		this.logger.debug("======================add class=========================");
		File file = new File();
		fileService.uploadFile(multipartFile, PhotoType.CLASSIFIACTION_LOG, file);
		classification.setImgId(file.getId());
		this.m.insert(classification);
		
		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		object.accumulate("id", classification.getId())
		.accumulate("text", classification.getName());
		array.add(object);
		this.logger.debug("=========================add class end===================================");
		return array.toString();
	}
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public String deleteClass(Long id){
		JSONObject object = new JSONObject();
		 this.m.deleteByPrimaryKey(id);
		 object.accumulate(Constants.SUCCESS, true);
		 return object.toString();
	}
	
	/**
	 * 编辑分类
	 * @param classification
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void updateClass(Classification classification, MultipartFile imgFile,FileService<File> fileService) 
			throws IllegalStateException,
			IOException{
		Long imgId=classification.getImgId();
		
		if(imgId ==null){
			//添加
			File file = new File();
			fileService.uploadFile(imgFile, PhotoType.CLASSIFIACTION_LOG, file);
			classification.setImgId(file.getId());
		}else{
			//修改
			File file = new File();
			file.setId(imgId);
			fileService.editFile(imgFile, PhotoType.CLASSIFIACTION_LOG, file);
		}
		this.m.updateByPrimaryKeySelective(classification);
		
	}
}
