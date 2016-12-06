package com.ld.action;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ld.common.DrawRandom;
import com.ld.util.RandomCreator;

@Controller
@Scope("prototype")
@ParentPackage("custom-default")
@Namespace(value = "/general/crateimage")
public class CreateImageAction extends BaseAction {

	/**
	* 
	*/
	private static final long serialVersionUID = -6028727140021732442L;

	private ByteArrayInputStream inputStream;

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	

	/**
	 * 生成验证码
	 * @return
	 * @throws Exception
	 */
	@Action(value="geneCode",results={@Result(type="stream",params={"image/jpeg","inputStream"})})	
	public String geneCode() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();

		// 设置浏览器不要缓存此图片
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String rands = RandomCreator.createRandom();
		BufferedImage image = new BufferedImage(RandomCreator.WIDTH, RandomCreator.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		// 产生图像
		DrawRandom dr = new DrawRandom(g, rands);
		dr.drawBackground();
		dr.drawRands();

		// 结束图像 的绘制 过程， 完成图像
		g.dispose();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpeg", outputStream);
		ByteArrayInputStream input = new ByteArrayInputStream(outputStream.toByteArray());
		this.setInputStream(input);
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("checkCode", rands);
		input.close();
		outputStream.close();
		return SUCCESS;
	}
	
	@Action(value="byteStreamTest")
	public void byteSreamTest() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setCharacterEncoding("utf-8");
//		response.setHeader("Content-type", "text/html;charset=utf-8");
//		response.setContentType("text/html;charset=utf-8");
//		response.getWriter().write("中国");
//		
//		ServletContext context = ServletActionContext.getServletContext();
//		String path = context.getRealPath("/res/images/哈哈.jpg");
//		System.out.println("文件路径："+path);
//		String fileName = path.substring(path.lastIndexOf("\\")+1);
//		fileName = URLEncoder.encode(fileName,"utf-8");
//		System.out.println(fileName);
//		response.setHeader("content-disposition", "attachment;filename="+fileName);
//		//将图片已字节流的形式写给客户机
//		InputStream is = context.getResourceAsStream("/res/images/9.jpg");
//		OutputStream out = response.getOutputStream();
//		byte[] buffer = new byte[1024];
//		int len = 0;
//		while((len = is.read(buffer))!=-1){
//			out.write(buffer, 0, len);
//		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getRequestDispatcher("");
		String path = ServletActionContext.getServletContext().getInitParameter("contextConfigLocation");
		System.out.println(path);
		response.getOutputStream().write(path.getBytes());
	}

}
