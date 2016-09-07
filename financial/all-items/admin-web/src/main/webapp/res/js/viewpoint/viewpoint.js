$(function() {
	// 格式化json格式日期
	var formatDate = function (date,fmt) { 
	    var o = {
	        "M+": formatDateStr(date.month+1), // 月份
	        "d+": formatDateStr(date.date), // 日
	        "h+": formatDateStr(date.hours), // 小时
	        "m+": formatDateStr(date.minutes), // 分
	        "s+": formatDateStr(date.seconds), // 秒
	        "q+": Math.floor((date.month + 3) / 3), // 季度
	        "S": date.time // 毫秒
	    };
	    if (/(y+)/.test(fmt)){
	    	fmt = fmt.replace(RegExp.$1, (date.year+1900 + "").substr(4 - RegExp.$1.length));
	    }
	    for (var k in o){
	    	if (new RegExp("(" + k + ")").test(fmt)){
	    		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    	}
	    }
	    return fmt;
	}
	// 日期格式化
	var formatDateStr = function(dateStr){
		if(dateStr<10){
			return '0'+dateStr;
		}
		return dateStr
	}
	
	// 初始化加载磊丹观点数据
	var bypag = new byPaging({
		node : $('#viewPointData'),
		url : contextPath+'/back/viewpoint/queryViewPointList.htm',
		deleteUrl:contextPath+'/back/viewpoint/delViewPoint.htm',
		title : "观点列表",
		columns : [{
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
				return formatDate(val,'yyyy-MM-dd hh:mm:ss');
			}
		},{
			field : 'module',
			align : 'center',
			title : '品种专题',
			width : 100
		}],
		primaryKey:"viewPointId",
		showPageList:false,
		pageSize:5
	});
	bypag.start();
	
	// 删除磊丹观点
	$('a[icon=icon-remove]').on('click',function(){
		var rows = $('#viewPointData').datagrid('getSelections');
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
		var rows = $('#viewPointData').datagrid('getSelections');
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
		var id = rows[0].viewPointId;
		window.location.href= contextPath+'/back/viewpoint/toEditViewPoint.htm?id='+id;
	});
});