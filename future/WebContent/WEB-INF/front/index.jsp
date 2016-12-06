    <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@page import="java.net.InetAddress"%>
            <%
				String contextPath = request.getContextPath();
			%>
        <!DOCTYPE html>
        <html>
        <head lang="en">
        <meta charset="utf-8">
        <meta name="renderer" content="webkit|ie-comp|ie-stand">
        <title>磊丹财经-在线视频直播室</title>
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">

        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/front/css/index.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/js/plugins/jquery-easyui-1.4.5/themes/default/easyui.css"/>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/res/js/plugins/jquery-easyui-1.4.5/themes/icon.css"/>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/base.js"></script>
        <script type="text/javascript" src="<%=contextPath %>/res/js/plugins/DateUtil.js" ></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/front_index/front_index.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/lrtk.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/scroll.js"></script>
        <script type="text/javascript" src="<%=contextPath%>/res/js/front/viewpoint/viewpoint.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/index.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/front_index/index_chat.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/js.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/plugins/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="<%=contextPath %>/res/js/ajaxFileUpload.js" ></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/general/login.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/zzsc.js"></script>
        <script type="text/javascript">
        var contextPath = '<%=contextPath %>';
        var ip='${ip}';
        var server_ip = ip;
        var roleId = '${roleId}';
        var viewPoint = '${cusLimitRela.viewPoint}';
        var aROOM = '${cusLimitRela.aROOM}';
        var inTransaction = '${cusLimitRela.inTransaction}';
        var localCourseWareLevelIndex = '${cusLimitRela.allCourseWareLevel}';
        var levelId ='${user.levelId}';
        var CFileId= '${user.CFileId}';
        
        var _hv678videoarea = "videoarea";
        var ip='${ip}';
        
        
        $(function(){
        	
        	var emo = new Emo('writeMsg1');
        	emo.init();
        	//server_ip = "192.168.16.253";
        	server_ip = "live.leidan18.com";
        	server_ip= "192.168.10.203";
        	//webSocket通信
            window.webSocket =  new WebSocket('ws://'+ server_ip + ':8080'+ contextPath + '/websocket');  
        	
            webSocket.onerror = function(event) {
              console.log('webSocket error:'+event);
            };  

            webSocket.onopen = function(event) {
            	console.log('--- open ---');
            };  
            
            webSocket.onclose = function(event){
            	 console.log("-- close --");
            }

            webSocket.onmessage = function(event) {
            	 console.log("--- message --");
            	 var msg = event.data;
            	 var node = new Str2Dom({
            		 content : msg,
            		 basepath : contextPath
        		 }).str2node();
            	 var _msgContent = $('#msg_content');
            	 _msgContent.append(node);
            	 _msgContent[0].scrollTop = _msgContent[0].scrollHeight-_msgContent[0].clientHeight;
            };
            
        	$('#writeMsg1').keydown(function(e){
        		if(e.keyCode==13){
        			e.cancelBubble=true;
        			e.preventDefault();
        			e.stopPropagation();
        			send();
        		}
        	});
        	
        	//聊天表情
        	$(".face").click(function(e) {
        		$(".emoji-field").toggle();
        		$(".emoji-caitiao").hide();
        	    e.stopPropagation();
        	});
        	
        	//点击页面其他地方隐藏表情和彩条
            $(document).click(function(e) {
                $(".emoji-field").hide();
                $(".emoji-caitiao").hide();
            });
        	
            //彩条
            $(".striped").click(function(e) {
                $(".emoji-caitiao").toggle();
                $(".emoji-field").hide();
                e.stopPropagation();
            });
            
            //发送彩条
            $(".emoji-caitiao img").click(function(){
            	if(levelId=='0'){//    游客发言控制     	
            		var topWin = window.top.document.getElementById("iframepage5").contentWindow;
            		if(topWin.document.getElementById("wait").value == "no"){
            			waitNo();
          		         return;
                  	}else{
          		        waitOk();
          	       }
          	    }
            	var hiddenCaitiao = $('div.hiddenCaitiao');
           	 	var imgSrc = this.src;
                var image = document.createElement("img");
                image.src = imgSrc;
                hiddenCaitiao.html(image);
        		var param = {ip : window.ip, msg : hiddenCaitiao.html()};
        		var toStr = JSON.stringify(param);
        		window.webSocket.send(toStr);
        		hiddenCaitiao.empty();
            });
            
            //清屏
            $('a.clear').click(function(){
            	$('#msg_content').empty();
            });
            
            //发图
            $('#uploadFile').live('change',function(){
        	  	var $this = $(this);
        		if($this.val().length>0){
        			var id = $(this).attr('id');
        			$.ajaxFileUpload({
        				url:contextPath+'/index/uploadPic.htm',
        				secureuri:false,
        				type:'POST',
        				fileElementId:id,
        				dataType:'json',
        				success:function(data){
        					if(data.success == -1){
        						var imgSrc = contextPath+'/download/file/show.htm?id='+data.obj;
        				        var image = document.createElement("img");
        				        image.src = imgSrc;
        				        image.width = 150;
        				        image.height = 150;
        				        var writeMsgDiv = $('div.hiddenCaitiao');
        				        writeMsgDiv.html(image);
        				        var writeMsg = $('#writeMsg1');
        				        writeMsg.append(writeMsgDiv.html());
        				        writeMsg[0].scrollTop = _msgContent[0].scrollHeight-_msgContent[0].clientHeight;
        					}else{
        						alert(data.message);
        					}
        				},
        				
        			})
        		}
        	});
            
            $.post("<%=request.getContextPath()%>/getHistoryMsg.htm",{t:new Date},function(data){
            	var node = new Str2Dom({
           		 content : data,
           		 basepath : contextPath
       		 }).str2node();
                var _msgContent = $('#msg_content');
                _msgContent.append(node);
                _msgContent[0].scrollTop = _msgContent[0].scrollHeight-_msgContent[0].clientHeight;
            });
            
        })
        </script>
        </head>
        <body>
        <img src="<%=request.getContextPath() %>/res/images/backbg2.png" class="bg-yx-pic">
        <div class="wrap">
        <div class="static-top">
        <div class="top_box clearfix">
        <div class="fl top-left">
        <ul class="clearfix">
        <li><a href="#"><img src="<%=request.getContextPath() %>/res/images/logo.gif"></a></li>
        <!--<li class="active" style=" margin-left: 1em;"><a href="#"><i class="computer"></i>保存到桌面</a></li>-->
        <!--<li class="small"><a href="#"><i class="collect"></i>收藏链接</a></li>-->
        <li class="small state-small stat-btn active" style=" margin-left: 1em;"><a href="#">公告声明</a></li>
        <li class="small stat-btn"><a href="#"><i class="guide"></i>一对一指导</a></li>
        <li class="small"><a href="#"><i class="aid aid-blue"></i>助理</a></li>
        <li class="small"><a href="#"><i class="aid aid-red"></i>助理</a></li>
        <p class="no-click">无法点击在线客服？<br>请添加QQ<span>800093193</span>咨询</p>
        <li class="exit-btn"><a href='javascript:void(0);'>退出</a></li>
        </ul>
        </div>
        <div class="fr top-right">
        <ul class="clearfix kf">
        <!--<li class="service"><a href="#"  class="top-btn01"><img src="<%=request.getContextPath() %>/res/images/qqkf.gif"></a><a href="#"><img src="<%=request.getContextPath() %>/res/images/qqkf.gif"></a></li>-->
        <c:if test="${isBackUser }">
            <li class="backIndex"><a href="<%=request.getContextPath()%>/back/backIndex.htm">进入后台</a></li>
        </c:if>
        <li class="account-li"><a href="http://kaihu.hzmpe.com:39110/account_helipay/index.jsp?8BC51E7EAF881208AB2184C6A0A470F1"  target="_blank"><i class="account"></i>立即开户</a></li>
        <!--<li class="calendar-li"><a href="#"><i class="calendar01"></i>财经日历</a></li>-->
        <!--<li class="login"><a href='javascript:;'>登录</a></li>-->
        <li><a href='<%=request.getContextPath()%>/login/logout.htm'>退出</a></li>
        </ul>
        </div>
        </div>
        </div>
        <div class="wrap_fluid">
        <div class="middle clearfix">
        <div class="unit-left fl">
        <div class="">
        <div class="left-top">
        <dl class="clearfix">
        <c:choose>
            <c:when test="${roleId ne '5'}">
                <dd>名称：${user.name}</dd>
                <c:if test="${user.CFileId!=null}">
                <dd>头像：<img src="<%=contextPath %>/download/file/show.htm?id=${user.CFileId}" width="45" height="40" ></dd>
			   </c:if>
            </c:when>
            <c:otherwise>
                <dd>会&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;员：${user.userName}</dd>
                <dd>会员等级：<img src="<%=request.getContextPath() %>/res/images/level${user.levelId}.png"></dd>
            </c:otherwise>
        </c:choose>

      
        </dl>
        <p class="upgrade">
        <a href="javascript:;" class="login">&nbsp;&nbsp;&nbsp;&nbsp;登录&nbsp;&nbsp;&nbsp;</a>
        <a href="#">升级会员</a>
        </p>
        <p class="room">
        <a onclick="enterQuotationRoom();" href="javascript:;">实盘房间</a>
        <a href="#">专题房间</a>
        </p>
        </div>
        <div class="left-tab-ul">
        <ul class="clearfix">

        <li class="margin-right">
        <a href="#" class="point-item">
        <img src="<%=request.getContextPath() %>/res/images/guand.png" class="js-img-top">
        <span>磊丹观点</span>
        </a>
        </li>

        <li>
        <a href="#" class="trans-item">
        <img src="<%=request.getContextPath() %>/res/images/neic.png" class="jy-img-top">
        <span>交易内参</span>
        </a>
        </li>

        <li class="margin-right">
        <a href="#" class="duel-item">
        <img src="<%=request.getContextPath() %>/res/images/duij.png" class="dk-img-top">
        <span>我的提醒</span>
        </a>
        </li>
        <li>
        <a href="#" class="spgd-item">
        <img src="<%=request.getContextPath() %>/res/images/gend.png" class="sp-img-top">
        <span>带我交易</span>
        </a>
        </li>
        <li class="margin-right">
        <a href="#" class="product-item">
        <img src="<%=request.getContextPath() %>/res/images/jies.png" class="cp-img-top">
        <span>产品介绍</span>
        </a>
        </li>
        <li>
        <a href="#" class="serve-item">
        <img src="<%=request.getContextPath() %>/res/images/vipfw.png" class="fw-img-top">
        <span>VIP服务</span>
        </a>
        </li>
        </ul>
        </div>
        <div class="course-box">
        <h3><i class="number number-1">01</i><span class="jc-color">基础</span>课件</h3>
        <div class="myscroll">
        <ul>
        
        <c:forEach items="${courseWareList}" var="courseWare">
	          <c:if test="${courseWare.courseWareType == 1 }">
	               <li><a   href="javascript:void(0)" onclick="doing(this,'${courseWare.linkAddress }','${courseWare.courseWareId }','${courseWare.courseName }')"   ><img src="<%=contextPath %>/download/file/show.htm?id=${courseWare.cfileId }" width="33" height="33">${courseWare.courseName }</a></li>
	          </c:if>
	    </c:forEach>
        </ul>
        </div>
        </div>
        <div class="course-box ">
        <h3><i class="number number-2">02</i><span class="gj-color">高级</span>课件</h3>
        <div class="myscroll">
        <ul>
         <c:forEach items="${courseWareList}" var="courseWare">
	          <c:if test="${courseWare.courseWareType == 2 }">
	               <li><a  href="javascript:void(0)" onclick="doing(this,'${courseWare.linkAddress }','${courseWare.courseWareId }','${courseWare.courseName }')"   ><img src="<%=contextPath %>/download/file/show.htm?id=${courseWare.cfileId }" width="33" height="33">${courseWare.courseName }</a></li>
	          </c:if>
	    </c:forEach>
        </ul>
        </div>
        </div>
        <div class="course-box zt-course">
        <h3><i class="number number-3">03</i><span class="zt-color">专题</span>课件</h3>
        </div>
        <!--课件滚动结束-->
        </div>
        </div>
        <div class="unit-center fl">
        <div class="video-tit clearfix">
        <span class="fl"><i class="video_icon"></i>实况直播</span>
        <p class="fl"><span class="refresh-tex">黑屏或卡顿请点击</span><a href="#" class="refresh">F5刷新</a></p>
        </div>
        <div class="video">
        <!--
        <video src="" width="100%" height="100%" controls ></video>
        -->
        <div class="game-box">
            <img  src="<%=request.getContextPath() %>/res/images/earnmoney.png">
        </div>
        <div id="videoarea"></div>
        </div>
        <div class="banner">
        <!--轮转开始-->
        <div class="slide-main" id="touchMain">
        <div class="slide-box" id="slideContent">

            <div class="slide" id="bgstylea">
                <a  href="#" target="_blank">
                    <%--<img src="<%=request.getContextPath() %>/res/images/slide02.jpg" width='100%' height="247">--%>
                </a>
            </div>
            <div class="slide" id="bgstyleb">
                <a  href="http://www.leidan18.com/activity/risk0/" target="_blank">
                    <%--<img src="<%=request.getContextPath() %>/res/images/slide03.jpg" width='100%' height="247">--%>
                </a>
            </div>
            <div class="slide" id="bgstylec">
                <a  href="http://www.leidan18.com/soft-page/index.html" target="_blank">
                <%--<img src="<%=request.getContextPath() %>/res/images/slide01.jpg" width='100%' height="247">--%>
                </a>
            </div>
        </div>
        <div class="item">
        <a class="cur" stat="item1001" href="javascript:;"></a><a href="javascript:;" stat="item1002"></a><a href="javascript:;" stat="item1003"></a>
        </div>
        </div>
        <!--轮转结束-->
        </div>
        </div>
        <div class="unit-right fl">
        <div class="voice-box">
        <span style="margin-left: 1em;"><i class="voice-icon"></i>实况直播</span>
        <div class="roll-box">
        <div id="demo" class="qimo8">
        <div class="qimo">
        <div id="demo1">
        <ul>
        <li><a href="#" >线下给一单，赛过活神仙，火速猛戳QQ交谈，进入磊丹财经直播线下战队，获取线下单！！！</a></li>
        <li><a href="#" >线下给一单，赛过活神仙，火速猛戳QQ交谈，进入磊丹财经直播线下战队，获取线下单！！！</a></li>
        <li><a href="#" >线下给一单，赛过活神仙，火速猛戳QQ交谈，进入磊丹财经直播线下战队，获取线下单！！！</a></li>
        <li><a href="#" >线下给一单，赛过活神仙，火速猛戳QQ交谈，进入磊丹财经直播线下战队，获取线下单！！！</a></li>
        <li><a href="#" >线下给一单，赛过活神仙，火速猛戳QQ交谈，进入磊丹财经直播线下战队，获取线下单！！！</a></li>
        <li><a href="#" >线下给一单，赛过活神仙，火速猛戳QQ交谈，进入磊丹财经直播线下战队，获取线下单！！！</a></li>
        <li><a href="#" >线下给一单，赛过活神仙，火速猛戳QQ交谈，进入磊丹财经直播线下战队，获取线下单！！！</a></li>
        <li><a href="#" >线下给一单，赛过活神仙，火速猛戳QQ交谈，进入磊丹财经直播线下战队，获取线下单！！！</a></li>
        </ul>
        </div>
        <div id="demo2"></div>
        </div>
        </div>
        </div>
        </div>
        <div class="chart-box fl">
        <div class="chart">
        <ul class="info" id="msg_content"></ul>
        </div>
        <c:if test="${sessionScope.isSpeak }">
            <div class="message">
            <div class="line-qq-box">
            <iframe id="iframepage" name="iframepage" src="<%=request.getContextPath() %>/qq-line/qq-line.html"  width="100%" height="44" frameBorder=0 scrolling="auto"   style="overflow-x:hidden;"></iframe>
            </div>

            <div class="send-message">
            <p class="message-box clearfix">
            <a href="#" class="face"><i class="face-icon"></i>表情</a>
            <a href="#" class="striped"><i class="striped-icon"></i>彩条</a>
            <a href="#" class="clear clear-a"><i class="clear-icon"></i>清屏</a>
            <c:if test="${roleId eq '3' }">
	            <a href="#" class="clear-a up-img"><i class="clear-icon"></i>发图<input type='file' id="uploadFile" name="file" class="up-input"></a>
            </c:if>
            <a href="#" class="color-btn small-mj"><i class="mj-icon"></i>马甲领取</a>
            <a href="#" class="color-btn"><i class="cd-icon"></i>持单跟踪</a>
            <div class="emoji-field">
            <ul class="clearfix">
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.1.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.2.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.3.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.4.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.5.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.6.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.7.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.8.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/1.9.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/2.1.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/2.2.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/2.3.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/2.4.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/2.5.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/2.6.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/2.7.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/2.9.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.1.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.2.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.3.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.4.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.5.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.6.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.7.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.8.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/3.9.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.1.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.2.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.3.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.4.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.5.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.6.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.7.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.8.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/4.9.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.1.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.2.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.3.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.4.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.5.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.6.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.7.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.8.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/5.9.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.1.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.2.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.3.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.4.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.5.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.6.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.7.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.8.gif">
            </li>
            <li>
            <img src="<%=request.getContextPath() %>/res/images/emoji-filed/6.9.gif">
            </li>
            </ul>
            </div>
            <div class="emoji-caitiao">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.1.gif">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.2.gif" style="background:transparent;">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.3.gif">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.4.gif">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.5.gif" width="90">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.6.jpg" width="80">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.7.gif" width="80">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.8.gif" width="60">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/1.9.gif" width="60">
            <img src="<%=request.getContextPath() %>/res/images/emoji-caitiao/2.1.gif" width="140">
            </div>
            </p>
            <form method="post" action="#" name="#">
            <div id="writeMsg" style="display:none;"></div>
            <div style="display:none;" class="hiddenCaitiao"></div>
            <div contentEditable="true" id="writeMsg1" style="overflow-y:auto;overflow-x:hidden;word-break:break-all;word-wrap:break-word;"></div>
            <a href="javascript:send();" class="send-btn"></a>
            <a href="#" class="small-send-btn">发送</a>
            </form>
            </div>
            </div>
        </c:if>
        </div>
        <div class="tip-box fr">
        <ul>
        <li>
        <a href="#" class="course-item">
        <img src="<%=request.getContextPath() %>/res/images/clock.png">
        <span>课程表</span>
        </a>
        </li>
        <li>
        <a href="#" class="teachers-item">
        <img src="<%=request.getContextPath() %>/res/images/lingd.png">
        <span>老师风采</span>
        </a>
        </li>
        <li>
        <a href="#" class="account-item">
        <img src="<%=request.getContextPath() %>/res/images/zhend.gif" class="zh-img-top">
        <span>账户诊断</span>
        </a>
        </li>
        <li>
        <a href="#" class="download-item">
        <img src="<%=request.getContextPath() %>/res/images/load.png">
        <span>软件下载</span>
        </a>
        </li>
        <li>
        <a href="#" class="t-course-item">
        <img src="<%=request.getContextPath() %>/res/images/load.png">
        <span>技术课程</span>
        </a>
        </li>
        <li style="display: none;">
        <a href="#">
        <span>备用2</span>
        </a>
        </li>
        <li class="sys-box">
        <a href="#">
        <img src="<%=request.getContextPath() %>/res/images/erweima.gif" class="weixin">
        <a href="#" class="sys-a">扫一扫</a><br>
        <a href="#" class="sys-a">关注微信号</a>
        </a>
        </li>
        </ul>
        <a href="#" class="complaint">投诉建议</a>
        </div>
        </div>
        </div>
        </div>
        <div class="bottom-text ">
        <p class="bottom-text-p">分析师所发表言论只代表个人观点，仅供参考，投资有风险，入市需谨慎。为保证浏览效果，建议使用最高屏幕分辨率。</p>
        </div>
        </div>
        <!--弹窗开始-->
        <!--账户诊断弹窗开始-->
        <div class="bg account-box" style="display: none;">
        <div class="account position-middle">
        <div class="account-con">
        <div class="header"><img src="<%=request.getContextPath() %>/res/images/banner01.gif" /></div>
        <div class="section01">
        <h1 class="title"><img src="<%=request.getContextPath() %>/res/images/sec01h.png" /></h1>
        <ul class="sec01-main">
        <li><p><img src="<%=request.getContextPath() %>/res/images/yuyue.png" /><b>第一时间&nbsp;在线预约</b></p><span>在线预约账户诊断，随时随地。</span></li>
        <li><p><img src="<%=request.getContextPath() %>/res/images/huifang.png" /><b>第二时间&nbsp;客户回访</b></p><span>专属助理回访，确认您的交易情况，安全可靠。</span></li>
        <li class="nobor"><p><img src="<%=request.getContextPath() %>/res/images/zhenduan.png"/><b>第一时间&nbsp;在线预约</b></p><span>分析师对您的账户进行深入分析，出具书面分析报告。</span></li>
        </ul>
        </div>
        <div class="section02">
        <h1 class="title"><img src="<%=request.getContextPath() %>/res/images/sec02h.png" /></h1>
        <ul class="sec02-main">
        <li class="dangqian" ><a href="#"></a></li>
        <li class="lishi"><a href="#"></a></li>
        <li class="hexin"><a href="#"></a></li>
        </ul>
        <a class="order" href="#">立即预约</a>
        </div>

        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        <!--账户诊断弹窗结束-->

        <!--用户登录弹窗开始-->
        <div class="bg login-wrap" style="display:none;">
        <div class="user-login position-middle">
        <div class="login-con position-con">

        <iframe id="iframepage" name="iframepage" src="<%=request.getContextPath() %>/login/login-index.jsp"  width="100%" height="437" frameBorder=0 scrolling="auto"   style="overflow-x:hidden;"></iframe>
        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        <!--用户登录弹窗结束-->

         <!--游客五秒禁言弹窗开始-->
        <div id="online-10-wrap" class="bg online-10-wrap" style="display:none;">
        <div class="online-10-yk position-middle">
        <div class="online-10-con position-con">
        <iframe id="iframepage5" name="iframepage5" src="<%=request.getContextPath() %>/on-line/online-5.jsp"  width="100%" height="437" frameBorder=0 scrolling="auto"   style="overflow-x:hidden;"></iframe>
        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
       <!--游客五秒禁言弹窗结束-->

        <!--进入直播室10分钟弹窗开始-->
        <div id="minutes-10-wrap" class="bg minutes-10-wrap" style="display: none;" > 
        <div class="minutes-10-yk position-middle">
        <div class="minutes-10-con position-con">
        <iframe id="iframepage" name="iframepage" src="<%=request.getContextPath() %>/on-line/10-minutes.jsp"  width="100%" height="437" frameBorder=0 scrolling="auto"   style="overflow-x:hidden;"></iframe>
        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        <!--进入直播室10分钟弹窗结束-->




        <!--课程表弹窗开始-->
        <div class="bg curriculum" style="display: none;">
        <div class="course-list position-middle">
        <div class="course-con position-con">
            <img src="<%=request.getContextPath() %>/res/images/course-t.png" />
        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        <!--课程表弹窗结束-->

        <!--下载页面弹窗开始-->
        <div class="bg download-box" style="display: none;">
        <div class="download-con position-middle">
        <div class="header"><img src="<%=request.getContextPath() %>/res/images/download_top.png" /></div>
        <div class="software">
        <ul class="loadbut">
        <li><a href="#">交易软件下载</a></li>
        <li><a href="#">行情软件下载</a></li>
        <!--<li><a href="#">模拟软件下载</a></li>-->

        </ul>
        <ul class="loadscan">
        <li class="padl"><img class="weix" src="<%=request.getContextPath() %>/res/images/weix_iphone.png" /><img class="indro" src="<%=request.getContextPath() %>/res/images/iphone_b.png" /></li>
        <li><img class="weix" src="<%=request.getContextPath() %>/res/images/weix_Android.png" /><img class="indro" src="<%=request.getContextPath() %>/res/images/Android_b.png" /></li>
        </ul>
        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        <!--下载页面弹窗结束-->

        <!--产品介绍弹窗开始-->
        <div class="bg product-box" style="display: none;">
        <div class="product position-middle">
        <div class="position-con product-con">
        <div class="header"><img src="<%=request.getContextPath() %>/res/images/banner_product.png"/></div>
        <!--S-table开始-->
        <div id="tab">
        <div class="tabList product-tab">
        <ul class="clearfix">
        <li class="cur">直播室简介</li>
        <li>交易明细</li>
        <li>产品对比</li>
        <li>产品优势</li>
        <li>资金保障</li>
        <li>盈利模式</li>
        </ul>
        </div>
        <div class="tabCon">
        <div class="cur"><img src="<%=request.getContextPath() %>/res/images/jianjie.jpg" /></div>
        <div><img src="<%=request.getContextPath() %>/res/images/mingx_01.png" /><img src="<%=request.getContextPath() %>/res/images/mingx_02.png" /><img src="<%=request.getContextPath() %>/res/images/mingx_03.png" /></div>
        <div><img src="<%=request.getContextPath() %>/res/images/cpdb.jpg" /></div>
        <div><img src="<%=request.getContextPath() %>/res/images/cpys.png" /></div>
        <div style="margin-top:20px;"><img src="<%=request.getContextPath() %>/res/images/zjbz.png" /></div>
        <div style="margin-top:20px;"><img src="<%=request.getContextPath() %>/res/images/ylms.png" /></div>
        </div>
        </div>
        <!--E--table结束-->
        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        <!--产品介绍弹窗结束-->

        <!--实盘跟单开始-->
        <div class="bg spgd-box" style="display: none;">
        <div class="spgd position-middle">
        <div class="spgd-con">
        <!--开始-->
        <div class="header_gend"><img src="<%=request.getContextPath() %>/res/images/banner_shipangd.png" /></div>
        <!--S-滚动新闻-->
        <div class="spgd-scroll-box">
        <div id="demo5" class="qimo8">
        <div class="qimo">
        <div id="demo11">
        <ul>
        <li><a href="#"><span>16-04-01 21:19</span>&nbsp;<i>李女士</i><span>出卖[T]现货白银</span>&nbsp;<b>3110</b></a></li>
        <li><a href="#"><span>16-04-01 21:19</span>&nbsp;<i>李女士</i><span>出卖[T]现货白银</span>&nbsp;<b>3110</b></a></li>
        <li><a href="#"><span>16-04-01 21:19</span>&nbsp;<i>李女士</i><span>出卖[T]现货白银</span>&nbsp;<b>3110</b></a></li>
        <li><a href="#"><span>16-04-01 21:19</span>&nbsp;<i>李女士</i><span>出卖[T]现货白银</span>&nbsp;<b>3110</b></a></li>
        <li><a href="#"><span>16-04-01 21:19</span>&nbsp;<i>李女士</i><span>出卖[T]现货白银</span>&nbsp;<b>3110</b></a></li>
        <li><a href="#"><span>16-04-01 21:19</span>&nbsp;<i>李女士</i><span>出卖[T]现货白银</span>&nbsp;<b>3110</b></a></li>
        <li><a href="#"><span>16-04-01 21:19</span>&nbsp;<i>李女士</i><span>出卖[T]现货白银</span>&nbsp;<b>3110</b></a></li>
        <li><a href="#"><span>16-04-01 21:19</span>&nbsp;<i>李女士</i><span>出卖[T]现货白银</span>&nbsp;<b>3110</b></a></li>
        </ul>
        </div>
        <div id="demo22"></div>
        </div>
        </div>
        </div>
        <!--E-滚动新闻-->
        <!--二级页面开始-->
        <div class="level-2">
        <div class="level-list clearfix">
        <p>排行榜</p>
        <ul class="level-list-title">
        <li class="active"><a href="#">收益之王</a></li>
        <li><a href="#">人气操盘手</a></li>
        <li><a href="#">精彩大单</a></li>
        </ul>
        </div>
        <div class="level-list-con">
        <div class="list-cons">
        <ul class="list-cons-tab clearfix">
        <li class="active li-w180"><a href="#">名次排序</a></li>
        <li class="li-w123"><a href="#">热门品种</a></li>
        <li class="li-w141"><a href="#">月收益率</a></li>
        <li class="li-w132"><a href="#">正确率</a></li>
        <li class="li-w150"><a href="#">月盈利点数</a></li>
        <li class="li-w133"><a href="#">跟单人数</a></li>
        <li class="li-w140"><a href="#">查看明细</a></li>
        </ul>
        <div class="rm-cons">
        <div class="rm-cons1 clearfix">
        <div class="li-w180 fl">
        <a href="#" class="fl">
        <i class="level-icon1 level-icon"></i>
        <span class="head-portrait"></span>
        </a>
        <div class="name-info">
        <p class="font-s16"><a href="#">白先生</a></p>
        <p>中国</p>
        <p>长期投资</p>
        </div>
        </div>
        <div class="fl">
        <div class="fl li-w123">
        <img src="<%=request.getContextPath() %>/res/images/lq-icon.png" class="lq-icon">
        <p class="font-s18">沥青</p>
        </div>
        <div class="fl li-w141">
        <p class="font-s22 cff0101">
        <span>95.53%</span>
        </p>
        </div>
        <div class="fl li-w132">
        <p>
        <span style="margin-top: 10px;display: inline-block;"><img src="<%=request.getContextPath() %>/res/images/progress-circle.png"></span>
        </p>
        </div>
        <div class="fl li-w150">
        <p class="font-s22 ">
        19257<span class="font-s21">点</span>
        </p>
        </div>
        <div class="fl li-w133">
        <p class="font-s22 ">
        <i class="rs-icon"></i><span>249</span>
        </p>
        </div>
        <div class="fl li-w140">
        <p>
        <a href="#" class="view-btn">查看单子</a>
        </p>
        </div>
        </div>
        </div>
        <div class="rm-cons1 clearfix">
        <div class="li-w180 fl">
        <a href="#" class="fl">
        <i class="level-icon2 level-icon"></i>
        <span class="head-portrait"></span>
        </a>
        <div class="name-info">
        <p class="font-s16"><a href="#">白先生</a></p>
        <p>中国</p>
        <p>长期投资</p>
        </div>
        </div>
        <div class="fl">
        <div class="fl li-w123">
        <img src="<%=request.getContextPath() %>/res/images/lq-icon.png" class="lq-icon">
        <p class="font-s18">沥青</p>
        </div>
        <div class="fl li-w141">
        <p class="font-s22 cff0101">
        <span>95.53%</span>
        </p>
        </div>
        <div class="fl li-w132">
        <p>
        <span style="margin-top: 10px;display: inline-block;"><img src="<%=request.getContextPath() %>/res/images/progress-circle.png"></span>
        </p>
        </div>
        <div class="fl li-w150">
        <p class="font-s22 ">
        19257<span class="font-s21">点</span>
        </p>
        </div>
        <div class="fl li-w133">
        <p class="font-s22 ">
        <i class="rs-icon"></i><span>249</span>
        </p>
        </div>
        <div class="fl li-w140">
        <p>
        <a href="#" class="view-btn">查看单子</a>
        </p>
        </div>
        </div>
        </div>
        <div class="rm-cons1 clearfix">
        <div class="li-w180 fl">
        <a href="#" class="fl">
        <i class="level-icon3 level-icon"></i>
        <span class="head-portrait"></span>
        </a>
        <div class="name-info">
        <p class="font-s16"><a href="#">白先生</a></p>
        <p>中国</p>
        <p>长期投资</p>
        </div>
        </div>
        <div class="fl">
        <div class="fl li-w123">
        <img src="<%=request.getContextPath() %>/res/images/lq-icon.png" class="lq-icon">
        <p class="font-s18">沥青</p>
        </div>
        <div class="fl li-w141">
        <p class="font-s22 cff0101">
        <span>95.53%</span>
        </p>
        </div>
        <div class="fl li-w132">
        <p>
        <span style="margin-top: 10px;display: inline-block;"><img src="<%=request.getContextPath() %>/res/images/progress-circle.png"></span>
        </p>
        </div>
        <div class="fl li-w150">
        <p class="font-s22 ">
        19257<span class="font-s21">点</span>
        </p>
        </div>
        <div class="fl li-w133">
        <p class="font-s22 ">
        <i class="rs-icon"></i><span>249</span>
        </p>
        </div>
        <div class="fl li-w140">
        <p>
        <a href="#" class="view-btn">查看单子</a>
        </p>
        </div>
        </div>
        </div>
        <div class="rm-cons1 clearfix">
        <div class="li-w180 fl">
        <a href="#" class="fl">
        <i class="level-icon4 level-icon">4</i>
        <span class="head-portrait"></span>
        </a>
        <div class="name-info">
        <p class="font-s16"><a href="#">白先生</a></p>
        <p>中国</p>
        <p>长期投资</p>
        </div>
        </div>
        <div class="fl">
        <div class="fl li-w123">
        <img src="<%=request.getContextPath() %>/res/images/lq-icon.png" class="lq-icon">
        <p class="font-s18">沥青</p>
        </div>
        <div class="fl li-w141">
        <p class="font-s22 cff0101">
        <span>95.53%</span>
        </p>
        </div>
        <div class="fl li-w132">
        <p>
        <span style="margin-top: 10px;display: inline-block;"><img src="<%=request.getContextPath() %>/res/images/progress-circle.png"></span>
        </p>
        </div>
        <div class="fl li-w150">
        <p class="font-s22 ">
        19257<span class="font-s21">点</span>
        </p>
        </div>
        <div class="fl li-w133">
        <p class="font-s22 ">
        <i class="rs-icon"></i><span>249</span>
        </p>
        </div>
        <div class="fl li-w140">
        <p>
        <a href="#" class="view-btn">查看单子</a>
        </p>
        </div>
        </div>
        </div>
        <div class="rm-cons1 clearfix">
        <div class="li-w180 fl">
        <a href="#" class="fl">
        <i class="level-icon4 level-icon">5</i>
        <span class="head-portrait"></span>
        </a>
        <div class="name-info">
        <p class="font-s16"><a href="#">白先生</a></p>
        <p>中国</p>
        <p>长期投资</p>
        </div>
        </div>
        <div class="fl">
        <div class="fl li-w123">
        <img src="<%=request.getContextPath() %>/res/images/lq-icon.png" class="lq-icon">
        <p class="font-s18">沥青</p>
        </div>
        <div class="fl li-w141">
        <p class="font-s22 cff0101">
        <span>95.53%</span>
        </p>
        </div>
        <div class="fl li-w132">
        <p>
        <span style="margin-top: 10px;display: inline-block;"><img src="<%=request.getContextPath() %>/res/images/progress-circle.png"></span>
        </p>
        </div>
        <div class="fl li-w150">
        <p class="font-s22 ">
        19257<span class="font-s21">点</span>
        </p>
        </div>
        <div class="fl li-w133">
        <p class="font-s22 ">
        <i class="rs-icon"></i><span>249</span>
        </p>
        </div>
        <div class="fl li-w140">
        <p>
        <a href="#" class="view-btn">查看单子</a>
        </p>
        </div>
        </div>
        </div>
        </div>
        </div>
        </div>
        </div>
        <!--二级页面结束-->
        <!--三级页面开始-->
        <div class="level-3" style="display: none">
        <!--S-个人交易信息-->
        <div class="per_message">
        <div class="per_mestop">
        <span class="per_mesname">
        <span class="pic"></span>
        <div class="detial">
        <h1>白先生</h1>
        <p>总交易日：95天</p>
        <p>加入时间：2016-01-06</p>
        </div>
        </span>
        <div class="per_mesgend">
        <ul class="per_mesgendtext">
        <li><i>100%</i><b>准确率</b></li>
        <li><i>95.53%</i><b>月收益率</b></li>
        <li class="nobr"><i>250</i><b>跟单人数</b></li>
        </ul>
        <a class="per_mesgendlink" href="#">我要跟单</a>
        </div>
        </div>
        <ul class="per_mescenter">
        <li>总收益</li><li>准确率</li><li>日均交易</li><li>平均持仓周期</li><li>平均每单收益率</li><li class="tcol">最新下单</li>
        </ul>
        <ul class="per_mesnum">
        <li>95.53</li><li>100</li><li>13.83笔</li><li>0天</li><li>0.17%</li><li><p>沥青&nbsp;:&nbsp;4.00<i>买入</i></p><p>成交价：&nbsp;<b>40.86建仓</b></p></li>
        </ul>
        </div>
        <!--E-个人交易信息-->
        <!--S-table标签-->
        <div class="tab">
        <div class="tabList">
        <ul>
        <li>账单详情</li>
        <li class="libgc">历史交易记录</li>
        <li>当前持仓</li>
        </ul>
        </div>
        <div class="tabCon">
        <div>
        <ol>
        <li>交易品种</li><li>多/空</li><li>开仓时间</li><li>平仓消费</li><li>开仓价格</li><li>平仓价格</li><li>盈亏（元）</li>
        </ol>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <ul>
        <li>美国原油</li><li><i>空</i></li><li>03-10&nbsp;13:21:11</li><li>03-10&nbsp;22:49:33</li><li>40.39</li><li>36.67</li><li><b>1440.00</b></li>
        </ul>
        <div class="page">
        <a href="#">690条</a><a href="#">上一页</a><span class="clobg">1</span><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a><p>...</p><a href="#">173条</a><a href="#">下一页</a>
        </div>
        </div>
        <div><!--交易记录内容--></div>
        </div>

        </div>
        <!--E-table标签-->
        <!--结束-->
        </div>
        <!--三级页面结束-->

        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        <!--实盘跟单结束-->

        <!--磊丹观点开始-->
        <div class="bg point-box" style="display: none;">
        <div class="point position-middle">
        <div class="point-con">
        <!--开始-->
        <div class="banner_view"><img src="<%=request.getContextPath() %>/res/images/banner_view.png" /></div>
        <div class="contain_main">
        <div id="viewPointContent" class="content"></div>
        <div class="pager"></div>
        </div>
        <!--结束-->
        <a href="#" class="close"></a>
        </div>
        </div>
        </div>
        <!--磊丹观点结束-->

        <!--VIP服务弹窗开始-->
        <div class="bg serve-box" style="display: none;">
        <div class="serve position-middle">
        <div class="serve-con">
        <!--开始-->
        <div class="item_box box10">
        <div class="item_box_wp">
        <div class="voice_2">
        <h1>VIP服务阶梯</h1>
        <p>精心专注，倾心尽心，全方位立体式服务体系</p>
        <ul class="clearfix">
        <li class="li0" id="li0" style="width: 124px;">
        <div class="fold" style="display: block;">
        <span class="img"></span>
        <span class="txt">游客</span>
        </div>
        <div class="unfold" style="display: none;">
        <dl>
        <dt>
        <span class="img"></span>
        <span class="txt">游客</span>
        </dt>
        <dd>
        <p><span></span>查看行情</p>
        <p style="margin-right:0;"><span></span>实时解盘</p>
        <p><span></span>精选资讯</p>
        <p style="margin-right:0;"><span></span>财经日历</p>
        <p><span></span>在线开户</p>
        </dd>
        <dd class="tips"></dd>
        <a target="_blank" href="#">立即申请</a>
        </dl>
        </div>
        </li>
        <li class="li1" id="li1" style="width: 124px;">
        <div class="fold" style="display: block;">
        <span class="img"></span>
        <span class="txt">注册会员</span>
        </div>
        <div class="unfold" style="display: none;">
        <dl>
        <dt>
        <span class="img"></span>
        <span class="txt">注册会员</span>
        <p class="ruler">净入金≥10万</p>
        </dt>
        <dd>
        <p><span></span>实时解盘</p>
        <p style="margin-right:0;"><span></span>精选资讯</p>
        <p><span></span>财经日历</p>
        <p style="margin-right:0;"><span></span>直播大厅</p>
        <p><span></span>原油神器</p>
        <p style="margin-right:0;"><span></span>在线开户</p>
        </dd>
        <dd class="tips"></dd>
        <a target="_blank" href="#">立即申请</a>
        </dl>
        </div>
        </li>
        <li class="li3" style="width: 124px;">
        <div class="fold" style="display: block;">
        <span class="img"></span>
        <span class="txt">黄金会员</span>
        </div>
        <div class="unfold" style="display: none;">
        <dl>
        <dt>
        <span class="img"></span>
        <span class="txt">黄金会员</span>
        <p class="ruler">10≤净入金&lt;50万</p>
        </dt>
        <dd>
        <p><span></span>磊丹观点</p>
        <p style="margin-right:0;"><span></span>多空对决</p>
        <p><span></span>四线合并</p>
        <p style="margin-right:0;"><span></span>实盘交易</p>
        <p><span></span>交易内参</p>
        </dd>
        <dd class="tips">
        (均包含注册会员服务)
        </dd>
        <a target="_blank" href="#">立即申请</a>
        </dl>
        </div>
        </li>
        <li class="li4" style="width: 124px;">
        <div class="fold" style="display: block;">
        <span class="img"></span>
        <span class="txt">铂金会员</span>
        </div>
        <div class="unfold" style="display: none;">
        <dl>
        <dt>
        <span class="img"></span>
        <span class="txt">铂金会员</span>
        <p class="ruler">50≤净入金&lt;200万</p>
        </dt>
        <dd>
        <p><span></span>热点专题</p>
        <%--<p style="margin-right:0;"><span></span>交易内参</p>--%>
        <p style="margin-right:0;"><span></span>机器人喊单</p>
        </dd>
        <dd class="tips">
        (均包含黄金会员服务)
        </dd>
        <a target="_blank" href="#">立即申请</a>
        </dl>
        </div> </li>
        <li class="li6" style="width: 124px;">
        <div class="fold" style="display: block;">
        <span class="img"></span>
        <span class="txt">钻石会员</span>
        </div>
        <div class="unfold" style="display: none;">
        <dl>
        <dt>
        <span class="img"></span>
        <span class="txt">钻石会员</span>
        <p class="ruler">200≤净入金&lt;500万</p>
        </dt>
        <dd>
        <p><span></span>账户诊断</p>
        <p style="margin-right:0;"><span></span>VIP 1vs1</p>
        <p><span></span>金河锁链</p>
        </dd>
        <dd class="tips">
        (均包含铂金会员服务)
        </dd>
        <a target="_blank" href="#">立即申请</a>
        </dl>
        </div> </li>
        <li class="li7" style="width: 442px;">
        <div class="fold" style="display: none;">
        <span class="img"></span>
        <span class="txt">至尊会员</span>
        </div>
        <div class="unfold" style="display: block;">
        <dl>
        <dt>
        <span class="img"></span>
        <span class="txt">至尊会员</span>
        <p class="ruler">500万以上</p>
        </dt>
        <dd>
        <p><span></span>理财A+B</p>
        <p style="margin-right:0;"><span></span>短线喊单</p>
        </dd>
        <dd class="tips">
        (均包含钻石会员服务)
        </dd>
        <a target="_blank" href="#">立即申请</a>
        </dl>
        </div> </li>
        </ul>
        </div>
        </div>
        </div>
        <!--结束-->
        <a href="#" class="close"></a>
        </div>
        </div>
        </div>
        <!--VIP服务弹窗结束-->

        <!--多空对决弹窗开始-->
        <div class="bg duel-box" style="display: none">
        <div class="duel position-middle">
        <div class="duel-con">
        <!--开始-->
        <div class="banner_duij"><img src="<%=request.getContextPath() %>/res/images/banner_dkdj.png" /></div>
        <!--S--交易品种-->
        <div class="change_main">
        <span class="change_main-title"><h1>交易品种</h1><div class="type"><a class="abg" href="#">银锭</a><a href="#">电解铜</a><a href="#">沥青</a></div></span>
        <ul class="chane_main-chic">
        <li class="fsy">
        <h1>非商业持仓</h1>
        <div class="fsy_main borl">
        <!--扇形图开始-->
        <div style="float: left;">
        <canvas id="canvas01" height="106" width="106"></canvas>
        </div>
        <!--扇形图结束-->
        <ol class="text">
        <li><i class="hongs">多方持仓58.91%</i><b>与上周数据相比 -1.37%</b></li>
        <li><i class="lvs">空方持仓41.09%</i><b>与上周数据相比 1.37%</b></li>
        </ol>
        </div>
        </li>
        <li class="fsy">
        <h1>商业持仓</h1>
        <div class="sy_main borl">
        <!--扇形图开始-->
        <div style="float: left;">
        <canvas id="canvas02" height="106" width="106"></canvas>
        </div>
        <!--扇形图结束-->
        <ol class="text">
        <li><i class="hongs">多方持仓38.87%</i><b>与上周数据相比 0.54%</b></li>
        <li><i class="lvs">空方持仓61.13%</i><b>与上周数据相比-0.54%</b></li>
        </ol>
        </div>
        </li>
        <li class="fsy">
        <h1>总持仓</h1>
        <div class="zcc_main">
        <!--扇形图开始-->
        <div style="float: left;">
        <canvas id="canvas03" height="106" width="106"></canvas>
        </div>
        <!--扇形图结束-->
        <ol class="text">
        <li><i class="hongs">多方持仓48.42%</i><b>与上周数据相比-0.22%</b></li>
        <li><i class="lvs">空方持仓51.58%</i><b>与上周数据相比 0.22%</b></li>
        </ol>
        </div>
        </li>
        </ul>
        </div>
        <!--E--交易品种-->
        <!--S--多方观点-->
        <div class="views">
        <div class="view_list">
        <div class="view_list_left left_bg_red">
        <p class="view_list_tex">多方阵营</p>
        <p class="view_list_num">88</p>
        </div>
        <ol class="view_list-right">
        <li class="top"><h2>多方观点</h2><p>共有<i>7</i>条观点</p></li>
        <li class="top"  onclick='enterWeiboRoom()' ><p>进入房间</p></li>
        <li class="main">
        <div class="photo"></div>
        <div class="detailed">
        <p>姓名<i>高级分析师</i></p>
        <b>现货沥青：3180多，止盈3210，3230，止损3150</b>
        </div>
        </li>
        <li class="time">2016年04月11日&nbsp;10:04:47</li>
        </ol>
        </div>
        <div class="view_list borlv">
        <div class="view_list_left left_bg_green">
        <p class="view_list_tex">空方阵营</p>
        <p class="view_list_num">22</p>
        </div>
        <ol class="view_list-right">
        <li class="top"><h2>空方观点</h2><p>共有<i>7</i>条观点</p></li>
        <li class="main">
        <div class="photo"></div>
        <div class="detailed">
        <p>姓名<i>高级分析师</i></p>
        <b>现货沥青：3180多，止盈3210，3230，止损3150</b>
        </div>
        </li>
        <li class="time">2016年04月11日&nbsp;10:04:47</li>
        </ol>
        </div>
        </div>
        <!--E--多方观点-->
        <!--结束-->
        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        <!--多空对决弹窗结束-->

        <!--交易内参弹窗开始-->
        <div class="bg trans-box" style="display: none;">
        <div class="trans position-middle">
        <div class="trans-con">
        <div class="jync-banner"><img src="<%=request.getContextPath() %>/res/images/jync-top-img.jpg"></div>
        <div class="jync-box">
        <ul class="jync-tab clearfix">
        <li class="active"><a href="#">金属内参</a></li>
        <li><a href="#">原油内参</a></li>
        </ul>
        <div class="jync-count">
        <div class="jync-con">
        <div class="metal-con clearfix"></div>
        <div class="metal-page"></div>
        </div>
        <div class="jync-con" style="display: none;">
        <div class="oil-con clearfix"></div>
        <div class="oil-page"></div>
        </div>
        </div>
        </div>
        <a href="#" class="close"></a>
        </div>
        </div>
        </div>
        <!--交易内参弹窗结束-->

        <%--财经日历弹窗开始--%>
        <!-- <div class="bg dailyfx-box">
        <div class="dailyfx position-middle">
        <div class="dailyfx-con">
        <%--<iframe  src="http://www.leidan18.com/market-calendar.html" width=100% height=100% name="_b_b" scrolling="auto" frameborder="0" />--%>
        <iframe id="iframe"  height="3500px"  width="100%"  frameborder="0"  scrolling="no"  src="http://rili.jin10.com/open.php"></iframe>
        <a href="#" class="close"></a>
        </div>
        </div>
        </div>-->
        <%--财经日历弹窗结束--%>


        <!--技术课程弹窗开始-->
        <div class="bg t-course-wrap" style="display:none;">
        <div class="dailyfx position-middle">
        <div class="dailyfx-con">
        <!--S--网站主要内容-->
        <div class="course">
        <h2>初级教程</h2>
        <div class="courseBox">
        <a href="<%=request.getContextPath() %>/course-tv/course1_01.html" target="_blank" class="course-detial">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic01.png" /></span>
        <h3>1.什么是贵金属及国内内要的贵金属投资品种</h3>
        <p><b>00:01:05分钟</b><i>751人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        <a href="<%=request.getContextPath() %>/course-tv/course1_02.html" target="_blank" class="course-detial">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic01.png" /></span>
        <h3>2.贵金属现货投资特点</h3>
        <p><b>00:01:10分钟</b><i>453人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        <a href="<%=request.getContextPath() %>/course-tv/course1_03.html" target="_blank" class="course-detial">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic01.png" /></span>
        <h3>3.影响贵金属价格的因素-美国经济指标</h3>
        <p><b>00:01:28分钟</b><i>695人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        <a href="<%=request.getContextPath() %>/course-tv/course1_04.html" target="_blank" class="course-detial nomarr">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic01.png" /></span>
        <h3>4.影响贵金属价格的因素-地缘政治和国内政经因素</h3>
        <p><b>00:01:13分钟</b><i>622人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        </div>
        </div>
        <div class="course">
        <h2>中级教程</h2>
        <div class="courseBox">
        <a   href="javascript:void(0)" onclick="openControl(this,'/course-tv/course2_01.html','中级')"    class="course-detial">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic2-01.png" /></span>
        <h3>交易心法课件</h3>
        <p><b>23:18分钟</b><i>610人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        <a    href="javascript:void(0)"  onclick="openControl(this,'/course-tv/course2_02.html','中级')"   class="course-detial">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic2-02.png" /></span>
        <h3>五维观市场课件</h3>
        <p><b>30分钟</b><i>479人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>

        <a   href="javascript:void(0)"  onclick="openControl(this,'/course-tv/course2_05.html','中级')"     class="course-detial">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic2-05.png" /></span>
        <h3>时空共振战法课件</h3>
        <p><b>24:20分钟</b><i>736人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        <a   href="javascript:void(0)"  onclick="openControl(this,'/course-tv/course2_06.html','中级')"   class="course-detial">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic2-06.png" /></span>
        <h3>生命线课件</h3>
        <p><b>16:55分钟</b><i>59人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        </div>
        </div>
        <div class="course">
        <h2>高级教程</h2>
        <div class="courseBox">
        <a href="javascript:void(0)"  onclick="openControl(this,'/course-tv/course2_03.html','高级')"  class="course-detial">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic2-03.png" /></span>
        <h3>系统化交易课件</h3>
        <p><b>30分钟</b><i>479人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        <a href="javascript:void(0)"  onclick="openControl(this,'/course-tv/course2_04.html','高级')"  class="course-detial ">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic2-04.png" /></span>
        <h3>双振战法课件</h3>
        <p><b>30分钟</b><i>479人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        <a href="javascript:void(0)"  onclick="openControl(this,'/course-tv/course2_08.html','高级')"   class="course-detial ">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic2-08.png" /></span>
        <h3>半分位指标课件</h3>
        <p><b>30：32分钟</b><i>713人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        <a href="javascript:void(0)"  onclick="openControl(this,'/course-tv/course2_09.html','高级')"  class="course-detial ">
        <span><img src="<%=request.getContextPath() %>/res/images/course_pic2-09.png" /></span>
        <h3>仓位管理</h3>
        <p><b>21:16分钟</b><i>752人</i></p>
        <figcaption>
        <img src="<%=request.getContextPath() %>/res/images/icon_start.png" />
        </figcaption>
        </a>
        </div>
        </div>
        <!--E--网站主要内容-->
        <a href="#" class="close"></a>
        </div>
        </div>
        </div>
        <!--技术课程弹窗结束-->

        <!--老师风采弹窗开始-->
        <!--1v1弹窗开始-->
        <div class="add-view v1-box teachers-box bg" style="display:none">
        <div class="add-view-bg"></div>
        <div class="v1-con">
        <div class="v1-wrap">
        <div class="v1-layout">
        <img src="<%=request.getContextPath() %>/res/images/1v1-t01.jpg" width="100%" height="231">
        <img src="<%=request.getContextPath() %>/res/images/1v1-t02.jpg" width="100%" height="195">
        <div id="zzsc">
        <a class="pre"></a>
        <div id="wai_box">
        <div class="zzsc_box">
        <ul>
        <li><a href="#" class="images"><img src="<%=request.getContextPath() %>/res/images/1v1-con01.jpg" alt=""/></a></li>
        <li><a href="#" class="images"><img src="<%=request.getContextPath() %>/res/images/1v1-con02.jpg" alt=""/></a></li>
        </ul>
        <ul>
        <li><a href="#" class="images"><img src="<%=request.getContextPath() %>/res/images/1v1-con03.jpg" alt=""/></a></li>
        <li><a href="#" class="images"><img src="<%=request.getContextPath() %>/res/images/1v1-con04.jpg" alt=""/></a></li>
        </ul>
        <ul>
        <li><a href="#" class="images"><img src="<%=request.getContextPath() %>/res/images/1v1-con05.jpg" alt=""/></a></li>
        <li><a href="#" class="images"><img src="<%=request.getContextPath() %>/res/images/1v1-con06.jpg" alt=""/></a></li>
        </ul>
        </div>
        </div>
        <a class="next"></a>
        </div>
        <img src="<%=request.getContextPath() %>/res/images/1v1-b01.jpg" width="100%" height="321">
        <img src="<%=request.getContextPath() %>/res/images/1v1-b02.jpg" width="100%" height="279">
        <img src="<%=request.getContextPath() %>/res/images/1v1-b03.jpg" width="100%" height="177">
        </div>
        <a href="javascript:;" class="close-b"></a>
        </div>
        </div>
        </div>
        <!--1v1弹窗结束-->
        <!--老师风采弹窗开始-->

        <%--游戏弹窗开始--%>
        <div class="bg game-p-box" style="display: none;">
            <div class="game-p position-middle">
                <div class="game-p-con">
                    <div class="game-inner">
        <div class="silvergame" style="display:none">
        <section class="silvergame-b">
        <object type="application/x-shockwave-flash" data="<%=request.getContextPath() %>/res/images/silvergame.swf" width="990" height="490" class="f-dn">
        <param name="movie" value="<%=request.getContextPath() %>/res/images/silvergame.swf" />
        <param name="quality" value="high" />
        <param name="wmode" value="transparent" />
        <param name="loop" value="true" />
        <embed src="<%=request.getContextPath() %>/res/images/silvergame.swf" width="990" height="490" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent" />
        </object>
        </section>
        </div>
        <div class="gameBox">
        <section class="earnmoney">
        <object type="application/x-shockwave-flash" data="<%=request.getContextPath() %>/res/images/earnmoney.swf" width="528" height="203" class="f-dn">
        <param name="movie" value="<%=request.getContextPath() %>/res/images/earnmoney.swf" />
        <param name="quality" value="high" />
        <param name="wmode" value="transparent" />
        <param name="loop" value="true" />
        <embed src="<%=request.getContextPath() %>/res/images/earnmoney.swf" width="528" height="203" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent" />
        </object>
        </section>
        <!--<section class="u-btn-s" id="start">
        <object type="application/x-shockwave-flash" data="images/start.swf" width="250" height="90" class="f-dn">
        <param name="movie" value="images/start.swf" />
        <param name="quality" value="high" />
        <param name="wmode" value="transparent" />
        <param name="scale" value="exactfit" />
        <embed src="images/start.swf" width="250" height="90" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent" />
        </object>
        </section>-->
        <div class="u-btn-s" id="start">
        <img src="<%=request.getContextPath() %>/res/images/start.png">
        </div>
        <img class="cow" src="<%=request.getContextPath() %>/res/images/icon_cow.png">
        </div>

        </div>

                    <a href="#" class="close"></a>
                </div>
            </div>
        </div>


        <%--游戏弹窗结束--%>



        <!--弹窗结束-->
        <script src="<%=request.getContextPath() %>/res/js/front/marquee.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/res/js/front/Chart.js"></script>
        <script type="text/javascript">
        var writeMsg = document.getElementById("writeMsg1");
        //    弹窗中的扇形图开始
        var pieData01 = [
        {
        value: 56,
        color:"#f95645"
        },
        {
        value : 44,
        color : "#4dcb8e"
        }
        ];
        var pieData02 = [
        {
        value: 80,
        color:"#f95645"
        },
        {
        value : 20,
        color : "#4dcb8e"
        }
        ];
        var pieData03 = [
        {
        value: 25,
        color:"#f95645"
        },
        {
        value : 75,
        color : "#4dcb8e"
        }
        ];
        var myPie01 = new Chart(document.getElementById("canvas01").getContext("2d")).Pie(pieData01);
        var myPie02 = new Chart(document.getElementById("canvas02").getContext("2d")).Pie(pieData02);
        var myPie03 = new Chart(document.getElementById("canvas03").getContext("2d")).Pie(pieData03);
        //弹窗中的扇形图结束
        $(".login").click(function(){
        $(".login-wrap").show();
        });
        $(".teachers-item").click(function(){
        $(".teachers-box").show();
        });
        $(".close-b").click(function(){
        $(".teachers-box").hide();
        });
        <%--vip服务--%>
        $(function(){
        $(".voice_2 ul li").each(function(){
        var fold = $(this).find(".fold");
        var unfold = $(this).find(".unfold");
        if(fold.is(":hidden")){
        $(this).width(442);
        }else{
        $(this).width(124);
        }
        });
        $(".voice_2 ul li").mouseover(function(){
        var li_index = $(this).index();
        $(this).animate({width:442},0);
        $(this).find(".unfold").show();
        $(this).find(".fold").hide();
        $(this).siblings().animate({width:124},0);
        $(this).siblings().find(".unfold").hide();
        $(this).siblings().find(".fold").show();
        })
        });
        <%--技术课程--%>
        $(".t-course-item").click(function(){
        $(".t-course-wrap").show();
        });
        <%--游戏--%>
        $(".game-box").click(function(){
            $(".game-p-box").show();
        });

        function mystar(){
        $('#start').animate({width:'258px',height:'92px'},200).animate({width:'250px',height:'90px'},200);
        };
        $(function(){
        setInterval("mystar()",200)
        });
        $('#start').click(function(){
        $('.silvergame').css('display','block');
        })
        </script>
        </body>
        </html>