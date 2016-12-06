package com.ld.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.ViewpointDto;
import com.ld.model.MessageBO;
import com.ld.model.Viewpoint;
import com.ld.service.ViewpointService;
import com.opensymphony.xwork2.ActionContext;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class ViewpointServiceImplTest extends TestCase {

	@Autowired
	private ViewpointService<Viewpoint> viewpointService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueryViewpointByAjax() {
		try {
			queryViewPoint();
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}
	
	private List<Viewpoint> queryViewPoint() throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();

		String[] pageArr = new String[1];
		pageArr[0] = "1";
		String[] rowArr = new String[1];
		rowArr[0] = "1";

		map.put("rows", pageArr);
		map.put("page", rowArr);

		ActionContext context = new ActionContext(map);
		context.setParameters(map);
		ActionContext.setContext(context);

		HttpServletResponse resp = EasyMock.createMock(HttpServletResponse.class);
		resp.setCharacterEncoding("UTF-8");
		EasyMock.expectLastCall();

		PrintWriter writer = new PrintWriter(new FileWriter(new File("e:\\test.txt")), true);
		EasyMock.expect(resp.getWriter()).andReturn(writer);

		EasyMock.replay(resp);
		ServletActionContext.setResponse(resp);

		return viewpointService.queryViewpointByAjax();
	}
	

	@Test
	public void testAddViewpoint() {
		MessageBO result = null;
		try {
			viewpointService.addViewpoint(null);

			ViewpointDto viewpointDto = new ViewpointDto();
			result = viewpointService.addViewpoint(viewpointDto);
			Assert.assertEquals("新增观点失败,请按要求填写观点信息!",result.getMessage());

			viewpointDto.setTitle("title");
			result = viewpointService.addViewpoint(viewpointDto);
			Assert.assertEquals("新增观点失败,请按要求填写观点信息!",result.getMessage());

			viewpointDto.setContent("content");
			result = viewpointService.addViewpoint(viewpointDto);
			Assert.assertEquals("新增观点成功!",result.getMessage());

			viewpointDto.setSpecial("special1,,special3,,special5");
			result = viewpointService.addViewpoint(viewpointDto);
			Assert.assertEquals("新增观点失败,请按要求填写观点信息!",result.getMessage());

			viewpointDto.setFileId("1,,,4,5");
			result = viewpointService.addViewpoint(viewpointDto);
			Assert.assertEquals("新增观点失败,请按要求填写观点信息!",result.getMessage());

			viewpointDto.setSpecial("special1,,,special4,special5");
			viewpointDto.setFileId("1,,3,,5");
			result = viewpointService.addViewpoint(viewpointDto);
			Assert.assertEquals("新增观点失败,请按要求填写观点信息!",result.getMessage());

			viewpointDto.setSpecial("special1,special2,special3,special4,special5");
			viewpointDto.setFileId("1,2,3,4,5");
			result = viewpointService.addViewpoint(viewpointDto);
			Assert.assertEquals("新增观点成功!",result.getMessage());

		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testAddViewpointException() {
		ViewpointDto viewpointDto = new ViewpointDto();
		viewpointDto.setTitle("title");
		viewpointDto.setContent("content");
		viewpointDto.setSpecial("special1,special2,special3,special4,special5");
		viewpointDto.setFileId("a1,a2,a3,a4,a5");
		MessageBO result = viewpointService.addViewpoint(viewpointDto);
		assertEquals("新增观点失败!", result.getMessage());
	}

	@Test
	public void testDelViewpoint() {
		MessageBO result = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);
			result = viewpointService.delViewpoint();
			Assert.assertEquals("删除观点失败", result.getMessage());

			String[] ids = new String[2];
			ids[0] = "1";
			ids[1] = "2";
			map.put("id", ids);
			ActionContext.setContext(context);
			result = viewpointService.delViewpoint();
			Assert.assertEquals("删除观点成功", result.getMessage());

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testDelViewpointException() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1,2");
		ActionContext context = new ActionContext(map);
		context.setParameters(map);
		ActionContext.setContext(context);
		MessageBO result = viewpointService.delViewpoint();
		assertEquals("删除观点失败", result.getMessage());
	}

	@Test
	public void testFindViewpointById() {
		MessageBO result = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);
			result = viewpointService.findViewpointById();
			Assert.assertEquals("查询观点失败", result.getMessage());

			String[] ids = new String[2];
			ids[0] = "1";
			ids[1] = "2";
			map.put("id", ids);
			context.setParameters(map);
			ActionContext.setContext(context);
			//异常情况
			result = viewpointService.findViewpointById();
			Assert.assertEquals("查询观点失败", result.getMessage());
			
			List<Viewpoint> list = queryViewPoint();
			ids[0] = list.get(0).getViewpointId().toString();
			map.put("id", ids);
			context.setParameters(map);
			ActionContext.setContext(context);
			result = viewpointService.findViewpointById();
			Assert.assertEquals("查询观点成功", result.getMessage());
			
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testUpdateViewpoint() {
		MessageBO result = null;
		try {
			result = viewpointService.updateViewpoint(null);
			Assert.assertEquals("更新观点失败,请填按要求填写观点信息!", result.getMessage());
			
			List<Viewpoint> list = queryViewPoint();
			ViewpointDto viewpointDto = new ViewpointDto();
			viewpointDto.setViewpointId(list.get(0).getViewpointId());
			viewpointDto.setTitle("title");
			viewpointDto.setContent("content");
			viewpointDto.setSpecial("special1,special2,special3");
			viewpointDto.setFileId("1,2,3");
			result = viewpointService.updateViewpoint(viewpointDto);
			Assert.assertEquals("更新观点成功!", result.getMessage());
			
			viewpointDto.setFileId("a1,a2,a3");
			//异常情况
			result = viewpointService.updateViewpoint(viewpointDto);
			Assert.assertEquals("更新观点失败!", result.getMessage());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Test
	public void testUploadViewpointPic() {
		MessageBO result = null;
		try {
			File file = new File("e:\\test\\ceshi\\test.jpg");
			String fileName = "test.jpg";
			result = viewpointService.uploadViewpointPic(file,null);
			Assert.assertEquals("上传的文件格式不正确,请重新上传!", result.getMessage());
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);
			String realPath = "e:\\test\\ceshi\\linshi";
			ServletContext ctx = EasyMock.createMock(ServletContext.class);
			HttpSession session = EasyMock.createMock(HttpSession.class);
			HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(ctx.getRealPath("/temp")).andReturn(realPath);
			EasyMock.expect(session.getServletContext()).andReturn(ctx);
			EasyMock.expect(request.getSession()).andReturn(session);
			EasyMock.replay(ctx,session,request);
			ServletActionContext.setRequest(request);
			result = viewpointService.uploadViewpointPic(file,fileName);
			Assert.assertEquals("上传图片成功!", result.getMessage());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void testUploadViewpointPic2() {
		MessageBO result = null;
		try {
			File file = new File("e:\\test\\ceshi\\large.jpg");
			String fileName = "large.jpg";
			
			Map<String, Object> map = new HashMap<String, Object>();
			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);
			String realPath = "e:\\test\\ceshi\\linshi";
			ServletContext ctx = EasyMock.createMock(ServletContext.class);
			HttpSession session = EasyMock.createMock(HttpSession.class);
			HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(ctx.getRealPath("/temp")).andReturn(realPath);
			EasyMock.expect(session.getServletContext()).andReturn(ctx);
			EasyMock.expect(request.getSession()).andReturn(session);
			EasyMock.replay(ctx,session,request);
			ServletActionContext.setRequest(request);
			result = viewpointService.uploadViewpointPic(file,fileName);
			Assert.assertEquals("请上传规定尺寸的图片!", result.getMessage());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@Test
	public void testUploadViewpointPicException() {
		MessageBO result = null;
		try {
			File file = new File("e:\\test\\ceshi");
			String fileName = "test.jpg";
			
			Map<String, Object> map = new HashMap<String, Object>();
			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);
			String realPath = "e:\\test\\ceshi\\linshi";
			ServletContext ctx = EasyMock.createMock(ServletContext.class);
			HttpSession session = EasyMock.createMock(HttpSession.class);
			HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(ctx.getRealPath("/temp")).andReturn(realPath);
			EasyMock.expect(session.getServletContext()).andReturn(ctx);
			EasyMock.expect(request.getSession()).andReturn(session);
			EasyMock.replay(ctx,session,request);
			ServletActionContext.setRequest(request);
			result = viewpointService.uploadViewpointPic(file,fileName);
			Assert.assertEquals("上传的文件格式不正确,请重新上传!", result.getMessage());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
