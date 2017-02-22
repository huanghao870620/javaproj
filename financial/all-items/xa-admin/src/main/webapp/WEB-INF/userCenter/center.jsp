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
		
		
		$('#ff').submit();
	}
	
	function clearForm(){
		$('#ff').form('clear');
	}
	
	function submitAddPicForm(){
		$('#addBigPicForm').submit();
		//$('#addBigPicForm').form('submit');
		//$('#dg_goodBigPic').datagrid('reload');
	}
	
	var shelves='${good.shelves}';
	 $(function(){
		 $('#brand').combobox('select','${good.brandId}');
		 $('#country').combobox('select','${good.countryId}');
		 $('#editBigPicDlg').dialog('close');
		 
		 if(shelves=='1'){
			 $('#sb').switchbutton({checked:false});
		 }else if(shelves=='0'){
			 $('#sb').switchbutton({checked:true});
		 }
		 

		  $('#sb').switchbutton({  
   //    checked: false,  
       onChange: function(checked){  
       	// alert(checked);
       	
           if (checked == true){  
             //  document.getElementById('authenTypeL').innerHTML = '已启用!';
              $('#shelves').val("0");
               return;  
           }  
           if (checked == false){  
               //document.getElementById('authenTypeL').innerHTML = '未启用!';  
               $('#shelves').val("1");
           } 
           
       }  
       })  
		 
		 
	
	$('#tt').tree({
		url:'<%=request.getContextPath() %>/classification/getClassifi.htm',
		checkbox:true,
		method:'get',
		cascadeCheck: false,
		animate:true,
		onBeforeCheck:function(node, checked){
			 $('#classid').val(node.id);
		},
		
		 onSelect: function (node) {
				var cknodes = $('#tt').tree("getChecked");
		        for (var i = 0; i < cknodes.length; i++) {
		          if (cknodes[i].id != node.id) {
		            $('#tt').tree("uncheck", cknodes[i].target);
		          }
		        }
		        if (node.checked) {
		          $('#tt').tree('uncheck', node.target);
		 
		        } else {
		          $('#tt').tree('check', node.target);
		 
		        }
			 
         },
		onLoadSuccess : function(node, data) {
			var node2= $('#tt').tree('find','${good.classid}');
	           $('#tt').tree('check',node2.target);
			
			
			 $(this).find('span.tree-checkbox').unbind().click(function () {
		          $('#tt').tree('select', $(this).parent());
		          return false;
		        });
			
		}
	});
		  
		 $('#class1').combobox({
	         editable:false,
	         onSelect:function(record){
	                 var pid = $('#class1').combobox('getValue');
	                 $.post("<%=request.getContextPath()%>/classification/getChildByClassId.htm",
	                		 {pid:record.value},
	                		 function(data){
	                	  $('#class2').html(data);
	                	   $('#class2').combobox({
                               disabled:false
                       });
	                 });
	         },
	         onChange: function (n,o) {
	         }
	 });
		 
			var pager = $('#dg_goodBigPic').datagrid({
				onDblClickRow: function(rowIndex,rowData){
				},
				 toolbar:[{ text:'增加',iconCls:'icon-add',handler:function(){  
					 $('#editBigPicDlg').dialog('open');  
					 $("#editBigPicDlg").panel("move",{top:$(document).scrollTop() + ($(window).height()-250) * 0.5});
                 }  
				},
                 {text:'删除', iconCls:'icon-remove', handler:function(){
                	 var row = $('#dg_goodBigPic').datagrid('getSelected');
                	  $.post("<%=request.getContextPath()%>/goods/delGoodPic.htm",{gfId:row.gfId},function(data){
                		    if(data.success){
                		    	$('#dg_goodBigPic').datagrid('reload');
                		    }
                	  });
                	 /*
                	 if (row) {
                         var rowIndex = $('#dg').datagrid('getRowIndex', row);
                         $('#dg').datagrid('deleteRow', rowIndex);  
                		 }	*/
                	 //$('#dg').datagrid('deleteRow', x); 
                 }
                 }
				 ]
			}).datagrid('getPager');	// get the pager of datagrid
		 
		 
	 });
	 
	 
	
	 
	 
	 /** 
	 * 从 file 域获取 本地图片 url 
	 */ 
	 function getFileUrl(sourceId) { 
		 var url; 
		 if (navigator.userAgent.indexOf("MSIE")>=1) { // IE 
		 url = document.getElementById(sourceId).value; 
		 } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox 
		 url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
		 } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome 
		 url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
		 } 
		 return url; 
	 } 
	 
	 
	 function getChecked(){
			var nodes = $('#tt').tree('getChecked');
			var s = '';
			for(var i=0; i<nodes.length; i++){
				if (s != '') s += ',';
				s += nodes[i].text;
			}
			alert(s);
		}
	 
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="用户中心" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/user/editUser.htm">
			<div style="margin-bottom:20px">
			  <input type="hidden" name="id" value="${user.id }" />
			  <input class="easyui-textbox" name="account" style="width:60%" value="${user.account }" data-options="label:'账户:',required:true">
			</div>
			
			<div style="margin-bottom:20px">
			  <input class="easyui-textbox" name="email" style="width:60%" value="${user.email }" data-options="label:'邮箱:',required:true">
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