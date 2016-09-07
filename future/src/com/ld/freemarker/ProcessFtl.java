package com.ld.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * ftl to string
 * @author hao.huang
 *
 */
public class ProcessFtl {

	private  Map<String,Object> root;
	private String ftlname;
	private String basePath;
	
	public ProcessFtl(Map<String,Object> root, String ftlname, String basePath){
		this.root = root;
		this.ftlname = ftlname;
		this.basePath = basePath;
		this.root.put("basePath", basePath);
	}
	
	
	/**
	 * �����ļ�
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public String process(){
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Template temp = null;
		try {
			String pathOfPrefix = this.getClass().getResource("/")+"freemarker" ; 
			File file = new File(this.deletePrefix(pathOfPrefix));
			cfg.setDirectoryForTemplateLoading(file);
			temp = cfg.getTemplate(this.ftlname);
			root.put("user", "Big Joe");
			Writer out = new OutputStreamWriter(baos);
			temp.process(root, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toString();
	}
	
	/**
	 * ɾ��ǰ׺
	 * @param pathOfPrefix
	 */
	private String deletePrefix(String pathOfPrefix){
		return pathOfPrefix.replace("file:", "");
	}
	
}
