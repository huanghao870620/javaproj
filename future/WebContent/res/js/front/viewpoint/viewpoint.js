$(function(){
	$(document).on('click','div.js-box2',function(){
		window.open($(this).attr('href'));
	})
})
//格式化日期对象
var dateUtil = new DateUtil();
//磊丹观点总条数
var viewpointCount;
//填充磊丹观点
function fillViewpoint(page){
	if(!page)page=1;
	$.ajax({
		type:'POST',
		url:contextPath+'/front/viewpoint/queryViewpointList.htm',
		data:{page:page,rows:'5'},
		cache:false,
		dataType:'json',
		success:function(data){
			viewpointCount = data.total;
			if(viewpointCount<=0){
				$('.point-box .contain_main .content').html('暂未查询到磊丹观点数据');
				return;
			}
			var objStr = '';
			var result = data.rows;
			for (var i = 0; i < result.length; i++) {
				objStr+='<div class="contain_main-item">';
				objStr+='<span class="time">';
				if(page==1&&i==0){
					objStr+='<i class="bgcolor">';
				}else{
					objStr+='<i>';
				}
				objStr+=dateUtil.formatDate(result[i].createTime,'dd')+'</i><p>'+dateUtil.formatDate(result[i].createTime,'yyyy-MM')+'</p></span>';
				objStr+='<ul class="list">';
				objStr+='<li class="list_title"><span>磊丹观点</span><i><a style="border-right:none;" target="_blank" href="'+contextPath+'/front/viewpoint/toJSGD.htm?id='+result[i].viewpointId+'">'+result[i].title+'</i></a>';
				if(page==1&&i==0){
					objStr+='<img src="'+contextPath+'/res/images/news_bot.png" />';
				}
				objStr+='</li>';
				objStr+='<li class="list_titfu"><i>磊丹财经</i><b>特邀嘉宾</b><i>磊丹财经研究院</i><b>'+dateUtil.formatDate(result[i].createTime,'hh:mm:ss')+'</b></li>';
				objStr+='<li class="miaoshu"><a target="_blank" href="'+contextPath+'/front/viewpoint/toJSGD.htm?id='+result[i].viewpointId+'">'+result[i].content+'</a></li>';
				objStr+='</ul>';
				objStr+='</div>';
			}
			$('.point-box .contain_main .content').html(objStr);
			$('.point-box .pager').pagination({
				total:viewpointCount,
				pageSize:5,
				showPageList:false,
				loading:true,
				layout:['first','prev','links','next','last'],
				links:5,
			    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			    onSelectPage:function(pageNumber,pageSize){
			    	fillViewpoint(pageNumber);
			    }
			});
		}
	});
}
    
//填充交易内参
var internalCount;
function fillInternalInfo(page,internalType){
	if(!page)page=1;
	$.ajax({
		type:'POST',
		url : contextPath+'/front/internal/queryInternalList.htm?internalType='+internalType,
		data:{page:page,rows:'5'},
		cache:false,
		dataType:'json',
		success:function(data){
			internalCount = data.total;
			if(internalCount<=0){
				if(internalType==1){
					$('div.trans-box div.metal-con').html('暂未查询到金属交易内参数据数据');
				}else{
					$('div.trans-box div.oil-con').html('暂未查询到原油交易内参数据');
				}
				return;
			}
			var result = data.rows;
			var objStr = '';
			for (var i = 0; i < result.length; i++) {
				if(i==0){
					objStr+='<div class="js-box1">';
    				objStr+='<div class="js-img">';
    				if(internalType==1){
    					objStr+='<img src="'+contextPath+'/res/images/jync-img.jpg">';
    				}else{
    					objStr+='<img src="'+contextPath+'/res/images/bgtu2_03.jpg">';
    				}
    				objStr+='</div>';
    				objStr+='<div class="js-tex">';
    				objStr+='<p class="js-title"><a target="_blank" href="'+contextPath+'/front/internal/toJYNC.htm?internalId='+result[i].internalId+'">'+result[i].title+'</a></p>';
    				objStr+='<p class="js-time"><span>磊丹财经</span>'+dateUtil.formatDate(result[i].createTime,'yyyy年MM月dd日 hh:mm:ss')+'</p>';
    				objStr+='<p class="js-con"><a target="_blank" href="'+contextPath+'/front/internal/toJYNC.htm?internalId='+result[i].internalId+'">'+result[i].content+'</a></p>';
    				objStr+='<p class="js-more"><a target="_blank" href="'+contextPath+'/front/internal/toJYNC.htm?internalId='+result[i].internalId+'">查看详情  >> </a></p>';
                    objStr+='</div>';
                    objStr+='</div>';
				}else{
					objStr+='<div class="js-box2" href="'+contextPath+'/front/internal/toJYNC.htm?internalId='+result[i].internalId+'">';
					objStr+='<div class="js-box2-time">'+dateUtil.formatDate(result[i].createTime,'dd');
					objStr+='<br>';
					objStr+='<span>'+dateUtil.formatDate(result[i].createTime,'yyyy-MM')+'</span>';
					objStr+='</div>';
					objStr+='<div class="js-box2-tex">';
					objStr+='<p>'+result[i].title+'</p>';
					objStr+='<p>'+result[i].content+'</p>'
					objStr+='</div>';
					objStr+='</div>';
				}
			}
			if(internalType==1){
				$('div.trans-box div.metal-con').html(objStr);
				if(internalCount>0){
					$('div.trans-box div.metal-page').pagination({
        				total:internalCount,
        				pageSize:5,
        				showPageList:false,
        				loading:true,
        				layout:['first','prev','links','next','last'],
        				links:5,
    				    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    				    onSelectPage:function(pageNumber,pageSize){
    				    	fillInternalInfo(pageNumber,1);
    				    }
        			});
				}
			}else{
				$('div.trans-box div.oil-con').html(objStr);
				if(internalCount>0){
    				$('div.trans-box div.oil-page').pagination({
        				total:internalCount,
        				pageSize:5,
        				showPageList:false,
        				loading:true,
        				layout:['first','prev','links','next','last'],
        				links:5,
    				    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    				    onSelectPage:function(pageNumber,pageSize){
    				    	fillInternalInfo(pageNumber,2);
    				    }
        			});
				}
			}
		}
	})
}
