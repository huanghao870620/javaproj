

/**
 * string to dom
 * @param o
 * @returns
 */
var Str2Dom = function(o){
	this.content = o.content;
	this.basepath = o.basepath;
}


/**
 * fill data
 * @returns {String}
 */
Str2Dom.prototype.fill = function(){
	return this.content;
}


/**
 * string to node
 */
Str2Dom.prototype.str2node = function(){
	//return  parseDom(this.fill());
	 // return this.content.children;
	return dom2str(this.content);
}

Str2Dom.prototype.toLevel = function(){
	var result;
	var level = this.content.level;
	switch (level) {
	case 0:
		result = 'icon_youke';
		break;
	case 1:
		result = 'icon_putong';
		break;
	case 2:
		result = 'icon_huangjin';
		break;
	case 3:
		result = 'icon_bojin';
		break;
	case 4:
		result = 'icon_zuanshi';
		break;
	case 5:
		result = 'icon_zhizun';
		break;
	case 10:
		result = 'icon-assistant'
		break;
	default:
		result = 'icon_youke'
		break;
	}
	return result;
}

