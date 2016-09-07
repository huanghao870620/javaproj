package com.ld.action;

import java.math.BigDecimal;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.ld.common.FTPStatus;
import com.ld.model.Cfile;
import com.ld.service.CfileService;
import com.ld.sftp.SFTPStatus;
import com.ld.sftp.SftpServ;
import com.ld.util.Common;
import com.ld.util.LoginUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * */
@Controller
@Namespace(value = "/download/file")
@Scope("prototype")
@ParentPackage("struts-default")
public class DownloadAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger log = Logger.getLogger(DownloadAction.class);
    
    
    @Autowired(required=true)
    private SftpServ sftp;
    
    @SuppressWarnings("rawtypes")
	@Autowired(required=true)
    private CfileService<Cfile>  fileService;
	
    @Action(value="show")
	public void show() {
    	
    	//HttpServletRequest request, 
    	HttpServletRequest request  = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	//request.getSession().get
    	if(ActionContext.getContext().getSession().get(LoginUtil.FRONT_LOGIN_USER)==null   &&  ActionContext.getContext().getSession().get(LoginUtil.BACK_LOGIN_USER)==null  )
    	{
    		return;
    	}
    	
    		String id = request.getParameter("id");
    	
    		if(null!=id && Common.isNumeric(id)){
    	        try {
    	        	Cfile cFile =(Cfile)fileService.findById(new BigDecimal(id));
    	            String minetype =  URLConnection.guessContentTypeFromName(cFile.getType());
    	            if(StringUtils.isNotEmpty(minetype)) 
    	                response.setContentType(minetype);
    	            else
    	                response.setContentType("application/octet-stream");
    	                response.addHeader("Content-Disposition", "attachment; filename="  + URLEncoder.encode(cFile.getName(), "UTF-8")); 
    	            SFTPStatus ftpStatus = sftp.download(cFile.getName(), response);
    	            if (FTPStatus.Download_New_Success.equals(ftpStatus)) {
    	                response.setStatus(200);
    	            }else if (FTPStatus.Connectin_Fail.equals(ftpStatus)) {
    	                response.setStatus(500);
    	            } else if(FTPStatus.Remote_File_Noexist.equals(ftpStatus)) {
    	                response.setStatus(404);
    	            } 
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	}
    }
}
