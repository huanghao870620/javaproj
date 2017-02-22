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
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	function submitForm(){
		
		
		$('#ff').submit();
	}
	function clearForm(){
		$('#ff').form('clear');
	}
	
	$(function(){
		$('#dg_goodBigPic').datagrid();
	});
	
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="编辑抢购商品" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/dsGood/editDsGood.htm">
			
			<div style="margin-bottom:20px">
				<input type="hidden" name="id" value="${dsGood.id }"/>
				<input type="hidden" name="dsId" value="${dsGood.dsId }"/>
				<input type="hidden" name="fbsId" value="${fbsId }" />
				<input class="easyui-numberspinner" name="inventory"  value="${good.limitCount }" data-options="label:'库存:',labelPosition:'top',spinAlign:'right'" style="width:40%;">
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