<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>添加活动</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/css/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/base.js"></script>
	
	<script type="text/javascript">
	
	function submitForm(){
		var idarr;
		
		
		if(type==1){//品牌
			idarr = $("input[name='brandId']:checked").map(function () {
	               return $(this).val();
	           }).get().join(',');
		}else if(type ==2){ //分类
			
		}else if(type==3){//国家
			idarr = $("input[name='countryId']:checked").map(function () {
	               return $(this).val();
	           }).get().join(',');
		}
		
		 $('#ids').val(idarr);
		 $('#ff').submit();
	}
	
	function clearForm(){
		$('#ff').form('clear');
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
			title:'活动详情图'+idx,
			content:'<div id="id'+idx+'" style="padding:10px"> \
							<div style="margin-bottom:20px"> \
								 <h2></h2> \
								 <p></p> \
								 <div style="margin:20px 0 10px 0;"> \
								 </div> \
								 <div id="p" class="easyui-panel" title="活动详情图" style="width:60%;height:auto;padding:10px;"> \
								      <div> \
								       <img id="activityDetail'+idx+'" src="" width="60%" height="auto" style="display: block;"/>\
								      </div> \
								 </div> \
							</div> \
						    <div style="margin-bottom:20px">\
						     <input class="easyui-filebox" name="activityDetailFile" id="aa" style="width:60%" data-options="onChange:function(){\
						    	 fileup(this,$(\'#activityDetail'+idx+'\'));\
						     },label:\'活动详情图:\',required:true"/>\
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
	
	var type=1;
	$(function(){
		
		$('#ddd').tabs({
		    border:true,
		    onSelect:function(title,index){
		    	 type = index+1;
		        $('#activityType').val(type);
		    }
		
		});
		
		
		
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
		
		
	});
	
	</script>
	
</head>
<body>
	
	<div id="p" class="easyui-panel" title="添加活动" style="width:100%;height:auto;padding:10px;">
		
		
	 <h2></h2>
	<p></p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="" style="width:100%;max-width:700px;padding:30px 60px;">
		<form id="ff" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/activity/addActivity.htm">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="name" style="width:60%" data-options="label:'活动名称:',required:true">
			</div>
			 
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="info" style="width:100%;height:60px" data-options="label:'活动描述:',multiline:true">
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="sill" style="width:60%" data-options="label:'价格门槛:',required:true">
			</div>
			
			<div style="margin-bottom:20px">
			<input class="easyui-numberspinner" name="count"  value="" data-options="label:'数量门槛:',required:true,labelPosition:'top',spinAlign:'right'" style="width:40%;">
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="price" style="width:60%" data-options="label:'优惠价格:',required:true">
			</div>
			
			 <input type="hidden" id="activityType" name="activityType" value="1" />
			 <input type="hidden" id="ids" name="ids" value="" />
			
	<p>活动类型:</p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-tabs" id="ddd"  style="width:100%;height:auto">
		<div title="品牌" style="padding:10px">
		    	<c:forEach items="${brands}" var="b">
					<input type="checkbox" name="brandId" value="${b.id}" />${b.name }
				</c:forEach>
		</div>
		<div title="分类" style="padding:10px">
			  <ul id="tt" class="easyui-tree" ></ul>
		</div>
		<div title="国家" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
					<c:forEach items="${countrys}" var="c">
						<input type="checkbox" name="countryId" value="${c.id}" />${c.name }
					</c:forEach>
		</div>
	</div>
			
			
			<div style="margin-bottom:20px">
				 <h2></h2>
				<p></p>
				<div style="margin:20px 0 10px 0;">
				</div>
				<div id="p" class="easyui-panel" title="活动轮播图" style="width:60%;height:auto;padding:10px;">
				      <div>
				     	<img id="imgPre" src="" width="60%" height="auto" style="display: block;" /> 
				      </div>
				</div>
			</div>
			
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="imgAdvFile" id="aa" style="width:60%" data-options="onChange:function(){
				fileup(this,$('#imgPre'));
				}, label:'活动轮播图:',required:true" >
			</div>
			
					
	<p>添加活动详情图:</p>
	<div style="margin:20px 0 10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectPanel()">选择活动详情图</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addPanel()">添加活动详情图</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="removePanel()">删除活动详情图</a>
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