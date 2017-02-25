package com.xa.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.xslf.model.geom.CosExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xa.entity.DebrisSession;
import com.xa.entity.DsGood;
import com.xa.entity.FastBuySession;
import com.xa.entity.FbsDs;
import com.xa.entity.File;
import com.xa.enumeration.PhotoType;
import com.xa.mapper.DebrisSessionMapper;
import com.xa.mapper.DsGoodMapper;
import com.xa.mapper.FastBuySessionMapper;
import com.xa.mapper.FbsDsMapper;
import com.xa.mapper.FileMapper;
import com.xa.service.FastBuySessionService;
import com.xa.service.FileService;
import com.xa.util.Constants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class FastBuySessionServiceImpl extends BaseServiceImpl<FastBuySession, FastBuySessionMapper>
		implements FastBuySessionService<FastBuySession> {
	
	@Autowired
	private DebrisSessionMapper debrisSessionMapper;
	
	@Autowired
	private DsGoodMapper dsGoodMapper;
	
	@Autowired
	private FbsDsMapper fbsDsMapper;
	
	@Autowired
	private FileMapper fileMapper;
	

	/**
	 * 添加秒杀专场
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void addFastBuySession(FastBuySession session,
			Long[] goodIds,
			Date[] cronDates,
			MultipartFile imgAdvFile,
			Integer inventory,
			FileService<File> fileService) throws IllegalStateException, IOException{
		
		//上传轮播图
		if(null != imgAdvFile && imgAdvFile.getSize() > 0){
			File file = new File();
			fileService.uploadFile(imgAdvFile, PhotoType.FAST_BUY_SESSION_FIGURE, file );
			session.setImgAdvId(file.getId());
		}
		session.setAddTime(new Date());
		this.m.insert(session);
		
		for(int i=0; i<cronDates.length;i++){
			DebrisSession ds = new DebrisSession();
			ds.setCronTime(cronDates[i]);
			this.debrisSessionMapper.insert(ds);
			
			DsGood dg = new DsGood();
			dg.setDsId(ds.getId());
			dg.setGoodId(goodIds[i]);
			dg.setInventory(inventory);
			this.dsGoodMapper.insert(dg);
			
			FbsDs fd = new FbsDs();
			fd.setDsId(ds.getId());
			fd.setFbsId(session.getId());
			fbsDsMapper.insert(fd);
		}
		
		
	}
	
	
	/**
	 * 获取所有专场
	 * @return
	 */
	public String getAllSession(Integer pageNum,Integer pageSize){
		JSONObject object = new JSONObject();
		PageHelper.startPage(pageNum, pageSize, true);
		Page<FastBuySession> fbsPage=(Page<FastBuySession>) this.m.findAll();
		List<FastBuySession> fbsList= fbsPage.getResult();
		JSONArray array = new JSONArray();
		for(int i=0;i<fbsList.size();i++){
			JSONObject fbsObj = new JSONObject();
			FastBuySession fbs= fbsList.get(i);
			Long imgAdvId= fbs.getImgAdvId();
			String uriPath="";
			if(null != imgAdvId){
				File file= this.fileMapper.selectByPrimaryKey(imgAdvId);
				uriPath= file.getUriPath(); 
			}
			fbs.getStartTime();
			fbs.getEndTime();
			Long id= fbs.getId();
			String name= fbs.getName();
			String info= fbs.getInfo();
			fbsObj.accumulate("name", name)
			.accumulate("id", id)
			.accumulate("info", info)
			.accumulate("uriPath", uriPath)
			;
			array.add(fbsObj);
		}
		object.accumulate(Constants.ROWS, array)
		.accumulate(Constants.TOTAL, fbsPage.getTotal())
		;
		return object.toString();
	}
	
	/**
	 * 编辑专场
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void editSession(FastBuySession session,
			MultipartFile imgAdvFile,
			FileService<File> fileService) throws IllegalStateException, IOException{
		Float discount= session.getDiscount();
		session.setDiscount(discount*0.1f);
		if(session.getImgAdvId() == null){
			if(null != imgAdvFile && imgAdvFile.getSize() > 0){
				File file = new File();
				fileService.uploadFile(imgAdvFile, PhotoType.FAST_BUY_SESSION_FIGURE, file );
				session.setImgAdvId(file.getId());
			}
		}else {
			File file = new File();
			file.setId(session.getImgAdvId());
			fileService.editFile(imgAdvFile, PhotoType.FAST_BUY_SESSION_FIGURE, file );
		}
		
		this.m.updateByPrimaryKeySelective(session);
	}
	
	/**
	 * 获取专场信息
	 * @param modelAndView
	 * @param id
	 */
	public void getSession(ModelAndView modelAndView, Long id){
		 FastBuySession session= this.m.selectByPrimaryKey(id);
		 session.setDiscount(session.getDiscount()*10);
		 Long imgAdvId= session.getImgAdvId();
		 File file= this.fileMapper.selectByPrimaryKey(imgAdvId);
		 modelAndView.addObject("file",file);
		 modelAndView.addObject("session", session);
		 
		 Date startTime= session.getStartTime();
		 Date endTime= session.getEndTime();
		 String startTimeStr= DateFormatUtils.format(startTime, Constants.DEFAULT_DATE_FORMAT);
		 String endTimeStr= DateFormatUtils.format(endTime, Constants.DEFAULT_DATE_FORMAT);
		 modelAndView.addObject("startTimeStr", startTimeStr);
		 modelAndView.addObject("endTimeStr", endTimeStr);
	}
	
	/**
	 * 获取专场信息   中国时间格式
	 * @param modelAndView
	 * @param id
	 */
	public void getSessionByCommonFormat(ModelAndView modelAndView, Long id) {
//		DebrisSession dSession= this.m.selectByPrimaryKey(id);
//		modelAndView.addObject("dSession", dSession);
//		
//		String cronTimeStr= DateFormatUtils.format(dSession.getCronTime(), Constants.DEFAULT_DATE_FORMAT);
//		modelAndView.addObject("cronTimeStr", cronTimeStr);
		modelAndView.addObject("fbsId", id);
		
		
		FastBuySession fastBuySession= m.selectByPrimaryKey(id);
		Date startTime= fastBuySession.getStartTime();
		Date endTime= fastBuySession.getEndTime();
		
		String startTimeStr= DateFormatUtils.format(startTime, Constants.COMMON_DATE_FORMAT);
		String endTimeStr= DateFormatUtils.format(endTime, Constants.COMMON_DATE_FORMAT);
		modelAndView.addObject("startTimeStr", startTimeStr);
		modelAndView.addObject("endTimeStr", endTimeStr);
	}
	
	/**
	 * 获取专场中的时间
	 * @param id
	 * @return
	 */
	public String getTimeBySessionId(Long id){
		JSONObject object = new JSONObject();
		List<DebrisSession> dsList= this.fbsDsMapper.getDebrisSessionByFBSID(id);
		JSONArray array = new JSONArray();
		for(int i=0; i<dsList.size();i++){
			JSONObject dsObject= new JSONObject();
			DebrisSession debrisSession= dsList.get(i);
			Date time= debrisSession.getCronTime();
			Long dsId=debrisSession.getId();
			String datestr= DateFormatUtils.format(time, Constants.COMMON_DATE_FORMAT);
			dsObject.accumulate("id", dsId)
			.accumulate("date", datestr)
			;
			array.add(dsObject);
		}
		object.accumulate(Constants.ROWS, array);
		return object.toString();
	}

	/**
	 * 删除秒杀专场
	 * @param fbsId
	 */
	public String delSession(Long fbsId){
		 JSONObject object = new JSONObject();
		 List<FbsDs> fdList= this.fbsDsMapper.getFbsDsByFbsId(fbsId);
		 for(int i=0;i<fdList.size();i++){
			 FbsDs fd= fdList.get(i);
			 Long dsId= fd.getDsId();
			 List<DsGood> dgList= this.dsGoodMapper.getDSGoodByDSID(dsId);
			 for(int j=0;j<dgList.size();j++){
				  DsGood dg= dgList.get(j);
				  dsGoodMapper.deleteByPrimaryKey(dg.getId());
			 }
			 fbsDsMapper.deleteByPrimaryKey(fd.getId());
		 }
		 this.m.deleteByPrimaryKey(fbsId);
		 object.accumulate(Constants.SUCCESS, true);
		 return object.toString();
	}
}
