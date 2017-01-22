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
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	function submitForm(){
		//$('#ff').form('submit');
		$('#ff').submit();
	}
	function clearForm(){
		$('#ff').form('clear');
	}
	
	
	 $(function(){
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
	 
	 
	 function getChecked(){
			var nodes = $('#tt').tree('getChecked');
			var s = '';
			for(var i=0; i<nodes.length; i++){
				if (s != '') s += ',';
				s += nodes[i].text;
			}
			alert(s);
		}
	 
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="编辑商品大图" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/goods/editGood.htm">
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="商品大图" style="width:100%;height:auto;padding:10px;">
				      <div>
				         <input type="hidden" value="${file.id }" name="thumbFileId" />
				     	<img id="imgPre" src="<%=request.getContextPath() %>/${file.uriPath}" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
			   <input type="hidden" id="thumbPic" name="thumbFileId" value="${file.id }" />
				<input class="easyui-filebox" name="smallFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre'));
				}, label:'缩略图:',required:true" >
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