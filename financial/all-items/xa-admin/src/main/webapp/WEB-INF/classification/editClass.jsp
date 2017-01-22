<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Form - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	
	function submitForm(){
		$('#ff').submit();
	//	$('#ff').form('submit');
	}
	function clearForm(){
		$('#ff').form('clear');
	}
	
	
	function fileup(_obj,img){
		var _imgsrc=$(_obj).filebox("getValue");
		var _file=$(_obj).context.ownerDocument.activeElement.files[0];
		//$('#imgPre').attr("src",getObjectURL(_file));
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
	
	
	
	 $(function(){
		  
		 $('#class1').combobox({
	         //url:"/classification/getChildByClassId.htm",
	         editable:false,
	        //panelWidth:130,
	        // width:130,
	         onSelect:function(record){
	        	 
	        	 
	                 var pid = $('#class1').combobox('getValue');
	       	  /*
	                 
	                 if(peo=="1"){						
	                         $('#select2id').combobox({
	                                 disabled:true
	                         });
	                 }
	                 else{
	                         $('#select2id').combobox({
	                                 disabled:false
	                         });
	                 } */
	                 
	                 
	                 $.post("<%=request.getContextPath()%>/classification/getChildByClassId.htm",
	                		 {pid:record.value},
	                		 function(data){
	                	  $('#class2').html(data);
	                	   $('#class2').combobox({
                               disabled:false
                       });
	                 });
	                 
	                 
	         },
	         onChange: function (n,o) {

	        	 //alert("我是老大!");
 				console.log("22");
	        	 }
	 });
		 
	 });
	 
	 
	
	 
	 
	 /** 
	 * 从 file 域获取 本地图片 url 
	 */ 
	 function getFileUrl(sourceId) { 
		 var url; 
		 if (navigator.userAgent.indexOf("MSIE")>=1) { // IE 
		 url = document.getElementById(sourceId).value; 
		 } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox 
		 url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
		 } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome 
		 url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
		 } 
		 return url; 
	 } 
	 
	 
	</script>
	
</head>
<body>
	<div id="p" class="easyui-panel" title="编辑分类" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
		<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/classification/editClass.htm">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="name" value="${classifi.name }" style="width:60%" data-options="label:'分类名称:',required:true">
			</div>
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="分类图片" style="width:60%;height:auto;padding:10px;">
				      <div>
				     	<img id="imgPre" src="<%=request.getContextPath() %>/${file.uriPath}" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<input type="hidden" name="imgId" id="imgId"  value="${file.id }"> 
			<input type="hidden" name="id"  value="${classifi.id}" />
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="imgFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre'));
				}, label:'分类图片:',required:true" >
			</div>
			
			
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清除</a>
		</div>
		
	</div>
	
	</div>
	
	
</body>
</html>