<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Animation Tree - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/base.js"></script>
</head>
<body class="easyui-layout">
  
  <div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
   欢迎你:${user.account } <a href="<%=request.getContextPath()%>/user/logout.htm">注销</a>
  </div>
  
	<div data-options="region:'west',split:true,title:'菜单'" style="width:150px;padding:10px;">
	
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" style="padding:5px">
	  <ul class="easyui-tree" data-options="url:'<%=request.getContextPath() %>/menu/getMenu.htm',method:'get',animate:true"></ul>
	</div>
	
	
	
	</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>
	
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	
	<div data-options="region:'center',title:''">
	    <iframe id="content_iframe" src="" style="width:100%;height:100%">
	   </iframe> 
	</div>
</body>
</html>
 
 
 <!-- 
 <!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Full Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../../themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../demo.css">
	<script type="text/javascript" src="../../jquery.min.js"></script>
	<script type="text/javascript" src="../../jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">north region</div>
	<div data-options="region:'west',split:true,title:'West'" style="width:150px;padding:10px;">west content</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div data-options="region:'center',title:'Center'"></div>
</body>
</html> -->