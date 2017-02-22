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
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	
	function doSearch(){
		/*
		$('#dg').datagrid({
			url: "abcd",
			queryParams:{a:22}
		}); */
		
		var params = {
				stateS: $('#stateS').combobox("getValue"),
				receWayS: $('#receWayS').combobox("getValue"),
				nameS: $('#nameS').val(),
				startTime: $("#startTime").datetimebox("getValue"),
				endTime: $('#endTime').datetimebox("getValue")
		};
		
		$('#dg').datagrid('reload',params);
	}
	
	
		$(function(){
			var pager = $('#dg').datagrid({
				onDblClickRow: function(rowIndex,rowData){
					 window.location.href = "<%=request.getContextPath()%>/order/toShowOrderGoods.htm?id=" + rowData.id;
				},
				toolbar: '#tb'
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
	<table id="dg" title="订单列表" style="width:100%;height:auto"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'getOrders.htm',method:'get'">
		<thead>
			<tr>
				<th data-options="field:'id',width:40">ID</th>
				<th data-options="field:'orderNo',width:200">订单号</th>
				<th data-options="field:'mobile',width:100">手机号</th>
				<th data-options="field:'sumPrice',width:200,formatter:function(a,b){
				   return formatCurrency(a)+'元';
				}">订单价格</th>
				<th data-options="field:'state',width:100">订单状态</th>
				<th data-options="field:'receWay',width:100">收货方式</th>
				<th data-options="field:'address',width:200">收货地址</th>
				<th data-options="field:'generateTime',width:200">下单时间</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="padding:2px 5px;">
		开始日期: <input id="startTime" class="easyui-datetimebox" style="width:110px" data-options="showSeconds:true"/>
		结束日期: <input id="endTime" class="easyui-datetimebox" style="width:110px" data-options="showSeconds:true"/>
		订单状态: 
		<select class="easyui-combobox" id="stateS"  style="width:100px">
						<option value="">==请选择==</option>
						<option value="1">待付款</option>
						<option value="2">待接单</option>
						<option value="3">待采购</option>
						<option value="4">待发货</option>
						<option value="5">待收货</option>
						<option value="6">完成订单</option>
						<option value="7">已取消</option>
				</select>
		
		收货方式: 
		<select class="easyui-combobox" id="receWayS"  style="width:100px">
						<option value="">==请选择==</option>
						<option value="1">自提</option>
						<option value="2">地址</option>
		</select>
		
		
		
		<input class="easyui-searchbox" name="nameS" id="nameS" data-options="prompt:'输入部分订单号或手机号',searcher:doSearch" style="width:15%">
		<a href="javascript:doSearch();" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	</div>
</body>
</html>