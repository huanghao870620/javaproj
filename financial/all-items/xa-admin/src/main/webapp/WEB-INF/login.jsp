<%@page language="java" contentType="text/html;charset=utf-8"  %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>后台管理</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	  $(function(){
		  console.log(1);
	  });
	  
	  
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

<div style="margin-left: 584px;margin-top: 200px;">
	<h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="用户登录" style="width:100%;max-width:400px;padding:30px 60px;">
		<form id="ff" method="post" action="<%=request.getContextPath()%>/user/login.htm">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="account" style="width:100%" data-options="label:'帐号:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-passwordbox" name="password" style="width:100%" data-options="label:'密码:',required:true,validType:'password'">
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