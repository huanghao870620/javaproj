package com.xa.service.impl;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.xa.entity.DebrisSession;
import com.xa.entity.DsGood;
import com.xa.entity.FastBuySession;
import com.xa.entity.FbsDs;
import com.xa.mapper.DebrisSessionMapper;
import com.xa.mapper.DsGoodMapper;
import com.xa.mapper.FastBuySessionMapper;
import com.xa.mapper.FbsDsMapper;
import com.xa.service.DebrisSessionService;
import com.xa.util.Constants;
/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class DebrisSessionServiceImpl extends BaseServiceImpl<DebrisSession, DebrisSessionMapper>
		implements DebrisSessionService<DebrisSession> {
	
	@Autowired
	private FastBuySessionMapper fastBuySessionMapper;
	
	@Autowired
	private DsGoodMapper dsGoodMapper;
	
	@Autowired
	private FbsDsMapper fbsDsMapper;
	

	/**
	 * 获取抢购时间
	 * @param modelAndView
	 * @param id
	 */
	public void getDebrisSession(ModelAndView modelAndView,Long id,Long fbsId){
		DebrisSession dSession= this.m.selectByPrimaryKey(id);
		modelAndView.addObject("dSession", dSession);
		
		String cronTimeStr= DateFormatUtils.format(dSession.getCronTime(), Constants.DEFAULT_DATE_FORMAT);
		modelAndView.addObject("cronTimeStr", cronTimeStr);
		modelAndView.addObject("fbsId", fbsId);
		
		
		FastBuySession fastBuySession= fastBuySessionMapper.selectByPrimaryKey(fbsId);
		Date startTime= fastBuySession.getStartTime();
		Date endTime= fastBuySession.getEndTime();
		
		String startTimeStr= DateFormatUtils.format(startTime, Constants.COMMON_DATE_FORMAT);
		String endTimeStr= DateFormatUtils.format(endTime, Constants.COMMON_DATE_FORMAT);
		modelAndView.addObject("startTimeStr", startTimeStr);
		modelAndView.addObject("endTimeStr", endTimeStr);
	}
	
	/**
	 * 编辑抢购时间
	 * @param modelAndView
	 * @param dSession
	 */
	public void editDebrisSession(ModelAndView modelAndView,DebrisSession dSession,Long fbsId){
		 this.m.updateByPrimaryKeySelective(dSession);
		 modelAndView.setViewName("redirect:/fastBuySession/toEditSession.htm?id="+fbsId);
	}
	
	/**
	 * 添加抢购时间
	 * @param dSession
	 */
	public void addDebrisSession(DebrisSession dSession,Long goodId,Long fbsId){
		this.m.insert(dSession);
		DsGood dg= new DsGood();
		dg.setDsId(dSession.getId());
		dg.setGoodId(goodId);
		this.dsGoodMapper.insert(dg);
		
		FbsDs fd = new FbsDs();
		fd.setDsId(dSession.getId());
		fd.setFbsId(fbsId);
		fbsDsMapper.insert(fd);
	}
	
	
}
