var webSocket;
//进入实盘房间
function  enterQuotationRoom(){
	if(roleId != '5'){
		if(levelId =='10'){
			alert("当前用户不具备[实盘房间]权限,请联系销售人员升级您的产品！");
	}else{
		window.location.href=contextPath+'/front/quotation/index.htm';
	}
	}else{
		if(aROOM =='Y'){
			window.location.href=contextPath+'/front/quotation/index.htm';
		}else{
			if(levelId =='0'){
		     $(".login-wrap").show();
			}else{
			 alert("当前用户不具备[实盘房间]权限,请联系销售人员升级您的产品！");
			}
		}
	}
}

//进入房间
function  enterWeiboRoom(){
	window.location.href=contextPath+'/front/weiboChat.htm';
}

function showView(){
   	var hp = document.createElement("script");
   	hp.src = "http://jinshi.hv678.com/room/script/playerchat/3702a66f98924bf7adec6ed272164659";
//   	hp.src = "http://jinshi.hv678.com/classroom/3702a66f98924bf7adec6ed272164659";
   	var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hp,s);
}

$(function(){
	tenMinutes(600);
    $(".close").click(function(){
       $(".bg").hide();
    });
    $(".course-item").click(function(){
        $(".curriculum").show();
    });
    $(".download-item").click(function(){
        $(".download-box").show();
    });
    $(".account-item").click(function(){
        $(".account-box").show();
    });
    $(".product-item").click(function(){
        $(".product-box").show();
    });
    $(".point-item").click(function(){
    	if(roleId != '5'){
    		fillViewpoint();
        	$(".point-box").show();
    		}else{
    			if(viewPoint =='Y'){
    				fillViewpoint();
    		    	$(".point-box").show();
    			}else{
    				if(levelId =='0'){
    				     $(".login-wrap").show();
    					}else{
    						alert("当前用户不具备[磊丹观点]权限,请联系销售人员升级您的产品！");
    					}
    			}
    		}
    });
    $(".serve-item").click(function(){
        $(".serve-box").show();
    });
    $(".spgd-item").click(function(){
        //$(".spgd-box").show();
        alert("敬请期待！");
    });
    $(".duel-item").click(function(){
        //$(".duel-box").show();
        alert("敬请期待！");
    });
    $(".trans-item").click(function(){
    	if(roleId != '5'){
    		fillInternalInfo(1,1);
            $(".trans-box").show();
    		}else{
    			if(inTransaction =='Y'){
    				fillInternalInfo(1,1);
    		        $(".trans-box").show();
    			}else{
    				if(levelId =='0'){
   				     $(".login-wrap").show();
   					}else{
   						alert("当前用户不具备[交易内参]权限,请联系销售人员升级您的产品！");
   					}
    			}
    		}
    });
    //VIP����ʼ
    $(".service ul li").addClass("libg");
    $(".service .title1 li:odd").addClass("coli");
    $(".service .title1 li:even").addClass("colb");
    $(".service .title2 li:odd").addClass("coli");
    $(".service .title2 li:even").addClass("colb");
    $(".service .title3 li:odd").addClass("coli");
    $(".service .title3 li:even").addClass("colb");
    $(".service .title4 li:odd").addClass("coli");
    $(".service .title4 li:even").addClass("colb");
    $(".service .title5 li:odd").addClass("coli");
    $(".service .title5 li:even").addClass("colb");
    //VIP�������
    //�����ڲ�tabѡ���ʼ
    var _index=0;
    $(".jync-tab li").click(function(){
        _index=$(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        if(_index == 1){
        	fillInternalInfo(1,2);
        }
        $(".jync-count .jync-con").eq(_index).show().siblings().hide();
    });
    $(".level-list-title li").click(function(){
        _index=$(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        $(".level-list-con .list-cons").eq(_index).show().siblings().hide();
    });
    //�����ڲ�tabѡ�����
    var demo5 = document.getElementById("demo5");
    var demo11 = document.getElementById("demo11");
    var demo22 = document.getElementById("demo22");
    demo22.innerHTML=document.getElementById("demo11").innerHTML;
    function Marquee(){
        if(demo5.scrollLeft-demo22.offsetWidth>=0){
            demo5.scrollLeft-=demo11.offsetWidth;
        }
        else{
            demo5.scrollLeft++;
        }
    }
    var myvar=setInterval(Marquee,30);
    demo5.onmouseout=function (){myvar=setInterval(Marquee,30);};
    demo5.onmouseover=function(){clearInterval(myvar);};
    //���ν�������ʼ
    $('.circle').each(function(index, el) {
        var num = $(this).find('span').text() * 3.6;
        if (num<=180) {
            $(this).find('.right').css('transform', "rotate(" + num + "deg)");
        } else {
            $(this).find('.right').css('transform', "rotate(180deg)");
            $(this).find('.left').css('transform', "rotate(" + (num - 180) + "deg)");
        };
    });
    
    $('.myscroll').myScroll({
        speed: 50, //数值越大，速度越慢
        rowHeight: 47 //li的高度
    });
    //视频直播
    showView();
    
    
})
//10分钟跳出提醒界面
function tenMinutes(count) {
    window.setTimeout(function(){ 
        count--; 
        if(count > 0) { 
        	tenMinutes(count); 
        } else { 
            $(".minutes-10-wrap").show();
        	return ;
        } 
    }, 1000); 
} 

function  doing(objA,linkAddress,courseWareId,courseName){
	if(roleId != '5'){
		objA.href=linkAddress;
		objA.target="_blank";
	}else{
		 var localCourseWareLevel = eval('('+ localCourseWareLevelIndex +')');
		if("Y"==localCourseWareLevel[courseWareId]){
		objA.href=linkAddress;
		objA.target="_blank";
		}else{
			if(levelId =='0'){
			 $(".login-wrap").show();
			}else{
			alert("当前用户不具备查看["+courseName+"]课件权限,请联系销售人员升级您的产品！");
			}
		}
		
	}
}

function openControl(obj,url,courseLevel){
	if(levelId =='0'){
		alert("当前用户不具备查看["+courseLevel+"]教程权限,请联系销售人员升级您的产品！");
	}else{
		obj.href=contextPath+url;
		obj.target="_blank";
		
	}
}
