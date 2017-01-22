package com.xa.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xpath.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Brand;
import com.xa.entity.File;
import com.xa.entity.Goods;
import com.xa.entity.UploadType;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.UploadTypeMapper;
import com.xa.service.BrandService;
import com.xa.service.FileService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class BrandServiceImpl extends BaseServiceImpl<Brand, BrandMapper> implements BrandService<Brand> {

	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private UploadTypeMapper uploadTypeMapper;
	
	/**
	 * 获取所有品牌
	 * @return
	 */
	public void getBrands(ModelAndView mav){
		mav.addObject("brands", this.m.findAll());
	}
	
	
	/**
	 * 添加品牌
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void addBrand(Brand brand,MultipartFile imgFile,MultipartFile detailPicFile,FileService<File> fileService) throws IllegalStateException, IOException{
		File file = new File();
		fileService.uploadFile(imgFile, PhotoType.BRAND_PHOTO, file);
		
		File dpFile = new File();
		fileService.uploadFile(detailPicFile, PhotoType.BRAND_DETAIL_PIC, dpFile );
		
		brand.setImgId(file.getId());
		brand.setDetailPic(dpFile.getId());
		this.m.insert(brand);
	}
	
	 
	public String  getBrandsByPaging(Integer page, Integer rows){
		JSONObject object = new JSONObject();
		PageHelper.startPage(page, rows);
		Page<Brand> brandPage = (Page<Brand>) this.m.findAll();
		List<Brand> brandList= brandPage.getResult();
		long total = brandPage.getTotal();
		JSONArray array = new JSONArray();
		for(int i=0;i<brandList.size();i++){
			JSONObject goodObj = new JSONObject();
			Brand brand= brandList.get(i);
			String name= brand.getName();
			Long imgId= brand.getImgId();
			String info= brand.getInfo();
			Long typeId= brand.getUploadTypeId();
			File img= this.fileMapper.selectByPrimaryKey(imgId);
			UploadType uploadType= this.uploadTypeMapper.selectByPrimaryKey(typeId);
			String brandName= uploadType.getName();
			String brandUriPath = null;
			if(null != img){
				brandUriPath = img.getUriPath();
			}
			goodObj
			.accumulate("name", name)
			.accumulate("id", brand.getId())
			.accumulate("uploadType", brandName == null ? "" : brandName)
			.accumulate("img", brandUriPath == null ? "" : brandUriPath)
			.accumulate("info", info)
			;
			array.add(goodObj);
		}
		object.accumulate(Constants.TOTAL, total).accumulate(Constants.ROWS, array);
		return object.toString();
	}
	
	
	
	
	/**
	 * 根据品牌id获取商品
	 * @return
	 */
	public String getGoodsByBrandId(Long brandId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
		   "brandId"		
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}

		List<Goods> list= this.goodsMapper.getGoodsByBrandId(brandId);
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			Goods good= list.get(i);
			JSONObject goodObj = new JSONObject();
			float price= good.getPrice();
			String name= good.getName();
			Map<String, Object> mapPic = new HashMap<String,Object>();
			mapPic.put("goodId", good.getId());
			mapPic.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
			
			List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(mapPic);
			com.xa.entity.File thumbFile=fileList.get(0);
			String uriPath= thumbFile.getUriPath();
			goodObj.accumulate("name", name).accumulate("price", price).accumulate("uriPath", uriPath);
			array.add(goodObj);
		}
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array);
		return object.toString();
	}
	
	/**
	 * 删除品牌根据id
	 */
	public String delByBrandId(Long brandId,FileService<File> fileService){
		JSONObject object = new JSONObject();
		Brand brand= this.m.selectByPrimaryKey(brandId);
		Long imgId= brand.getImgId();
		this.m.deleteByPrimaryKey(brandId);
		fileService.removeFile(imgId);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	
	/**
	 * 获取品牌
	 * @param mav
	 * @param id
	 */
	public void getBrandById(ModelAndView mav,Long id){
		Brand brand= this.m.selectByPrimaryKey(id);
		Long imgId= brand.getImgId();
		Long dpId= brand.getDetailPic();
		File file= this.fileMapper.selectByPrimaryKey(imgId);
		File detailPicFile = this.fileMapper.selectByPrimaryKey(dpId);
		mav.addObject("file", file);
		mav.addObject("detailPicFile",detailPicFile);
		mav.addObject("brand", brand);
	}
	
	/**
	 * 编辑品牌
	 * @param brand
	 * @param imgFile
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void editBrand(Brand brand, MultipartFile imgFile, MultipartFile detailPicFile,FileService<File> fileService) throws IllegalStateException, IOException{
		if (imgFile != null && imgFile.getSize() > 0) {
			Long imgId= brand.getImgId();
			if(null != imgId){
				//修改
				File file = new File();
				file.setId(imgId);
				fileService.editFile(imgFile, PhotoType.BRAND_PHOTO, file);
			}else {
				//添加
				File file = new File();
				fileService.uploadFile(imgFile, PhotoType.BRAND_PHOTO, file);
				brand.setImgId(file.getId());
			}
		}
		
		if(null != detailPicFile && detailPicFile.getSize() > 0){
			
			Long detailPic= brand.getDetailPic();
			if (null == detailPic) {
				//添加
				File file = new File();
				file.setId(brand.getDetailPic());
				fileService.uploadFile(detailPicFile, PhotoType.BRAND_PHOTO, file);
				brand.setDetailPic(file.getId());
			}else {
				//修改
				File file = new File();
				file.setId(brand.getDetailPic());
				fileService.editFile(detailPicFile, PhotoType.BRAND_PHOTO, file);
			}
			
		}
		
		 this.m.updateByPrimaryKeySelective(brand);
	} 
	
}
