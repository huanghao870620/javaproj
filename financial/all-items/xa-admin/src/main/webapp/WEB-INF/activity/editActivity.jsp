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
	
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="编辑品牌" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/brand/editBrand.htm">
			<div style="margin-bottom:20px">
			    <input type="hidden" value="${brand.id }"  name="id" />
				<input class="easyui-textbox" name="name" value="${brand.name}" style="width:60%" data-options="label:'品牌名称:',required:true">
			</div>
			
			
			 
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="info" value="${brand.info }" style="width:100%;height:60px" data-options="label:'品牌描述:',multiline:true">
			</div>
			
				<div style="margin-bottom:20px">
				<select id="class1"   class="easyui-combobox" name="uploadTypeId"   label="上传者类型:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${types}" var="t">
						<option value="${t.id}">${t.name }</option>
					</c:forEach>
				</select>
			</div>
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="品牌缩略图" style="width:60%;height:auto;padding:10px;">
				      <div>
				      <input type="hidden" name="imgId" value="${file.id }" />
				     	<img id="imgPre" src="<%=request.getContextPath() %>/${file.uriPath}" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="imgFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre'));
				}, label:'品牌缩略图:',required:true" >
			</div>
			
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="品牌详情图" style="width:60%;height:auto;padding:10px;">
				      <div>
				        <input type="hidden" value="${detailPicFile.id }" name="detailPic">
				     	<img id="imgPre2" src="<%=request.getContextPath() %>/${detailPicFile.uriPath}" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="detailPicFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre2'));
				}, label:'品牌详情图:',required:true" >
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