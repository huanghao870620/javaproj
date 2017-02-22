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
		
		var start=$("#limitStart").html(); 
		var end=$("#limitEnd").html();
		
		var s_t = new Date(start);
		var e_t = new Date(end);
		
		var v = $("#cronDate").datetimebox("getValue");
		if(v==""){
			$.messager.alert('警告','抢购时间不能为空!');
			return;
		}
		
		var t = new Date(v);
		if(t.getTime() < s_t.getTime()){
		   $.messager.alert('警告','抢购时间不能早于专场开始时间!');
		   $('#aa0').accordion('select',"商品"+sp[1]);
		   return;
		}
		
		if(t.getTime()>e_t.getTime()){
			$.messager.alert('警告','抢购时间不能晚于专场结束时间!');
			$('#aa0').accordion('select',"商品"+sp[1]);
			return;
		}
		
		
		$('#ff').submit();
	}
	function clearForm(){
		$('#ff').form('clear');
	}
	
	$(function(){
		$('#w').window('close');
		$('#dg_goodBigPic').datagrid();
		
		var pager = $('#dg').datagrid({
			onDblClickRow: function(rowIndex,rowData){
				 var tab_id= $('#idx_id').val();
				 $('#goodname'+tab_id).html(rowData.name);
				 $('#goodId'+tab_id).val(rowData.id);
				 $('#w').window('close');
				// window.location.href = "<%=request.getContextPath()%>/goods/toEditGood.htm?id=" + rowData.id;
			},
		//	 pageSize:50,
		//	 pageList: [50,15,20,100],
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
	});
	
	

	function selectPanel(){
		$.messager.prompt('Prompt','Please enter the panel title:',function(s){
			if (s){
				$('#aa0').accordion('select',s);
			}
		});
	}
	
	var idx = 1;
	function addPanel(){
		/*
		var start=$("#limitStart").datetimebox("getValue"); 
		var end=$("#limitEnd").datetimebox("getValue");
	    console.log(start);
	    console.log(end);
	    if(start==""){
	    	$.messager.alert('警告','请选择专场开始时间!');
	    	return;
	    }
	    
	    if(end==""){
	    	$.messager.alert('警告','请选择专场结束时间!');
	    	return;
	    }
		*/
		$('#aa0').accordion('add',{
			title:'商品'+idx,
			content:'<div id="id'+idx+'" style="padding:10px"> \
			 <div id="p" class="easyui-panel" title="商品" style="width:60%;height:auto;padding:10px;"> \
			 <div style="margin:20px 0;">\
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openGoods('+idx+')">选择商品</a>\
			</div>\
			 <div style="margin:20px 0;">\
				商品名称:<span id="goodname'+idx+'"></span>\
				 <input type="hidden" name="goodId" id="goodId'+idx+'" value=""/>\
			</div>\
		    </div> \
			</div>'
		});
		idx++;
	}
	
//	var start=$("#limitStart").datetimebox("getValue"); \
	//	var end=$("#limitEnd").datetimebox("getValue");\
	//	console.log(start);\
	//	console.log(end);\
	
	function removePanel(){
		var pp = $('#aa0').accordion('getSelected');
		if (pp){
			var index = $('#aa0').accordion('getPanelIndex',pp);
			$('#aa0').accordion('remove',index);
		}
	}
	
	
	function openGoods(o){
		$('#idx_id').val(o);
		$('#w').window('open');
		$("#w").panel("move",{top:$(document).scrollTop() + ($(window).height()-250) * 0.5});
		$('.window-shadow').css('display','none');
	}
	
	
	function doSearch(){
		/*
		$('#dg').datagrid({
			url: "abcd",
			queryParams:{a:22}
		}); */
		
		var params = {
				brandS: $('#brandId').val(),
				countryS: $('#countryId').val(),
				nameS: $('#nameS').val()
		};
		
		$('#dg').datagrid('reload',params);
	}
	
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="添加抢购时间" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
		<div style="margin-bottom:20px">
				专场开始时间:<span id="limitStart">${startTimeStr }</span>
		</div>
		
		<div style="margin-bottom:20px">
				专场结束时间:<span id="limitEnd">${endTimeStr }</span>
		</div>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/debrisSession/addDebrisSession.htm">
			<div style="margin-bottom:20px">
			   <input type="hidden" name="id" value="${dSession.id }"/>
				<input type="hidden" name="fbsId" value="${fbsId }" />
				<input class="easyui-datetimebox" value="${cronTimeStr }" name="cronTime" id="cronDate" data-options="required:true,showSeconds:true,onSelect:function(date){
			}" label="抢购时间:" labelPosition="top" style="width:30%;"> 
			</div>
			
			
			<p>添加抢购商品:</p>
	<div style="margin:20px 0 10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectPanel()">选择商品</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addPanel()">添加商品</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="removePanel()">删除商品</a>
	</div>
	<div id="aa0" class="easyui-accordion" style="width:500px;height:300px;">
	</div>
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清除</a>
		</div>
		
		
		<div id="w" class="easyui-window" title="商品列表" data-options="iconCls:'icon-save'" style="width:500px;height:auto;padding:10px;">
		<div style="margin:20px 0;"></div>
		 <input type="hidden" id="idx_id" value="" />
	<table id="dg" title="商品列表" style="width:auto;height:auto"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'<%=request.getContextPath() %>/goods/getGoodsByDeSession.htm',method:'get'">
		<thead>
			<tr>
			<th data-options="field:'id',width:50">ID</th>
				<th data-options="field:'name',width:300">商品名称</th>
				<th data-options="field:'shelves',width:30,align:'right'">上架</th>
				<%--
				<th data-options="field:'info',width:400">商品描述</th>
				<th data-options="field:'price',width:200,align:'right'">商品价格</th>
				<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
				<th data-options="field:'attr1',width:240">Attribute</th>
				<th data-options="field:'status',width:60,align:'center'">Status</th>
				 --%>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:2px 5px;">
		开始日期: <input class="easyui-datebox" style="width:110px">
		结束日期: <input class="easyui-datebox" style="width:110px">
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
	
		</div>
	</div>
		
		
		
		
		
		
		
		
		 
	</div>
	
	
</body>
</html>