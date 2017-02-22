package com.xa.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.dto.HotSearchDto;
import com.xa.entity.Brand;
import com.xa.entity.Classification;
import com.xa.entity.File;
import com.xa.entity.GoodFile;
import com.xa.entity.Goods;
import com.xa.entity.GoodsSearchRecord;
import com.xa.entity.Mall;
import com.xa.entity.MallGoods;
import com.xa.entity.ShoppingCartGoods;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.FileMapper;
import com.xa.mapper.GoodFileMapper;
import com.xa.mapper.GoodsMapper;
import com.xa.mapper.GoodsSearchRecordMapper;
import com.xa.mapper.MallGoodsMapper;
import com.xa.mapper.MallMapper;
import com.xa.mapper.ShoppingCartGoodsMapper;
import com.xa.service.ClassificationService;
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
	
	@Autowired
	private GoodsSearchRecordMapper goodsSearchRecordMapper;
	
	

	/**
	 * 添加商品
	 */
	@Override
	public int insert(Goods record) {
		return super.insert(record);
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
	 * 添加商品
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String addGood(Goods good,MultipartFile bigFile,MultipartFile smallFile,FileService<File> fileService) 
			throws IllegalStateException, IOException{
		JSONObject object = new JSONObject();
		
		com.xa.entity.File big = new com.xa.entity.File();
		fileService.uploadFile(bigFile, PhotoType.GOOD_ADV_PHOTO, big);
		
		com.xa.entity.File small = new com.xa.entity.File();
		fileService.uploadFile(smallFile, PhotoType.COMMODITY_THUMBNAIL, small);
		
		good.setAdvPic(big.getId());
		this.m.insert(good);
		
		GoodFile goodFile = new GoodFile();
		goodFile.setGoodId(good.getId());
		goodFile.setFileId(small.getId());
		this.goodFileMapper.insert(goodFile);
		
		object.accumulate(Constants.SUCCESS, true);
		return object.toString();
	}
	
	
	

	/**
	 * 异步添加商品
	 * 
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public String addGoodsByAjax(MultipartFile goodsAccordingToPositiveFile, // 商品正面照
			MultipartFile backGoodsAccordingToFile, // 商品背面照
			MultipartFile productProfileFile, // 商品侧面照
			MultipartFile goodsInvoiceFile, // 商品发票
			MultipartFile expressSingleFile, // 快递单
			Goods goods,
			HttpServletRequest request) throws IllegalStateException, IOException {
		
		JSONObject object = new JSONObject();
//		String namespace = "/upload/";
//		String goodsAccordingToPositiveFileName = goodsAccordingToPositiveFile.getOriginalFilename(); // 商品正面照
//		String backGoodsAccordingToFileName = backGoodsAccordingToFile.getOriginalFilename(); // 商品背面照
//		String productProfileFileName = productProfileFile.getOriginalFilename();//  商品側面照
//		String goodsInvoiceFileName =  goodsInvoiceFile.getOriginalFilename(); // 商品發票
//		String expressSingleFileName = expressSingleFile.getOriginalFilename(); //  快遞單
//		
//		String path = request.getSession().getServletContext().getRealPath("upload");
//		
//		String contextPath = request.getContextPath();
//		String goodsAccordingToPositiveUriPath = contextPath + namespace + goodsAccordingToPositiveFileName;
//		String backGoodsAccordingToUriPath = contextPath + namespace + backGoodsAccordingToFileName;
//		String productProfileUriPath = contextPath + namespace + productProfileFileName;
//		String goodsInvoiceUriPath = contextPath + namespace + goodsInvoiceFileName;
//		String expressSingleUriPath = contextPath + namespace + expressSingleFileName;
//		
//		com.xa.entity.File goodsAccordingToPositiveFileEntity = new com.xa.entity.File(); // 商品正面照
//		goodsAccordingToPositiveFileEntity.setName(goodsAccordingToPositiveFileName);
//		goodsAccordingToPositiveFileEntity.setPath(path);
//		goodsAccordingToPositiveFileEntity.setUriPath(goodsAccordingToPositiveUriPath);
//		goodsAccordingToPositiveFileEntity.setTypeId(PhotoType.GOODS_ACCORDING_TO_POSITIVE_TYPE.getValue());
//		this.fileMapper.insert(goodsAccordingToPositiveFileEntity);
//		goods.setGoodsAccordingToPositive(goodsAccordingToPositiveFileEntity.getId());
//		
//		com.xa.entity.File backGoodsAccordingToFileEntity = new com.xa.entity.File(); // 商品背面照
//		backGoodsAccordingToFileEntity.setName(backGoodsAccordingToFileName);
//		backGoodsAccordingToFileEntity.setPath(path);
//		backGoodsAccordingToFileEntity.setTypeId(PhotoType.BACK_GOODS_ACCORDING_TO_TYPE.getValue());
//		backGoodsAccordingToFileEntity.setUriPath(backGoodsAccordingToUriPath);
//		
//		this.fileMapper.insert(backGoodsAccordingToFileEntity);
//		goods.setBackGoodsAccordingTo(backGoodsAccordingToFileEntity.getId());
//		
//		com.xa.entity.File productProfileFileEntity = new com.xa.entity.File(); //商品側面照
//		productProfileFileEntity.setName(productProfileFileName);
//		productProfileFileEntity.setPath(path);
//		productProfileFileEntity.setUriPath(productProfileUriPath);
//		productProfileFileEntity.setTypeId(PhotoType.PRODUCT_PROFILE_TYPE.getValue());
//		this.fileMapper.insert(productProfileFileEntity);
//		goods.setProductProfile(productProfileFileEntity.getId());
//		
//		com.xa.entity.File goodsInvoiceFileEntity = new com.xa.entity.File(); //商品發票
//		goodsInvoiceFileEntity.setName(goodsInvoiceFileName);
//		goodsInvoiceFileEntity.setUriPath(goodsInvoiceUriPath);
//		goodsInvoiceFileEntity.setPath(path);
//		goodsInvoiceFileEntity.setTypeId(PhotoType.GOODS_INVOICE_TYPE.getValue());
//		this.fileMapper.insert(goodsInvoiceFileEntity);
//		goods.setGoodsInvoice(goodsInvoiceFileEntity.getId());
//		
//		com.xa.entity.File expressSingleFileEntity = new com.xa.entity.File(); //快遞單
//		expressSingleFileEntity.setPath(path);
//		expressSingleFileEntity.setUriPath(expressSingleUriPath);
//		expressSingleFileEntity.setTypeId(PhotoType.EXPRESS_SINGLE_TYPE.getValue());
//		expressSingleFileEntity.setName(expressSingleFileName);
//		this.fileMapper.insert(expressSingleFileEntity);
//		goods.setExpressSingle(expressSingleFileEntity.getId());
//		
//
//		File goodsAccordingToPositiveTargetFile = new File(path, goodsAccordingToPositiveFileName);
//		if (!goodsAccordingToPositiveTargetFile.exists()) {
//			goodsAccordingToPositiveTargetFile.mkdirs();
//		}
//
//		goodsAccordingToPositiveFile.transferTo(goodsAccordingToPositiveTargetFile); //商品正面照
//		
//		File backGoodsAccordingToTargetFile = new File(path,backGoodsAccordingToFileName);
//		if(!backGoodsAccordingToTargetFile.exists()){
//			backGoodsAccordingToTargetFile.mkdirs();
//		}
//		backGoodsAccordingToFile.transferTo(backGoodsAccordingToTargetFile);  //商品背面照
//		
//		File productProfileTargetFile = new File(path,productProfileFileName);
//		if(!productProfileTargetFile.exists()){
//			productProfileTargetFile.mkdirs();
//		}
//		productProfileFile.transferTo(productProfileTargetFile); // 商品側面照
//		
//		
//		File goodsInvoiceTargetFile = new File(path,goodsInvoiceFileName); // 商品發票
//		if(!goodsInvoiceTargetFile.exists()){
//			 goodsInvoiceTargetFile.mkdirs();
//		}
//		goodsInvoiceFile.transferTo(goodsInvoiceTargetFile);
//		
//		File expressSingleTargetFile = new File(path,expressSingleFileName); // 快遞單
//		if(!expressSingleTargetFile.exists()){
//			expressSingleTargetFile.mkdirs();
//		}
//		expressSingleFile.transferTo(expressSingleTargetFile);
//		
//		this.goodsMapper.insert(goods);
//		object.accumulate("success", true);
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
	 * 获取商品根据class id
	 * @return
	 */
	public String getGoodsByClassifi(Long classid,
			String nameS,
			Long buyerId, 
			Integer pageNum, 
			Integer pageSize, 
			String sign,
			ClassificationService<Classification> classificationService){
		JSONObject object = new JSONObject();
		if(!sign.equals(Security.getSign(new String[]{
			"classid","pageNum","pageSize","nameS","buyerId"	
		}))){
			 return object.accumulate(Constants.SUCCESS, false).accumulate(Constants.MSG, Msg.NOT_PERMISSION).toString();
		}
		if(!StringUtils.isBlank(nameS)){
			GoodsSearchRecord record = new GoodsSearchRecord();
			record.setAddTime(new Date());
			record.setName(nameS);
			record.setBuyerId(buyerId);
			goodsSearchRecordMapper.insert(record );
		}
		
		
		Map<String, Object> searchMap = new HashMap<String,Object>();
		
		String classids= classificationService.getChildIdByPid(classid);
		searchMap.put("classids", classids);
		searchMap.put("pid", classid);
		if(!StringUtils.isBlank(nameS)){
			searchMap.put("nameS", nameS);
		}
		PageHelper.startPage(pageNum, pageSize, true);
		Page<Goods> goodsPage = (Page<Goods>)this.m.getGoodsByClassId(searchMap );
		List<Goods> goodsList = goodsPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<goodsList.size();i++){
			JSONObject goodObj = new JSONObject();
			Goods good = goodsList.get(i);
			String name = good.getName();
			String info = good.getInfo();
			float price = good.getPrice();
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("goodId", good.getId());
			map.put("typeId", PhotoType.COMMODITY_THUMBNAIL.getValue());/*商品缩略图*/
			
		    JSONArray fileArray = new JSONArray();
			List<com.xa.entity.File> fileList = this.fileMapper.getFileByGoodIdAndTypeId(map );
			for(int j=0;j<fileList.size();j++){
				JSONObject fileObj = new JSONObject();
				com.xa.entity.File file= fileList.get(j);
				String uriPath= file.getUriPath();
				fileObj.accumulate("uriPath", uriPath);
				fileArray.add(fileObj);
			}
			goodObj.accumulate("name", name).accumulate("fileList",fileArray)
			.accumulate("price", price)
			.accumulate("id", good.getId());
			array.add(goodObj);
		}
		
		List<GoodsSearchRecord> gsrList= this.goodsSearchRecordMapper.getGSRByBuyerId(buyerId);
		JSONArray gsrArray = new JSONArray();
		for(int i=0; i<gsrList.size(); i++){
				GoodsSearchRecord gsr= gsrList.get(i);
				JSONObject gsrObj = new JSONObject();
				gsr.getAddTime();
				String historySearchRecord= gsr.getName();
				gsrObj.accumulate("historySearchRecord", historySearchRecord);
				gsrArray.add(gsrObj);
		}
		
		List<HotSearchDto> hsdList= this.goodsSearchRecordMapper.getHotSearch();
		JSONArray hsdArray = new JSONArray();
		for(int i=0;i<hsdList.size();i++){
			JSONObject hsdObj = new JSONObject();
			HotSearchDto hotSearchDto= hsdList.get(i);
			String name= hotSearchDto.getName();
			hotSearchDto.getTotal();
			hsdObj.accumulate("name", name);
			hsdArray.add(hsdObj);
		}
		
		object.accumulate(Constants.SUCCESS, true).accumulate(Constants.DATA, array)
		.accumulate("gsrArray", gsrArray)
		.accumulate("hsdArray", hsdArray)
		;
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


	

}
