<%@page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				brandId: $('#brandS').combobox("getValue"),
				countryId: $('#countryS').combobox("getValue"),
				nameS: $('#nameS').val(),
				startTime: $("#startTime").datetimebox("getValue"),
				endTime: $('#endTime').datetimebox("getValue")
		};
		
		$('#dg').datagrid('reload',params);
	}
	
	
	$(function(){
		var pager = $('#dg').datagrid({
			onDblClickRow: function(rowIndex,rowData){
				 window.location.href = "<%=request.getContextPath()%>/goods/toEditGood.htm?id=" + rowData.id;
			},
			 //pageSize:50,
			 //pageList: [50,15,20,100],
		     toolbar: '#tb'
			// toolbar:[
			     /*     
             {text:'删除', iconCls:'icon-remove', handler:function(){
            	 var row = $('#dg').datagrid('getSelected');
            	  $.post("<%=request.getContextPath()%>/goods/delGood.htm",{id:row.id},function(data){
            		    if(data.success){
            		    	$('#dg').datagrid('reload');
            		    }
            	  });*/
            	/* if (row) {
                     var rowIndex = $('#dg').datagrid('getRowIndex', row);
                     $('#dg').datagrid('deleteRow', rowIndex);  
            		 }*/	
            	 //$('#dg').datagrid('deleteRow', x); 
           //  }}
			    /*  {  
                 text:'增加',iconCls:'icon-add',handler:function(){  
                     window.location.href='StuAdd.aspx';  
                 }  
             },  
             {text:'导入',iconCls:'icon-add',handler:function(){  
                 window.location.href='StuImport.aspx'  
                 }  
             },  
             {text:'查找',iconCls:'icon-search'}  */
            // ]
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
					console.log("2");
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
	<table id="dg" title="商品列表" style="width:100%;height:auto"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'getGoodsByPaging.htm',method:'get'">
		<thead>
			<tr>
			<th data-options="field:'id',width:200">ID</th>
				<th data-options="field:'name',width:200">商品名称</th>
				<th data-options="field:'info',width:400">商品描述</th>
				<th data-options="field:'price',width:200,align:'right',formatter:function(a,b){
				 return formatCurrency(a)+'元';
				}">商品价格</th>
				<th data-options="field:'shelves',width:200,align:'right'">上架</th>
				<%--
				<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
				<th data-options="field:'attr1',width:240">Attribute</th>
				<th data-options="field:'status',width:60,align:'center'">Status</th>
				 --%>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:2px 5px;">
		开始日期: <input id="startTime"  class="easyui-datetimebox" style="width:110px">
		结束日期: <input id="endTime" class="easyui-datetimebox" style="width:110px">
		品牌: 
		<select class="easyui-combobox" id="brandS"  style="width:100px">
					<option value="">==请选择==</option>
					<c:forEach items="${brands}" var="b">
						<option value="${b.id }">${b.name }</option>
					</c:forEach>
				</select>
		
		
		国家: 
		<select class="easyui-combobox" id="countryS"  style="width:100px">
					<option value="">==请选择==</option>
			<c:forEach items="${countrys}" var="c">
						<option value="${c.id }">${c.name }</option>
			</c:forEach>
		</select>
		
		
		
		<input class="easyui-searchbox" name="nameS" id="nameS" data-options="prompt:'输入部分商品名称',searcher:doSearch" style="width:15%">
		<a href="javascript:doSearch();" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	</div>
</body>
</html>