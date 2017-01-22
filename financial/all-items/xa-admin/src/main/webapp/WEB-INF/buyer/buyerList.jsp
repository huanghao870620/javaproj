<%@page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom DataGrid Pager - jQuery EasyUI Demo</title>
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			var pager = $('#dg').datagrid({
				onDblClickRow: function(rowIndex,rowData){
					 window.location.href = "<%=request.getContextPath()%>/country/toEditCountry.htm?id=" + rowData.id;
				}
			}).datagrid('getPager');	// get the pager of datagrid
			pager.pagination({
				buttons:[{
					iconCls:'icon-search',
					handler:function(){
						alert('search');
					}
				},{
					iconCls:'icon-add',
					handler:function(){
						alert('add');
					}
				},{
					iconCls:'icon-edit',
					handler:function(){
						alert('edit');
					}
				}]
			});			
		})
	</script>
	
</head>
<body>
	<h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<table id="dg" title="买家列表" style="width:100%;height:auto"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'getBuyers.htm',method:'get'">
		<thead>
			<tr>
				<th data-options="field:'id',width:200">ID</th>
				<th data-options="field:'mobile',width:200">手机号</th>
				<th data-options="field:'nickname',width:400">昵称</th>
				<th data-options="field:'gender',width:200">性别</th>
				<th data-options="field:'birthday',width:200">生日</th>
				<th data-options="field:'signature',width:200">签名</th>
			</tr>
		</thead>
	</table>
	
</body>
</html>