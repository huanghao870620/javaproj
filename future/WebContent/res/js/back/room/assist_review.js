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
   return this.content;
}



/**
 * 
 */
str2Dom.prototype.str2node = function(){
	return parseDom(this.fill());
}
