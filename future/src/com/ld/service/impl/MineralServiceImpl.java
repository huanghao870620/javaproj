package com.ld.service.impl;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ld.mapper.MineralMapper;
import com.ld.model.Mineral;
import com.ld.service.MineralService;
import com.opensymphony.xwork2.ActionContext;

@Service
public class MineralServiceImpl extends BaseServiceImpl<Mineral, MineralMapper > implements MineralService<Mineral> {
	 @Autowired
	 private MineralMapper mineralMapper;
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
	public void getAllMineral(ActionContext ctx) {
		// TODO Auto-generated method stub
		List<Mineral>  mList = this.mineralMapper.findAll();
		ctx.put("minerals", mList);
		
	}


}
