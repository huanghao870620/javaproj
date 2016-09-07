package com.ld.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.ViewpointDto;
import com.ld.mapper.CfileMapper;
import com.ld.mapper.ViewpointMapper;
import com.ld.mapper.ViewpointSpecialFileRelaMapper;
import com.ld.model.Cfile;
import com.ld.model.MessageBO;
import com.ld.model.Viewpoint;
import com.ld.model.ViewpointSpecialFileRela;
import com.ld.service.ViewpointService;
import com.ld.sftp.SftpServ;
import com.ld.sftp.SftpServ.UploadStatus;
import com.ld.util.Logs;
import com.opensymphony.xwork2.ActionContext;

@Service
@Transactional
public class ViewpointServiceImpl extends BaseServiceImpl<Viewpoint, ViewpointMapper>
		implements ViewpointService<Viewpoint> {

	@Autowired
	private ViewpointMapper viewpointMapper;
	
	@Autowired
	private ViewpointSpecialFileRelaMapper viewpointSpecialFileRelaMapper;

	@Autowired
	private CfileMapper cfileMapper;

	
	@Autowired(required = true)
	private SftpServ sftp;

	/**
	 * 分页查询磊丹观点
	 */
	public List<Viewpoint> queryViewpointByAjax() {
		List<Viewpoint> result = null;
		try {
			Viewpoint viewpoint = new Viewpoint();
			ActionContext ac = ActionContext.getContext();
			Map<String,Object> map = ac.getParameters();
			String[] params = (String[]) map.get("title");
			if(ArrayUtils.isNotEmpty(params)&&StringUtils.isNotBlank(params[0])){
				viewpoint.setTitle(params[0]);
			}
			result = this.viewpointMapper.findAllByPaging(viewpoint);
			if(result!=null&&result.size()>0){
				for (int i = 0; i < result.size(); i++) {
					Viewpoint v = result.get(i);
					//查询观点关联的专题、图片信息
					List<ViewpointSpecialFileRela> list = this.viewpointSpecialFileRelaMapper.findListById(new BigDecimal(v.getViewpointId()));
					if(list!=null&&list.size()>0){
						StringBuilder sb = new StringBuilder();
						for (int j = 0; j < list.size(); j++) {
							ViewpointSpecialFileRela viewpointSpecialFileRela = list.get(j);
							sb.append(viewpointSpecialFileRela.getSpecial());
							sb.append(" ");
						}
						v.setSpecial(sb.toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("ViewpointServiceImpl.queryViewpointByAjax error",e);
		}
		return result;
	}

	/**
	 * 添加磊丹观点
	 */
	public MessageBO addViewpoint(ViewpointDto viewpointDto){
		MessageBO messageBO = null;
		try {
			//验证观点表单数据
			boolean bo = validateViewpoint(viewpointDto);
			if(bo){
				//当前系统时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowDate = sdf.format(new Date());
				//封装观点数据
				Viewpoint viewpoint = new Viewpoint();
				//标题
				viewpoint.setTitle(viewpointDto.getTitle());
				//内容简介
				viewpoint.setContent(viewpointDto.getContent());
				//创建时间
				viewpoint.setCreateTime(sdf.parse(nowDate));
				//更新时间
				viewpoint.setUpdateTime(sdf.parse(nowDate));
				//添加观点
				int result = this.viewpointMapper.insert(viewpoint);
				if(result>0){
					//专题
					String special = viewpointDto.getSpecial();
					//图片ID
					String fileId = viewpointDto.getFileId();
					if(fileId!=null&&!"".equals(fileId)){
						String[] fileIdArr = fileId.split(",");
						String[] specialArr = special.split(",");
						for (int i = 0; i < fileIdArr.length; i++) {
							String fileIdStr = fileIdArr[i];
							if(StringUtils.isNotBlank(fileIdStr)&&StringUtils.isNotBlank(fileIdStr.trim())){
								ViewpointSpecialFileRela viewpointSpecialFileRela = new ViewpointSpecialFileRela();
								viewpointSpecialFileRela.setViewpointId(new BigDecimal(viewpoint.getViewpointId()));
								viewpointSpecialFileRela.setCfileId(new BigDecimal(fileIdStr.trim()));
								viewpointSpecialFileRela.setSpecialFileSequ(new BigDecimal(i + 1));
								viewpointSpecialFileRela.setCreateTime(sdf.parse(nowDate));
								viewpointSpecialFileRela.setUpdateTime(sdf.parse(nowDate));
								viewpointSpecialFileRela.setSpecial(specialArr[i].trim());
								this.viewpointSpecialFileRelaMapper.insert(viewpointSpecialFileRela);
							}
						}
					}
					messageBO = new MessageBO("-1","新增观点成功!");
				}
			}else{
				Logs.getLogger().info("ViewpointServiceImpl.addViewpoint 参数验证失败");
				messageBO = new MessageBO("0","新增观点失败,请按要求填写观点信息!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("ViewpointServiceImpl.addViewpoint error",e);
			messageBO = new MessageBO("0","新增观点失败!");
		}
		return messageBO;
	}

	/**
	 * 根据观点ID删除
	 */
	public MessageBO delViewpoint() {
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> map = ctx.getParameters();
			String[] ids = (String[]) map.get("id");
			if(ArrayUtils.isEmpty(ids)){
				messageBO = new MessageBO("0","删除观点失败");
				return messageBO;
			}
				String idString = new String(ids[0]);
				String[] idList =idString.split(",");
				for(int k=0 ; k<idList.length ; k++){
					BigDecimal id = new BigDecimal(idList[k]);
			//删除观点
			this.viewpointMapper.delete(id);
			
			//删除图片及文件表
			List<ViewpointSpecialFileRela> list = this.viewpointSpecialFileRelaMapper.findListById(id);
			if(list!=null&&list.size()>0){
				List<String> cfileIdList = new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					ViewpointSpecialFileRela viewpointSpecialFileRela = list.get(i);
					cfileIdList.add(viewpointSpecialFileRela.getCfileId().toString());
				}
				List<Cfile> cfileList = cfileMapper.batchFindByCfileIdList(cfileIdList);
				List<String> cfileNameList = new ArrayList<String>();
				for(int j=0;j<cfileList.size();j++){
					Cfile cfile = cfileList.get(j);
					cfileNameList.add(cfile.getName());
				}
				//删除图片
				sftp.batchDelete(cfileNameList);
				//删除文件表
				cfileMapper.batchRemoveCfileById(cfileIdList);
				//删除观点专题图片关联关系
				this.viewpointSpecialFileRelaMapper.delete(id);
			}
				}
			
			messageBO = new MessageBO("-1","删除观点成功");
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("ViewpointServiceImpl.delViewpoint error",e);
			messageBO = new MessageBO("0","删除观点失败");
		}
		return messageBO;
	}

	/**
	 * 根据ID查询观点
	 */
	public MessageBO findViewpointById() {
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> map = ctx.getParameters();
			String[] ids = (String[]) map.get("id");
			if(ArrayUtils.isEmpty(ids)){
				messageBO = new MessageBO("0","查询观点失败");
				return messageBO;
			}
			//查询观点信息
			Viewpoint viewpoint = this.viewpointMapper.findById(new BigDecimal(ids[0]));
			ViewpointDto viewpointDto = new ViewpointDto();
			viewpointDto.setViewpointId(viewpoint.getViewpointId());
			viewpointDto.setTitle(viewpoint.getTitle());
			viewpointDto.setContent(viewpoint.getContent());
			viewpointDto.setCreateTime(viewpoint.getCreateTime());
			viewpointDto.setUpdateTime(viewpoint.getUpdateTime());
			//查询观点关联的专题、图片信息
			List<ViewpointSpecialFileRela> list = this.viewpointSpecialFileRelaMapper.findListById(new BigDecimal(ids[0]));
			if(list!=null&&list.size()>0){
				List<String> fileIdList = new ArrayList<String>();
				List<String> specialList = new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					ViewpointSpecialFileRela viewpointSpecialFileRela = list.get(i);
					fileIdList.add(viewpointSpecialFileRela.getCfileId().toString());
					specialList.add(viewpointSpecialFileRela.getSpecial());
				}
				viewpointDto.setFileIdArray(fileIdList.toArray(new String[]{}));
				viewpointDto.setSpecialArray(specialList.toArray(new String[]{}));
			}
			messageBO = new MessageBO("-1","查询观点成功",viewpointDto);
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("ViewpointServiceImpl.findViewpointById error",e);
			messageBO = new MessageBO("0","查询观点失败");
		}
		return messageBO;
	}

	/**
	 *编辑磊丹观点
	 */
	public MessageBO updateViewpoint(ViewpointDto viewpointDto) {
		MessageBO messageBO = null;
		try {
			//验证观点表单数据
			boolean bo = validateViewpoint(viewpointDto);
			if(bo){
				Viewpoint viewpoint = this.viewpointMapper.findById(new BigDecimal(viewpointDto.getViewpointId()));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowDate = sdf.format(new Date());
				viewpoint.setTitle(viewpointDto.getTitle());
				viewpoint.setContent(viewpointDto.getContent());
				viewpoint.setUpdateTime(sdf.parse(nowDate));
				//更新观点信息
				this.viewpointMapper.updateByPrimaryKey(viewpoint);
				
				//专题
				String special = viewpointDto.getSpecial();
				//图片ID
				String fileId = viewpointDto.getFileId();
				if(fileId!=null&&!"".equals(fileId)){
					String[] fileIdArr = fileId.split(",");
					String[] specialArr = special.split(",");
					for (int i = 0; i < fileIdArr.length; i++) {
						String fileIdStr = fileIdArr[i];
						if(fileIdStr!=null&&fileIdStr.trim().length()>0){
							ViewpointSpecialFileRela viewpointSpecialFileRela = new ViewpointSpecialFileRela();
							viewpointSpecialFileRela.setViewpointId(new BigDecimal(viewpoint.getViewpointId()));
							viewpointSpecialFileRela.setSpecialFileSequ(new BigDecimal(i+1));
							ViewpointSpecialFileRela result = viewpointSpecialFileRelaMapper.findByIdAndSequ(viewpointSpecialFileRela);
							if(result!=null){
								result.setViewpointId(new BigDecimal(viewpoint.getViewpointId()));
								result.setCfileId(new BigDecimal(fileIdStr.trim()));
								result.setSpecialFileSequ(new BigDecimal(i + 1));
								result.setUpdateTime(sdf.parse(nowDate));
								result.setSpecial(specialArr[i].trim());
								this.viewpointSpecialFileRelaMapper.updateByPrimaryKey(result);
							}else{
								viewpointSpecialFileRela.setCfileId(new BigDecimal(fileIdStr.trim()));
								viewpointSpecialFileRela.setCreateTime(sdf.parse(nowDate));
								viewpointSpecialFileRela.setUpdateTime(sdf.parse(nowDate));
								viewpointSpecialFileRela.setSpecial(specialArr[i].trim());
								this.viewpointSpecialFileRelaMapper.insert(viewpointSpecialFileRela);
							}
						}
					}
				}
				messageBO = new MessageBO("-1","更新观点成功!");
			}else{
				Logs.getLogger().info("ViewpointServiceImpl.updateViewpoint 参数验证失败!");
				messageBO = new MessageBO("0","更新观点失败,请填按要求填写观点信息!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("ViewpointServiceImpl.updateViewpoint error",e);
			messageBO = new MessageBO("0","更新观点失败!");
		}
		return messageBO;
	}

	/**
	 * 上传磊丹观点图片
	 * 
	 * @throws IOException
	 */
	public MessageBO uploadViewpointPic(File file, String fileName) {
		//返回结果对象
		MessageBO messageBO = null;
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
				//限制文件大小上限为3M
				long fileSize = file.length();
				if(fileSize > 3*1024*1024){
					messageBO = new MessageBO("0","�ļ���С���ܳ���3M��������ѡ���ļ��ϴ�!");
					return messageBO;
				}
				// 文件后缀名
				String suffix = fileName.split("\\.")[1];
				//时间戳
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
				String timestr = sdf.format(new Date());
				//文件名
				String realName = timestr + "." + suffix;
				//文件存放物理路径
				HttpServletRequest request = ServletActionContext.getRequest();
				//临时文件路径
				String realPath = request.getSession().getServletContext().getRealPath("/temp");
				InputStream inputStream = new FileInputStream(file);
				BufferedImage sourceImg = ImageIO.read(inputStream);
				int width = sourceImg.getWidth();
				if(width>945){
					messageBO = new MessageBO("0","请上传规定尺寸的图片!");
					return messageBO;
				}
				FileUtils.copyInputStreamToFile(new FileInputStream(file), new File(realPath, fileName));
				//SFTP上传文件
			//	UploadStatus uploadStatus = ftp.upload(realPath + File.separator + fileName, "Send/" + realName);
			
					UploadStatus uploadStatus = sftp.upload(realPath + File.separator + fileName, realName);
				if(uploadStatus != UploadStatus.Upload_New_File_Success){
					messageBO = new MessageBO("0","上传图片失败!");
					return messageBO;
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
				file = new File(realPath , fileName);
				if (file.isFile() && file.exists()) {
					file.delete();
				}
				//返回文件相对应ID，用于文件下载
				messageBO = new MessageBO("-1","上传图片成功!",cfile.getId());
			} else {
				messageBO = new MessageBO("0","上传的文件格式不正确,请重新上传!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("ViewpointServiceImpl.uploadViewpointPic error",e);
			messageBO = new MessageBO("0","上传的文件格式不正确,请重新上传!");
		}
		return messageBO;
	}
	
	/**
	 * 验证观点表单数据
	 * @param viewpointDto
	 */
	private boolean validateViewpoint(ViewpointDto viewpointDto){
		if(viewpointDto==null){
			return false;
		}
		//标题为空
		if(StringUtils.isBlank(viewpointDto.getTitle())){
			return false;
		}
		//内容简介为空
		if(StringUtils.isBlank(viewpointDto.getContent())){
			return false;
		}
		//专题
				//没一个专题必须对应一张图片
		String special = viewpointDto.getSpecial();
		String fileId = viewpointDto.getFileId();
		if(StringUtils.isBlank(special)){
			if(StringUtils.isBlank(fileId)){
				return false;
			}else{
				String[] specialArr = special.split(",");
				String[] fileIdArr = fileId.split(",");
				for (int i = 0; i < specialArr.length; i++) {
					String specialStr = specialArr[i].trim();
					String fileIdStr = fileIdArr[i].trim();
					if(StringUtils.isNotBlank(specialStr)){
						if(StringUtils.isBlank(fileIdStr)){
							return false;
						}else{
							continue;
						}
					}else{
						if(StringUtils.isBlank(fileIdStr)){
							continue;
						}else{
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
