package com.xa.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/**
 * 
 * @author burgess
 *
 */
public class GenerateFilePath {

	private  final char digit[] = {'0','1','2','3','4','5','6','7','8','9'};
//	private static final char letters[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	
	private String fileName;
	private String suffix;
	private Long typeId;
	private String fileSpace;
	private String date4Str;
	private String thirtyRandomNumber;/*30位*/
	private String fortyRandomNumber;/*40位*/
	private String sixteenRandomNumber;/*60位*/
	private String uri;
	
	public GenerateFilePath(Long typeId, String suffix){
		 this.typeId = typeId;
		 this.suffix = suffix;
		 
		 
		 /*30位*/
		 StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for(int i=0; i<30; i++){
				sb.append(digit[random.nextInt(10)]);
			}
			this.thirtyRandomNumber = sb.toString();
			
			
			/*60位*/
			StringBuilder sbSixty = new StringBuilder();
			Random randomSixty = new Random();
			for(int i=0;i<16;i++){
				sbSixty.append(digit[randomSixty.nextInt(10)]);
			}
			this.sixteenRandomNumber = sbSixty.toString();
			
			
			/*40位*/
			StringBuilder sbForty = new StringBuilder();
			Random randomForty = new Random();
			for(int i=0;i<40;i++){
				sbForty.append(digit[randomForty.nextInt(10)]);
			}
			this.fortyRandomNumber = sbForty.toString();
		 
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			this.date4Str = sdf.format(new Date());
			
			
			StringBuilder fileSpaceSb = new StringBuilder();
			fileSpaceSb.append(this.thirtyRandomNumber).append(java.io.File.separatorChar).append(typeId.toString())
			.append(java.io.File.separatorChar).append(this.date4Str).append(this.sixteenRandomNumber)
			.append(java.io.File.separatorChar);
			
			this.fileSpace = fileSpaceSb.toString();
			this.uri = fileSpaceSb.append(this.fortyRandomNumber).append(suffix).toString();
			this.fileName = new StringBuffer(this.fortyRandomNumber).append(suffix).toString();
			
	}
	
	
	/**
	 * 获取30位随机数
	 * @return
	 */
	public  String getThirtyRandomNumber(){
		return this.thirtyRandomNumber;
	}
	
	/**
	 * 获取16位随机数
	 * @return
	 */
	public  String getSixteenRandomNumber() {
		return this.sixteenRandomNumber;
	}
	
	/**
	 * 获取40位随机数
	 * @return
	 */
	public  String getFortyRandomNumber() {
		return this.fortyRandomNumber;
	}
	
	/**
	 * 获取uri
	 * @param typeId
	 * @return
	 */
	public  String  getUri() {
		return this.uri;
	}
	
	/**
	 * 获取文件名
	 * @param suffix
	 * @return
	 */
	public  String getFileName() {
		return this.fileName;
	}
	
	/**
	 * 获取
	 * @param typeId
	 * @return
	 */
	public  String getFileSpace() {
		return this.fileSpace;
	}
	
	/**
	 * 
	 * @return
	 */
	public  String getDate4Str() {
		return this.date4Str;
	}
	
}
