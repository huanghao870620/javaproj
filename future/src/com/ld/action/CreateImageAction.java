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
	 * ������֤��
	 * @return
	 * @throws Exception
	 */
	@Action(value="geneCode",results={@Result(type="stream",params={"image/jpeg","inputStream"})})	
	public String geneCode() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();

		// �����������Ҫ�����ͼƬ
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String rands = RandomCreator.createRandom();
		BufferedImage image = new BufferedImage(RandomCreator.WIDTH, RandomCreator.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		// ����ͼ��
		DrawRandom dr = new DrawRandom(g, rands);
		dr.drawBackground();
		dr.drawRands();

		// ����ͼ�� �Ļ��� ���̣� ���ͼ��
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
//		response.getWriter().write("�й�");
//		
//		ServletContext context = ServletActionContext.getServletContext();
//		String path = context.getRealPath("/res/images/����.jpg");
//		System.out.println("�ļ�·����"+path);
//		String fileName = path.substring(path.lastIndexOf("\\")+1);
//		fileName = URLEncoder.encode(fileName,"utf-8");
//		System.out.println(fileName);
//		response.setHeader("content-disposition", "attachment;filename="+fileName);
//		//��ͼƬ���ֽ�������ʽд���ͻ���
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
