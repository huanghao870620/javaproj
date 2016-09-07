/**
 * string to dom
 * @param o
 * @returns
 */
var str2Dom = function(o){
	this.content = o.content;
	this.basepath = o.basepath;
}


/**
 * 填充未审核
 */
str2Dom.prototype.fill = function(){
  var str = " \
	<ul>   \
    <li class=\"name\"> " + this.content.account + " </li>  \
    <li class=\"time\"> " + this.content.inputTime + " </li>  \
    <li class=\"state\"><button type=\"button\" onclick=\"auditMsg('" + this.content.id + "', this.parentNode.parentNode)\" value=\"\">公开</button></li> \
    <li class=\"text\"> " + this.content.msg + " </li> \
</ul> \
 ";
   return str;
}



/**
 * 
 */
str2Dom.prototype.str2node = function(){
	return parseDom(this.fill());
}
