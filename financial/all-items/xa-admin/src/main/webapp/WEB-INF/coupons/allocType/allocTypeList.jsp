<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

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
				 window.location.href = "<%=request.getContextPath()%>/allocType/toEditAllocType.htm?id=" + rowData.id;
			},
			 toolbar:[
			         
            {text:'删除', iconCls:'icon-remove', handler:function(){
           	 var row = $('#dg').datagrid('getSelected');
           	  $.post("<%=request.getContextPath()%>/allocType/delAllocType.htm",{id:row.id},function(data){
           		    if(data.success){
           		    	$('#dg').datagrid('reload');
           		    }
           	  });
           	/* if (row) {
                    var rowIndex = $('#dg').datagrid('getRowIndex', row);
                    $('#dg').datagrid('deleteRow', rowIndex);  
           		 }*/	
           	 //$('#dg').datagrid('deleteRow', x); 
            }}
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
            ]
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
	<table id="dg" title="分配类型列表" style="width:100%;height:auto"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'listAllocType.htm',method:'get'">
		<thead>
			<tr>
			<th data-options="field:'id',width:50">ID</th>
				<th data-options="field:'name',width:200">名称</th>
				 <th data-options="field:'info',width:400">描述</th> 
			<!-- 	<th data-options="field:'uploadType',width:200,align:'right'">上传类型</th> -->
			</tr>
		</thead>
	</table>
</body>
</html>