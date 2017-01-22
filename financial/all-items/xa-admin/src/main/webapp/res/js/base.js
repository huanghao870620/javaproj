
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



var byPaging = function(o){
   this.node = o.node;
   this.url = o.url;
   this.deleteUrl = o.deleteUrl;
   this.title = o.title;
   this.columns = o.columns;
   this.primaryKey = o.primaryKey;
   this.deleteKey = o.deleteKey;
   this.showPageList = o.showPageList;
   this.pageSize = o.pageSize;
}


byPaging.prototype.init = function(){
	var rows = this.node.datagrid("getSelections");
	var copyRows = [];
	var idList = "";
	for(var i=0; i < rows.length; i++){
		copyRows.push(rows[i]);
	}
	
	for(var i=0; i<copyRows.length; i++){
		var index = this.node.datagrid('getRowIndex', copyRows[i]);
		var id = rows[i][this.primaryKey];
		idList += id+",";
	}
	this.deleteThis(idList,index);
//	this.node.datagrid('reload');
}


byPaging.prototype.deleteThis = function(idList,index){
	  var pag = this;
	 $.post(this.deleteUrl, {id : idList}, function(data){
		 pag.node.datagrid('reload');
	 });
}


byPaging.prototype.start = function(){
	var pageSize = this.pageSize;
	if(!pageSize){
		pageSize =10;
	}
	this.node.datagrid({  
	    title: this.title,
	    rownumbers:true,  
	    fitColumns:true,  
	    pagination:true,
	    scrollbarSize:0,
	    loadMsg:'数据加载中...',
	    queryParams: {
	  	  index: '1',
		  size:pageSize
	    },
	    
	    url: this.url,
	    columns:[  
	        this.columns  
	    ],
	    onLoadSuccess:function(data){
	    }
	});  
	var pager = this.node.datagrid("getPager");  
	var opts = this.node.datagrid('options');
	
	var url = this.url;
	var node = this.node;
	var showPageList = this.showPageList;
	
	
	pager.pagination({  
	    total:113,  
	    pageSize:pageSize,
	    showPageList:showPageList,
	    beforePageText: '第',
	    afterPageText: '页    共 {pages} 页', 
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    onSelectPage: function (pageNo, pageSize) {
		  	var gd = new getData({
	    		  page : pageNo,
	    		  rows : pageSize,
	    		  node : node,
	    		  url : url,
	    		  ajaxObj : {             
	    		    	type: "POST",   
	    		    	url: url,    
	    		    	data: "page=" + pageNo + "&rows=" + pageSize,     
	    		    	error: function (XMLHttpRequest, textStatus, errorThrown) {}, 
	    		        success: function (data) {
    		        		node.datagrid('loadData',data);
	    		        } 
	    		    }
	    	  });
		  	gd.toAjax();
		  	 
		    }  
	});  
}

  function iframesrc(o,url){
	   o.attr('src',url);
  }

  
  /*预览图片*/
  function fileup(_obj,img){
		var _imgsrc=$(_obj).filebox("getValue");
		var _file=$(_obj).context.ownerDocument.activeElement.files[0];
		img.attr("src",getObjectURL(_file));
	}

  function getObjectURL(file){
		 var url = null;
		 if(window.createObjectURL != undefined){
			 url = window.createObjectURL(file);
		 }else if(window.URL != undefined){
			 url = window.URL.createObjectURL(file);
		 }else if(window.webkitURL != undefined){
			 url = window.webkitURL.createObjectURL(file);
		 }
		 return  url;
	}
  
  $.fn.serializeObject = function(){    
     var o = {};    
     var a = this.serializeArray();    
     $.each(a, function() {    
         if (o[this.name]) {    
             if (!o[this.name].push) {    
                 o[this.name] = [o[this.name]];    
             }    
             o[this.name].push(this.value || '');    
         } else {    
             o[this.name] = this.value || '';    
         }    
     });    
     return o;    
  };  