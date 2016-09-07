
/**
 * 实盘房间 讲师观点模块（当前登录用户为客户 角色）
 * @param o
 * @returns
 */
var viewCustomerDom = function(o){
	this.basepath = o.basepath;
    this.content = o.content;
	this.adviceId = o.adviceId;
	this.mineralId = o.mineralId;
	this.cfileId = o.cfileId;
	this.id = o.id;
	this.createTime =o.createTime;
	this.teacherId =o.teacherId;
	this.teacherName =o.teacherName;
	this.teacherIconId = o.teacherIconId;
	this.isReplied = o.isReplied;
}
/**
 * fill data
 * @returns {String}
 */
viewCustomerDom.prototype.fill = function(){
	var str = '';
		str +='<li id="teacherView_li'+this.id +'" class="green" style="display: list-item;">';
		str +='<dl><dt class="time-stamp">'+this.createTime+'</dt><dd class="teach-name">';
		if(this.teacherIconId == null||this.teacherIconId == 0){
			str += '<img src="'+this.basepath+'/res/images/default.png" width="45" height="40">';
		}else{
			str += '<img src="'+this.basepath+'/download/file/show.htm?id='+this.teacherIconId+'" width="45" height="40">';
		}
		str +=' '+this.teacherName+'老师 </dd>'
		str +='<dd class="teach-tex">';
		str += this.content
		if(!this.isReplied){
			str += '<a teacherId="'+this.teacherId+'" viewId="'+this.id+'" questionType="1" href="javascript:;" class="ask-a">向老师提问</a>';
		}
		str += '</dd>';
		str +='<dd class="teach-img">';
		if(this.cfileId!=null&&this.cfileId!=0){
			str +='<a href="javascript:lookBigImg();" class="a-teach-img">';
			str +='<img width="229" height="145"  src="'+this.basepath+'/download/file/show.htm?id='+this.cfileId +'">';
			str +='</a>';
		}
		str +='<span><em>'+this.mineralId+'</em> '+this.adviceId+'</span>';
		str +='</dd></dl></li>';
		return str;
}

/**
 * string to node
 */
viewCustomerDom.prototype.str2node = function(){
	return  parseDom(this.fill());
}
