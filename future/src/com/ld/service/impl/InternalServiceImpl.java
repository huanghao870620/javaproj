package com.ld.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.InternalDto;
import com.ld.mapper.InternalMapper;
import com.ld.mapper.InternalSpecialFileRelaMapper;
import com.ld.model.Internal;
import com.ld.model.InternalSpecialFileRela;
import com.ld.model.MessageBO;
import com.ld.service.InternalService;
import com.ld.util.Logs;
import com.opensymphony.xwork2.ActionContext;

@Service
@Transactional
public class InternalServiceImpl extends BaseServiceImpl<Internal, InternalMapper> implements InternalService<Internal> {

	@Autowired
	private InternalMapper internalMapper;
	
	@Autowired
	private InternalSpecialFileRelaMapper internalSpecialFileRelaMapper;
	
	/**
	 * 查询交易内参列表
	 */
	public List<Internal> queryInternalByAjax(String internalType) {
		List<Internal> result = null;
		try {
			Internal internal = new Internal();
			internal.setInternalType(internalType);
			ActionContext ac = ActionContext.getContext();
			Map<String,Object> map = ac.getParameters();
			String[] params = (String[]) map.get("title");
			if(ArrayUtils.isNotEmpty(params)&&StringUtils.isNotBlank(params[0])){
				internal.setTitle(params[0]);
			}
			result = this.internalMapper.findAllByPaging(internal);
			if(result!=null&&result.size()>0){
				for (int i = 0; i < result.size(); i++) {
					Internal v = result.get(i);
					//查询交易内参关联的专题、图片信息
					List<InternalSpecialFileRela> list = this.internalSpecialFileRelaMapper.findListById(v.getInternalId());
					if(list!=null&&list.size()>0){
						StringBuilder sb = new StringBuilder();
						for (int j = 0; j < list.size(); j++) {
							InternalSpecialFileRela internalSpecialFileRela = list.get(j);
							sb.append(internalSpecialFileRela.getSpecial());
							sb.append(" ");
						}
						v.setSpecial(sb.toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("InternalServiceImpl.queryInternalByAjax error",e);
		}
		return result;
	}
	
	/**
	 * 根据ID查询内参信息
	 */
	public MessageBO findById() {
		MessageBO messageBO = null;
		try {
			ActionContext context = ActionContext.getContext();
			Map<String, Object> map = context.getParameters();
			String[] internalId = (String[]) map.get("internalId");
			if (ArrayUtils.isNotEmpty(internalId)) {
				Internal internal = this.internalMapper.findById(new BigDecimal(internalId[0]));
				InternalDto internalDto = new InternalDto();
				internalDto.setInternalId(internal.getInternalId());
				internalDto.setTitle(internal.getTitle());
				internalDto.setContent(internal.getContent());
				internalDto.setCreateTime(internal.getCreateTime());
				internalDto.setUpdateTime(internal.getUpdateTime());
				List<InternalSpecialFileRela> list = this.internalSpecialFileRelaMapper.findListById(new BigDecimal(internalId[0]));
				if (list != null && list.size() > 0) {
					List<String> specialList = new ArrayList<String>();
					List<String> FileIdList = new ArrayList<String>();
					for (int i = 0; i < list.size(); i++) {
						InternalSpecialFileRela internalSpecialFileRela = list.get(i);
						specialList.add(internalSpecialFileRela.getSpecial());
						FileIdList.add(internalSpecialFileRela.getCfileId());
					}
					internalDto.setSpecialArray(specialList.toArray(new String[]{}));
					internalDto.setFileIdArray(FileIdList.toArray(new String[]{}));
				}
				messageBO = new MessageBO("-1","查询内参详情成功!",internalDto);
			}else{
				messageBO = new MessageBO("0","查询内参详情失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("InternalServiceImpl.findById error",e);
			messageBO = new MessageBO("0","查询内参详情失败!");
		}
		return messageBO;
	}

	/**
	 * 添加交易内参
	 */
	public MessageBO addInternal(InternalDto internalDto) {
		MessageBO messageBO = null;
		try {
			boolean bo = validateInternal(internalDto);
			if(bo){
				//当前系统时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowDate = sdf.format(new Date());
				Internal internal = new Internal();
				internal.setTitle(internalDto.getTitle());
				internal.setContent(internalDto.getContent());
				internal.setCreateTime(sdf.parse(nowDate));
				internal.setUpdateTime(sdf.parse(nowDate));
				internal.setInternalType(internalDto.getInternalType());
				int result = this.internalMapper.insert(internal);
				if(result>0){
					String specialStr = internalDto.getSpecial();
					String fileIdStr = internalDto.getFileId();
					if(StringUtils.isNotBlank(fileIdStr)){
						String[] fileIds = fileIdStr.split(",");
						String[] special = specialStr.split(",");
						for (int i = 0; i < fileIds.length; i++) {
							if(ArrayUtils.isNotEmpty(fileIds)&&StringUtils.isNotBlank(fileIds[i].trim())){
								InternalSpecialFileRela internalSpecialFileRela = new InternalSpecialFileRela();
								internalSpecialFileRela.setInternalId(internal.getInternalId());
								internalSpecialFileRela.setSpecial(special[i].trim());
								internalSpecialFileRela.setCfileId(fileIds[i].trim());
								internalSpecialFileRela.setSpecialFileSequ(new BigDecimal(i+1));
								internalSpecialFileRela.setCreateTime(sdf.parse(nowDate));
								internalSpecialFileRela.setUpdateTime(sdf.parse(nowDate));
								this.internalSpecialFileRelaMapper.insert(internalSpecialFileRela);
							}
						}
					}
					messageBO = new MessageBO("-1","新增交易内参成功!");
				}
			}else{
				Logs.getLogger().info("InternalServiceImpl.addInternal 参数验证失败");
				messageBO = new MessageBO("0","新增交易内参失败,请按要求填写交易内参信息!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("InternalServiceImpl.addInternal error",e);
			messageBO = new MessageBO("0","新增交易内参失败!");
		}
		return messageBO;
	}

	/**
	 * 更新交易内参信息
	 */
	public MessageBO updateInternal(InternalDto internalDto) {
		MessageBO messageBO = null;
		try {
			boolean bo = validateInternal(internalDto);
			if(bo){
				//当前系统时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowDate = sdf.format(new Date());
				Internal internal = this.internalMapper.findById(internalDto.getInternalId());
				if(internal!=null){
					internal.setTitle(internalDto.getTitle());
					internal.setContent(internalDto.getContent());
					internal.setUpdateTime(sdf.parse(nowDate));
					this.internalMapper.updateByPrimaryKey(internal);
				}
				String fileIdStr = internalDto.getFileId();
				String[] fileIds = fileIdStr.split(",");
				String[] special = internalDto.getSpecial().split(",");
				//根据内参ID查询所对应的图片列表
				for (int i = 0; i < fileIds.length; i++) {
					InternalSpecialFileRela internalSpecialFileRela = new InternalSpecialFileRela();
					internalSpecialFileRela.setInternalId(internal.getInternalId());
					internalSpecialFileRela.setSpecialFileSequ(new BigDecimal(i+1));
					InternalSpecialFileRela result = this.internalSpecialFileRelaMapper.findByIdAndSequ(internalSpecialFileRela);
					String fileId = fileIds[i].trim();
					if(result!=null&&StringUtils.isNotBlank(fileId)){
						result.setSpecial(special[i].trim());
						result.setCfileId(fileId);
						result.setUpdateTime(sdf.parse(nowDate));
						this.internalSpecialFileRelaMapper.updateByPrimaryKey(result);
					}else if(StringUtils.isNotBlank(fileId)){
						InternalSpecialFileRela cfile = new InternalSpecialFileRela();
						cfile.setSpecial(special[i].trim());
						cfile.setCfileId(fileIds[i].trim());
						cfile.setInternalId(internal.getInternalId());
						cfile.setSpecialFileSequ(new BigDecimal(i+1));
						cfile.setCreateTime(sdf.parse(nowDate));
						cfile.setUpdateTime(sdf.parse(nowDate));
						this.internalSpecialFileRelaMapper.insert(cfile);
					}
				}
				messageBO = new MessageBO("-1","更新交易内参成功!");
			}else{
				Logs.getLogger().info("InternalServiceImpl.updateInternal 参数验证失败");
				messageBO = new MessageBO("0","更新交易内参失败,请按要求填写交易内参信息!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("InternalServiceImpl.updateInternal error",e);
			messageBO = new MessageBO("0","更新交易内参失败!");
		}
		return messageBO;
	}

	/**
	 * 删除交易内参信息
	 */
	public MessageBO deleteInternal() {
		MessageBO messageBO = null;
		try {
			ActionContext act = ActionContext.getContext();
			Map<String, Object> map = act.getParameters();
			String[] ids = (String[]) map.get("id");
			
			if (ArrayUtils.isNotEmpty(ids)) {
				String idString = new String(ids[0]);
				String[] idList =idString.split(",");
				for(int k=0 ; k<idList.length ; k++){
					BigDecimal id = new BigDecimal(idList[k]);
				//删除交易内参信息
				this.internalMapper.delete(id);
				//删除交易内参关联的图片信息
				this.internalSpecialFileRelaMapper.delete(id);
				messageBO = new MessageBO("-1","删除交易内参成功!");
				}
			}else{
				messageBO = new MessageBO("0","删除交易内参失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("InternalServiceImpl.deleteInternal error",e);
			messageBO = new MessageBO("0","删除交易内参失败!");
		}
		return messageBO;
	}
	
	/**
	 * 验证交易内参表单数据
	 * @param viewpointDto
	 */
	private boolean validateInternal(InternalDto internalDto){
		if(internalDto==null){
			return false;
		}
		//标题为空
		if(StringUtils.isBlank(internalDto.getTitle())){
			return false;
		}
		//内容简介为空
		if(StringUtils.isBlank(internalDto.getContent())){
			return false;
		}
		//内参类型
		if(StringUtils.isBlank(internalDto.getInternalType())){
			return false;
		}
		//专题和图片必须成对出现
		String special = internalDto.getSpecial();
		String fileId = internalDto.getFileId();
		if(StringUtils.isNotBlank(special)){
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
