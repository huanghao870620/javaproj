
var chatPrivateDom = function(o){
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
var chatDom = function(o){
	this.content = o.content;
	this.basepath = o.basepath;
	this.customerName = o.customerName;
	this.createTime = o.createTime;
	this.roleId_msg = o.roleId_msg;
	this.roleId_current = o.roleId_current;
	this.customerId_msg =o.customerId_msg;
	this.customerId_current =o.customerId_current;
}

function startSingleChat(customerid_msg,  customerId_current,roleId_msg,roleId_current){
	
	       var  teacherUserId;
		   if(customerid_msg!=customerId_current && roleId_msg!=roleId_current)
			{
			   // 判断哪个userid是教师，哪个userid是客户
			     if(roleId_msg=="1")// 当前消息的发送者角色为1 ，则为讲师； 说明点击该消息的人必然为客户
				   {
			    	 teacherUserId = customerid_msg;
				   }
			     else{
			    	 teacherUserId =customerId_current;
			     }
			     enterPrivateChat(teacherUserId);
			}
	   else{
	   }
}
/**
 * 
 * fill data
 * @returns {String}
 */
chatDom.prototype.fill = function(){
	var str ;
	
	str="  <li  onclick='startSingleChat("+this.customerId_msg+","+this.customerId_current+","+this.roleId_msg+","+this.roleId_current+")' class=\"talk-li\"> \
                                <img src=\"" + this.basepath + "/res/images/huangj.png\" class=\"user-level\"> \
                                <div class=\"li-tit\"> \
                                    <div class=\"li-tit-name\"> \
                                        <p> "+this.customerName+ "<span>["+this.createTime+"]</span></p> \
                                    </div> \
                                </div> \
                                <div class=\"li-text\"> \
                                    <div class=\"box\"> \
                                        <div class=\"detest\">  \
                                            <i></i>  \
                                            <p>   " +
                                                 this.content + 
                                            " </p> \
                                        </div> \
                                    </div> \
                                </div> \
                            </li>" ;
	return str;
}

chatPrivateDom.prototype.fill = function(){
	var str ;
	str="  <li   class=\"talk-li\"> \
                                <img src=\"" + this.basepath + "/res/images/huangj.png\" class=\"user-level\"> \
                                <div class=\"li-tit\"> \
                                    <div class=\"li-tit-name\"> \
                                        <p> "+this.customerName+ "<span>["+this.createTime+"]</span></p> \
                                    </div> \
                                </div> \
                                <div class=\"li-text\"> \
                                    <div class=\"box\"> \
                                        <div class=\"detest\">  \
                                            <i></i>  \
                                            <p>   " +
                                                 this.content + 
                                            " </p> \
                                        </div> \
                                    </div> \
                                </div> \
                            </li>" ;
	return str;
}

/**
 * string to node
 */
chatDom.prototype.str2node = function(){
	return  parseDom(this.fill());
}

chatPrivateDom.prototype.str2node = function(){
	return  parseDom(this.fill());
}