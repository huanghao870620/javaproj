package com.ld.action;
import java.io.File;
import java.math.BigDecimal;
import java.util.Map;
import org.apache.http.util.TextUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.ld.common.Dictionary;
import com.ld.dto.TeacherViewDto;
import com.ld.model.AdviceType;
import com.ld.model.MessageBO;
import com.ld.model.Mineral;
import com.ld.model.TeacherView;
import com.ld.service.AdviceTypeService;
import com.ld.service.MineralService;
import com.ld.service.TeacherViewService;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSONObject;

@Namespace(value="/front/quotation")
@ParentPackage(value="struts-front-login")
@InterceptorRefs(value={@InterceptorRef(value="frontLoginStack")
})
public class QuotationRoomAction extends FrontBaseAction {
	
	private static final long serialVersionUID = -2187617231707596097L;
	
	@Autowired
	private AdviceTypeService<AdviceType> adviceTypeService;
	
	@Autowired
	private MineralService<Mineral> mineralService;
	
	@Autowired
	private TeacherViewService<TeacherView>  teacherViewService;
	
	private TeacherViewDto tv;//��ʦ�۵�ʵ��
	private File file; // �ϴ��ļ�����
	private String fileFileName;//�ϴ��ļ���
    private String content;// �ͻ�����ʦ���ʵ�����
    private BigDecimal viewId;// ��ʦ�۵�id
    private BigDecimal questionType;// �ͻ���������
    private BigDecimal msgId;//  PrivateMessage Сֽ����Ϣid
    
    public TeacherViewDto getTv() {
		return tv;
	}

	public void setTv(TeacherViewDto tv) {
		this.tv = tv;
	}
	
    public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public BigDecimal getViewId() {
		return viewId;
	}

	public BigDecimal getMsgId() {
		return msgId;
	}

	public void setMsgId(BigDecimal msgId) {
		this.msgId = msgId;
	}

	public void setViewId(BigDecimal viewId) {
		this.viewId = viewId;
	}

	public BigDecimal getQuestionType() {
		return questionType;
	}

	public void setQuestionType(BigDecimal questionType) {
		this.questionType = questionType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
	/**
	 * ajax file upload �ϴ�ͼƬ
	 */
	@Action(value="uploadTeacherViewFile")
	public void uploadViewpointPic(){
		if(file != null){
			if(file.isFile()){
				Map<String,Object> result = this.teacherViewService.uploadTeacherViewPic(file,fileFileName);
				writeJson(result);
			}			
		}
	}

	/**
	 * ��ʼ�����ؽ�ʦ�۵���Ϣ��˽�������Ϣ
	 * @return
	 */
	@Action(value = "index", results = {
		@Result(name = "success", location = "/WEB-INF/front/quotation/chart_teacher_view.jsp", type = "dispatcher") ,
		@Result(name = "customer", location = "/WEB-INF/front/quotation/chart_customer_view.jsp", type = "dispatcher") 
	})
	public String index(){
		ActionContext ctx = ActionContext.getContext();
		// ��ʦ�۵�����
		this.mineralService.getAllMineral(ctx);
		// ��ʦ��������
		this.adviceTypeService.getAllAdviceType(ctx);
		// ��ȡ��ʦ�۵�����
		this.teacherViewService.getLatestTeacherView(ctx);
		// ��ȡ��ǰ�û�˽������
		this.teacherViewService.getCurrentUserPrivateMessage(ctx);
		// ��ȡʵ�̷�����������
		this.teacherViewService.getRoomConfig(ctx);
		// ��ǰ�û���ɫ
		String roleStr = teacherViewService.justifyCurrentRole(ctx);
		if(roleStr.equals(Dictionary.TEACHER_TYPE_ROLE))
		{
			ctx.put("roleId", ctx.getSession().get(LoginUtil.FRONT_CUSTOMER_ROLEID));
			return SUCCESS;
		}else{
			return "customer";
		}
	}
	
	/**
	 * ��ӽ�ʦ�۵�
	 */
	@Action(value="addView")
	public void  addView(){
		ActionContext ctx1= ActionContext.getContext();
		JSONObject  obj = teacherViewService.generateTeacherView(ctx1,tv) ;
		this.sendAjaxMsg(obj.toString());
	}
	
	/**
	 * ɾ����ʦ�۵�
	 */
	@Action(value="deleteTeacherView")
	public void  deleteTeacherView(){
		ActionContext ctx1= ActionContext.getContext();
		JSONObject  obj = teacherViewService.deleteTeacherView(ctx1,viewId) ;
		this.sendAjaxMsg(obj.toString());
	}
	
	/**
	 * �ͻ���ʦ����
	 */
	@Action(value="addViewMessage")
	public void  addViewMessage(){
		JSONObject  obj = teacherViewService.dealWithAskTeacher(viewId, content,questionType);
		this.sendAjaxMsg(obj.toString());
	}
	
	/**
	 *  ��ʦ�ظ��ͻ���Ϣ
	 */
	@Action(value="replyViewMessage")
	public void  replyViewMessage(){
		ActionContext ctx1= ActionContext.getContext();
		if(!TextUtils.isEmpty(content)){
			JSONObject  obj = teacherViewService.dealWithTeacherReply(ctx1, msgId, content)  ;
			this.sendAjaxMsg(obj.toString());
		}
	}
	
	/**
	 * ��ʦ�۵�ɸѡ
	 */
	@Action(value="teacherViewScreen")
	public void teacherViewScreen(){
		MessageBO messageBO = this.teacherViewService.getTeacherViewScreen(tv);
		writeJson(messageBO);
	}
}


