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
		if($('#brand').val().trim() == ""){
			$.messager.alert("","请选择品牌!");
			return;
		}
		
		if($('#country').val().trim() == ""){
			$.messager.alert("","请选择国家!");
			return;
		}
		
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
	
	<div id="p" class="easyui-panel" title="编辑商品" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/goods/editGood.htm">
			<div style="margin-bottom:20px">
			  <input type="hidden" name="id" value="${good.id }"/>
				<input class="easyui-textbox" name="name" value="${good.name }" style="width:60%" data-options="label:'商品名称:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="price" value="${good.price }" style="width:40%" data-options="label:'商品价格:',required:true">元
			</div>
			
			
			<div style="margin-bottom:20px">
			    <span style="margin-right:25px;">是否上架:</span> 
			    <input type="hidden" name="shelves" id="shelves" value="${good.shelves }">
				<input id="sb" class="easyui-switchbutton" checked  onText="上架" offText="下架" style="width:100px;height:30px" >
			</div>
			
			<div style="margin-bottom:20px">
				<select id="brand"  class="easyui-combobox" name="state"   label="品牌:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${brands}" var="b">
						<option value="${b.id }">${b.name }</option>
					</c:forEach>
				</select>
			</div>
			
			<div style="margin-bottom:20px">
				<select class="easyui-combobox" name="countryId"  id="country"  label="国家:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${countrys}" var="c">
						<option value="${c.id }">${c.name }</option>
					</c:forEach>
				</select>
			</div>
			
			<input type="hidden" id="classid" name="classid" value="${good.classid }"/>
			
			<div style="margin-bottom:20px">
				分类:
				<div class="easyui-panel" style="padding:5px">
					<ul id="tt" class="easyui-tree" ></ul>
				</div>
			</div>
			
			 
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="info" value="${good.info }" style="width:100%;height:60px" data-options="label:'商品描述:',multiline:true">
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-numberspinner" name="limitCount"  value="${good.limitCount }" data-options="label:'限购次数:',labelPosition:'top',spinAlign:'right'" style="width:40%;">
			</div>
			
			<div style="margin-bottom:20px">
			<input class="easyui-numberspinner" name="inventory"  value="${good.inventory }" data-options="label:'库存:',required:true,labelPosition:'top',spinAlign:'right'" style="width:40%;">
			</div>
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="缩略图" style="width:60%;height:auto;padding:10px;">
				      <div>
				         <input type="hidden" value="${file.id }" name="thumbFileId" />
				     	<img id="imgPre" src="<%=request.getContextPath() %>/${file.uriPath}" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
			   <input type="hidden" id="thumbPic" name="thumbFileId" value="${file.id }" />
				<input class="easyui-filebox" name="smallFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre'));
				}, label:'缩略图:',required:true" >
			</div>
			
			
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="商品详情图" style="width:60%;height:auto;padding:10px;">
				      <div>
				        <input type="hidden" value="${advPicFile.id }" name="advPic"/>
				     	<img id="imgPre2" src="<%=request.getContextPath() %>/${advPicFile.uriPath }" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
			   <input type="hidden" name="advPic" value="${advPicFile.id }" />
				<input class="easyui-filebox" name="bigFile" id="aa2" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre2'));
				}, label:'商品详情图:',required:true" >
			</div>
			
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清除</a>
		</div>
	</div>
	
		<div style="margin:20px 0;"></div>
		<table id="dg_goodBigPic" title="商品大图列表" style="width:100%;height:auto"
				data-options="rownumbers:true,singleSelect:true,pagination:true,url:'getBigPic4Good.htm?goodId=${good.id}',method:'get'">
			<thead>
				<tr>
				<th data-options="field:'gfId',width:200">GF_ID</th>
					<th data-options="field:'uriPath',width:1000,formatter:function(value,rec){
					  return '<img src=<%=request.getContextPath() %>/'+value+'></img>';
					}"></th>
				
				</tr>
			</thead>
		</table>
	
	</div>
	
	<div id="editBigPicDlg" class="easyui-dialog" title="添加商品大图" data-options="iconCls:'icon-save'" style="width:400px;height:auto;padding:10px">
		
<form id="addBigPicForm" method="post" enctype="multipart/form-data" action="<%=request.getContextPath() %>/goods/addBigPic4Good.htm">
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p4" class="easyui-panel" title="" style="width:100%;height:auto;padding:10px;">
				      <div>
				         <input type="hidden" value="" name="bigPicFileId" />
				         <input type="hidden" value="${good.id}" name="goodId"> 
				     	<img id="imgBigPic" src="" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="bigPicFile" id="aa4" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgBigPic'));
				}, label:'选择文件:',required:true" >
			</div>
			
			 
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAddPicForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearAddPicForm()" style="width:80px">清除</a>
		</div>
	</div>
	 
	
</body>
</html>