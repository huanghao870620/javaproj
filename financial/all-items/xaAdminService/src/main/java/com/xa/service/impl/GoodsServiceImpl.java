package com.xa.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.Brand;
import com.xa.entity.File;
import com.xa.entity.GoodFile;
import com.xa.entity.Goods;
import com.xa.entity.Mall;
import com.xa.entity.MallGoods;
import com.xa.entity.ShoppingCartGoods;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodFileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.MallGoodsMapper;
import com.xa.mapper.MallMapper;
import com.xa.mapper.ShoppingCartGoodsMapper;
import com.xa.service.FileService;
import com.xa.service.GoodsService;
import com.xa.util.Constants;
import com.xa.util.Msg;
import com.xa.util.Security;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class GoodsServiceImpl extends BaseServiceImpl<Goods, GoodsMapper> implements GoodsService<Goods> {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private MallMapper mallMapper;
	
	@Autowired
	private MallGoodsMapper mallGoodsMapper;
	
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private ShoppingCartGoodsMapper shoppingCartGoodsMapper;
	
	@Autowired
	private GoodFileMapper goodFileMapper;

	/**
	 * 添加商品
	 */
	@Override
	public int insert(Goods record) {
		return super.insert(record);
	}
	
	/**
	 * 获取商品信息
	 * @param modelAndView
	 * @param id
	 */
	public void getGood(ModelAndView modelAndView, Long id){
		Goods good = this.m.selectByPrimaryKey(id);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("goodId", good.getId());
		map.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
		
		List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(map);
		File file= null;
		if(null != fileList && fileList.size() > 0){
			file=fileList.get(0);//缩略图
		}
		
		Long advPic= good.getAdvPic(); // 详情图
		File advPicFile= null;
		if (null != advPic) {
			advPicFile=this.fileMapper.selectByPrimaryKey(advPic);
		}
		
		modelAndView.addObject("good", good);
		modelAndView.addObject("file",file); // 缩略图
		modelAndView.addObject("advPicFile",advPicFile); //详情图
	}

	/**
	 * 添加商品
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public String addGood(
			MultipartFile gatpPhotoFile, // 商品正面照
			MultipartFile leftPhotoFile,//左侧视图
			MultipartFile rightPhotoFile,//右侧视图
			HttpServletRequest request,
			Goods goods,
			String mallName, 
			String mallAddress, 
			String brandName,
			Long uploadTypeId,
			String sign,
			FileService<com.xa.entity.File> fileService) throws IllegalStateException, IOException {

		JSONObject object = new JSONObject();
		
		if(!sign.equals(Security.getSign(new String[]{
				"classid", /*分类id*/
				"brandName", /*品牌名称*/
				"uploadTypeId",/*上传者类型*/
				"name", /*商品名称*/
				"price", /*商品价格*/
				"capacity",  /*规格*/
				"color", /*颜色*/
				"currencyType",/*货币种类*/
				"speciUnit",/*规格单位*/
				"dateOfProduction", /*生产日期*/
				"shelfLife", /*保质期*/
				"leftPhotoFile",/*左侧视图*/
				"rightPhotoFile",/*右侧视图*/
				"gatpPhotoFile",/*正面视图*/
				"mallName",/*商场名称*/
				"mallAddress"/*商场位置*/
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		com.xa.entity.File gatpFile = new com.xa.entity.File();
		fileService.uploadFile( gatpPhotoFile, PhotoType.GOODS_ACCORDING_TO_POSITIVE_TYPE,gatpFile); //商品正面视图
		
		com.xa.entity.File leftFile = new com.xa.entity.File();
		fileService.uploadFile(leftPhotoFile, PhotoType.LEFT_PHOTO, leftFile);//左侧视图
		
		com.xa.entity.File rightFile = new com.xa.entity.File();
		fileService.uploadFile(rightPhotoFile, PhotoType.RIGHT_PHOTO, rightFile);//右侧视图
		
		/*商场*/
		Mall mall = new Mall();
		mall.setName(mallName);
		mall.setAddress(mallAddress);
		this.mallMapper.insert(mall);
		
		//商品
		goods.setGoodsAccordingToPositive(gatpFile.getId());//正面视图
		goods.setLeftPhoto(leftFile.getId());//左侧视图
		goods.setRightPhoto(rightFile.getId());//右侧视图
		
		//品牌
		Brand brand = new Brand();
		brand.setName(brandName);		
		brand.setUploadTypeId(uploadTypeId);
		this.brandMapper.insert(brand);
		
		goods.setBrandId(brand.getId());//品牌
		this.goodsMapper.insert(goods);
		
		//商品和商场关系
		MallGoods mallGood = new MallGoods();
		mallGood.setGoodId(goods.getId());
		mallGood.setMallId(mall.getId());
		this.mallGoodsMapper.insert(mallGood);
		
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	public String delGoodById(Long id){
		JSONObject object = new JSONObject();
		
		List<GoodFile> gfList= goodFileMapper.selectGoodFileByGoodId(id);
		for(int i=0;i<gfList.size();i++){
			GoodFile gFile= gfList.get(i);
			Long fileId= gFile.getFileId();
			goodFileMapper.deleteByPrimaryKey(gFile.getId());
			fileMapper.deleteByPrimaryKey(fileId);
		}
		
		this.m.deleteByPrimaryKey(id);
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	
	/**
	 * 添加商品
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String addGood(Goods good,MultipartFile bigFile,MultipartFile smallFile,MultipartFile[] bigPicFile,FileService<File> fileService) 
			throws IllegalStateException, IOException{
		JSONObject object = new JSONObject();
		
		// 详情图
		com.xa.entity.File big = new com.xa.entity.File();
		fileService.uploadFile(bigFile, PhotoType.GOOD_ADV_PHOTO, big); 
		good.setAdvPic(big.getId());//详情图
		this.m.insert(good);
		
		
		// 缩略图
		com.xa.entity.File small = new com.xa.entity.File();
		fileService.uploadFile(smallFile, PhotoType.COMMODITY_THUMBNAIL, small); 
		
		GoodFile goodFile = new GoodFile();
		goodFile.setGoodId(good.getId());
		goodFile.setFileId(small.getId());
		this.goodFileMapper.insert(goodFile);
		
		//商品大图
		for(int i=0;i<bigPicFile.length; i++){
			File bigPic = new File();
			fileService.uploadFile(bigPicFile[i], PhotoType.GOOD_BIG_PHOTO, bigPic);
			
			GoodFile bigPicGoodFile = new GoodFile();
			bigPicGoodFile.setGoodId(good.getId());
			bigPicGoodFile.setFileId(bigPic.getId());
			this.goodFileMapper.insert(bigPicGoodFile);
		}
		
		
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	
	/**
	 * 添加商品
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String editGood(Goods good,Long thumbFileId,MultipartFile bigFile,MultipartFile smallFile,FileService<File> fileService) 
			throws IllegalStateException, IOException{
		JSONObject object = new JSONObject();
		if(null != bigFile && bigFile.getSize() > 0){
			com.xa.entity.File big = new com.xa.entity.File();
			big.setId(good.getAdvPic());
			fileService.editFile(bigFile, PhotoType.GOOD_ADV_PHOTO, big);
		}
		
		if(null != smallFile && smallFile.getSize() > 0){
			com.xa.entity.File small = new com.xa.entity.File();
			small.setId(thumbFileId);
			fileService.editFile(smallFile, PhotoType.COMMODITY_THUMBNAIL, small);
		}
		
		this.m.updateByPrimaryKeySelective(good);
		
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	
	


	/**
	 * 获取前十个商品
	 * 
	 * @return
	 */
	public String getGoodsByPaging(Integer pageNum, Integer pageSize,String sign) {
		JSONObject object = new JSONObject();
//		if(!sign.equals(Security.getSign(new String[]{"pageNum","pageSize"}))){
//			object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG,Msg.NOT_PERMISSION);
//			return object.toString();
//		}
//		
//		PageHelper.startPage(pageNum, pageSize, true);
//		List<Goods> goodList = this.goodsMapper.selectTopTen();
//		JSONArray array = new JSONArray();
//		for (int i = 0; i < goodList.size(); i++) {
//			 JSONObject goodObj = new JSONObject(); // 商品 json
//	    	  Goods goods = goodList.get(i);
//	    	  String barCode = goods.getBarCode();
//	    	  Long capacity = goods.getCapacity();
//	    	  Date dateOfProduction = goods.getDateOfProduction();
//	    	  Long highestPrice = goods.getHighestPrice();
//	    	  String info = goods.getInfo();
//	    	  Long lowestPrice = goods.getLowestPrice();
//	    	  String name = goods.getName();
//	    	  Long shelfLife = goods.getShelfLife();
//	    	  String purchasingPosition = goods.getPurchasingPosition();
//	    	  
//	    	  Long backGoodsAccordingToFileId = goods.getBackGoodsAccordingTo();
//	    	  Long expressSingleFileId = goods.getExpressSingle();
//	    	  Long goodsAccordingToPositiveFileId = goods.getGoodsAccordingToPositive();
//	    	  Long goodsInvoiceFileId = goods.getGoodsInvoice();
//	    	  Long productProfileFileId = goods.getProductProfile();
//	    	  
//	    	  com.xa.entity.File backGoodsAccordingToFile = this.fileMapper.selectByPrimaryKey(backGoodsAccordingToFileId);
//	    	  com.xa.entity.File expressSingleFile = this.fileMapper.selectByPrimaryKey(expressSingleFileId);
//	    	  com.xa.entity.File goodsAccordingToPositiveFile = this.fileMapper.selectByPrimaryKey(goodsAccordingToPositiveFileId);
//	    	  com.xa.entity.File goodsInvoiceFile = this.fileMapper.selectByPrimaryKey(goodsInvoiceFileId);
//	    	  com.xa.entity.File productProfileFile = this.fileMapper.selectByPrimaryKey(productProfileFileId);
//	    	  
//	    	  goodObj.accumulate("barCode", barCode)
//	    	  .accumulate("capacity", capacity)
//	    	  .accumulate("dateOfProduction", dateOfProduction)
//	    	  .accumulate("highestPrice", highestPrice)
//	    	  .accumulate("info", info)
//	    	  .accumulate("lowestPrice", lowestPrice)
//	    	  .accumulate("name", name)
//	    	  .accumulate("shelfLife", shelfLife)
//	    	  .accumulate("purchasingPosition", purchasingPosition)
//	    	  .accumulate("backGoodsAccordingTo", backGoodsAccordingToFile.getUriPath())
//	    	  .accumulate("expressSingle", expressSingleFile.getUriPath())
//	    	  .accumulate("goodsAccordingToPositive", goodsAccordingToPositiveFile.getUriPath())
//	    	  .accumulate("goodsInvoice", goodsInvoiceFile.getUriPath())
//	    	  .accumulate("productProfile", productProfileFile.getUriPath())
//	    	  ;
//	    	  array.add(goodObj);
//		}
//		object.accumulate("success", true).accumulate("goodsList", array);
		return object.toString();
	}

	
	
	/**
	 * 获取商品详情
	 * @return
	 */
	public String getGoodsDetailById(Long id,Long cartId, String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"id","cartId"	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		
		
		Goods good = this.m.selectByPrimaryKey(id);
		Float price = good.getPrice();
		
		Map<String, Object> map = new HashMap<>();
		map.put("goodId", good.getId());
		map.put("cartId", cartId);
		ShoppingCartGoods scg= this.shoppingCartGoodsMapper.getSCGByCartIdAndGoodId(map );
		if(null != scg){
			Long count= scg.getCount();
			Long scgId= scg.getId();
			object.accumulate("count", count)
			.accumulate("scgId", scgId);
		}
		
		Map<String, Object> mapPic = new HashMap<String,Object>();
		mapPic.put("goodId", good.getId());
		mapPic.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
		
		List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(mapPic);
		com.xa.entity.File thumbFile=fileList.get(0);
		
		Long advPicId =  good.getAdvPic();
		com.xa.entity.File  advFile=this.fileMapper.selectByPrimaryKey(advPicId);
		
		
		
		Map<String, Object> mapBigPic = new HashMap<String,Object>();
		mapBigPic.put("goodId", good.getId());
		mapBigPic.put("typeId", PhotoType.GOOD_BIG_PHOTO.getValue());/*商品大图*/
		
		List<com.xa.entity.File> bigPicList = this.fileMapper.getFileByGoodIdAndTypeId(mapBigPic);
		JSONArray bigPicArray = new JSONArray();
		for(int i=0;i<bigPicList.size();i++){
			JSONObject bigPicObj = new JSONObject();
			com.xa.entity.File bigPic = bigPicList.get(i);
			String bigPicUriPath = bigPic.getUriPath();
			bigPicObj.accumulate("bigPicUriPath", bigPicUriPath);
			bigPicArray.add(bigPicObj);
		}
		
		
		List<ShoppingCartGoods> scgs=this.shoppingCartGoodsMapper.getSCGByCartId(cartId);
		long countSum = 0;
		for(int i=0;i<scgs.size();i++){
			 countSum+=scgs.get(i).getCount();
		}
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("name", good.getName())
		.accumulate("info", good.getInfo())
		.accumulate("capacity", good.getCapacity())
		.accumulate("lowestPrice", good.getLowestPrice())
		.accumulate("highestPrice", good.getHighestPrice())
		.accumulate("id", good.getId())
		.accumulate("countSum", countSum)
		.accumulate("filePath", advFile.getUriPath())
		.accumulate("price", price)
		.accumulate(Constants.DATA, bigPicArray)
		;
		
		return object.toString();	
	}
	
	/**
	 * 获取商品详情通过id
	 * @return
	 */
	public String getGoodDetailById(Long goodId,String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				"goodId"
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		Goods good= this.m.selectByPrimaryKey(goodId);
		float price = good.getPrice();
		
		
		
		Long advPicId =  good.getAdvPic();
		com.xa.entity.File  advFile=this.fileMapper.selectByPrimaryKey(advPicId);
		
		
		
		Map<String, Object> mapPic = new HashMap<String,Object>();
		mapPic.put("goodId", good.getId());
		mapPic.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
		
		List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(mapPic);
		com.xa.entity.File thumbFile=fileList.get(0);
		
		
		Map<String, Object> mapBigPic = new HashMap<String,Object>();
		mapBigPic.put("goodId", good.getId());
		mapBigPic.put("typeId", PhotoType.GOOD_BIG_PHOTO.getValue());/*商品大图*/
		List<com.xa.entity.File> bigPicList = this.fileMapper.getFileByGoodIdAndTypeId(mapBigPic);
		JSONArray bigPicArray = new JSONArray();
		for(int i=0;i<bigPicList.size();i++){
			JSONObject bigPicObj = new JSONObject();
			com.xa.entity.File bigPic = bigPicList.get(i);
			String bigPicUriPath = bigPic.getUriPath();
			bigPicObj.accumulate("bigPicUriPath", bigPicUriPath);
			bigPicArray.add(bigPicObj);
		}
		
		object.accumulate(Constants.SUCCESS, true)
		.accumulate("name", good.getName())
		.accumulate("info", good.getInfo())
		.accumulate("capacity", good.getCapacity())
		.accumulate("lowestPrice", good.getLowestPrice())
		.accumulate("highestPrice", good.getHighestPrice())
		.accumulate("id", good.getId())
		
		.accumulate("filePath", advFile.getUriPath())
		.accumulate("price", price)
		.accumulate(Constants.DATA, bigPicArray)
		;
		return object.toString();
	}
	
	/**
	 * 获取推荐商品
	 * @return
	 */
	public String getRecommendedGoods(String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			""	
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		
		return null;
	}
	
	/**
	 * 通过品牌id获取商品
	 * @return
	 */
	public String getGoodsByBrandId(String sign){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
				""
		}))){
			return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.SUCCESS, Msg.NOT_PERMISSION).toString();
		}
		
		
		return null;
	}

	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public String getGoodsByPaging(Integer page,Integer rows, String nameS, Long brandId, Long countryId) {
		JSONObject object = new JSONObject();
		PageHelper.startPage(page, rows);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("nameS", nameS);
		map.put("brandId", brandId);
		map.put("countryId", countryId);
		Page<Goods> goodPage = (Page<Goods>) this.m.searchGoods(map );
		List<Goods> goodList= goodPage.getResult();
		long total = goodPage.getTotal();
		JSONArray array = new JSONArray();
		for(int i=0;i<goodList.size();i++){	
			JSONObject goodObj = new JSONObject();
			Goods good= goodList.get(i);
			if(null != good){
				Float price= good.getPrice();
				String name= good.getName();
				String info= good.getInfo();
				Integer shelves= good.getShelves();
				String shelvesStr = null;
				if(shelves==1){
					shelvesStr="是";
				}else if(shelves==0){
					shelvesStr="否";
				}
				goodObj.accumulate("price", price)
				.accumulate("id", good.getId())
				.accumulate("name", name)
				.accumulate("info", info)
				.accumulate("shelves", shelvesStr)
				;
				array.add(goodObj);
			}
		}
		object.accumulate(Constants.TOTAL, total).accumulate(Constants.ROWS, array);
		return object.toString();
	}
	
	
	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public String getGoodsByDeSession(Integer page,Integer rows, String nameS, Long brandId, Long countryId) {
		JSONObject object = new JSONObject();
		PageHelper.startPage(page, rows);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("nameS", nameS);
		map.put("brandId", brandId);
		map.put("countryId", countryId);
		Page<Goods> goodPage = (Page<Goods>) this.m.getGoodsByDeSession(map);
		List<Goods> goodList= goodPage.getResult();
		long total = goodPage.getTotal();
		JSONArray array = new JSONArray();
		for(int i=0;i<goodList.size();i++){	
			JSONObject goodObj = new JSONObject();
			Goods good= goodList.get(i);
			if(null != good){
				Float price= good.getPrice();
				String name= good.getName();
				String info= good.getInfo();
				Integer shelves= good.getShelves();
				String shelvesStr = null;
				if(shelves==1){
					shelvesStr="是";
				}else if(shelves==0){
					shelvesStr="否";
				}
				goodObj.accumulate("price", price)
				.accumulate("id", good.getId())
				.accumulate("name", name)
				.accumulate("info", info)
				.accumulate("shelves", shelvesStr)
				;
				array.add(goodObj);
			}
		}
		object.accumulate(Constants.TOTAL, total).accumulate(Constants.ROWS, array);
		return object.toString();
	}


    /**
     * 获取商品大图
     * @param goodId
     * @return
     */
	public String getBigPic4Good(Long goodId,Integer pageNum, Integer pageSize){
		JSONObject object = new JSONObject();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("goodId", goodId);
		map.put("typeId", PhotoType.GOOD_BIG_PHOTO.getValue());/*商品大图*/
		
		PageHelper.startPage(pageNum, pageSize, true);
		Page<GoodFile> gfPage= (Page<GoodFile>)this.goodFileMapper.selectGoodFileByGoodId(goodId);
//		Page<File> filePage  = (Page<File>)this.fileMapper.getFileByGoodIdAndTypeId(map);
		List<GoodFile> gfList = gfPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<gfList.size(); i++){
			JSONObject fileObj = new JSONObject();
			GoodFile gf= gfList.get(i);
			Long gfId= gf.getId();
			Long fileId= gf.getFileId();
			File file= this.fileMapper.selectByPrimaryKey(fileId);
			String uriPath= file.getUriPath();
			if(file.getTypeId().longValue()==PhotoType.GOOD_BIG_PHOTO.getValue().longValue()){
				fileObj.accumulate("gfId", gfId)
				.accumulate("fileId", fileId)
				.accumulate("uriPath", uriPath);
				array.add(fileObj);
			}
		}
		object.accumulate(Constants.TOTAL, gfPage.getTotal())
		.accumulate(Constants.ROWS, array);
		return object.toString();
	}
	
	/**
	 * 添加商品大图
	 * @param goodId
	 * @param bigPicFile
	 * @param fileService
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void addBigPic4Good(Long goodId, MultipartFile bigPicFile, FileService<File> fileService) throws IllegalStateException, IOException{
		File file = new File();
		fileService.uploadFile(bigPicFile, PhotoType.GOOD_BIG_PHOTO, file);
		
		GoodFile gFile = new GoodFile();
		gFile.setGoodId(goodId);
		gFile.setFileId(file.getId());
		this.goodFileMapper.insert(gFile);
	}


	/**
	 * 删除商品大图
	 * @param gfId
	 * @param fileService
	 */
	public String delBigPic(Long gfId, FileService<File> fileService){
		JSONObject object = new JSONObject();
		GoodFile gFile= this.goodFileMapper.selectByPrimaryKey(gfId);
		this.goodFileMapper.deleteByPrimaryKey(gfId);
		fileService.removeFile(gFile.getFileId());
//		modelAndView.setViewName("redirect:toEditGood.htm?id="+gFile.getGoodId());
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	} 

	
}
