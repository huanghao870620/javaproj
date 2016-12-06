package com.sh.lw;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CypherFile {

	public static void main(String[] args) throws Exception{
		 String srcPath = args[0];
		 String desDir = args[1];
		 FileInputStream fis = new FileInputStream(srcPath);
		 String desFileName = srcPath.substring(srcPath.lastIndexOf("\\") +1);
		 String desPath = desDir + "\\" + desFileName;
		 FileOutputStream fos=  new FileOutputStream(desPath);
		 cypher(fis,fos);
		 fis.close();
		 fos.close();
	}
	
	public static void cypher(InputStream in, OutputStream out) throws Exception{
		int b = -1;
		while((b = in.read()) != -1){
			out.write(b ^ 0xff);
		}
	}
}
