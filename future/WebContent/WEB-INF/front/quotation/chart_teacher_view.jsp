<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.net.InetAddress"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="UTF-8">
	<title>聊天界面</title>
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/res/front/css/index.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/res/js/plugins/jquery-easyui-1.4.5/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/res/js/plugins/jquery-easyui-1.4.5/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/res/front/css/chart.css">
	<script type="text/javascript" src="<%=contextPath %>/res/js/front/jquery-1.11.3.js"></script>
	<script type="text/javascript">
		var time = <%=System.currentTimeMillis()%>;
		var userId = ${customer.userId};
		var contextPath = '<%=contextPath%>';
	</script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/base.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/plugins/DateUtil.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/front/quotation_room/teacher_view.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/front/quotation_room/quotation_teacher_actual.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/front/quotation_room/quotation_teacher_interaction.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/front/quotation_room/customer_add_view.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/ajaxFileUpload.js"></script>    
	<script type="text/javascript" src="<%=contextPath %>/res/js/front/zzsc.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/front/util/chat.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/plugins/currentTime.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/front/viewpoint/viewpoint.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/res/js/plugins/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
</head>
<body>
<input type="hidden" name="msgId" id="msgId"/>
<div class="chart-warp">
    <div class="nav-chart clearfix">
        <div class="nav-section01 clearfix">
            <div class=" nav-logo fl">
                <a href="#" class="nav-logo-img"><img src="<%=contextPath %>/res/images/logo.jpg"></a>
                <a href="javascript:;" class="back-btn">返回</a>
            </div>
            <div class="fl teach-box">
                    <span class="teach-tiele">老师</span><i class="arrow-icon-b arrow-off"></i>
                    <ul class="teach-item y-teach">
                        <i class="arrow-icon-y"></i>
                        <li><a href="#"><i class="teacher-head-img teacher-head01"></i>希文老师11:00~12:00</a></li>
                        <li><a href="#"><i class="teacher-head-img teacher-head02"></i>长风老师11:00~12:00</a></li>
                        <li><a href="#"><i class="teacher-head-img teacher-head03"></i>凌青老师11:00~12:00</a></li>
                        <li><a href="#"><i class="teacher-head-img teacher-head04"></i>晋鹏老师11:00~12:00</a></li>
                        <p style="color: red;">以上是众赢大户室老师直播时间</p>
                    </ul>
            </div>
            <div class=" act fl">
<!--                 <a href="javascript:;" class="act-title">活动</a> -->
                <a href="#" class="v1"><img src="<%=contextPath %>/res/images/1v1.jpg"></a>
            </div>
        </div>
        <div class="nav-section02">
            <div class="nav-right">
                <p class="my-box">
                	<span class="port-head tport-head">
                		<c:if test="${roleId ne '5' }">
                			<c:if test="${customer.CFileId == null }">
		                		<img src="<%=contextPath %>/res/images/default.png" width="45" height="40">
                			</c:if>
                			<c:if test="${customer.CFileId != null }">
		                		<img src="<%=contextPath %>/download/file/show.htm?id=${customer.CFileId }" width="45" height="40">
                			</c:if>
                		</c:if>
                		<c:if test="${roleId eq '5' }">
                			<img src="<%=contextPath %>/res/images/level${customer.levelId }.png" width="45" height="40">
                		</c:if>
                	</span>
                	<em class="my-name tmy-name">${customer.name }</em>
<!--                 	<i class="arrow-icon-b arrow-off"></i> -->
                </p>
<!--                 <ul class="teach-item my-teach"> -->
<!--                     <i class="arrow-icon-y"></i> -->
<!--                     <li><a href="#"><i class="p-ce"></i>个人中心</a></li> -->
<!--                     <li><a href="#"><i class="p-info"></i>消息</a></li> -->
<!--                     <li><a href="#"><i class="p-edit"></i>退出</a></li> -->
<!--                 </ul> -->
            </div>
        </div>
    </div>
    <div class="chart-con">
        <div class="chart-live clearfix">
             <div class="chart-hide">
<!--                 <p class="c-bg"> -->
<!--                     <a href="javascript:;">个人中心</a> -->
<!--                 </p> -->
<!--                 <p class="c-bg"><a href="javascript:;">专题房间</a></p> -->
                <ul class="clearfix c-bg">
                    <h1><i class="c-se-icon"></i><a href="javascript:;">磊丹服务</a></h1>
                    <li class="point-item"><a href="javascript:;">磊丹观点</a></li>
                    <li class="trans-item"><a href="javascript:;">交易内参</a></li>
                    <li class="account-item"><a href="javascript:;">账户诊断</a></li>
<!--                     <li><a href="#">财经日历</a></li> -->
<!--                     <li><a href="#">一对一</a></li> -->
<!--                     <li><a href="#">软件下载</a></li> -->
<!--                     <li><a href="#">产品介绍</a></li> -->
                </ul>
            </div>
            <div class="chart-live-con">
                <span class="btn-hide">点击</span>
                <div class="type-sel">
                    <div class="sel-box clearfix">
                        <div class="fl  sel-a"><a href="javascript:;">类型筛选<i class="arrow-icon-b arrow-chose"></i></a></div>
                        <div class="fr voice-time">
