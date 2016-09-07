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
			// ���� ��ʦ�۵�
			tv.setTeacherId(user.getUserId());
			tv.setTeacherName(user.getName());
			tv.setCreateTime(new Date());
			tv.setAdviceId(dto.getAdviceId());
			tv.setViewContent(dto.getViewContent());
			tv.setMineralId(dto.getMineralId());
		    insert(tv);
		}
	    // ���뽲ʦ�۵��ӦͼƬ�м��
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
	 * �жϵ�ǰ��¼�ͻ���ɫ�жϣ�������Ӧ����
	 */
	public String justifyCurrentRole(ActionContext ctx) {
		String returnRoleStr ="0";
		User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		ctx.put("customer", user);
		// ��ȡ��ǰ��¼�û��Ƿ� ��������̨�û��������ȡ��Ӧ�Ĺ����û�������ɫ, �����ɫ����Ϊ��ͨ
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
	 *  ɾ�����뵱ǰʱ�� ǰ����֮ǰ�Ľ�ʦ�۵�����
	 */
	public  void dealGoneTeacherView(){
		// ��ȡ���ڽ�ʦ�۵����ݣ������ʦ�۵㣬�۵��Ӧ�ļ������� �۵��Ӧ˽���������
			List<TeacherView> tvList_gone = teacherViewMapper.selectGoneData();
			if(tvList_gone!=null && !tvList_gone.isEmpty())
			{
				for(TeacherView  tv:  tvList_gone)
				{
					// ɾ����ʦ�۵��Ӧ�ļ�
					TeacherviewCfile viewCfile = teacherviewCfileMapper.findById(tv.getId());
					//delete(
						if(viewCfile!=null)
						{
							// ����cfileId ���ҳ�CFILE �õ��ļ����ƣ�ɾ����Ӧ�ļ�
							Cfile cFile =(Cfile)cfileMapper.findById(viewCfile.getCfileId());
							sftp.delete(cFile.getName());
							// ɾ��cfile ���¼
							cfileMapper.delete(viewCfile.getCfileId());
							// ɾ�� ��ʦ�۵��Ӧ�ļ� ������
							teacherviewCfileMapper.delete(tv.getId());
						}
					   // ɾ��˽�Żظ�
						List<BigDecimal>  priMsgId =privateMessageMapper.getPriMsgIdByViewId(tv.getId());
						if(priMsgId!=null && priMsgId.size() > 0){
							for(BigDecimal msgId : priMsgId){
								privateMessageReplyMapper.delete(msgId);
							}
						}
						//ɾ��˽��
						privateMessageMapper.delete(tv.getId());
					   // ���ɾ����ʦ�۵�
						teacherViewMapper.delete(tv.getId());
					
				}
			}
	}

	/**
	 * ��ȡ��ʦ�۵�
	 */
	public void  getLatestTeacherView(ActionContext ctx) {
		dealGoneTeacherView();
		List<TeacherView> tvList = teacherViewMapper.getLatestTeacherView(null);
		List<TeacherViewDto>  dtoList = new ArrayList<TeacherViewDto>();
		//��ȡ��ǰ��¼�û�
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
			//��⵱ǰ�û��Ƿ�ظ����ù۵�
			PrivateMessage param = new PrivateMessage();
			param.setViewId(tv.getId());
			param.setCreatorId(currentUser.getUserId());
			//�۵����ʼ���
			BigDecimal result = privateMessageMapper.checkExistsReply(param);
			if(result.compareTo(new BigDecimal(0)) == 1){
				dto.setReplied(true);
			}
			TeacherviewCfile   tfile = teacherviewCfileMapper.findById(tv.getId());
			//��ȡ��ʦͼ��ID
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
	 * ����ͻ��Խ�ʦ�������ʵ�ҵ���߼�
	 * @param ctx
	 * @param viewId
	 * @param teacherId
	 * @param content
	 * @return
	 */
	public JSONObject dealWithAskTeacher(BigDecimal viewId, String content,BigDecimal questionType) {
		try {
			ActionContext ctx = ActionContext.getContext();
			//˽�Ŷ���
			PrivateMessage  pm = new PrivateMessage();
			//��ȡ��ǰ��¼�û�
			User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
			if(user!=null){
				//��������
				pm.setContent(content);
				//����ʱ��
				pm.setCreateTime(new Date());
				//������ID
				pm.setCreatorId(user.getUserId());
				//�۵�ID
				pm.setViewId(viewId);
				//����˽����Ϣ
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
	 * ��ʦ�ظ�˽��
	 */
	public JSONObject dealWithTeacherReply(ActionContext ctx, BigDecimal msgId, String content) {
		//��ȡ��ǰ��¼�û�
		User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		
		PrivateMessageReply pmr = new PrivateMessageReply();
		pmr.setContent(content);
		pmr.setCreateTime(new Date());
		pmr.setCreatorId(user.getUserId());
		pmr.setPrivateMessageId(msgId);
		//������ʦ�ظ���Ϣ
		this.privateMessageReplyMapper.insert(pmr);
		
		//��ȡ˽����Ϣ
		PrivateMessage privateMessage = this.privateMessageMapper.findById(msgId);
		//��ȡ��ʦ��Ϣ
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
	 * ��ȡ˽���б�
	 */
	public void getCurrentUserPrivateMessage(ActionContext ctx) {
		//��ȡ��ǰ��¼�û�
		User user = (User)ctx.getSession().get(LoginUtil.FRONT_LOGIN_USER);
		//��ǰ��¼�û���ɫ��Ĭ��Ϊ�ͻ���ɫ��
		BigDecimal roleId = new BigDecimal(Dictionary.CUSTOMER_TYPE_ROLE);
		//��ȡ��ǰ��¼�û���ɫID
		if(ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID) != null){
			roleId = (BigDecimal) ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID);
		}
		if(user!=null){
			List<QuestionAndReplyDto> qrList = new ArrayList<QuestionAndReplyDto>();
			List<BigDecimal> viewIdList = new ArrayList<BigDecimal>();
			List<BigDecimal> creatorIdList = new ArrayList<BigDecimal>();
			int count = 0;
			int quesIndex = 0;
			//���ݿͻ���ʦID��ȡ˽���б�
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
				//��ȡ�ÿͻ����ʹ��Ĺ۵�ID����
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
	 * ɾ����ʦ�۵�ҵ���߼�����
	 */
	public JSONObject deleteTeacherView(ActionContext ctx1,BigDecimal  viewId) {
		//��ȡ��ʦ�۵����ͼƬ
		TeacherviewCfile viewCfile = teacherviewCfileMapper.findById(viewId);
		if(viewCfile!=null){
			Cfile cFile =(Cfile)cfileMapper.findById(viewCfile.getCfileId());
			sftp.delete(cFile.getName());
			cfileMapper.delete(viewCfile.getCfileId());
			// ɾ�� ��ʦ�۵��Ӧ�ļ� ������
			teacherviewCfileMapper.delete(viewId);
		}
		// ɾ��˽�Żظ�
		List<BigDecimal>  priMsgId =privateMessageMapper.getPriMsgIdByViewId(viewId);
		if(priMsgId!=null && priMsgId.size() > 0){
			for(BigDecimal msgId : priMsgId){
				privateMessageReplyMapper.delete(msgId);
			}
		}
		//ɾ��˽��
		privateMessageMapper.delete(viewId);
		
		//���ɾ����ʦ�۵�
		teacherViewMapper.delete(viewId);
		JSONObject obj = new JSONObject();
		obj.accumulate("deleteFlag", true);
		obj.accumulate("customerCount", ctx1.get("customerCount"));
		obj.accumulate("count", ctx1.get("count"));
		return obj;
	}

	public Map<String, Object> uploadTeacherViewPic(File file, String fileName) {
		/**
		 * �ϴ��ڵ��۵�ͼƬ
		 * 
		 * @throws IOException
		 */
			//���ؽ������
			Map<String, Object> result = new HashMap<String, Object>();
			try {
				// �����ͬ�������ȡ�����ļ����Ͳ�һ��������
				List<String> types = new ArrayList<String>();
				types.add("gif");
				types.add("jpg");
				types.add("jpeg");
				types.add("png");
				if (fileName != null && fileName.trim() != "" 
					&& fileName.contains(".") 
					&& fileName.trim().split("\\.").length == 2 
					&& types.contains(fileName.split("\\.")[1].toLowerCase())) {
					// �ļ���׺��
					String suffix = fileName.split("\\.")[1];
					// ʱ���
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					String timestr = sdf.format(new Date());
					// �ļ���
					String realName = timestr + Common.getTimeInMillls()+ "." + suffix;
					// �ļ��������·��
					HttpServletRequest request = ServletActionContext.getRequest();
					//��ʱ�ļ�·��
					String realPath = request.getSession().getServletContext().getRealPath("/temp");
					InputStream inputStream = new FileInputStream(file);
					FileUtils.copyInputStreamToFile(inputStream, new File(realPath, fileName));
					//FTP�ϴ��ļ�
					UploadStatus uploadStatus = sftp.upload(realPath + File.separator + fileName, realName);
					if(uploadStatus == UploadStatus.Upload_New_File_Success){
						result.put("success", -1);
						result.put("msg", "�ϴ��ļ��ɹ�!");
					}else{
						result.put("success", 0);
						result.put("msg", "�ϴ��ļ�ʧ��!");
						return result;
					}
	
					//�����ļ���Ϣ
					Cfile cfile = new Cfile();
					cfile.setName(realName);
					cfile.setType(suffix);
					cfile.setUrlCode(realPath);
					cfile.setFilesize(new BigDecimal(file.length()));
					cfile.setCreateTime(new Date());
					cfile.setOriginalName(fileName);
					cfileMapper.insert(cfile);
	
					//�����ʱĿ¼
					file = new File(realPath + "\\" + fileName);
					if (file.isFile() && file.exists()) {
						file.delete();
					}
					//�����ļ����ӦID�������ļ�����
					result.put("id", cfile.getId());
				} else {
					result.put("success", 0);
					result.put("msg", "�ϴ����ļ���ʽ����ȷ,�������ϴ�!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", 0);
				result.put("msg", "�ϴ��ļ��쳣!");
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
	 * ��ʦ�۵�ɸѡ
	 */
	public MessageBO getTeacherViewScreen(TeacherViewDto teacherViewDto) {
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			//��ȡ��ǰ��¼�û�
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
				//��ȡ��ʦͼ��ID
				User user = this.userMapper.findById(tv.getTeacherId());
				if(user != null){
					dto.setTeacherIconId(user.getCFileId());
				}
				
				//��⵱ǰ�û��Ƿ�ظ����ù۵�
				PrivateMessage param = new PrivateMessage();
				param.setViewId(tv.getId());
				param.setCreatorId(currentUser.getUserId());
				//�۵����ʼ���
				List<PrivateMessage> questionList = privateMessageMapper.getPrivateMessageInfo(param);
				if(questionList != null && questionList.size() > 0){
					dto.setReplied(true);
				}
				
				dtoList.add(dto);
			}
			messageBO = new MessageBO("-1","��ѯ��ʦ�۵�ɹ�!",dtoList);
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("TeacherViewImpl.getTeacherViewScreen error",e);
			messageBO = new MessageBO("0","��ѯ��ʦ�۵�ʧ��!");
		}
		return messageBO;
	}
	
	/**
	 * ����û��Ƿ�Ϊ�ͻ�
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
	 * ��װ�۵����ʻظ�����
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
			//���ݹ۵�ID��ȡ�۵���Ϣ
			TeacherView  teacherView = teacherViewMapper.findById(viewId);
			qr.setViewId(viewId);
			qr.setViewContent(teacherView.getViewContent());
			qr.setAdviceId(new BigDecimal(teacherView.getAdviceId()));
			qr.setMineralId(new BigDecimal(teacherView.getMineralId()));
			qr.setViewCreateTime(teacherView.getCreateTime());
			//��ȡ��ʦͷ��ID
			User teacher = this.userMapper.findById(teacherView.getTeacherId());
			if(teacher != null){
				qr.setViewCreatorHeadId(teacher.getCFileId());
			}
			qr.setViewCreatorId(teacherView.getTeacherId());
			qr.setViewCreatorName(teacherView.getTeacherName());
			//��ȡ�۵������ͼƬID
			TeacherviewCfile teacherviewCfile = teacherviewCfileMapper.findById(viewId);
			if(teacherviewCfile != null){
				qr.setViewPicId(teacherviewCfile.getCfileId());
			}
			//��ȡ��ǰ��˽���û���ɫID����
			List<UserRole> userRoleList = this.userRoleMapper.captureRoleByUserId(creatorId);
			//��⵱ǰ��˽���û��Ƿ�Ϊ�ͻ�
			boolean isCustomer = checkIsCustomer(userRoleList);
			qr.setCustomer(isCustomer);
			//��ȡ��ǰ��˽���û�
			User currentUser = this.userMapper.findById(creatorId);
			if(currentUser != null){
				qr.setUser(currentUser);
			}
			//���ݹ۵�ID�ʹ�����ID��ȡ�ù۵��������Ϣ
			PrivateMessage param = new PrivateMessage();
			param.setViewId(viewId);
			param.setCreatorId(creatorId);
			//�۵����ʼ���
			List<PrivateMessage> questionList = privateMessageMapper.getPrivateMessageInfo(param);
			if(questionList != null && questionList.size() > 0){
				qr.setQuestionList(questionList);
				for (int j = 0; j < questionList.size(); j++) {
					PrivateMessage question = questionList.get(j);
					//˽�Żظ�����
					List<PrivateMessageReply> replyList = this.privateMessageReplyMapper.findByPrivateMessageId(question.getId());
					if(replyList != null && replyList.size() > 0){
						question.setReplyList(replyList);
						//ͳ�ƿͻ����һ�����ʽ�ʦ�ظ�����Ϣ����
						if(j == questionList.size()-1 && type == 2){
							count += replyList.size();
						}
						quesIndex = (j+1);
					}else{
						
					}
				}
				//ͳ�ƽ�ʦδ�ظ�����Ϣ����
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
	 * ���û�Ϊ�ͻ�ʱ����ȡ��ʦ�ظ�����
	 * ���û�Ϊ��ʦʱ����ȡ��ȡ�ͻ�����������������δ�ظ�����
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getReplyCount(BigDecimal userId){
		Map<String,Object> result = new HashMap<String,Object>();
		//��ȡ�û���ɫID����
		List<UserRole> userRoleList = userRoleMapper.captureRoleByUserId(userId);
		//����û��Ƿ�Ϊ�ͻ�
		boolean isCustomer = checkIsCustomer(userRoleList);
		if(isCustomer){
			int replyCount = 0;
			//��ȡ�ͻ����ʹ��Ĺ۵㼯��
			PrivateMessageDto pmd = new PrivateMessageDto();
			pmd.setCreatorId(userId);
			List<BigDecimal> viewIdList = privateMessageMapper.getViewIdByCreator(pmd);
			if(viewIdList != null && viewIdList.size() > 0){
				//�����۵�ID����
				for (int i = 0; i < viewIdList.size(); i++) {
					//���ݹ۵�ID�ʹ�����ID��ȡ�ù۵��������Ϣ
					PrivateMessage param = new PrivateMessage();
					param.setViewId(viewIdList.get(i));
					param.setCreatorId(userId);
					//�۵����ʼ���
					List<PrivateMessage> questionList = privateMessageMapper.getPrivateMessageInfo(param);
					if(questionList != null && questionList.size() > 0){
						PrivateMessage question = questionList.get(questionList.size()-1);
						//˽�Żظ�����
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
			//��ȡ�Ըý�ʦ�۵����ʹ��Ŀͻ�ID����
			List<BigDecimal> creatorIdList = privateMessageMapper.getCreatorIdByTeacherId(userId);
			if(creatorIdList != null && creatorIdList.size() >0){
				for (int i = 0; i < creatorIdList.size(); i++) {
					BigDecimal creatorId = creatorIdList.get(i);
					//��ȡ�ͻ����ʹ��Ĺ۵㼯��
					PrivateMessageDto pmd = new PrivateMessageDto();
					pmd.setCreatorId(creatorId);
					pmd.setTeacherId(userId);
					List<BigDecimal> viewIdList = privateMessageMapper.getViewIdByCreator(pmd);
					if(viewIdList != null && viewIdList.size() > 0){
						for (int j = 0; j < viewIdList.size(); j++) {
							int lastReplyIndex = 0;
							//���ݹ۵�ID�ʹ�����ID��ȡ�ù۵��������Ϣ
							PrivateMessage param = new PrivateMessage();
							param.setViewId(viewIdList.get(j));
							param.setCreatorId(creatorId);
							//�۵����ʼ���
							List<PrivateMessage> questionList = privateMessageMapper.getPrivateMessageInfo(param);
							for (int k = 0; k < questionList.size(); k++) {
								PrivateMessage question = questionList.get(k);
								//˽�Żظ�����
								List<PrivateMessageReply> replyList = this.privateMessageReplyMapper.findByPrivateMessageId(question.getId());
								if(replyList != null && replyList.size() > 0){
									//��¼��ʦ���ظ��������±�
									lastReplyIndex = (k+1);
								}
							}
							//��ʦδ�ظ��ͻ���������
							unReplyCount +=  (questionList.size()-lastReplyIndex);
						}
					}
				}
				//����������
				result.put("customerCount", creatorIdList.size());
				//��ʦδ�ظ��ͻ���������
				result.put("unReplyCount", unReplyCount);
			}
		}
		return result;
	}
}
