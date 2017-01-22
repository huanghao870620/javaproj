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
			//$('#ff').form('submit');
			if($('#classid').val().trim() == ""){
				 $.messager.alert('','分类是必选项!');
				 return ;
			}
			
			
			$('#ff').submit();
		}
		function clearForm(){
			$('#ff').form('clear');
		}
		
	 $(function(){
		 //var thisValue = thisSwitchbuttonObj.switchbutton("options").checked;  
		 
		 
		  $('#sb').switchbutton({  
        checked: false,  
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
			          // $('#tt').tree('check',node2.target);
					
					
					 $(this).find('span.tree-checkbox').unbind().click(function () {
				          $('#tt').tree('select', $(this).parent());
				          return false;
				        });
					
				}
			});
		 
			/*
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
	*/
		 
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
	 
	 
		function selectPanel(){
			$.messager.prompt('Prompt','Please enter the panel title:',function(s){
				if (s){
					$('#aa0').accordion('select',s);
				}
			});
		}
		var idx = 1;
		function addPanel(){
			$('#aa0').accordion('add',{
				title:'商品大图'+idx,
				content:'<div id="id'+idx+'" style="padding:10px"> \
								<div style="margin-bottom:20px"> \
									 <h2></h2> \
									 <p></p> \
									 <div style="margin:20px 0 10px 0;"> \
									 </div> \
									 <div id="p" class="easyui-panel" title="商品大图" style="width:60%;height:auto;padding:10px;"> \
									      <div> \
									       <img id="bigPic'+idx+'" src="" width="60%" height="auto" style="display: block;"/>\
									      </div> \
									 </div> \
								</div> \
							    <div style="margin-bottom:20px">\
							     <input class="easyui-filebox" name="bigPicFile" id="aa" style="width:60%" data-options="onChange:function(){\
							    	 fileup(this,$(\'#bigPic'+idx+'\'));\
							     },label:\'商品大图:\',required:true"/>\
								</div>\
						</div>'
			});
			idx++;
		}
		function removePanel(){
			var pp = $('#aa0').accordion('getSelected');
			if (pp){
				var index = $('#aa0').accordion('getPanelIndex',pp);
				$('#aa0').accordion('remove',index);
			}
		}
	 
	 
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="添加商品" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/goods/addGood.htm">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="name" style="width:60%" data-options="label:'商品名称:',required:true">
				<%--
				<input class="easyui-textbox" name="capacity" style="width:40%" data-options="label:'商品容量:',required:true,validType:'capacity'">
				 --%>
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="price" style="width:40%" data-options="label:'商品价格:',required:true">元
			</div>
			
			<div style="margin-bottom:20px">
			    <span style="margin-right:25px;">是否上架:</span> 
			    <input type="hidden" name="shelves" id="shelves" value="1">
				<input id="sb" class="easyui-switchbutton" checked  onText="上架" offText="下架" style="width:100px;height:30px" >
			</div>
			
			
			<div style="margin-bottom:20px">
				<select class="easyui-combobox" name="brandId"   data-options="required:true" label="品牌:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${brands}" var="b">
						<option value="${b.id }">${b.name }</option>
					</c:forEach>
				</select>
			</div>
			
			<div style="margin-bottom:20px">
				<select class="easyui-combobox" name="countryId"  data-options="required:true"  label="国家:"  labelPosition="top" style="width:40%;">
					<c:forEach items="${countrys}" var="c">
						<option value="${c.id }">${c.name }</option>
					</c:forEach>
				</select>
			</div>
			
			
			
			<input type="hidden" id="classid" name="classid" value=""/>
			
			<div style="margin-bottom:20px">
				分类:
				<div class="easyui-panel" style="padding:5px">
					<ul id="tt" class="easyui-tree" ></ul>
				</div>
			</div>
			
		
			 
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="info" style="width:100%;height:60px" data-options="label:'商品描述:',multiline:true,required:true">
			</div>
			
			<div style="margin-bottom:20px">
			<input class="easyui-numberspinner" name="limitCount"  value="5000" data-options="label:'限购次数:',required:true,labelPosition:'top',spinAlign:'right'" style="width:40%;">
			</div>
			
			<div style="margin-bottom:20px">
			<input class="easyui-numberspinner" name="inventory"  value="" data-options="label:'库存:',required:true,labelPosition:'top',spinAlign:'right'" style="width:40%;">
			</div>
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="缩略图" style="width:60%;height:auto;padding:10px;">
				      <div>
				     	<img id="imgPre" src="" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
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
				     	<img id="imgPre2" src="" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="bigFile" id="aa2" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre2'));
				}, label:'商品详情图:',required:true" >
			</div>
			
	<p>添加商品大图:</p>
	<div style="margin:20px 0 10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectPanel()">选择大图</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addPanel()">添加大图</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="removePanel()">删除大图</a>
	</div>
	<div id="aa0" class="easyui-accordion" style="width:500px;height:300px;">
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