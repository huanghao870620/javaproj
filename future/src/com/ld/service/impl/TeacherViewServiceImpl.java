package com.ld.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.common.Dictionary;
import com.ld.dto.PrivateMessageDto;
import com.ld.dto.QuestionAndReplyDto;
import com.ld.dto.RoomConfigDto;
import com.ld.dto.TeacherViewDto;
import com.ld.mapper.CfileMapper;
import com.ld.mapper.PrivateMessageMapper;
import com.ld.mapper.PrivateMessageReplyMapper;
import com.ld.mapper.RoomConfigMapper;
import com.ld.mapper.RoomconfigCfileMapper;
import com.ld.mapper.TeacherViewMapper;
import com.ld.mapper.TeacherviewCfileMapper;
import com.ld.mapper.UserMapper;
import com.ld.mapper.UserRoleMapper;
import com.ld.model.Cfile;
import com.ld.model.MessageBO;
import com.ld.model.PrivateMessage;
import com.ld.model.PrivateMessageReply;
import com.ld.model.RoomConfig;
import com.ld.model.RoomconfigCfile;
import com.ld.model.TeacherView;
import com.ld.model.TeacherviewCfile;
import com.ld.model.User;
import com.ld.model.UserRole;
import com.ld.service.TeacherViewService;
import com.ld.sftp.SftpServ;
import com.ld.sftp.SftpServ.UploadStatus;
import com.ld.util.Common;
import com.ld.util.LoginUtil;
import com.ld.util.Logs;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

