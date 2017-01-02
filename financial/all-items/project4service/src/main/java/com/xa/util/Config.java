package com.xa.util;

public class Config {

	/**
	 * 获取上传根路径
	 * @return
	 */
	public static String getUploadBasePath(){
		return Configuration.getConfigValue("upload_base_path");
	}
}
