package com.xa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xa.entity.GoodFile;
import com.xa.mapper.GoodFileMapper;
import com.xa.service.GoodFileService;
import com.xa.service.impl.BaseServiceImpl;
import com.xa.util.Constants;

import net.sf.json.JSONObject;
/**
 * 
 * @author burgess
 *
 */
@Service
@Transactional
public class GoodFileServiceImpl extends BaseServiceImpl<GoodFile, GoodFileMapper>
		implements GoodFileService<GoodFile> {
	
	

	/**
	 * 
	 * @param goodFile
	 * @return
	 */
	public String addGoodFile(GoodFile goodFile){
		JSONObject object = new JSONObject();
		 this.m.insert(goodFile);
		 object.accumulate(Constants.SUCCESS, true);
		 return object.toString();
	}
	
	
	public void delGoodFileByGoodId(Long  goodId){
		List<GoodFile> gfs= this.m.selectGoodFileByGoodId(goodId);
		for(int i=0;i<gfs.size();i++){
			 GoodFile gf= gfs.get(i);
			 Long gId= gf.getGoodId();
			 Long fileId= gf.getFileId();
		}
	}
}
