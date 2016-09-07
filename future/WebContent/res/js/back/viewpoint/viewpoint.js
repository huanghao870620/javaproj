var bypag;
$(function() {
	var dateUtil = new DateUtil();
	// 初始化加载磊丹观点数据
	bypag = new byPaging({
		node : $('#viewpointData'),
		url : contextPath+'/back/viewpoint/queryViewpointList.htm?rows=5',
		deleteUrl:contextPath+'/back/viewpoint/delViewpoint.htm',
		title : "观点列表",
		columns :
		[{
			field : 'week',
			align : "center",
			title : "序号",
			width : 100
		}, {
			field : 'title',
			align : "center",
			title : "标题",
			width : 100
		}, {
			field : 'createTime',
			align : 'center',
			title : '时间',
			width : 100,
			formatter:function(val){
				return dateUtil.formatDate(val,'yyyy-MM-dd hh:mm:ss');
			}
		},{
			field : 'special',
			align : 'center',
			title : '品种专题',
			width : 100
		}],
		primaryKey:"viewpointId",
		showPageList:false,
		pageSize:5
	});
	bypag.start();
	
	// 删除磊丹观点
	$('a[icon=icon-remove]').on('click',function(){
		var rows = $('#viewpointData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','请选择要删除的观点!','info');
			return;
		}
		$.extend($.messager.defaults,{  ok:"确定",  cancel:"取消" });
		$.messager.confirm('操作提示','确定要删除所选观点吗？',function(r){
			if(r){
				bypag.init();
			}
		});
	});
	// 编辑磊丹观点
	$('a[icon=icon-edit]').on('click',function(){
		var rows = $('#viewpointData').datagrid('getSelections');
		if(!rows||rows.length<=0){
			$.extend($.messager.defaults,{ok:"好的"}); 
			$.messager.alert('提示','请选择要编辑的观点!','info');
			return;
		}
		if(rows.length>1){
			$.extend($.messager.defaults,{  ok:"好的" }); 
			$.messager.alert('提示','一次只能编辑一条观点!','info');
			return;
		}
		var id = rows[0].viewpointId;
		window.location.href= contextPath+'/back/viewpoint/toEditViewpoint.htm?id='+id;
	});
});

//根据标题搜索
function doSearch(value){
	bypag.url = contextPath+'/back/viewpoint/queryViewpointList.htm?rows=5&title='+value;
	bypag.start();
}