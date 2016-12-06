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
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.RoomConfigDto;
import com.ld.live.AuditMessageQueue;
import com.ld.live.Message;
import com.ld.live.MessageQueue;
import com.ld.live.UserManager;
import com.ld.mapper.CfileMapper;
import com.ld.mapper.RoomConfigMapper;
import com.ld.mapper.RoomMapper;
import com.ld.mapper.RoomconfigCfileMapper;
import com.ld.mapper.WeiboMessageMapper;
import com.ld.model.Cfile;
import com.ld.model.Room;
import com.ld.model.RoomConfig;
import com.ld.model.RoomconfigCfile;
import com.ld.model.WeiboMessage;
import com.ld.service.RoomService;
import com.ld.sftp.SftpServ;
import com.ld.sftp.SftpServ.UploadStatus;
import com.ld.util.Common;
import com.ld.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

/**
 * 
 * @author huang.hao
 *
 */
@Service
@Transactional
public class RoomServiceImpl extends BaseServiceImpl<Room, RoomMapper> implements RoomService<Room> {

	@Autowired(required = true)
	private SftpServ sftp;

	@Autowired
	private CfileMapper cfileMapper;

	@Autowired
	private RoomConfigMapper roomConfigMapper;

	@Autowired
	private RoomconfigCfileMapper roomconfigCfileMapper;
	
	
	@Autowired
	private WeiboMessageMapper weiboMessageMapper;
	

	/**
	 * 审核消息
	 */
	public JSONObject auditMessage() {
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> params = ctx.getParameters();
		String msgs[] = (String[]) params.get("msgid");
		if (!StringUtil.isBlank(msgs[0])) {
			UserManager manager = UserManager.getUserManager();
			AuditMessageQueue amq = manager.getAmq(); // 
			WeiboMessage message = this.weiboMessageMapper.findById(new BigDecimal(msgs[0]));
			message.setPass(true); //
			amq.removeMessage(message);

			MessageQueue mq = manager.getMq();
			mq.addMessage(message); //
			message.setInputTime(new Date());
			this.weiboMessageMapper.updateByPrimaryKey(message);
		}
		JSONObject obj = new JSONObject();
		return obj.accumulate("success", true);
	}

	@Override
	public boolean addRoomConfig(ActionContext ctx1, RoomConfigDto dto, File file, String fileName) {
		// TODO Auto-generated method stub
		if(file!=null)
		{
		Map<String, Object> map = uploadRoomconfigPic(file, fileName);
		// JSONObject obj = new JSONObject();
		if (map.get("id") != null) {
			BigDecimal fileId = (BigDecimal) map.get("id");
			// 进行保存 实盘房间配置操作
			RoomConfig rc = new RoomConfig();
			rc.setName(dto.getName());
			rc.setType(dto.getType());

			roomConfigMapper.insert(rc);
			// 保存 实盘房间配置与关联文件中间表数据
			RoomconfigCfile rfile = new RoomconfigCfile();
			rfile.setCfileId(fileId);
			rfile.setRoomId(rc.getId());
			roomconfigCfileMapper.insert(rfile);

			dto.setCfileId(fileId);
			ctx1.put("rcd", dto);
			return true;
		} else {
			ctx1.put("rcd", dto);
			return false;
		}
		}
		else{
			
			RoomConfig rc = new RoomConfig();
			rc.setName(dto.getName());
			rc.setType(dto.getType());
			roomConfigMapper.insert(rc);
			
			ctx1.put("rcd", dto);
			return true;
		}
	}

