/**
 * string to dom
 * @param o
 * 
 *  content : msg,
     		 basepath : '<%=request.getContextPath()%>',
     		 customerName:customerName,
     		 createTime : createTime,
     		 roleId_msg:roleId_msg,
     		 roleId_current:roleId_current,
     		 customerId_msg:customerId_msg,
     		 customerId_current:customerId_current
 * @returns
 */
var actualChatDom = function(o){
	this.content = o.content;
	this.basepath = o.basepath;
	this.customerName = o.customerName;
	this.createTime = o.createTime;
	this.roleId_msg = o.roleId_msg;
	this.roleId_current = o.roleId_current;
	this.customerId_msg =o.customerId_msg;
	this.customerId_current =o.customerId_current;
	this.levelId = o.levelId;
	this.cfileId = o.cfileId;
}
/**
 * fill data
 * @returns {String}
 */
actualChatDom.prototype.fill = function(){
	var str ='<li id="actual_chat_li'+this.customerId_msg+this.createTime+'" class="clearfix">';
		str +='<span class="port-head fl">';
		if(this.roleId_msg != "5"){
			if(this.cfileId == null){
				str +='<img src="'+ this.basepath + '/res/images/default.png" width="45" heigth="40">';
			}else{
				str +='<img src="'+ this.basepath + '/download/file/show.htm?id='+this.cfileId+'" width="45" heigth="40">';
			}
		}else{
			str +='<img src="'+ this.basepath + '/res/images/level'+this.levelId+'.png" width="45" heigth="40">';
		}
		str +='</span>';
		str +='<div class="fl port-tex-box">';
		str +='<div class="port-tex clearfix">';
		str +='<div class="port-tex01 fl">';
		str +='<p class="port-name">'+this.customerName+'</p>';
		str +='<div class="port-item port-bor" id="actual_content_div'+this.customerId_msg+this.createTime+'"><span>'+this.content+'</span>';
		if(this.roleId_msg=="5" && this.roleId_current=="1"){
			str +='<em id="public_text_em'+this.customerId_msg+this.createTime+'"';
			str +=' onclick="javascript:publicChat('+"'"+this.customerId_msg+"','"+this.roleId_msg+"','"+this.createTime+"','"+this.customerName+"','"+this.levelId+"'"+');"';
			str +=' class="c-color-r">公开</em>';
		}
		str +='</div></div>';
		str +='<div class="port-ope fr">';
		str +='<p><i class="watch-icon"></i>'+this.createTime+'</p>';
		str +='<p><i id= "actual_chat_del'+this.customerId_msg+this.createTime+'" onclick="javascript:deleteActualChat('+"'"+this.customerId_msg+"','"+this.createTime+"'"+');" class="del-icon"></i></p>';
		str +='</div></div></div></li>';
		return str;
}


/**
 * string to node
 */
actualChatDom.prototype.str2node = function(){
	return  parseDom(this.fill());
}
