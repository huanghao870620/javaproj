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
	
	private TeacherViewDto tv;//讲师观点实体
	private File file; // 上传文件对象
	private String fileFileName;//上传文件名
    private String content;// 客户向老师提问的问题
    private BigDecimal viewId;// 讲师观点id
    private BigDecimal questionType;// 客户提问类型
    private BigDecimal msgId;//  PrivateMessage 小纸条消息id
    
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
	 * ajax file upload 上传图片
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
	 * 初始化加载讲师观点信息，私信相关信息
	 * @return
	 */
	@Action(value = "index", results = {
		@Result(name = "success", location = "/WEB-INF/front/quotation/chart_teacher_view.jsp", type = "dispatcher") ,
		@Result(name = "customer", location = "/WEB-INF/front/quotation/chart_customer_view.jsp", type = "dispatcher") 
	})
	public String index(){
		ActionContext ctx = ActionContext.getContext();
		// 讲师观点类型
		this.mineralService.getAllMineral(ctx);
		// 讲师发言类型
		this.adviceTypeService.getAllAdviceType(ctx);
		// 获取讲师观点数据
		this.teacherViewService.getLatestTeacherView(ctx);
		// 获取当前用户私信数据
		this.teacherViewService.getCurrentUserPrivateMessage(ctx);
		// 获取实盘房间配置数据
		this.teacherViewService.getRoomConfig(ctx);
		// 当前用户角色
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
	 * 添加讲师观点
	 */
	@Action(value="addView")
	public void  addView(){
		ActionContext ctx1= ActionContext.getContext();
		JSONObject  obj = teacherViewService.generateTeacherView(ctx1,tv) ;
		this.sendAjaxMsg(obj.toString());
	}
	
	/**
	 * 删除讲师观点
	 */
	@Action(value="deleteTeacherView")
	public void  deleteTeacherView(){
		ActionContext ctx1= ActionContext.getContext();
		JSONObject  obj = teacherViewService.deleteTeacherView(ctx1,viewId) ;
		this.sendAjaxMsg(obj.toString());
	}
	
	/**
	 * 客户向讲师提问
	 */
	@Action(value="addViewMessage")
	public void  addViewMessage(){
		JSONObject  obj = teacherViewService.dealWithAskTeacher(viewId, content,questionType);
		this.sendAjaxMsg(obj.toString());
	}
	
	/**
	 *  讲师回复客户信息
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
	 * 讲师观点筛选
	 */
	@Action(value="teacherViewScreen")
	public void teacherViewScreen(){
		MessageBO messageBO = this.teacherViewService.getTeacherViewScreen(tv);
		writeJson(messageBO);
	}
}