	public Map<String, Object> uploadRoomconfigPic(File file, String fileName) {
		// TODO Auto-generated method stub
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
			if (fileName != null && fileName.trim() != "" && fileName.contains(".")
					&& fileName.trim().split("\\.").length == 2 && types.contains(fileName.split("\\.")[1])) {
				//文件后缀名
				String suffix = fileName.split("\\.")[1];
				//时间戳
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String timestr = sdf.format(new Date());
				// 文件名
				String realName = timestr + Common.getTimeInMillls()+ "." + suffix;
				// 文件存放物理路径
				HttpServletRequest request = ServletActionContext.getRequest();
				// 临时文件路径
				String realPath = request.getSession().getServletContext().getRealPath("/temp");
				InputStream inputStream = new FileInputStream(file);
				FileUtils.copyInputStreamToFile(inputStream, new File(realPath, fileName));
				//  FTP上传文件
			//	UploadStatus uploadStatus = ftp.upload(realPath + File.separator + fileName, "Send/" + realName);
					UploadStatus uploadStatus = sftp.upload(realPath + File.separator + fileName,  realName);
				if (uploadStatus == UploadStatus.Upload_New_File_Success) {
					result.put("success", 0);
					result.put("msg", "上传文件成功!");
				} else {
					result.put("success", 1);
					result.put("msg", "上传文件失败!");
					return result;
				}

				// 保存文件信息
				Cfile cfile = new Cfile();
				cfile.setName(realName);
				cfile.setType(suffix);
				cfile.setUrlCode(realPath);
				cfile.setFilesize(new BigDecimal(file.length()));
				cfile.setCreateTime(new Date());
				cfile.setOriginalName(fileName);
				cfileMapper.insert(cfile);

				// 清除临时目录
				file = new File(realPath + "\\" + fileName);
				if (file.isFile() && file.exists()) {
					file.delete();
				}
				//返回文件相对应ID，用于文件下载
				result.put("id", cfile.getId());
			} else {
				result.put("success", 1);
				result.put("msg", "上传的文件格式不正确,请重新上传!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", 1);
			result.put("msg", "上传文件异常!");
		}
		return result;
	}

	@Override
	public boolean isExistRoomConfig(ActionContext ctx1) {
		// TODO Auto-generated method stub
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
			return true;
		}
		return false;
	}

	@Override
	public boolean updateRoomConfig(ActionContext ctx1, RoomConfigDto dto, File file, String fileName) {
		// TODO Auto-generated method stub
		// 处理文件重新上传逻辑
		if (file != null) {
			Map<String, Object> map = uploadRoomconfigPic(file, fileName);
			if (map.get("id") != null) {
				BigDecimal fileId = (BigDecimal) map.get("id");
				//  进行保存 实盘房间配置操作
				RoomConfig rc = new RoomConfig();
				rc.setName(dto.getName());
				rc.setType(dto.getType());
				rc.setId(dto.getId());
				roomConfigMapper.updateByPrimaryKey(rc);
				//保存 实盘房间配置与关联文件中间表数据
				RoomconfigCfile rfile = new RoomconfigCfile();
				rfile.setCfileId(fileId);
				rfile.setRoomId(rc.getId());
				
				//更新 实盘房间配置 对应文件id
				roomconfigCfileMapper.updateByPrimaryKey(rfile);
				dto.setCfileId(fileId);
				ctx1.put("rcd", dto);
				return true;
			} else {
				ctx1.put("rcd", dto);
				return false;
			}
		}
		//  没修改图片，只修改了文字
		else{
			
			//进行更新 实盘房间配置操作
			RoomConfig rc = new RoomConfig();
			rc.setName(dto.getName());
			rc.setType(dto.getType());
			rc.setId(dto.getId());
			roomConfigMapper.updateByPrimaryKey(rc);
			RoomconfigCfile  rcc = roomconfigCfileMapper.findById(dto.getId());
			if(rcc != null){
				dto.setCfileId(rcc.getCfileId());
			}
			dto.setName(dto.getName());
			dto.setType(dto.getType());
			ctx1.put("rcd", dto);
			return true;
		}
	}
	
	
	/**
	 * 保存普通房间消息
	 */
	public void addCommonRoomMsg(Message message){
		if(message instanceof WeiboMessage){
			WeiboMessage weiboMsg = (WeiboMessage) message;
			this.weiboMessageMapper.insert(weiboMsg);
		}
		
	}


}
