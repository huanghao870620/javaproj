
function extend(des, src, override){
   if(src instanceof Array){
       for(var i = 0, len = src.length; i < len; i++)
            extend(des, src[i], override);
   }
   for( var i in src){
       if(override || !(i in des)){
           des[i] = src[i];
       }
   } 
   return des;
}


/*
 * 合并json
 */
function merge_json(a,b){
	return extend({}, [a,b]).a;
}


/**
 * map数据结构
 */
var map = function(){
	this.arr = [];
};

map.prototype.put = function(key,value){
	 if(null == this.get(key)){
		 this.arr.push(new KV(key,value));
	 }else{
		 this.get(key).setVal(value);
	 }
};

map.prototype.get = function(key){
	 for(var i=0; i<this.arr.length; i++){
		   if(this.arr[i].getKey() == key){
			    return this.arr[i].getVal();
		   }
	 }
	 return null;
};

map.prototype.size = function(){
	return this.arr.length; 
};


/**
 * 键值对数据结构
 */
var KV = function(key,val){
	this.key = key;
	this.val = val;
};

KV.prototype.newIns = function(key,val){
	 this.key = key;
	 this.val = val;
};

KV.prototype.getKey = function(){
	 return this.key;
};

KV.prototype.getVal = function(){
	 return this.val;
};

KV.prototype.setVal = function(val){
	this.val = val;
};


/**
 * string 转 dom
 */
var parseDom = function (arg) {
    var objE = document.createElement("div");
	objE.innerHTML = arg;
	return objE.childNodes;
	}

var dom2str = function(arg){
	 var div = document.createElement("div");
	  $(div).append(arg);
	  return $(div).html();
}