package com.xa.junit3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xa.entity.FileType;
import com.xa.mapper.FileTypeMapper;

import junit.framework.TestCase;

public class FileTypeTest extends TestCase {
	
	private ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:mvc-context.xml");
	private FileTypeMapper fileTypeMapper;

	@Override
	protected void setUp() throws Exception {
		 this.fileTypeMapper = (FileTypeMapper)ctx.getBean("fileTypeMapper");
	}



	@Override
	protected void tearDown() throws Exception {
	}


	public void init(){
		try {
			this.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 图片类型
	 */
	public void testInsert(){
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
	}
}
