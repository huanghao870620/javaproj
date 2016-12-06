
/**
 * string to dom
 * @param o
 * @returns
 */
var  interactionTeacherChatDom = function(o){
	this.data = o;
}

/**
 * fill data
 * @returns {String}
 */
interactionTeacherChatDom.prototype.fill = function(){
	var str = '';
	str += '<div class="chart_b">';
	str += '<div class="chart_pic">';
	if(this.data.isCustomer){
		str += '<img src="'+ contextPath + '/res/images/level'+this.data.quesCreatorLevelId+'.png" width="48" height="20">';
	}else{
		if(this.data.quesCreatorHeadId != null){
			str += '<img src="'+contextPath+'/download/file/show.htm?id='+this.data.quesCreatorHeadId+'" width="45" height="40">';
		}else{
			str += '<img src="'+contextPath+'/res/images/default.png" width="45" height="40">';
		}
	}
	str += '<p class="name">'+this.data.quesCreatorName+'</p>';
	str += '</div>';
	str += '<div class="qipao">';
	str += '<span class="chat_arrow"></span>';
	str += '<ol class="chart_m">';
	str += '<li class="chart_c"><p class="content">'+this.data.quesContent+'</p>';
	str += '<p class="function"><i>'+this.data.quesCreateTime+'</i><a privateMessageId="'+this.data.privateMessageId+'" href="javascript:;" class="ask-a">回复</a></p></li>';
	str += '</ol>';
	str += '</div>';
	str += '</div>';
return str;
}


/**
 * string to node
 */
interactionTeacherChatDom.prototype.str2node = function(){
	return  parseDom(this.fill());
}
