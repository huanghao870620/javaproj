package com.ld.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ld.mapper.AdviceTypeMapper;
import com.ld.model.AdviceType;
import com.ld.service.AdviceTypeService;
import com.opensymphony.xwork2.ActionContext;

@Service

public class AdviceTypeServiceImpl extends BaseServiceImpl<AdviceType, AdviceTypeMapper > implements AdviceTypeService<AdviceType> {
	 @Autowired
	 private AdviceTypeMapper adviceTypeMapper;
	 /**
	  * 获取所有级别  by ajax
	  * @return
	  * @throws TemplateException 
	  * @throws IOException 
	  */
	/* public JSONObject getAllLevelByJson() throws IOException, TemplateException{
		 Map<String, Object> root = new HashMap<String,Object>();
		 root.put("levels", this.levelMapper.findAll());
		 ProcessFtl pf = new ProcessFtl(root, "show_level.ftl");
		 return new JSONObject()
		 .accumulate("data", pf.process())
		 ;
	 }
   */
	@Override
	public void getAllAdviceType(ActionContext ctx) {
		// TODO Auto-generated method stub
		ctx.put("adviceTypes", this.adviceTypeMapper.findAll());
	}

}
