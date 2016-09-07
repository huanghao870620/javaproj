

// 客户
var Customer = function(o){
	this.url = o.url;
	this.form = o.form;
}


Customer.prototype.add = function(){
	this.form.submit();
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
	for(var i=0; i < rows.length; i++){
		copyRows.push(rows[i]);
	}
	
	for(var i=0; i<copyRows.length; i++){
		var index = this.node.datagrid('getRowIndex', copyRows[i]);
		var id = rows[i][this.primaryKey];
		this.deleteThis(id,index);
	}
	this.node.datagrid('reload');
}


byPaging.prototype.deleteThis = function(id,index){
	  var pag = this;
	 $.post(this.deleteUrl, {id : id}, function(data){
		 pag.node.datagrid('deleteRow',index); 
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
	    queryParams: {
	  	  index: '1',
		  size:pageSize
	    },
	    
	    url: this.url,
	    columns:[  
	        this.columns  
	    ]  
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
	    		        	console.log(data);
	    		        	data = JSON.parse(data);
	    		        	node.datagrid('loadData',data);
	    		        } 
	    		    }
	    	  });
		  	gd.toAjax();
		  	 
		    }  
	});  
}


var getData = function(o){
	 this.page = o.page;
	 this.rows = o.rows;
	 this.node = o.node;
	 this.url = o.url;
	 this.ajaxObj = o.ajaxObj;
}


	getData.prototype.toAjax = function(){ 
		    $.ajax(this.ajaxObj); 
	};

	getData.prototype.start = function(){
		
	}
	
	
	