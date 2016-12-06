
/**
 * 提问时添加观点
 * @param o
 * @returns
 */
var customerAddViewDom = function(o){
	this.data = o;
}

/**
 * fill data
 * @returns {String}
 */
customerAddViewDom.prototype.fill = function(){
	var advice = this.data.adviceId == 1?'策略':'操作建议';
	var mineral = this.data.mineralId == 1?'油':'金属';
	var str = '<li id="interaction_li_'+this.data.quesCreatorId+'_'+this.data.viewId+'" class="clearfix chart-sep interaction_li_'+this.data.viewId+'">';
	str += '<div class="chart-new clearfix">';
	str += '<span class="chat_arrow"></span>';
	str += '<div class="chart_pic" style="margin-right: 20px;">';
	if(this.data.isCustomer){
		str += '<img src="'+ contextPath + '/res/images/level'+this.data.quesCreatorLevelId+'.png" width="48" height="20">';
	}else{
		if(this.currentIconId != null){
			str += '<img src="'+contextPath+'/download/file/show.htm?id='+this.data.quesCreatorHeadId+'" width="45" height="40">';
		}else{
			str += '<img src="'+contextPath+'/res/images/default.png" width="45" height="40">';
		}
	}
	str += '</div>';
	str += '<ol class="chart_m">';
	str += '<li class="tit"><em>'+this.data.quesCreatorName+'</em> <b>对 '+this.data.teacherName+'老师说</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>'+this.data.viewCreateTime+'</i></li>';
	str += '<li class="chart_c">';
	if(this.data.teacherIconId == null){
		str += '<img class="at_per" src="'+contextPath+'/res/images/default.png" width="45" height="40">';
	}else{
		str += '<img class="at_per" src="'+contextPath+'/download/file/show.htm?id='+this.data.teacherIconId+'" width="45" height="40">';
	}
	str += '<div class="main-c">';
	str += '<p class="pic_r">'+this.data.viewContent+'</p>';
	str += '<div class="chart_tp">';
	str += '<div class="chart_tp-l">';
	if(this.data.viewCfile!=null && this.data.viewCfile != 0){
		str += '<a href="javascript:lookBigImg('+this.data.viewCfile+');" class="a-teach-img">';
		str += '<img src="'+ contextPath + '/download/file/show.htm?id='+this.data.viewCfile+'" width="140" height="88">';
		str += '</a>';
	}
	str += '<a class="link_j">'+mineral+'</a><a class="link_c">'+advice+'</a>';
	str += '</div>';
	str += '</div>';
	str += '</div>';
	str += '</li>';
	str += '</ol>';
	str += '</div>';
	str += '<div class="chat_container">';
	str += '</div>'
	str += '</li>';
	return str;
}


/**
 * string to node
 */
customerAddViewDom.prototype.str2node = function(){
	return  parseDom(this.fill());
}
