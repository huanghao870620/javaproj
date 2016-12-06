<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Form - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
</head>
<body>
	<h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="添加商品" style="width:100%;max-width:400px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/goods/addGood.htm">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="name" style="width:100%" data-options="label:'商品名称:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="capacity" style="width:100%" data-options="label:'商品容量:',required:true,validType:'capacity'">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="lowestPrice" style="width:100%" data-options="label:'商品最低价:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="highestPrice" style="width:100%" data-options="label:'商品最高价:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="info" style="width:100%;height:60px" data-options="label:'商品描述:',multiline:true">
			</div>
			
			<div style="margin-bottom:20px">
			 <h2></h2>
	<p></p>
	<div style="margin:20px 0 10px 0;">
	</div>
	<div id="p" class="easyui-panel" title="商品图片" style="width:100%;height:200px;padding:10px;">
	   <%--
		<p>The panel has a width of 100%.<p>
	    --%>
	</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="file" style="width:100%" data-options="label:'商品图片:',required:true">
			</div>
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清除</a>
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