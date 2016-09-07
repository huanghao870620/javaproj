package com.ld.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.RoomConfigDto;
import com.ld.model.Room;
import com.ld.service.RoomService;
import com.opensymphony.xwork2.ActionContext;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(locations = "classpath:./spring/application.xml")
public class RoomServiceImplTest {

	@Autowired
	private RoomService<Room> roomService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAuditMessage() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String[] msgid = new String[2];
			msgid[0] = "1";
			msgid[1] = "2";
			map.put("msgid", msgid);

			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);

			roomService.auditMessage();
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testAddRoomConfig() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);

			RoomConfigDto dto = new RoomConfigDto();
			dto.setName("test");
			dto.setType("test");

			File file = new File("e:\\test\\ceshi\\test.jpg");
			String fileName = "test.jpg";

			Assert.assertTrue(roomService.addRoomConfig(context, dto, null, fileName));

			String realPath = "e:\\test\\ceshi\\linshi";
			ServletContext ctx = EasyMock.createMock(ServletContext.class);
			HttpSession session = EasyMock.createMock(HttpSession.class);
			HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(ctx.getRealPath("/temp")).andReturn(realPath);
			EasyMock.expect(session.getServletContext()).andReturn(ctx);
			EasyMock.expect(request.getSession()).andReturn(session);
			EasyMock.replay(ctx, session, request);
			ServletActionContext.setRequest(request);

			Assert.assertTrue(roomService.addRoomConfig(context, dto, file, fileName));
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testAddRoomConfigError() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);

			RoomConfigDto dto = new RoomConfigDto();
			dto.setName("test");
			dto.setType("test");

			File file = new File("e:\\test\\ceshi\\test.jpg");
			String fileName = "test.txt";

			Assert.assertTrue(!roomService.addRoomConfig(context, dto, file, fileName));

			File file2 = new File("e:\\test\\abc");
			String fileName2 = "test.jpg";
			Assert.assertTrue(!roomService.addRoomConfig(context, dto, file2, fileName2));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	//testAddRoomConfig“—∏≤∏«
	// @Test
	public void testUploadRoomconfigPic() {
	}

	@Test
	public void testIsExistRoomConfig() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			boolean result = roomService.isExistRoomConfig(context);
			Assert.assertTrue(result);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testUpdateRoomConfig() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);

			RoomConfigDto dto = new RoomConfigDto();
			dto.setName("test");
			dto.setType("test");
			dto.setId(new BigDecimal(1));

			File file = new File("e:\\test\\ceshi\\test.jpg");
			String fileName = "test.jpg";
			
			Assert.assertTrue(roomService.updateRoomConfig(context, dto, null, fileName));
			
			String realPath = "e:\\test\\ceshi\\linshi";
			ServletContext ctx = EasyMock.createMock(ServletContext.class);
			HttpSession session = EasyMock.createMock(HttpSession.class);
			HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
			EasyMock.expect(ctx.getRealPath("/temp")).andReturn(realPath);
			EasyMock.expect(session.getServletContext()).andReturn(ctx);
			EasyMock.expect(request.getSession()).andReturn(session);
			EasyMock.replay(ctx, session, request);
			ServletActionContext.setRequest(request);

			Assert.assertTrue(roomService.updateRoomConfig(context, dto, file, fileName));
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}
	@Test
	public void testUpdateRoomConfigError() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			ActionContext context = new ActionContext(map);
			context.setParameters(map);
			ActionContext.setContext(context);

			RoomConfigDto dto = new RoomConfigDto();
			dto.setName("test");
			dto.setType("test");
			dto.setId(new BigDecimal(1));

			File file = new File("e:\\test\\ceshi\\test.jpg");
			String fileName = "test.txt";

			Assert.assertTrue(!roomService.updateRoomConfig(context, dto, file, fileName));
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}
}
