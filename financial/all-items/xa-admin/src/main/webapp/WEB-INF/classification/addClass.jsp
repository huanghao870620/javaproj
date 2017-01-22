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
	
	<div id="p" class="easyui-panel" title="添加商品" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/goods/addGood.htm">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="name" style="width:60%" data-options="label:'商品名称:',required:true">
				<%--
				<input class="easyui-textbox" name="capacity" style="width:40%" data-options="label:'商品容量:',required:true,validType:'capacity'">
				 --%>
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="price" style="width:40%" data-options="label:'商品价格:',required:true">元
			</div>
			<div style="margin-bottom:20px">
				<select class="easyui-combobox" name="state"   label="品牌:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${brands}" var="b">
						<option value="${b.id }">${b.name }</option>
					</c:forEach>
				</select>
			</div>
			
			<div style="margin-bottom:20px">
				<select id="class1"   class="easyui-combobox" name="state"   label="一级分类:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${classifis4first}" var="cf4">
						<option value="${cf4.id}">${cf4.name }</option>
					</c:forEach>
				</select>
				
			</div>
			
			<div style="margin-bottom:20px">
				<select id="class2" class="easyui-combobox" name="state"   label="二级分类:"  labelPosition="top" style="width:40%;">
				</select>
				
			</div>
			 
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="info" style="width:100%;height:60px" data-options="label:'商品描述:',multiline:true">
			</div>
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="缩略图" style="width:60%;height:auto;padding:10px;">
				      <div>
				     	<img id="imgPre" src="" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="smallFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre'));
				}, label:'缩略图:',required:true" >
			</div>
			
			
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="商品详情图" style="width:60%;height:auto;padding:10px;">
				      <div>
				     	<img id="imgPre2" src="" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="bigFile" id="aa2" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre2'));
				}, label:'商品详情图:',required:true" >
			</div>
			
			
			
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清除</a>
		</div>
	</div>
		
		
	</div>
	
	
	
	
	
	
	<script>
		function submitForm(){
			$('#ff').form('submit');
		}
		function clearForm(){
			$('#ff').form('clear');
		}
	</script>
</body>
</html>