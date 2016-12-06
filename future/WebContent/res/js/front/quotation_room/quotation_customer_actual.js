
var actualPrivateDom = function(o){
	this.content = o.content;
	this.basepath = o.basepath;
	this.customerName = o.customerName;
	this.createTime = o.createTime;
}
/**
 * string to dom
 * @param o
 * @returns
 */
var actualCustomerChatDom = function(o){
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
actualCustomerChatDom.prototype.fill = function(){
	var str= '<li id="actual_chat_li'+this.customerId_msg+this.createTime+'" class="clearfix">';
	str += '<span class="port-head fl">';
	if(this.roleId_msg != "5"){
		if(this.cfileId == null){
			str +='<img src="'+ this.basepath + '/res/images/default.png" width="45" heigth="40">';
		}else{
			str +='<img src="'+ this.basepath + '/download/file/show.htm?id='+this.cfileId+'" width="45" heigth="40">';
		}
	}else{
		str +='<img src="'+ this.basepath + '/res/images/level'+this.levelId+'.png" width="48" heigth="20">';
	}
	str += '</span><div class="fl port-tex-box"> ';
	str += '<div class="port-tex clearfix">';
	str += '<div class="port-tex01 fl">';
	str += '<p class="port-name">'+this.customerName+'</p>';
	str += '<p class="port-item port-bor">'+this.content+'</p>';
	str += '</div>';
	str += '<div class="port-ope fr">';
	str += '<p><i class="watch-icon"></i>'+this.createTime+'</p>';
	str += '</div>';
	str += '</div>';
	str += '</div>';
	str += '</li>';
	return str;
}


/**
 * string to node
 */
actualCustomerChatDom.prototype.str2node = function(){
	return  parseDom(this.fill());
}
