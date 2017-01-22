package com.xa.service.impl;


import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.AccountType;
import com.xa.entity.BankType;
import com.xa.entity.Brand;
import com.xa.entity.Buyers;
import com.xa.entity.Classification;
import com.xa.entity.Country;
import com.xa.entity.Currency;
import com.xa.entity.File;
import com.xa.entity.FileType;
import com.xa.entity.GeneralLedger;
import com.xa.entity.Norms;
import com.xa.entity.UploadType;
import com.xa.mapper.AccountTypeMapper;
import com.xa.mapper.BankTypeMapper;
import com.xa.mapper.BrandMapper;
import com.xa.mapper.BuyersMapper;
import com.xa.mapper.ClassificationMapper;
import com.xa.mapper.CountryMapper;
import com.xa.mapper.CurrencyMapper;
import com.xa.mapper.FileTypeMapper;
import com.xa.mapper.GeneralLedgerMapper;
import com.xa.mapper.NormsMapper;
import com.xa.mapper.UploadTypeMapper;
import com.xa.service.InitApplicationContextService;
import com.xa.service.impl.BaseServiceImpl;

@Service
@Transactional
public class InitApplicationContextServiceImpl extends BaseServiceImpl<Buyers, BuyersMapper>
		implements InitApplicationContextService<Buyers> {
	
	@Autowired
	private AccountTypeMapper accountTypeMapper;
	
	@Autowired
	private BankTypeMapper bankTypeMapper;
	
	@Autowired
	private FileTypeMapper fileTypeMapper;
	
	@Autowired
	private GeneralLedgerMapper generalLedgerMapper;
	
	@Autowired
	private ClassificationMapper classificationMapper;
	
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private CountryMapper countryMapper;
	
	@Autowired
	private CurrencyMapper currencyMapper;
	
	@Autowired
	private NormsMapper normsMapper;
	
	@Autowired
	private UploadTypeMapper uploadTypeMapper;

	/**
	 * 账号类型
	 */
	public void initAccountType(){
		AccountType weChat = new AccountType();
		weChat.setName("微信");
		this.accountTypeMapper.insert(weChat);
		
		AccountType faceBook = new AccountType();
		faceBook.setName("faceBook");
		this.accountTypeMapper.insert(faceBook);
		
		AccountType twitter = new AccountType();
		twitter.setName("推特");
		this.accountTypeMapper.insert(twitter);
	}

	/**
	 * 银行类型
	 */
	public void initBankType(){
		BankType industrialAndCom = new BankType(); //工商银行
		industrialAndCom.setName("工商银行");
		this.bankTypeMapper.insert(industrialAndCom);
		
		BankType consBank = new BankType(); 
		consBank.setName("建设银行");
		this.bankTypeMapper.insert(consBank);
		
		BankType bankOfChina = new BankType();
		bankOfChina.setName("中国银行");
		this.bankTypeMapper.insert(bankOfChina);
		
		BankType agricChina = new BankType();
		agricChina.setName("中国农业银行");
		this.bankTypeMapper.insert(agricChina);
		
		BankType banComm = new BankType();
		banComm.setName("交通银行");
		this.bankTypeMapper.insert(banComm);
		
		BankType merchBank = new BankType();
		merchBank.setName("招商银行");
		this.bankTypeMapper.insert(merchBank);
	}
	
	/**
	 * 照片类型
	 */
	public void initFileType() {
		FileType sidPhotoType = new FileType(); // 学生证照片
		sidPhotoType.setName("学生证照片");
		fileTypeMapper.insert(sidPhotoType);
		
		FileType admNoticeFileType = new FileType(); // 录取通知书
		admNoticeFileType.setName("录取通知书");
		fileTypeMapper.insert(admNoticeFileType);
		
		FileType passportFileType = new FileType(); // 护照	
		passportFileType.setName("护照");
		this.fileTypeMapper.insert(passportFileType);
		
		FileType lifePhotoFileType = new FileType(); // 生活照
		lifePhotoFileType.setName("生活照");
		this.fileTypeMapper.insert(lifePhotoFileType);
		
		FileType goodsAccordingToPositiveType = new FileType();
		goodsAccordingToPositiveType.setName("商品正面照");
		this.fileTypeMapper.insert(goodsAccordingToPositiveType);
		
		FileType backGoodsAccordingToType = new FileType();
		backGoodsAccordingToType.setName("商品背面照");
		this.fileTypeMapper.insert(backGoodsAccordingToType);
		
		FileType productProfileType = new FileType();
		productProfileType.setName("商品側面照");
		this.fileTypeMapper.insert(productProfileType);
		
		FileType goodsInvoiceType = new FileType();
		goodsInvoiceType.setName("商品發票");
		this.fileTypeMapper.insert(goodsInvoiceType);
		
		FileType expressSingleType = new FileType();
		expressSingleType.setName("快遞單");
		this.fileTypeMapper.insert(expressSingleType);
		
		FileType headPortraitType = new FileType();
		headPortraitType.setName("客户头像");
		this.fileTypeMapper.insert(headPortraitType);
		
		FileType classLogo = new FileType();
		classLogo.setName("分类logo");
		this.fileTypeMapper.insert(classLogo);
		
		FileType goodThumb = new FileType();
		goodThumb.setName("商品缩略图");
		this.fileTypeMapper.insert(goodThumb);
		
		FileType goodBigFigure = new FileType();
		goodBigFigure.setName("商品大图");
		this.fileTypeMapper.insert(goodBigFigure);
		
		FileType goodCutFigure = new FileType();
		goodCutFigure.setName("商品切图");
		this.fileTypeMapper.insert(goodCutFigure);
		
		FileType brandPic = new FileType();
		brandPic.setName("品牌图片");
		this.fileTypeMapper.insert(brandPic);

		FileType countryPic = new FileType();
		countryPic.setName("国家图片");
		this.fileTypeMapper.insert(countryPic);
		
		FileType advPic = new FileType();
		advPic.setName("商品广告图");
		this.fileTypeMapper.insert(advPic);
		
		FileType venuePic = new FileType();
		venuePic.setName("会场广告图");
		this.fileTypeMapper.insert(venuePic);
		
		FileType verFrontPhoto = new FileType();
		verFrontPhoto.setName("认证身份证正面照");
		this.fileTypeMapper.insert(verFrontPhoto);
		
		FileType verBackPhoto = new FileType();
		verBackPhoto.setName("认证身份证背面照");
		this.fileTypeMapper.insert(verBackPhoto);
	}
	
	
	/**
	 * 初始化分类
	 */
	public void initClassification() {
		/************************分类*****************************/
		Classification cla0 = new Classification();
		cla0.setName("潮流美包");
		cla0.setPid(-1L);
		this.classificationMapper.insert(cla0);
		
		Classification cla1 = new Classification();
		cla1.setName("家电数码");
		cla1.setPid(-1L);
		this.classificationMapper.insert(cla1);
		
		Classification cla2 = new Classification();
		cla2.setName("美妆个护");
		cla2.setPid(-1L);
		this.classificationMapper.insert(cla2);
		
		Classification cla3 = new Classification();
		cla3.setName("母婴用品");
		cla3.setPid(-1L);
		this.classificationMapper.insert(cla3);
		
		Classification cla4 = new Classification();
		cla4.setName("休闲美食");
		cla4.setPid(-1L);
		this.classificationMapper.insert(cla4);
		
		Classification cla5 = new Classification();
		cla5.setName("营养保健");
		cla5.setPid(-1L);
		this.classificationMapper.insert(cla5);
		
		
		/***********************分类*****************************/
	}
	
	/**
	 * 初始化品牌
	 */
	public void initBrand(){
		/*********************品牌********************************/
		
		Brand cla8 = new Brand();
		cla8.setName("LANCOME");
		this.brandMapper.insert(cla8);
		
		Brand cla9 = new Brand();
		cla9.setName("WYETH");
		this.brandMapper.insert(cla9);
		
		Brand cla10 = new Brand();
		cla10.setName("APTAMIL");
		this.brandMapper.insert(cla10);
		
		Brand cla11 = new Brand();
		cla11.setName("KAO");
		this.brandMapper.insert(cla11);
		
		Brand cla12 = new Brand();
		cla12.setName("NATURE'S WAY");
		this.brandMapper.insert(cla12);
		
		Brand cla13 = new Brand();
		cla13.setName("LOREAL");
		this.brandMapper.insert(cla13);
		
		Brand cla14 = new Brand();
		cla14.setName("CORNICHE");
		this.brandMapper.insert(cla14);
		
		Brand cla15 = new Brand();
		cla15.setName("NEOCELL");
		this.brandMapper.insert(cla15);
		
		Brand cla16 = new Brand();
		cla16.setName("LORENZ");
		this.brandMapper.insert(cla16);
		
		Brand cla17 = new Brand();
		cla17.setName("**");
		this.brandMapper.insert(cla17);
		
		Brand cla18 = new Brand();
		cla18.setName("MOONY");
		this.brandMapper.insert(cla18);
		
		Brand cla19 = new Brand();
		cla19.setName("OWL");
		this.brandMapper.insert(cla19);
		
		Brand cla20 = new Brand();
		cla20.setName("CHANEL");
		this.brandMapper.insert(cla20);
		
		Brand cla21 = new Brand();
		cla21.setName("格力高");
		this.brandMapper.insert(cla21);
		
		Brand cla22 = new Brand();
		cla22.setName("BACI");
		this.brandMapper.insert(cla22);
		
		Brand cla23 = new Brand();
		cla23.setName("FHLFELDO");
		this.brandMapper.insert(cla23);
		
		/***********************品牌**********************************/
	}
	
	/**
	 * 初始化国家
	 */
	public void initCountry(){
		/***********************国家*********************************/
		Country cla24 = new Country();
		cla24.setName("美国");
		this.countryMapper.insert(cla24);
		
		Country cla25 = new Country();
		cla25.setName("法国");
		this.countryMapper.insert(cla25);
		
		Country cla26 = new Country();
		cla26.setName("日本");
		this.countryMapper.insert(cla26);
		
		Country cla27 = new Country();
		cla27.setName("德国");
		this.countryMapper.insert(cla27);
		
		Country cla28 = new Country();
		cla28.setName("英国");
		this.countryMapper.insert(cla28);
		
		Country cla29 = new Country();
		cla29.setName("韩国");
		this.countryMapper.insert(cla29);
		
		Country cla30 = new Country();
		cla30.setName("荷兰");
		this.countryMapper.insert(cla30);
		
		Country cla31 = new Country();
		cla31.setName("意大利");
		this.countryMapper.insert(cla31);
		/*************************国家******************************/
	}
	
	/**
	 * 初始化币种
	 */
	public void initCurrency() {
		Currency currency1 = new Currency(); 
		currency1.setName("人民币");
		currency1.setSymbol("¥");
		this.currencyMapper.insert(currency1);
		
		Currency currency2 = new Currency();
		currency2.setName("卢布");
		currency2.setSymbol("руб");
		this.currencyMapper.insert(currency2);
		
		Currency currency3 = new Currency(); 
		currency3.setName("新台币");
		currency3.setSymbol("NT");
		this.currencyMapper.insert(currency3);
		
		Currency currency4 = new Currency();
		currency4.setName("欧元");
		currency4.setSymbol("€");
		this.currencyMapper.insert(currency4);
		
		Currency currency5 = new Currency();
		currency5.setName("韩元");
		currency5.setSymbol("₩");
		this.currencyMapper.insert(currency5);
		
		Currency currency6 = new Currency();
		currency6.setName("美元");
		currency6.setSymbol("$");
		this.currencyMapper.insert(currency6);
		
		Currency currency7 = new Currency();
		currency7.setName("英镑");
		currency7.setSymbol("£");
		this.currencyMapper.insert(currency7);
	}
	
	/**
	 * 初始化规格类型
	 */
	public void initNorms() {
		Norms norms1 = new Norms();
		norms1.setName("ml");
		this.normsMapper.insert(norms1);
		
		Norms norms2 = new Norms();
		norms2.setName("g");
		this.normsMapper.insert(norms2);
		
		Norms norms3 = new Norms();
		norms3.setName("g/片");
		this.normsMapper.insert(norms3);
		
		Norms norms4 = new Norms();
		norms4.setName("g/支");
		this.normsMapper.insert(norms4);
	}
	
	/**
	 * 初始化上传者类型
	 */
	public void initUploadType(){
		UploadType ut1 = new UploadType();
		ut1.setName("买手");
		this.uploadTypeMapper.insert(ut1);
		
		UploadType ut2 = new UploadType();
		ut2.setName("寻安");
		this.uploadTypeMapper.insert(ut2);
		
		UploadType ut3 = new UploadType();
		ut3.setName("商家");
		this.uploadTypeMapper.insert(ut3);
	}
	
	
	/**
	 * 初始化应用基础数据
	 */
	public void initApp(){
		/****************账号类型*****************/
		this.initAccountType();	
		
		/**********************银行类型***************************/
		this.initBankType();
		
		/********************照片类型***************************/
		this.initFileType();
		
		/*******************总分类类型********************/
		this.initClassification();
		
		/****************国家**************************/
		this.initCountry();
		
		/******************币种*****************************/
		this.initCurrency();
		
		/********************规格类型**************************/
		this.initNorms();
		
		/******************上传类型*****************************/
		this.initUploadType();
	}
}
