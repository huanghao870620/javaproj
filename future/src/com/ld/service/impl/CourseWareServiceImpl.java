package com.ld.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ld.dto.CourseWareDto;
import com.ld.mapper.CfileMapper;
import com.ld.mapper.CourseWareMapper;
import com.ld.mapper.CustomerLevelLimitRelaMapper;
import com.ld.model.Cfile;
import com.ld.model.CourseWare;
import com.ld.model.CustomerLevelLimitRela;
import com.ld.model.MessageBO;
import com.ld.service.CourseWareService;
import com.ld.sftp.SftpServ;
import com.ld.util.LoginUtil;
import com.ld.util.Logs;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

@Service
@Transactional
public class CourseWareServiceImpl extends BaseServiceImpl<CourseWare, CourseWareMapper>
		implements CourseWareService<CourseWare> {

	@Autowired
	private CourseWareMapper courseWareMapper;

	@Autowired
	private CfileMapper cfileMapper;

	@Autowired(required = true)
	private SftpServ sftp;

	@Autowired
	CustomerLevelLimitRelaMapper customerLevelLimitRelaMapper;
	/**
	 * 查询交易课件列表
	 */
	public List<CourseWare> queryCourseWareByAjax(String courseWareType) {
		List<CourseWare> result = null;
		try {
			CourseWare courseware = new CourseWare();
			courseware.setCourseWareType(courseWareType);
			ActionContext ac = ActionContext.getContext();
			Map<String, Object> map = ac.getParameters();
			String[] params = (String[]) map.get("courseware_name");
			if (ArrayUtils.isNotEmpty(params) && StringUtils.isNotBlank(params[0])) {
				courseware.setCourseName(params[0]);
			}
			result = this.courseWareMapper.findAllByPaging(courseware);

		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CoursewareServiceImpl.queryCoursewareByAjax error", e);
		}
		return result;
	}

	/**
	 * 根据ID查询课件信息
	 */
	public MessageBO findById() {
		MessageBO messageBO = null;
		try {
			ActionContext context = ActionContext.getContext();
			Map<String, Object> map = context.getParameters();
			String[] courseWareId = (String[]) map.get("courseWareId");
			if (ArrayUtils.isNotEmpty(courseWareId)) {
				CourseWare courseware = this.courseWareMapper.findById(new BigDecimal(courseWareId[0]));
				CourseWareDto courseWareDto = new CourseWareDto();
				courseWareDto.setCourseWareId(courseware.getCourseWareId());
				courseWareDto.setCfileId(courseware.getCfileId());
				courseWareDto.setCourseName(courseware.getCourseName());
				courseWareDto.setLinkAddress(courseware.getLinkAddress());
				courseWareDto.setCreateTime(courseware.getCreateTime());
				courseWareDto.setUpdateTime(courseware.getUpdateTime());
				messageBO = new MessageBO("-1", "查询课件成功!", courseWareDto);
			} else {
				messageBO = new MessageBO("0", "查询课件失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CourseWareServiceImpl.findById error", e);
			messageBO = new MessageBO("0", "查询课件详情失败!");
		}
		return messageBO;
	}

	/**
	 * 添加交易课件
	 */
	public MessageBO addCourseWare(CourseWareDto coursewaredto) {
		MessageBO messageBO = null;
		try {
			boolean bo = validateCourseWare(coursewaredto);
			if (bo) {
				// 当前系统时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowDate = sdf.format(new Date());
				coursewaredto.setCreateTime(sdf.parse(nowDate));
				int result = this.courseWareMapper.insert(coursewaredto);
				if (result > 0) {
					messageBO = new MessageBO("-1", "新增课件成功!");
				}
				//增加权限信息
				List<CustomerLevelLimitRela> list = this.customerLevelLimitRelaMapper.batchFindCustomerLevelLimitRela();
				for(int j=0;j<list.size();j++){
					CustomerLevelLimitRela clllr = list.get(j);
					JSONObject jsons = JSONObject.fromObject(clllr.getAllCourseWareLevel());	
				    jsons.put(coursewaredto.getCourseWareId(), "N");
				    clllr.setAllCourseWareLevel(jsons.toString());
				    list.remove(j);
				    list.add(j, clllr);
				}
				this.customerLevelLimitRelaMapper.batchUpdateByCustomerLevelLimitRelaList(list);
				
				 LoginUtil.COURSEWARELIST=this.courseWareMapper.queryAllCourseWare();
			} else {
				Logs.getLogger().info("CourseWareServiceImpl.addCourseWare 参数验证失败！");
				messageBO = new MessageBO("0", "新增课件失败,请按要求填写交易课件信息!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CourseWareServiceImpl.addCourseWare error", e);
			messageBO = new MessageBO("0", "新增课件失败!");
		}
		return messageBO;
	}

	/**
	 * 更新交易课件信息
	 */
	public MessageBO updateCourseWare(CourseWareDto coursewaredto) {
		MessageBO messageBO = null;
		try {
			boolean bo = validateCourseWare(coursewaredto);
			if (bo) {
				//当前系统时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowDate = sdf.format(new Date());
				CourseWare courseWare = this.courseWareMapper.findById(coursewaredto.getCourseWareId());
				if (courseWare != null) {
					courseWare.setCfileId(coursewaredto.getCfileId());
					courseWare.setCourseName(coursewaredto.getCourseName());
					courseWare.setCourseWareType(coursewaredto.getCourseWareType());
					courseWare.setLinkAddress(coursewaredto.getLinkAddress());
					courseWare.setUpdateTime(sdf.parse(nowDate));
					this.courseWareMapper.updateByPrimaryKey(courseWare);
				}
				LoginUtil.COURSEWARELIST=this.courseWareMapper.queryAllCourseWare();
				messageBO = new MessageBO("-1", "更新交易课件成功!");
			} else {
				Logs.getLogger().info("CourseWareServiceImpl.updateCourseWare 参数验证失败！");
				messageBO = new MessageBO("0", "更新课件失败,请按要求填写课件信息!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CourseWareServiceImpl.updateCourseWare error", e);
			messageBO = new MessageBO("0", "更新课件失败!");
		}
		return messageBO;
	}

	/**
	 * 删除交易课件信息
	 */
	public MessageBO deleteCourseWare() {
		MessageBO messageBO = null;
		try {
			ActionContext act = ActionContext.getContext();
			Map<String, Object> map = act.getParameters();
			String[] ids = (String[]) map.get("id");
			if (ArrayUtils.isNotEmpty(ids)) {
				String idString = new String(ids[0]);
				String[] idList = idString.split(",");
				for (int k = 0; k < idList.length; k++) {
					BigDecimal id = new BigDecimal(idList[k]);
					CourseWare cw = this.courseWareMapper.findById(id);
					Cfile cfile = this.cfileMapper.findById(cw.getCfileId());
					// 删除交易课件信息
					this.courseWareMapper.delete(id);
					// 删除图片
					sftp.delete(cfile.getName());
					//删除权限信息
					List<CustomerLevelLimitRela> list = this.customerLevelLimitRelaMapper.batchFindCustomerLevelLimitRela();
					for(int j=0;j<list.size();j++){
						CustomerLevelLimitRela clllr = list.get(j);
						JSONObject jsons = JSONObject.fromObject(clllr.getAllCourseWareLevel());	
					    jsons.remove(idList[k]);
					    clllr.setAllCourseWareLevel(jsons.toString());
					    list.remove(j);
					    list.add(j, clllr);
					}
					this.customerLevelLimitRelaMapper.batchUpdateByCustomerLevelLimitRelaList(list);
					
					LoginUtil.COURSEWARELIST=this.courseWareMapper.queryAllCourseWare();
					messageBO = new MessageBO("-1", "删除交易课件成功!");

				}
			} else {
				messageBO = new MessageBO("0", "删除交易课件失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("CourseWareServiceImpl.deleteCourseWare error", e);
			messageBO = new MessageBO("0", "删除课件失败!");
		}
		return messageBO;
	}

	/**
	 * 验证交易课件表单数据
	 * 
	 * @param viewpointDto
	 */
	private boolean validateCourseWare(CourseWareDto coursewaredto) {
		if (coursewaredto == null) {
			return false;
		}
		//课件名称
		if (StringUtils.isBlank(coursewaredto.getCourseName())) {
			return false;
		}
		// 课件类型
		if (StringUtils.isBlank(coursewaredto.getCourseWareType())) {
			return false;
		}
		// 链接地址
		if (StringUtils.isBlank(coursewaredto.getLinkAddress())) {
			return false;
		}
		// 图片ID
		if (StringUtils.isBlank(coursewaredto.getCfileId().toString())) {
			return false;
		}

		return true;
	}
}
