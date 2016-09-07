package com.ld.test.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DealFreemarker {

	/**
	 * Éú³É
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public void generate() throws IOException, TemplateException{
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
//		cfg.setDirectoryForTemplateLoading(new File(this.getClass().getResource("/")+"freemarkerfile"));
		cfg.setDirectoryForTemplateLoading(new File("D:/javawork/future/target/classes/" +"freemarkerfile"));
//		cfg.setSharedVariable("company", "FooInc.");
		Template temp = cfg.getTemplate("test.ftl");
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("user", "Big Joe");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer out = new OutputStreamWriter(baos);
		temp.process(root, out);
		System.out.println(baos.toString());
	}
}