@Service
@Transactional
public class TeacherViewServiceImpl  extends BaseServiceImpl<TeacherView, TeacherViewMapper>
implements TeacherViewService<TeacherView> {

	@Autowired
	private CfileMapper  cfileMapper;
	
	@Autowired
	private TeacherViewMapper  teacherViewMapper;
	
	@Autowired
	private TeacherviewCfileMapper teacherviewCfileMapper;
	
	@Autowired
	private  PrivateMessageMapper  privateMessageMapper;
	
	@Autowired(required = true)
	private SftpServ sftp;
	
	@Autowired
	private RoomConfigMapper roomConfigMapper;

	@Autowired
	private RoomconfigCfileMapper roomconfigCfileMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private PrivateMessageReplyMapper privateMessageReplyMapper;
	
	
	
	
	@Override
	public JSONObject generateTeacherView(ActionContext ctx1,TeacherViewDto dto) {
		User user = (User)ctx1.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		TeacherView  tv = new TeacherView();
		if(dto!=null && StringUtils.isNotEmpty(dto.getViewContent()))
		{
			// 插入 讲师观点
			tv.setTeacherId(user.getUserId());
			tv.setTeacherName(user.getName());
			tv.setCreateTime(new Date());
			tv.setAdviceId(dto.getAdviceId());
			tv.setViewContent(dto.getViewContent());
			tv.setMineralId(dto.getMineralId());
		    insert(tv);
		}
	    // 插入讲师观点对应图片中间表
	    if(dto.getCfileId()!=null)
	    {
		    TeacherviewCfile  tc =new TeacherviewCfile();
		    tc.setViewId(tv.getId());
		    tc.setCfileId(dto.getCfileId());
		    teacherviewCfileMapper.insert(tc);
	    }
		JSONObject obj = new JSONObject();
		obj.accumulate("cfileId", dto.getCfileId());
		obj.accumulate("content", dto.getViewContent());
		obj.accumulate("adviceId", dto.getAdviceId());
		obj.accumulate("mineralId", dto.getMineralId());
		obj.accumulate("id", tv.getId());
		
		return obj;
		
	}
	
	/**
	 * 判断当前登录客户角色判断，返回相应数据
	 */
	public String justifyCurrentRole(ActionContext ctx) {
		String returnRoleStr ="0";
		User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		ctx.put("customer", user);
		// 获取当前登录用户是否 关联过后台用户，有则获取对应的关联用户所属角色, 否则角色设置为普通
		if(ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID)!=null)
		{
			BigDecimal roleId = (BigDecimal) ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID);
			if(roleId.toString().equals(Dictionary.TEACHER_TYPE_ROLE))
			{
				returnRoleStr =Dictionary.TEACHER_TYPE_ROLE;
			}
		}
		return returnRoleStr;
	}
	/**
	 *  删除距离当前时间 前两天之前的讲师观点数据
	 */
	public  void dealGoneTeacherView(){
		// 获取过期讲师观点数据，清除讲师观点，观点对应文件关联表， 观点对应私信数据清除
			List<TeacherView> tvList_gone = teacherViewMapper.selectGoneData();
			if(tvList_gone!=null && !tvList_gone.isEmpty())
			{
				for(TeacherView  tv:  tvList_gone)
				{
					// 删除讲师观点对应文件
					TeacherviewCfile viewCfile = teacherviewCfileMapper.findById(tv.getId());
					//delete(
						if(viewCfile!=null)
						{
							// 根据cfileId 查找出CFILE 得到文件名称，删除对应文件
							Cfile cFile =(Cfile)cfileMapper.findById(viewCfile.getCfileId());
							sftp.delete(cFile.getName());
							// 删除cfile 表记录
							cfileMapper.delete(viewCfile.getCfileId());
							// 删除 讲师观点对应文件 关联表
							teacherviewCfileMapper.delete(tv.getId());
						}
					   // 删除私信回复
						List<BigDecimal>  priMsgId =privateMessageMapper.getPriMsgIdByViewId(tv.getId());
						if(priMsgId!=null && priMsgId.size() > 0){
							for(BigDecimal msgId : priMsgId){
								privateMessageReplyMapper.delete(msgId);
							}
						}
						//删除私信
						privateMessageMapper.delete(tv.getId());
					   // 最后删除讲师观点
						teacherViewMapper.delete(tv.getId());
					
				}
			}
	}

	/**
	 * 获取讲师观点
	 */
	public void  getLatestTeacherView(ActionContext ctx) {
		dealGoneTeacherView();
		List<TeacherView> tvList = teacherViewMapper.getLatestTeacherView(null);
		List<TeacherViewDto>  dtoList = new ArrayList<TeacherViewDto>();
		//获取当前登录用户
		User currentUser = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		for(TeacherView  tv: tvList)
		{
			TeacherViewDto  dto = new TeacherViewDto();
			dto.setId(tv.getId());
			dto.setAdviceId(tv.getAdviceId());
			dto.setCreateTime(tv.getCreateTime());
			dto.setMineralId(tv.getMineralId());
			dto.setTeacherId(tv.getTeacherId());
			dto.setTeacherName(tv.getTeacherName());
			dto.setViewContent(tv.getViewContent());
			//检测当前用户是否回复过该观点
			PrivateMessage param = new PrivateMessage();
			param.setViewId(tv.getId());
			param.setCreatorId(currentUser.getUserId());
			//观点提问集合
			BigDecimal result = privateMessageMapper.checkExistsReply(param);
			if(result.compareTo(new BigDecimal(0)) == 1){
				dto.setReplied(true);
			}
			TeacherviewCfile   tfile = teacherviewCfileMapper.findById(tv.getId());
			//获取讲师图像ID
			User user = this.userMapper.findById(tv.getTeacherId());
			if(user != null){
				dto.setTeacherIconId(user.getCFileId());
			}
			
			if(tfile != null){
				dto.setCfileId(tfile.getCfileId());
			}
			dtoList.add(dto);
		}
		ctx.put("teacherViewDtos", dtoList);
	}

	/**
	 * 处理客户对讲师进行提问的业务逻辑
	 * @param ctx
	 * @param viewId
	 * @param teacherId
	 * @param content
	 * @return
	 */
	public JSONObject dealWithAskTeacher(BigDecimal viewId, String content,BigDecimal questionType) {
		try {
			ActionContext ctx = ActionContext.getContext();
			//私信对象
			PrivateMessage  pm = new PrivateMessage();
			//获取当前登录用户
			User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
			if(user!=null){
				//提问内容
				pm.setContent(content);
				//提问时间
				pm.setCreateTime(new Date());
				//提问人ID
				pm.setCreatorId(user.getUserId());
				//观点ID
				pm.setViewId(viewId);
				//新增私信信息
				privateMessageMapper.insert(pm);
			}
			TeacherView  tv = teacherViewMapper.findById(viewId);
			List<UserRole> roleList = userRoleMapper.captureRoleByUserId(user.getUserId());
			boolean isCustomer = checkIsCustomer(roleList);
			
			JSONObject obj = new JSONObject();
			obj.accumulate("quesContent", pm.getContent());
			obj.accumulate("quesCreateTime", Common.fromDateH(pm.getCreateTime()));
			obj.accumulate("quesCreatorName", user.getName());
			obj.accumulate("quesCreatorLevelId", user.getLevelId());
			obj.accumulate("quesCreatorHeadId", user.getCFileId());
			obj.accumulate("questionType", questionType);
			obj.accumulate("viewId", viewId);
			obj.accumulate("teacherId", tv.getTeacherId());
			obj.accumulate("isCustomer", isCustomer);
			obj.accumulate("quesCreatorId", user.getUserId());
			obj.accumulate("privateMessageId", pm.getId());
			
			if(questionType.compareTo(new BigDecimal(1)) == 0){
				User teacher = this.userMapper.findById(tv.getTeacherId());
				obj.accumulate("teacherIconId", teacher.getCFileId());
				obj.accumulate("teacherName", teacher.getName());
				obj.accumulate("viewContent", tv.getViewContent());
				TeacherviewCfile viewCfile = teacherviewCfileMapper.findById(tv.getId());
				if(viewCfile != null){
					obj.accumulate("viewCfile", viewCfile.getCfileId());
				}
				obj.accumulate("viewCreateTime", Common.fromDateH(tv.getCreateTime()));
				obj.accumulate("adviceId", tv.getAdviceId());
				obj.accumulate("mineralId", tv.getMineralId());
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("TeacherViewImpl.dealWithAskTeacher error",e);
		}
		return null;
	}

	/**
	 * 讲师回复私信
	 */
	public JSONObject dealWithTeacherReply(ActionContext ctx, BigDecimal msgId, String content) {
		//获取当前登录用户
		User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		
		PrivateMessageReply pmr = new PrivateMessageReply();
		pmr.setContent(content);
		pmr.setCreateTime(new Date());
		pmr.setCreatorId(user.getUserId());
		pmr.setPrivateMessageId(msgId);
		//新增讲师回复消息
		this.privateMessageReplyMapper.insert(pmr);
		
		//获取私信信息
		PrivateMessage privateMessage = this.privateMessageMapper.findById(msgId);
		//获取讲师信息
		User teacher = this.userMapper.findById(pmr.getCreatorId());
		
	    JSONObject obj = new JSONObject();
		obj.accumulate("replyContent", content);
		obj.accumulate("replyTime", Common.fromDateH(pmr.getCreateTime()));
		obj.accumulate("replyName", teacher.getName());
		obj.accumulate("replyHeadId", teacher.getCFileId());
		obj.accumulate("replyViewId", privateMessage.getViewId());
		obj.accumulate("replierId", teacher.getUserId());
		obj.accumulate("quesCreatorId", privateMessage.getCreatorId());
		
		return  obj;
	}

	/**
	 * 获取私信列表
	 */
	public void getCurrentUserPrivateMessage(ActionContext ctx) {
		//获取当前登录用户
		User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		//当前登录用户角色（默认为客户角色）
		BigDecimal roleId = new BigDecimal(Dictionary.CUSTOMER_TYPE_ROLE);
		//获取当前登录用户角色ID
		if(ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID) != null){
			roleId = (BigDecimal) ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID);
		}
		if(user!=null){
			List<QuestionAndReplyDto> qrList = new ArrayList<QuestionAndReplyDto>();
			List<BigDecimal> viewIdList = new ArrayList<BigDecimal>();
			List<BigDecimal> creatorIdList = new ArrayList<BigDecimal>();
			int count = 0;
			int quesIndex = 0;
			//根据客户或讲师ID获取私信列表
			if(roleId.toString().equals(Dictionary.TEACHER_TYPE_ROLE)){
				creatorIdList = privateMessageMapper.getCreatorIdByTeacherId(user.getUserId());
				if(creatorIdList != null && creatorIdList.size() > 0){
					for (int i = 0; i < creatorIdList.size(); i++) {
						BigDecimal creatorId = creatorIdList.get(i);
						PrivateMessageDto pmd = new PrivateMessageDto();
						pmd.setTeacherId(user.getUserId());
						pmd.setCreatorId(creatorId);
						viewIdList = privateMessageMapper.getViewIdByCreator(pmd);
						Map<String,Object> result = encQuestionReply(viewIdList,qrList,creatorId,1);
						count += (int)result.get("count");
					}
				}
			}else{
				//获取该客户提问过的观点ID集合
				PrivateMessageDto pmd = new PrivateMessageDto();
				pmd.setCreatorId(user.getUserId());
				viewIdList = privateMessageMapper.getViewIdByCreator(pmd);
				Map<String,Object> result = encQuestionReply(viewIdList,qrList,user.getUserId(),2);
				count += (int)result.get("count");
				if(result.get("quesIndex") != null){
					quesIndex = (int)result.get("quesIndex");
				}
			}
			ctx.put("count", count);
			ctx.put("quesIndex", quesIndex);
			ctx.put("messageDtoList", qrList);
			ctx.put("customerCount", creatorIdList.size());
		}
	}
	/**
	 * 删除讲师观点业务逻辑操作
	 */
	public JSONObject deleteTeacherView(ActionContext ctx1,BigDecimal  viewId) {
		//获取讲师观点关联图片
		TeacherviewCfile viewCfile = teacherviewCfileMapper.findById(viewId);
		if(viewCfile!=null){
			Cfile cFile =(Cfile)cfileMapper.findById(viewCfile.getCfileId());
			sftp.delete(cFile.getName());
			cfileMapper.delete(viewCfile.getCfileId());
			// 删除 讲师观点对应文件 关联表
			teacherviewCfileMapper.delete(viewId);
		}
		// 删除私信回复
		List<BigDecimal>  priMsgId =privateMessageMapper.getPriMsgIdByViewId(viewId);
		if(priMsgId!=null && priMsgId.size() > 0){
			for(BigDecimal msgId : priMsgId){
				privateMessageReplyMapper.delete(msgId);
			}
		}
		//删除私信
		privateMessageMapper.delete(viewId);
		
		//最后删除讲师观点
		teacherViewMapper.delete(viewId);
		JSONObject obj = new JSONObject();
		obj.accumulate("deleteFlag", true);
		obj.accumulate("customerCount", ctx1.get("customerCount"));
		obj.accumulate("count", ctx1.get("count"));
		return obj;
	}

	public Map<String, Object> uploadTeacherViewPic(File file, String fileName) {
		/**
		 * 上传磊丹观点图片
		 * 
		 * @throws IOException
		 */
			//返回结果对象
			Map<String, Object> result = new HashMap<String, Object>();
			try {
				// 解决不同的浏览器取到的文件类型不一样的问题
				List<String> types = new ArrayList<String>();
				types.add("gif");
				types.add("jpg");
				types.add("jpeg");
				types.add("png");
				if (fileName != null && fileName.trim() != "" 
					&& fileName.contains(".") 
					&& fileName.trim().split("\\.").length == 2 
					&& types.contains(fileName.split("\\.")[1].toLowerCase())) {
					// 文件后缀名
					String suffix = fileName.split("\\.")[1];
					// 时间戳
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					String timestr = sdf.format(new Date());
					// 文件名
					String realName = timestr + Common.getTimeInMillls()+ "." + suffix;
					// 文件存放物理路径
					HttpServletRequest request = ServletActionContext.getRequest();
					//临时文件路径
					String realPath = request.getSession().getServletContext().getRealPath("/temp");
					InputStream inputStream = new FileInputStream(file);
					FileUtils.copyInputStreamToFile(inputStream, new File(realPath, fileName));
					//FTP上传文件
					UploadStatus uploadStatus = sftp.upload(realPath + File.separator + fileName, realName);
					if(uploadStatus == UploadStatus.Upload_New_File_Success){
						result.put("success", -1);
						result.put("msg", "上传文件成功!");
					}else{
						result.put("success", 0);
						result.put("msg", "上传文件失败!");
						return result;
					}
	
					//保存文件信息
					Cfile cfile = new Cfile();
					cfile.setName(realName);
					cfile.setType(suffix);
					cfile.setUrlCode(realPath);
					cfile.setFilesize(new BigDecimal(file.length()));
					cfile.setCreateTime(new Date());
					cfile.setOriginalName(fileName);
					cfileMapper.insert(cfile);
	
					//清除临时目录
					file = new File(realPath + "\\" + fileName);
					if (file.isFile() && file.exists()) {
						file.delete();
					}
					//返回文件相对应ID，用于文件下载
					result.put("id", cfile.getId());
				} else {
					result.put("success", 0);
					result.put("msg", "上传的文件格式不正确,请重新上传!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", 0);
				result.put("msg", "上传文件异常!");
			}
			return result;
	}

	public void  getRoomConfig(ActionContext ctx1) {
		List<RoomConfig> rcList = roomConfigMapper.findAll();
		if (rcList != null && !rcList.isEmpty()) {
			RoomConfigDto rcd = new RoomConfigDto();
			RoomConfig rc = rcList.get(0);
			RoomconfigCfile rcc = roomconfigCfileMapper.findById(rc.getId());
			if(rcc != null){
				rcd.setCfileId(rcc.getCfileId());
			}
			rcd.setName(rc.getName());
			rcd.setType(rc.getType());
			rcd.setId(rc.getId());
			ctx1.put("rcd", rcd);
		}
    }

	/**
	 * 老师观点筛选
	 */
	public MessageBO getTeacherViewScreen(TeacherViewDto teacherViewDto) {
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			//获取当前登录用户
			User currentUser = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
			List<TeacherView> tvList = teacherViewMapper.getLatestTeacherView(teacherViewDto);
			List<TeacherViewDto>  dtoList = new ArrayList<TeacherViewDto>();
			for(TeacherView  tv: tvList){
				TeacherViewDto  dto = new TeacherViewDto();
				dto.setId(tv.getId());
				dto.setAdviceId(tv.getAdviceId());
				dto.setCreateTime(tv.getCreateTime());
				dto.setMineralId(tv.getMineralId());
				dto.setTeacherId(tv.getTeacherId());
				dto.setTeacherName(tv.getTeacherName());
				dto.setViewContent(tv.getViewContent());
				TeacherviewCfile tfile = teacherviewCfileMapper.findById(tv.getId());
				if(tfile!=null){
					dto.setCfileId(tfile.getCfileId());
				}
				//获取讲师图像ID
				User user = this.userMapper.findById(tv.getTeacherId());
				if(user != null){
					dto.setTeacherIconId(user.getCFileId());
				}
				
				//检测当前用户是否回复过该观点
				PrivateMessage param = new PrivateMessage();
				param.setViewId(tv.getId());
				param.setCreatorId(currentUser.getUserId());
				//观点提问集合
				List<PrivateMessage> questionList = privateMessageMapper.getPrivateMessageInfo(param);
				if(questionList != null && questionList.size() > 0){
					dto.setReplied(true);
				}
				
				dtoList.add(dto);
			}
			messageBO = new MessageBO("-1","查询老师观点成功!",dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("TeacherViewImpl.getTeacherViewScreen error",e);
			messageBO = new MessageBO("0","查询老师观点失败!");
		}
		return messageBO;
	}
	
	/**
	 * 检测用户是否为客户
	 * @return
	 */
	private boolean checkIsCustomer(List<UserRole> userRoleList){
		boolean result = false;
		for (int i = 0; i < userRoleList.size(); i++) {
			UserRole userRole = userRoleList.get(i);
			if(StringUtils.equals(Dictionary.CUSTOMER_TYPE_ROLE, String.valueOf(userRole.getRoleid()))){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 封装观点提问回复数据
	 * @param viewIdList
	 * @param qrList
	 * @param user
	 * @return
	 */
	private Map<String,Object> encQuestionReply(List<BigDecimal> viewIdList,List<QuestionAndReplyDto> qrList,BigDecimal creatorId,int type){
		int count = 0;
		Map<String,Object> result = new HashMap<String,Object>();
		for (int i = 0; i < viewIdList.size(); i++) {
			int quesIndex = 0;
			BigDecimal viewId = viewIdList.get(i);
			QuestionAndReplyDto qr = new QuestionAndReplyDto();
			//根据观点ID获取观点信息
			TeacherView  teacherView = teacherViewMapper.findById(viewId);
			qr.setViewId(viewId);
			qr.setViewContent(teacherView.getViewContent());
			qr.setAdviceId(new BigDecimal(teacherView.getAdviceId()));
			qr.setMineralId(new BigDecimal(teacherView.getMineralId()));
			qr.setViewCreateTime(teacherView.getCreateTime());
			//获取讲师头像ID
			User teacher = this.userMapper.findById(teacherView.getTeacherId());
			if(teacher != null){
				qr.setViewCreatorHeadId(teacher.getCFileId());
			}
			qr.setViewCreatorId(teacherView.getTeacherId());
			qr.setViewCreatorName(teacherView.getTeacherName());
			//获取观点关联的图片ID
			TeacherviewCfile teacherviewCfile = teacherviewCfileMapper.findById(viewId);
			if(teacherviewCfile != null){
				qr.setViewPicId(teacherviewCfile.getCfileId());
			}
			//获取当前发私信用户角色ID集合
			List<UserRole> userRoleList = this.userRoleMapper.captureRoleByUserId(creatorId);
			//检测当前发私信用户是否为客户
			boolean isCustomer = checkIsCustomer(userRoleList);
			qr.setCustomer(isCustomer);
			//获取当前发私信用户
			User currentUser = this.userMapper.findById(creatorId);
			if(currentUser != null){
				qr.setUser(currentUser);
			}
			//根据观点ID和创建者ID获取该观点的提问信息
			PrivateMessage param = new PrivateMessage();
			param.setViewId(viewId);
			param.setCreatorId(creatorId);
			//观点提问集合
			List<PrivateMessage> questionList = privateMessageMapper.getPrivateMessageInfo(param);
			if(questionList != null && questionList.size() > 0){
				qr.setQuestionList(questionList);
				for (int j = 0; j < questionList.size(); j++) {
					PrivateMessage question = questionList.get(j);
					//私信回复集合
					List<PrivateMessageReply> replyList = this.privateMessageReplyMapper.findByPrivateMessageId(question.getId());
					if(replyList != null && replyList.size() > 0){
						question.setReplyList(replyList);
						//统计客户最后一条提问讲师回复的消息数量
						if(j == questionList.size()-1 && type == 2){
							count += replyList.size();
						}
						quesIndex = (j+1);
					}else{
						
					}
				}
				//统计讲师未回复的消息数量
				if(type == 1){
					count += questionList.size()-quesIndex;
				}
			}
			qrList.add(qr);
			result.put("quesIndex", quesIndex);
		}
		result.put("count", count);
		return result;
	}
	
	/**
	 * 当用户为客户时，获取讲师回复数量
	 * 当用户为讲师时，获取获取客户参与人数量和提问未回复数量
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getReplyCount(BigDecimal userId){
		Map<String,Object> result = new HashMap<String,Object>();
		//获取用户角色ID集合
		List<UserRole> userRoleList = userRoleMapper.captureRoleByUserId(userId);
		//检测用户是否为客户
		boolean isCustomer = checkIsCustomer(userRoleList);
		if(isCustomer){
			int replyCount = 0;
			//获取客户提问过的观点集合
			PrivateMessageDto pmd = new PrivateMessageDto();
			pmd.setCreatorId(userId);
			List<BigDecimal> viewIdList = privateMessageMapper.getViewIdByCreator(pmd);
			if(viewIdList != null && viewIdList.size() > 0){
				//遍历观点ID集合
				for (int i = 0; i < viewIdList.size(); i++) {
					//根据观点ID和创建者ID获取该观点的提问信息
					PrivateMessage param = new PrivateMessage();
					param.setViewId(viewIdList.get(i));
					param.setCreatorId(userId);
					//观点提问集合
					List<PrivateMessage> questionList = privateMessageMapper.getPrivateMessageInfo(param);
					if(questionList != null && questionList.size() > 0){
						PrivateMessage question = questionList.get(questionList.size()-1);
						//私信回复集合
						List<PrivateMessageReply> replyList = this.privateMessageReplyMapper.findByPrivateMessageId(question.getId());
						if(replyList != null && replyList.size() > 0){
							replyCount += replyList.size();
						}
					}
				}
			}
			result.put("replyCount", replyCount);
		}else{
			int unReplyCount = 0;
			//获取对该讲师观点提问过的客户ID集合
			List<BigDecimal> creatorIdList = privateMessageMapper.getCreatorIdByTeacherId(userId);
			if(creatorIdList != null && creatorIdList.size() >0){
				for (int i = 0; i < creatorIdList.size(); i++) {
					BigDecimal creatorId = creatorIdList.get(i);
					//获取客户提问过的观点集合
					PrivateMessageDto pmd = new PrivateMessageDto();
					pmd.setCreatorId(creatorId);
					pmd.setTeacherId(userId);
					List<BigDecimal> viewIdList = privateMessageMapper.getViewIdByCreator(pmd);
					if(viewIdList != null && viewIdList.size() > 0){
						for (int j = 0; j < viewIdList.size(); j++) {
							int lastReplyIndex = 0;
							//根据观点ID和创建者ID获取该观点的提问信息
							PrivateMessage param = new PrivateMessage();
							param.setViewId(viewIdList.get(j));
							param.setCreatorId(creatorId);
							//观点提问集合
							List<PrivateMessage> questionList = privateMessageMapper.getPrivateMessageInfo(param);
							for (int k = 0; k < questionList.size(); k++) {
								PrivateMessage question = questionList.get(k);
								//私信回复集合
								List<PrivateMessageReply> replyList = this.privateMessageReplyMapper.findByPrivateMessageId(question.getId());
								if(replyList != null && replyList.size() > 0){
									//记录讲师最后回复的提问下标
									lastReplyIndex = (k+1);
								}
							}
							//讲师未回复客户提问数量
							unReplyCount +=  (questionList.size()-lastReplyIndex);
						}
					}
				}
				//参与人数量
				result.put("customerCount", creatorIdList.size());
				//讲师未回复客户提问数量
				result.put("unReplyCount", unReplyCount);
			}
		}
		return result;
	}
}
