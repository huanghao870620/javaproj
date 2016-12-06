var bypag;
var coursewareType;
$(function(){
	var dateUtil = new DateUtil();
	//获取内参类型
	coursewareType = $('#coursewareTypeHidden').val();
	// 初始化加载磊丹观点数据
	bypag = new byPaging({
		node : $('#coursewareData'),
		url : contextPath+'/back/courseware/queryCoursewareList.htm?coursewareType='+coursewareType+'&rows=5',
		deleteUrl:contextPath+'/back/courseware/deleteCourseware.htm',
		title : "课件列表",
		columns :
		[{
			field : 'week',
			align : "center",
			title : "序号",
			width : 50
		}, {
			field : 'cfileId',
			align : 'center',
			title : '图片',
			width : 100,
			formatter:function(val){
				return  "<img src="+contextPath+"/download/file/show.htm?id="+val+" width='45' height='40' >"
			}
		},{
			field : 'courseName',
			align : 'center',
			title : '课程名称',
			width : 100
		},{
			field : 'linkAddress',
			align : 'center',
			title : '链接地址',
			width : 100
		}, {
			field : 'createTime',
			align : 'center',
			title : '时间',
			width : 100,
			formatter:function(val){
				return dateUtil.formatDate(val,'yyyy-MM-dd hh:mm:ss');
			}
		}],
		primaryKey:"courseWareId",
		showPageList:false,
		pageSize:5
	});
	bypag.start();
	
	// 删除交易内参
	$('a[icon=icon-remove]').on('click',function(){
		var rows = $('#coursewareData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','请选择要删除的交易内参!','info');
			return;
		}
		$.extend($.messager.defaults,{  ok:"确定",  cancel:"取消" });
		$.messager.confirm('操作提示','确定要删除所选交易内参吗？',function(r){
			if(r){
				bypag.init();
			}
		});
	});
	// 编辑磊丹观点
	$('a[icon=icon-edit]').on('click',function(){
		var rows = $('#coursewareData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','请选择要编辑的交易内参!','info');
			return;
		}
		if(rows.length>1){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','一次只能编辑一条交易内参!','info');
			return;
		}
		var id = rows[0].courseWareId;
		window.location.href= contextPath+'/back/courseware/toEditCourseWare.htm?coursewareType='+coursewareType+'&courseWareId='+id;
	});
});

//根据标题搜索
function doSearch(value){
	bypag.url = contextPath+'/back/courseware/queryCoursewareList.htm?coursewareType='+coursewareType+'&rows=5&courseware_name='+value;
	bypag.start();
}