<!--                             <span>声音提示<i class="voice-on"></i></span> -->
                            <span><i class="time"></i><span id="currentTime"></span></span>
                        </div>
                    </div>
                    <div class="data-chose clearfix">
                    	<form id="formData" onsubmit="return false;">
	                        <div class="fl data-type">
								数据类型：
	                            <label><input type="checkbox" name="tv.mineral" value="1"><span>油</span></label>
	                            <label><input type="checkbox" name="tv.mineral" value="2"><span>金属</span></label>
	                        </div>
	                        <div class="import">
								重要性：
	                            <label><input type="checkbox" name="tv.advice" value="1"><span>策略</span></label>
	                            <label><input type="checkbox" name="tv.advice" value="2"><span>操作建议</span></label>
	                            <button class="data-btn">确定</button>
	                        </div>
                        </form>
                    </div>
                </div>
                <div class="live-view">
                    <p class="live-tex" style="height: 2%;">观点直播
                        <!--老师界面添加按钮开始-->
                        <a href="javascript: popupAddview();" class="add-view-btn"><i class="add-view-icon"></i>添加观点</a>
                        <!--老师界面添加按钮结束-->
                    </p>

                    <div class="live-view-list">
                        <div class="ld-history">
                            <div class="history-date">
                                <ul id="msg_teacher_view">
                                 <c:forEach items="${teacherViewDtos}"    var="view">
                                 <li  id="teacherView_li${view.id}" class="green" style="display: list-item;">
                                            <dl>
                                                <dt class="time-stamp">
                                                	<fmt:formatDate value='${view.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
                                                </dt>
                                                <dd class="teach-name">
                                                    <c:if test="${view.teacherIconId == null }">
								                		<img src="<%=contextPath %>/res/images/default.png" width="45" height="40">
						                			</c:if>
						                			<c:if test="${view.teacherIconId != null }">
								                		<img src="<%=contextPath %>/download/file/show.htm?id=${view.teacherIconId }" width="45" height="40">
						                			</c:if>
						                			${view.teacherName}老师
                                                </dd>
                                               <dd     class="teach-tex">${view.viewContent}<a  style="display: none"  href="#" class="ask-a">向老师提问</a></dd>
                                                <dd class="teach-img">
                                                    <c:choose>
                                                       <c:when test="${view.cfileId!=null}">  
                                                                <a href="javascript:lookBigImg(${view.cfileId});" class="a-teach-img">
														   			  <img width="229" height="145"   src="<%=contextPath %>/download/file/show.htm?id=${view.cfileId}">
                                                                 </a> 
														   				</c:when>
														    </c:choose>
                                                    <span>
                                                    <em>
                                                                 <c:choose>
														   			<c:when test="${view.mineralId==1}">    
														   			           油
														   				</c:when>
														      <c:otherwise>
														                     金属
														      </c:otherwise>
														        </c:choose>
														    </em>
														       <c:choose>
														   			<c:when test="${view.adviceId==1}">    
														   			           策略
														   				</c:when>
														        <c:otherwise>
														                     操作建议
														      </c:otherwise>
														        </c:choose>
                                                     </span>
                                                </dd>
                                            </dl>
                                            <c:if test="${view.teacherId==customer.userId}">
                                            	<a class="del-li"    href="javascript:deleteTeacherView(${ view.id});"    ><i class="del-i"></i>删除</a>
                                            </c:if>
                                        </li> 
                            </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="chart-list">
            <div class="sp-notice clearfix">
             <div class="fl">
                 <c:choose>
							<c:when test="${rcd.cfileId!=null}">    
									 <img src="<%=contextPath%>/download/file/show.htm?id=${rcd.cfileId}" width="100%" >
										</c:when>
											<c:otherwise>
											<img src="<%=contextPath %>/res/images/sp-notice-img.jpg" width="100%" >
								      </c:otherwise>
				</c:choose>
                </div>
                <p class="fl p-w">
                         <c:choose>
							<c:when test="${rcd.name!=null}">    
									 ${rcd.name}
										</c:when>
											<c:otherwise>
											  【重点公告】 下周1V1在麦时为20:00——23:00 直播间麦时为23:00—2:00，
										                白天不定时在线，有做单机会及建议随时在1V1里发布，大家如果有问题可留
										                言。
								     </c:otherwise>
				       </c:choose>
                </p>
            
               <%--  <div class="fl"><img src="<%=contextPath %>/res/images/sp-notice-img.jpg" width="100%" ></div>
                <p class="fl p-w">【重点公告】 下周1V1在麦时为20:00——23:00 直播间麦时为23:00—2:00，
                白天不定时在线，有做单机会及建议随时在1V1里发布，大家如果有问题可留
                言。</p> --%>
                
            </div>
            <!--老师看到的聊天界面开始-->
            <div class="sp-chart" style=" height: 62%;overflow-y: auto;">
                <ul class="sp-title clearfix">
                
                    <li  id="actual_li"   class="active"><a href="javascript:;"><em  id="actual_count_id"  class="c-tip">0</em>聊天</a></li>
                     <li id="interaction_li" >
                     		<c:if test="${customerCount == 0}">
		                     	<a id="noCustomer" href="javascript:;">暂无客户参与</a>
		                     	<a id="hasCustomer" href="javascript:;" style="display:none;">
	                     			<span id="customerCount">${customerCount }</span>人参与
	                     			,<span id="unReplyCount">${count }</span>条提问未回复
	                   			</a>
                     		</c:if>
                     		<c:if test="${customerCount != 0}">
	                     		<a id="noCustomer" href="javascript:;" style="display:none;">暂无客户参与</a>
		                     	<a id="hasCustomer" href="javascript:;">
	                     			<span id="customerCount">${customerCount }</span>人参与
	                     			,<span id="unReplyCount">${count }</span>条提问未回复
	                   			</a>
                     		</c:if>
                     </li>
                </ul>
                <div class="sp-title-con">
                    <div class="sp-title-con01"  id="msg_actual_div">
                        <ul class="sp-com"  id="msg_actual_ul" >
                          
                        </ul>
                    </div>
                    <div class="sp-title-con01" style="display: none;"  id="msg_interaction_div">
                        <ul class="sp-com" id="interaction_view">
                        	<c:forEach var="messageDto" varStatus="status" items="${messageDtoList}">
                            	<li id="interaction_li_${messageDto.user.userId }_${messageDto.viewId}" class="clearfix chart-sep interaction_li_${messageDto.viewId}">
									<div class="chart-new clearfix">
										<span class="chat_arrow"></span>
										<div class="chart_pic" style="margin-right: 20px;">
											<c:if test="${messageDto.isCustomer }">
												<img src="<%=contextPath %>/res/images/level${messageDto.user.levelId }.png" width="48" height="20">
											</c:if>
											<c:if test="${!messageDto.isCustomer }">
												<c:if test="${messageDto.user.CFileId != null }">
													<img src="<%=contextPath %>/download/file/show.htm?id=${messageDto.user.CFileId }" width="45" height="40">
												</c:if>
												<c:if test="${messageDto.user.CFileId == null }">
													<img src="<%=contextPath %>/res/images/default.png" width="45" height="40">
												</c:if>
											</c:if>
										</div>
										<ol class="chart_m">
											<li class="tit">
												<em>${messageDto.user.name }</em>
												<b>对${messageDto.viewCreatorName }老师说</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<i><fmt:formatDate value="${messageDto.viewCreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </i>
											</li>
											<li class="chart_c">
												<c:if test="${messageDto.viewCreatorHeadId == null }">
													<img class="at_per" src="<%=contextPath %>/res/images/default.png" width="45" height="40">
												</c:if>
												<c:if test="${messageDto.viewCreatorHeadId != null}">
													<img class="at_per" src="<%=contextPath %>/download/file/show.htm?id=${messageDto.viewCreatorHeadId }" width="45" height="40">
												</c:if>
												<div class="main-c">
													<p class="pic_r">${messageDto.viewContent }</p>
													<div class="chart_tp">
														<div class="chart_tp-l">
															<c:if test="${messageDto.viewPicId != null }">
																<a href="javascript:lookBigImg(${messageDto.viewPicId });" class="a-teach-img">
																	<img src="<%=contextPath %>/download/file/show.htm?id=${messageDto.viewPicId }" width="140" height="88">
																</a>
															</c:if>
															<a class="link_j">
																<c:choose>
							   										<c:when test="${messageDto.mineralId==1}">油</c:when>
									      							<c:otherwise>金属</c:otherwise>
																</c:choose>
															</a>
															<a class="link_c">
																<c:choose>
								   									<c:when test="${messageDto.adviceId==1}">策略</c:when>
																	<c:otherwise>操作建议</c:otherwise>
										        				</c:choose>
															</a>
														</div>
													</div>
												</div>
											</li>
										</ol>
									</div>
									<div class="chat_container">
									<c:forEach var="question" varStatus="questionStatus" items="${messageDto.questionList }">
									<div class="chart_b">
										<div class="chart_pic">
											<c:if test="${messageDto.isCustomer }">
												<img src="<%=contextPath %>/res/images/level${messageDto.user.levelId }.png" width="48" height="20">
											</c:if>
											<c:if test="${!messageDto.isCustomer }">
												<c:if test="${messageDto.user.CFileId != null }">
													<img src="<%=contextPath %>/download/file/show.htm?id=${messageDto.user.CFileId }" width="45" height="40">
												</c:if>
												<c:if test="${messageDto.user.CFileId == null }">
													<img src="<%=contextPath %>/res/images/default.png" width="45" height="40">
												</c:if>
											</c:if>
										<p class="name">客户</p>
										<p class="name">${messageDto.user.name }</p>
										</div>
										<div class="qipao">
											<span class="chat_arrow"></span>
											<ol class="chart_m">
												<li class="chart_c">
													<p class="content">${question.content }</p>
													<p class="function">
														<i><fmt:formatDate value='${question.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></i>
														<c:if test="${questionStatus.last }">
															<a privateMessageId="${question.id}" href="javascript:;" class="ask-a">回复</a>
														</c:if>
													</p>
												</li>
											</ol>
										</div>
									</div>
									<c:if test="${question.replyList != null}">
										<c:forEach var="reply" items="${question.replyList }">
											<div class="chart-new">
												<span class="chat_arrow_g"></span>
												<div class="chart_pic1">
													<c:if test="${messageDto.viewCreatorHeadId == null}">
														<img class="at_per" src="<%=contextPath %>/res/images/default.png" width="45" height="40">
													</c:if>
													<c:if test="${messageDto.viewCreatorHeadId != null}">
														<img class="at_per" src="<%=contextPath %>/download/file/show.htm?id=${messageDto.viewCreatorHeadId}" width="45" height="40">
													</c:if>
													<p class="name">老师</p>
													<p class="name">${messageDto.viewCreatorName}</p>
												</div>
												<ol class="chart_m mrt">
													<li class="chart_g">
														<div class="main-c">
															<p class="content">${reply.content}</p>
															<p class="function">
																<i><fmt:formatDate value='${reply.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></i>
															</p>
														</div>
													</li>
												</ol>
											</div>
										</c:forEach>
									</c:if>
									</c:forEach>
									</div>
								</li>
                           </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <!--老师看到的聊天界面结束-->

            <div class="sp-speak"    id="actualSpeak_id">
                <div class="clearfix">
                   <!--  <span class="fl onlookers">围观（2条评论）</span> -->
                    <div class="fr">
                        <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes" class="q01"><img src="<%=contextPath %>/res/images/qqkf.png"></a>
                        <a href="tencent://message/?uin=800015047&amp;Site=www.leidan18.com&amp;Menu=yes"><img src="<%=contextPath %>/res/images/qqkf.png"></a>
                    </div>
                </div>
                <div class="clearfix send-way">
                    <p class="fl">
                        <a href="javascript:;" class="blue-bg"><i class="brow-tbn"></i>表情</a>
                        <a href="javascript:;" class="red-bg"><i class="str-btn"></i>彩条</a>
                        <a href="javascript:;" class="cls"><i class="cls-btn"></i>清屏</a>
                        <div class="emoji-field">
                                    <ul class="clearfix">
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.1.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.2.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.3.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.4.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.5.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.6.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.7.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.8.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/1.9.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/2.1.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/2.2.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/2.3.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/2.4.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/2.5.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/2.6.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/2.7.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/2.9.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.1.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.2.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.3.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.4.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.5.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.6.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.7.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.8.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/3.9.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.1.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.2.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.3.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.4.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.5.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.6.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.7.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.8.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/4.9.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.1.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.2.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.3.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.4.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.5.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.6.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.7.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.8.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/5.9.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.1.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.2.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.3.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.4.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.5.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.6.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.7.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.8.gif">
                                        </li>
                                        <li>
                                            <img src="<%=contextPath %>/res/images/emoji-filed/6.9.gif">
                                        </li>
                                    </ul>
                                </div>
                                <div class="emoji-caitiao">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.1.gif">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.2.gif" style="background:transparent;">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.3.gif">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.4.gif">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.5.gif" width="90">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.6.jpg" width="80">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.7.gif" width="80">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.8.gif" width="60">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/1.9.gif" width="60">
                                    <img src="<%=contextPath %>/res/images/emoji-caitiao/2.1.gif" width="140">
                                </div>
                    </p>
                    <span class="fr">每次最多发出150个字</span>
                </div>
                <div class="edit-box" >
                    <!-- <textarea   id="actual_ta" ></textarea> -->
                    <div contentEditable="true" id="actual_ta1" style="overflow-y:auto;overflow-x:hidden;word-break:break-all;word-wrap:break-word;"></div>
                    <a href="javascript:sendActual();">发言</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!--弹窗开始-->
    <!--star向老师提问弹窗-->
    <!--end向老师提问弹窗-->
    <!--star回复学生弹窗-->
    <div class="q-layout a-rep">
        <i class="close-q"></i>
        <p class="q-title"><i class="q-title-icon"></i>回复学生</p>
        <textarea class="text-a"    id="replyContent_ta"></textarea>
        <a href="javascript: teacherReply();" class="q-btn">回复</a>
    </div>
    <!--end回复学生弹窗-->
    <!--star添加观点-->
    <div class="add-view add-teach-view">
    
    <form id="newsForm" name="newsForm"  method="post" enctype="multipart/form-data"> 
        <div class="add-view-bg"></div>
        <div class="add-view-con">
            <i class="close-q"></i>
            <p class="q-title"><i class="q-title-icon"></i>添加观点</p>
            <!-- <textarea class="add-t">请在此处输入内容.....</textarea> -->
            <textarea class="add-t"   name="tv.viewContent" id="content"></textarea>
            <p class="q-title"><i class="q-title-icon"></i>图片</p>
            <p class="q-file">
             <a href="javascript:;" class="a-upload"  id="selectpic_a">
                    <input id="file" name="file"  type="file">选择图片...</a>
               <!-- <span class="q-file-name">选择图片</span> -->
              <input type="hidden" name="tv.cfileId"  id="cfileId"   ></input>

            </p>
            <p class="q-title"><i class="q-sort-icon"></i>类别
                <span class="chose01">
                
                <c:forEach items="${minerals}" varStatus="status" var="mineral">
					<label><input  type="radio" name="tv.mineralId" value="${mineral.id }"
					<c:if test="${status.first }">
					checked="checked"
					</c:if>
				   />${mineral.name}</label>
				</c:forEach>
                </span>
                <span class="chose02">
                           <c:forEach items="${adviceTypes}"   begin="0"  end="0"   var="advice">
                             <label><input   type="radio"    name="tv.adviceId" value="${advice.id }" checked="checked" />${advice.adviceName}</label>
                            </c:forEach>
							<c:forEach items="${adviceTypes}" begin ="1"  var="advice">
							<label><input type="radio" name="tv.adviceId" value="${advice.id }"/>${advice.adviceName}</label>
                            </c:forEach>
                </span>
            </p>
            <a href="javascript:;" class="q-btn cancel-btn">取消</a>
            <a href="javascript: commitQ();" class="q-btn">发表</a>
        </div>
        </form> 
        
    </div>
    <!--end添加观点-->
    <!--star点击图片效果-->
    <div class="img-b">
        <div class="add-view-bg"></div>
        <div class="img-c">
            <i class="img-clo" title="关闭"></i>
            <img id="dynamic_big_img"   src="<%=contextPath %>/res/images/chat-screen.jpg" width="100%" height="100%">
        </div>
    </div>
    <!--end点击图片效果-->
     <!--1v1弹窗开始-->
    <div class="add-view v1-box">
        <div class="add-view-bg"></div>
            <div class="v1-wrap">
                <div class="v1-layout">
                    <img src="<%=contextPath %>/res/images/1v1-t01.jpg" width="100%" height="231">
                    <img src="<%=contextPath %>/res/images/1v1-t02.jpg" width="100%" height="195">
                    <div id="zzsc"> <a class="pre"></a>
                        <div id="wai_box">
                            <div class="zzsc_box">
                                <ul>
                                    <li><a href="#" class="images"><img src="<%=contextPath %>/res/images/1v1-con01.jpg" alt=""/></a></li>
                                    <li><a href="#" class="images"><img src="<%=contextPath %>/res/images/1v1-con02.jpg" alt=""/></a></li>
                                </ul>
                                <ul>
                                    <li><a href="#" class="images"><img src="<%=contextPath %>/res/images/1v1-con01.jpg" alt=""/></a></li>
                                    <li><a href="#" class="images"><img src="<%=contextPath %>/res/images/1v1-con02.jpg" alt=""/></a></li>
                                </ul>
                                <ul>
                                    <li><a href="#" class="images"><img src="<%=contextPath %>/res/images/1v1-con01.jpg" alt=""/></a></li>
                                    <li><a href="#" class="images"><img src="<%=contextPath %>/res/images/1v1-con02.jpg" alt=""/></a></li>
                                </ul>
                            </div>
                        </div>
                        <a class="next"></a>
                    </div>
                    <img src="<%=contextPath %>/res/images/1v1-b01.jpg" width="100%" height="321">
                    <img src="<%=contextPath %>/res/images/1v1-b02.jpg" width="100%" height="279">
                    <img src="<%=contextPath %>/res/images/1v1-b03.jpg" width="100%" height="177">
                </div>
                <a href="javascript:;" class="close-b"></a>
            </div>
    </div>
    <!--1v1弹窗结束-->
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
<!--弹窗结束-->
<!--<script type="text/javascript" src="js/chart-view.js"></script>-->
  <script>
     var  actual_count=0;
     var  actual_content; // 用于保存讲师的观点内容  在本页面使用  
     var actual_ta = document.getElementById("actual_ta1");
     var viewSocket, actualSocket,interactionSocket ;
     var  roleId_current ='${roleId}';
     var  customerId_current = '${customer.userId}';
     var server_ip = '<%=InetAddress.getLocalHost().getHostAddress()%>'; 
     $(function(){
         // 1 讲师观点  websocket 
        window.viewSocket=  new WebSocket('ws://'+ server_ip + ':8080/quotationsocket');  
        viewSocket.onerror = function(event) {
           console.log('讲师观点WebSocket错误...');
         };  
         viewSocket.onopen = function(event) {
			console.log('讲师观点WebSocket连接建立成功...');
         };  
         viewSocket.onmessage = function(event) {  
         	 var data = event.data;
         	 data= $.parseJSON(data);  
         	 var  id = data.id;
         	 var  deleteFlag =data.deleteFlag;
         	 if(deleteFlag){
         		$('#teacherView_li'+id).remove();
        		$('.interaction_li_'+id).remove();
        		var unReplyCount = data.unReplyCount;
				var customerCount = data.customerCount;
				if(unReplyCount > 99){
					$('#unReplyCount').text('99+');
				}else{
					$('#unReplyCount').text(unReplyCount);
				}
				if(customerCount > 99){
					$('#customerCount').text('99+');
				}else{
					$('#customerCount').text(customerCount);
				}
				if(customerCount == 0){
					$('#noCustomer').show();
					$('#hasCustomer').hide();
				}
        	 }else{
	    	     var   msg = data.msg;
	         	 var  cfileId =data.cfileId;
	         	 var  createTime = data.createTime;
	         	 var  adviceId = data.adviceId;
	         	 var  mineralId =data.mineralId;
	         	 var  id = data.id;
	         	 var  teacherId =data.teacherId;
	         	 var  teacherName =data.teacherName;
	         	 var teacherIconId = data.teacherIconId;
         	 	if(adviceId==1){
         		 	adviceId ='策略';
         		 }else{
         		 	adviceId ='操作建议';
         	 	} 
         	 
         	 if(mineralId==1)
         		 {
         		   mineralId= "油";
         		 }
         	 else{
         		   mineralId="金属";
         	 }
         	 var node = new viewDom({
         		 basepath : '<%=request.getContextPath()%>',
         		 content : msg,
         		 adviceId: adviceId,
         		 mineralId: mineralId,
         		 cfileId:cfileId,
         		 id:id,
         		 createTime:createTime,
         		 teacherId:teacherId,
         		 teacherName:teacherName,
         		teacherIconId:teacherIconId
         			 }
         	 ).str2node();
         	 $('#msg_teacher_view').prepend(node);
         	 }
         };  
         //  2 实盘房间  实时websocket 
     window.actualSocket =  new WebSocket('ws://'+ server_ip + ':8080/quotationactualsocket');  
     actualSocket.onerror = function(event) {
       console.log(2);
     };  
     actualSocket.onopen = function(event) {
     };  
     actualSocket.onmessage = function(event) {  
     	 var data = event.data;
     	 data= $.parseJSON(data);  
     	 var msg = data.msg;
     	// 赋值给本页实时消息变量 
     	 actual_content = msg;
     	 var    deleteFlag =data.deleteFlag;
     	 var    customerId_msg =data.customerId;
     	 var    createTime = data.createTime;
     	 var levelId = data.levelId;
     	 var cfileId = data.cfileId;
     	 
     	 if(deleteFlag)
    	 {
     		 actual_count =actual_count-1;
    	 }
     	 else{
     	         var customerName =data.customerName;
     	         var   roleId_msg = data.roleId;
	     	     var node = new actualChatDom({
	     		 content : msg,
	     		 basepath : '<%=request.getContextPath()%>',
	     		 customerName:customerName,
	     		 createTime : createTime,
	     		 roleId_msg:roleId_msg,
	     		 roleId_current:roleId_current,
	     		 customerId_msg:customerId_msg,
	     		 customerId_current:customerId_current,
	     		 levelId:levelId,
	     		 cfileId:cfileId
	     			 }
	     	 ).str2node();
	     	 $('#msg_actual_ul').append(node);
	     	 //  统计实时聊天条数 
	     	 actual_count =actual_count+1;
	     	 //滚动条到最底
	     	 var _msgContent = $('div.sp-chart');
	    	 _msgContent[0].scrollTop = _msgContent[0].scrollHeight-_msgContent[0].clientHeight;
     	 }
     	 
     	 if(actual_count>99)
     	 {
     		document.getElementById('actual_count_id').innerHTML='99+';
     	 }
     	 else{
     		document.getElementById('actual_count_id').innerHTML=''+actual_count;
     	 }
     };  
        
         // 3  实盘房间  讲师与客户交互(提问与回复)
         window.interactionSocket =  new WebSocket('ws://'+ server_ip + ':8080/interactionsocket');  
         interactionSocket.onerror = function(event) {
        	 console.log('讲师客户交互WebSocket错误');
         };  
         interactionSocket.onopen = function(event) {
			console.log('讲师客户交互WebSocket连接建立...');
         };  
         interactionSocket.onmessage = function(event) {
			var data = event.data;
			data= $.parseJSON(data);  
			var msg = data.msg;
			$('#interaction_li').addClass("active").siblings().removeClass("active");
          	$('#msg_actual_div').hide();
          	$('#msg_interaction_div').show();
          	$('#actualSpeak_id').hide();
         	 
			if(data.replyTime){
				var str = '<div class="chart-new">';
				str += '<span class="chat_arrow_g"></span>';
				str += '<div class="chart_pic1">';
				if(data.replyHeadId){
					str += '<img src="/download/file/show.htm?id='+data.replyHeadId+'" width="45" height="40">';
				}else{
					str += '<img src="/res/images/default.png" width="45" height="40">';
				}
				str += '<p class="name">老师</p>';
				str += '<p class="name">'+data.replyName+'</p>';
				str += '</div>';
				str += '<ol class="chart_m mrt">';
				str += '<li class="chart_g">';
				str += '<div class="main-c">';
				str += '<p class="content">'+data.replyContent+'</p>';
				str += '<p class="function"><i>'+data.replyTime+'</i></p>';
				str += '</div>';
				str += '</li>';
				str += '</ol>';
				str += '</div>';
				$('#interaction_li_'+data.quesCreatorId+'_'+data.replyViewId+' .chat_container').append(str);
			}else{
				$('#interaction_li_'+data.quesCreatorId+'_'+data.viewId+' .chat_container').find('div.chart_b a.ask-a').remove();
				var node = new interactionTeacherChatDom(data).str2node();
				var viewNode = new customerAddViewDom(data).str2node();
				if(data.questionType == 1){
					if($('#interaction_view').find('#interaction_li_'+data.quesCreatorId+'_'+data.viewId).length <= 0){
						$('#interaction_view').append(viewNode);
					}
					$('#interaction_li_'+data.quesCreatorId+'_'+data.viewId+' .chat_container').append(node);
					$('#noCustomer').hide();
					$('#hasCustomer').show();
				}else{
					$('#interaction_li_'+data.quesCreatorId+'_'+data.viewId+' .chat_container').append(node);
				}
				var unReplyCount = data.unReplyCount;
				if(unReplyCount > 99){
					$('#unReplyCount').text('99+');
				}else{
					$('#unReplyCount').text(unReplyCount);
				}
				var customerCount = data.customerCount;
				if(customerCount > 99){
					$('#customerCount').text('99+');
				}else{
					$('#customerCount').text(customerCount);
				}
			}
         }
 		var searchUrl = '<%=request.getContextPath() %>/front/quotation/teacherViewScreen.htm';
 		$('.data-btn').on('click',function(){
 			$.post(searchUrl,$('#formData').serialize(),function(data){
 				if(!data){
 					alert('操作失败,请稍后再试!');
 				}else{
 					if(data.success==0){
 						alert(data.message);
 					}else{
 						var obj = data.obj;
 						if(!obj||obj.length==0){
 							alert('根据条件未查询到数据!');
 						}else{
 							var objStr = '';
 							var dateUtil = new DateUtil();
 							for(var i = 0;i < obj.length;i++){
 				         	 var cfileId =obj[i].cfileId;
 				         	 var content = obj[i].viewContent;
 				         	 var createTime = dateUtil.formatDate(obj[i].createTime,'yyyy-MM-dd hh:mm:ss');
 				         	 var adviceId = obj[i].adviceId;
 				         	 var mineralId =obj[i].mineralId;
 				         	 var id = obj[i].id;
 				         	 var teacherId =obj[i].teacherId;
 				         	 var teacherName =obj[i].teacherName;
 				         	 var teacherIconId = obj[i].teacherIconId;
 				         	 if(adviceId==1){
				         		adviceId ='策略';
							 }else{
 				         		adviceId ='操作建议';
 				         	 } 
 				         	 
 				         	 if(mineralId==1){
								mineralId= "油";
			         		 }else{
				         		mineralId="金属";
 				         	 }
 				         	 var node = new viewDom({
 				         		 basepath : '<%=request.getContextPath()%>',
 				         		 content : content,
 				         		 adviceId: adviceId,
 				         		 mineralId: mineralId,
 				         		 cfileId:cfileId,
 				         		 id:id,
 				         		 createTime:createTime,
 				         		 teacherId:teacherId,
 				         		 teacherName:teacherName,
 				         		 teacherIconId:teacherIconId
		         			 }).fill();
 				         	 objStr += node;
 							}
 				         	$('#msg_teacher_view').html(objStr);
 				         	 
 						}
 					}
 				}
 			},'json');
 		});
     });

    ////聊天界面下磊丹服务房间切换
    $(".chart-hide ul li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    $(".chart-hide P").mouseenter(function(){
        $(this).removeClass("c-bg").addClass("c-border01").siblings().removeClass("c-border01");
     });
     $(".chart-hide P").mouseleave(function(){
         $(this).removeClass("c-border01").addClass("c-bg");
     });
     $(".chart-hide ul").mouseenter(function(){
         $(this).removeClass("c-bg").addClass("c-border02")
     });
     $(".chart-hide ul").mouseleave(function(){
         $(this).removeClass("c-border02").addClass("c-bg")
     });
    
    var _index=0;
    $(".sp-title li").click(function(){
        _index = $(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        $(".sp-title-con01").eq(_index).show().siblings().hide();
        if(_index==0)
        {
        	 document.getElementById('actualSpeak_id').setAttribute("style", "display:block");
        }
        else{
        	 document.getElementById('actualSpeak_id').setAttribute("style", "display:none");
        }
    });
  //头部导航移入移出效果
    $('.teach-box').mouseenter(function(){
        $(".y-teach").show();
    }).mouseleave(function(){
        $(".y-teach").hide();
    });
    $('.nav-right').mouseenter(function(){
        $(".my-teach").show();
    }).mouseleave(function(){
        $(".my-teach").hide();
    });
    $(".sel-a a").click(function(){
        if($(".arrow-chose").css("backgroundPosition")=="-66px -60px"){
            $(".arrow-chose").css({backgroundPosition:"-57px -60px"});
        }else{
            $(".arrow-chose").css({backgroundPosition:"-66px -60px"});
        }
        $(".data-chose").slideToggle();
    });
    
    
  //单项选择
    $(".data-chose input[type='radio']").click(function(){
        $(".data-chose input[type='radio']").each(function(){
            if($(this).is(':checked')){
                $(this).siblings().addClass("s-colorB") ;
            }else{
                $(this).siblings().removeClass("s-colorB") ;
            }
        });
    });

    //得到箭头位置
    //获取.btn-hide占页面的百分比
    var c_w = parseInt($('.chart-live-con').css('width'))*100/parseInt($('.chart-live').css('width'))+"%";
    $(".btn-hide").click(function(){
        //var c_w = parseInt($('.chart-live-con').css('width'))*100/parseInt($('.chart-live').css('width'))+"%";
        //alert(c_w);
        if($(".chart-hide").css('marginLeft') == '0px'){
            $(".chart-hide").animate({marginLeft:-240},"slow");
            $(".chart-live-con").animate({width:"100%"},"slow")

        }else{
            $(".chart-hide").animate({marginLeft:0},"slow");
            $(".chart-live-con").animate({width:c_w},"slow")
        }
    });
    $(".voice-on").click(function(){
        if($(".voice-on").css("backgroundPosition")=="-34px -130px"){
            $(this).css({backgroundPosition:"-34px -150px"});
        }else{
            $(this).css({backgroundPosition:"-34px -130px"});
        }
    });
    $(".close-q").click(function(){
        $(".q-layout").hide();
    });
    $(".cancel-btn,.close-q").click(function(){
        $(".add-view").hide();
    });
    $(".img-clo").click(function(){
        $(".img-b").hide();
    });
    
     //聊天表情
    $(".blue-bg").click(function(e) {
        $(".emoji-field").toggle();
        $(".emoji-caitiao").hide();
        e.stopPropagation();
    });

    $(".emoji-field ul li img").click(getImg);
    //点击页面其他地方隐藏表情和彩条
    $(document).click(function() {
        $(".emoji-field").hide();
        $(".emoji-caitiao").hide();

    });

    //彩条
    $(".red-bg").click(function(e) {
        $(".emoji-caitiao").toggle();
        $(".emoji-field").hide();
        e.stopPropagation();
    });
    $(".emoji-caitiao img").click(getImg);
    //选择图片 actual_ta
    function getImg(){
        var imgSrc = this.src;
        var image = document.createElement("img");
        image.src = imgSrc;
        //writeMsg.appendChild(image);
        actual_ta.value = actual_ta.value+actual_ta.appendChild(image);
    }
    //清屏
    $(".cls").click(function(e) {
        document.getElementById('msg_actual_ul').innerHTML=''; 
        actual_count=0;
        document.getElementById('actual_count_id').innerHTML='0';
    });
    // 表情，彩条结束 
    //1v1弹窗
    $('.v1').click(function(){
       $('.v1-box').show(200)
    });
    $('.close-b').click(function(){
        $('.v1-box').hide()
    })
    //返回
    $('.back-btn').on('click',function(){
    	window.location.href="<%=request.getContextPath() %>/index/frontIndex.htm";
    });
    //关闭
    $(".close").click(function(){
        $(".bg").hide();
     });
    //磊丹观点
    $('.point-item').on('click',function(){
    	fillViewpoint();
    	$(".point-box").show();
    });
    //交易内参
    $('.trans-item').on('click',function(){
    	fillInternalInfo(1,1);
        $(".trans-box").show();
    });
    var _index=0;
    $(".jync-tab li").click(function(){
        _index=$(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        if(_index == 1){
        	fillInternalInfo(1,2);
        }
        $(".jync-count .jync-con").eq(_index).show().siblings().hide();
    });
    //账户诊断
    $(".account-item").click(function(){
        $(".account-box").show();
    });
    
	//回复
	$(document).on('click','.ask-a',function(){
		$(".a-rep").show();
		$('#replyContent_ta').focus();
		//回复的私信ID
		var msgId = $(this).attr('privateMessageId');
		$("#msgId").val(msgId);
	});
    
    //发表观点图片
    $(document).on('change','#file',function(){
		var fileName = $('#file').val();
    	 if($('#file').val().length>0){
  	      $.ajaxFileUpload({
  			url:'<%=request.getContextPath() %>/front/quotation/uploadTeacherViewFile.htm',
  			secureuri:false,
  			type:'POST',
  			fileElementId:'file',
  			dataType:'json',
  			success:function(data){
  				if(data.success == -1){
  					$('#cfileId').val(data.id);
  					var acontent = '<input id="file" name="file"  type="file">'+fileName
  					$('#selectpic_a').html(acontent);
  				}else{
  					if(data.msg){
	  					alert(data.msg);
  					}else{
  						window.location.href='<%=request.getContextPath() %>/login/toLogin.htm';
  					}
  				}
  			},
  			error:function(data){
  				console.log(data);
  				$.messager.alert('操作提示','上传文件异常','info');
  			}
  		  });
  		}
    });
    // 讲师观点弹框 
	function popupAddview(){
		$(".add-teach-view").show();
		document.getElementById('cfileId').value=null;
		var acontent = '<input id="file" name="file"  type="file">选择图片...';
		$('#selectpic_a').html(acontent);
		$('#content').focus();
    }
    
     // 查看大图
    function lookBigImg(fileId){
		$("#dynamic_big_img").attr("src","<%=request.getContextPath() %>/download/file/show.htm?id="+fileId);
		var image = new Image();
		image.src = "<%=request.getContextPath() %>/download/file/show.htm?id="+fileId;
		$("#dynamic_big_img").attr('width',image.width);
		$("#dynamic_big_img").attr('height',image.height);
		$(".img-b").show();
    }
    // 发送讲师观点 创建 成功  socket
   function sendView(msg,cfileId,adviceId,mineralId,id){
		var param = {msg : msg, cfileId : cfileId, adviceId:adviceId, mineralId:mineralId, id:id};
		var toStr = JSON.stringify(param);
		window.viewSocket.send(toStr);
	}
    
    
   function sendDeleteView(id,deleteFlag){
	   var param = {deleteFlag:deleteFlag, id:id};
	   var toStr = JSON.stringify(param);
	   window.viewSocket.send(toStr);
	}
   
   
   // ajax 删除讲师观点
   function  deleteTeacherView(viewId){
	   if(confirm('删除观点该观点的提问和回复也会一并删除，确定要删除吗？')){
		   var url1= '<%=request.getContextPath() %>/front/quotation/deleteTeacherView.htm';
	 	   var params={viewId:viewId};
	    	$.ajax({
	    		type:"POST",
	 			url:url1,
	 			data: params,
	 			dataType:"json",
	 			success:function(data){
	 				if(data){
	 					if(data.deleteFlag){
	 					sendDeleteView(viewId,true);
	 					// 隐藏本地对应的视图
	 					 var  viewLi_var = 'teacherView_li'+viewId;
	 	         		 document.getElementById(viewLi_var).setAttribute("style", "display:none");
	 					}
	 				}
	 			}
	 		});
	   }
   }
    //讲师提交观点
	function commitQ() {
		var addViewUrl= '<%=request.getContextPath() %>/front/quotation/addView.htm';
		var text  = $('#content').val();
		if(text==null || $.trim(text)==''){
			alert("请填写观点内容！");
			$('#content').focus();
			return;
		}else if(text.length>255 ){
			alert("内容长度不能超过255!");
			return;
		}
		$.ajax({
			type:"POST",
			url:addViewUrl,
			dataType:"json",
			data: $('#newsForm').serialize(),
			success:function(data){
				if(data){
					$('#content').val("");
					var  msg = data.content;
					var  cfileId = data.cfileId;
					var  adviceId = data.adviceId;
					var  mineralId = data.mineralId;
					var  id= data.id;
					sendView(msg,cfileId,adviceId,mineralId,id);
					document.getElementById('cfileId').value=null;
					var acontent = '<input id="file" name="file"  type="file">选择图片...'
					$('#selectpic_a').html(acontent);
					$(".add-view").hide();
				}
			}
		});
	}
    
    function  sendActual(){
    	var emo = new Emo($('#actual_ta1'),$(".emoji-field ul li img"));
    	var msg =emo.getTrimHtml();
	  	if(!msg||msg.length<=0){
	  		alert("不能发送空消息!");
		  	return ;
	  	}
	  	console.log('消息长度：'+emo.getLen());
   		if(emo.getLen()>150 )
    	{
    		alert("内容长度不能超过150 ");
    		return;
    	}
		var param = { msg : msg};
		var toStr = JSON.stringify(param)
		window.actualSocket.send(toStr);
		$('#actual_ta1').empty();
    }
    // delete actual chat
    function  deleteActualChat(customerId_msg, createTime){
		var id_realDelete = "actual_content_div"+customerId_msg+createTime;
		var  realDelete = document.getElementById(id_realDelete).innerHTML;
		
		var  id_li = "actual_chat_li"+customerId_msg+createTime;
		document.getElementById(id_li).setAttribute("style", "display:none");
		
		var param = { deleteFlag:true,msg: realDelete,customerId_msg:customerId_msg, createTime:createTime};
		var toStr = JSON.stringify(param);
		window.actualSocket.send(toStr);
    }
 
    //  讲师点击消息公开，则更换文字为已公开，同时发送此消息给别的客户 
    function publicChat(customerId_msg, roleId_msg, createTime,customerName,levelId)
    {
      // 更改文字元素为 已公开
    	var  id_find = "public_text_em"+customerId_msg+createTime;
    	var  publicValue= document.getElementById(id_find);
    	var id_realPublic = "actual_content_div"+customerId_msg+createTime;
    	var  realPublicContent = document.getElementById(id_realPublic);
    	if(publicValue.innerHTML.length==2)
    	 {
    	 document.getElementById(id_find).innerHTML='已公开'; 
    	 var param = { msg : $(realPublicContent).find('span').html(),customerId_msg:customerId_msg,customerName:customerName,createTime:createTime,roleId_msg:roleId_msg,levelId:levelId};
   	     var toStr = JSON.stringify(param);
   	     // 当前消息发送者为客户时，才讲消息发到后台;因为讲师发的消息 默认已经对所有人可见 
   	     if(roleId_msg=="5")
   	      {
   	      window.actualSocket.send(toStr);
   	      }
    	 }
    }
    
    // 讲师回复客户消息 
	function teacherReply(){
		var replyUrl= '<%=request.getContextPath() %>/front/quotation/replyViewMessage.htm';
		var text  = $('#replyContent_ta').val();
		if(text==null || $.trim(text)==''){
			alert("回复不能为空  ");
			return;
		}else if(text.length>150 ){
			alert("内容长度不能超过150 ");
			return;
		}
		var params={content:$('#replyContent_ta').val(),msgId:$("#msgId").val()};
		$.ajax({
			type:"POST",
			url:replyUrl,
			data: params,
			dataType:"json",
			success:function(data){
				if(data){
					$('#replyContent_ta').val('');
					$(".a-rep").hide();
					var replyStr = JSON.stringify(data);
					window.interactionSocket.send(replyStr);
				}
			}
		});
	}
</script>
</body>
</html>