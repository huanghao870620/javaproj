package com.sh.lw;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author huang.hao
 * @version 1.0
 * @created 14-ÆßÔÂ-2016 16:55:50
 */
public class SelfClassLoad  extends ClassLoader {
	
	private String srcPath;

	public SelfClassLoad (String srcPath){
		 this.srcPath = srcPath;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String classFileName = srcPath + "\\" + name + ".class";
		try {
			FileInputStream fis = new FileInputStream(classFileName);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			cypher(fis,bos);
			byte [] bytes = bos.toByteArray();
			fis.close();
			bos.close();
			return defineClass(null, bytes, 0, bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.findClass(name);
	}
	
	

	public static void cypher(InputStream in, OutputStream out) throws Exception{
		  int b = -1;
		  while((b = in.read()) != -1){
			  out.write(b^0xff);
		  }
	}


}