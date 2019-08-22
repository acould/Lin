<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%--  <%@ include file="../../system/index/top.jsp"%> --%>
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet"
	href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
<link rel="stylesheet"
	href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
<style>
.lk-product-title {
	font-size: 18px;
}

.lk-product-time {
	color: #999;
	margin-top: 12px;
	font-size: 12px;
}

.layui-text ul li {
	margin-top: 10px;
}

.layui-timeline-title {
	margin-bottom: 0;
}
</style>
</head>
<body class="no-skin scroll" style="background-color: #f2f2f2;">
	<form action="" id="toShowFrom" name="toShowFrom" target="_blank"
		method="post">
		<input type="hidden" id="income_url" name="income_url"
			value="<%=basePath%>incomeCount.do"> <input type="hidden"
			id="user_url" name="user_url" value="<%=basePath%>userCount.do">
		<input type="hidden" id="store_id" name="store_id"
			value="${pd.store_id}">
		<!-- 相关门店的id集合 -->
		<input type="hidden" id="list" name="list" value="${pd.list}">
		<!-- 初始进来赋值全部门店 -->
		<input type="hidden" id="store_name" name="store_name"
			value="${pd.store_name}">
	</form>
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content" style="background-color: #f2f2f2;">
					<div class="layui-fluid">
						<div class="layui-row layui-col-space15">
							<div class="layui-col-md12">
								<div class="layui-row lk-bg-wihte">
									<div class="layui-col-md4">
										<div class="lanke-index-topBox1">
											<p>
												Hi，您当前的角色是：${roleName}，当前数据:<span id="data_store">${store_state}</span>
											</p>
										</div>
									</div>
									<div class="layui-col-md8">
										<div class="lanke-index-topBox">
											<form class="layui-form">
												<div class="layui-inline">
													<label class="layui-form-label">切换门店：</label>
													<div class="layui-input-inline">
														<select id="modules" name="modules" lay-verify="required"
															lay-search="" lay-filter="store">
															<option value="" selected>搜索或选择</option>
															<c:forEach items="${storeList}" var="var">
																<option value="${var.STORE_ID}"
																	<c:if test="${pd.store_id ==var.STORE_ID}">selected="selected"</c:if>>${var.STORE_NAME}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="layui-inline">
													<label class="layui-form-label">日期查询：</label>
													<div class="layui-input-inline">
														<input type="text" class="layui-input" id="search_time"
															placeholder=" - ">
													</div>
												</div>
												<span class="layui-btn layui-btn-normal" id="toSearch"
													style="margin-left: 10px"> <i class="layui-icon"
													style="padding-right: 4px; font-size: 14px;">&#xe615;</i>搜索
												</span>
											</form>
										</div>
									</div>
								</div>
							</div>
							<div class="layui-col-md4">
								<div class="layui-card">
									<div class="layui-card-header">快速入口</div>
									<div class="layui-card-body">
										<div
											class="layui-carousel layadmin-carousel layadmin-shortcut">
											<div carousel-item>
												<ul class="layui-row layui-col-space10">
													<li class="layui-col-xs3"><a href="javascript:void(0)"
														onclick="toHome('用户中心','accountSettings/list.do');"> <i
															class="layui-icon lk-homeIocn-user"></i> <cite>用户中心</cite>
													</a></li>

													<li class="layui-col-xs3"><a href="javascript:void(0)"
														onclick="toHome('门店管理', 'storeShow/list.do');"> <i
															class="layui-icon lk-homeIocn-store"></i> <cite>门店管理</cite>
													</a></li>

													<li class="layui-col-xs3"><a href="javascript:void(0)"
														onclick="toHome('新建图文','sendRecord/list.do');"> <i
															class="layui-icon lk-homeIocn-imgtext"></i> <cite>新建图文</cite>
													</a></li>

													<li class="layui-col-xs3"><a href="javascript:void(0)"
														onclick="toHome('会员营销','msgManager/goList.do');"> <i
															class="layui-icon lk-homeIocn-member"></i> <cite>活动群发</cite>
													</a></li>

													<li class="layui-col-xs3"><a href="javascript:void(0)"
														onclick="toHome('充值规则','rechargeRule/list.do');"> <i
															class="layui-icon lk-homeIocn-pay"></i> <cite>充值规则</cite>
													</a></li>

													<li class="layui-col-xs3"><a href="javascript:void(0)"
														onclick="toHome('卡券设置', 'card/list.do');"> <i
															class="layui-icon lk-homeIocn-card"></i> <cite>卡券设置</cite>
													</a></li>

													<li class="layui-col-xs3"><a href="javascript:void(0)"
														onclick="toHome('赛事设置', 'matches/goList.do');"> <i
															class="layui-icon lk-homeIocn-sysmatch"></i> <cite>赛事设置</cite>
													</a></li>

													<li class="layui-col-xs3"><a href="javascript:void(0)"
														onclick="toHome('商品管理', 'auction/list.do');"> <i
															class="layui-icon lk-homeIocn-auction"></i> <cite>商品管理</cite>
													</a></li>
												</ul>
											</div>
										</div>

									</div>
								</div>
							</div>
							<div class="layui-col-md4">
								<div class="layui-card">
									<div class="layui-card-header">待办事项</div>
									<div class="layui-card-body">

										<div class="layui-carousel layadmin-carousel layadmin-backlog">
											<div carousel-item>
												<ul class="layui-row layui-col-space10">

													<li class="layui-col-xs6"><a href="javascript:void(0)"
														class="layadmin-backlog-body"
														onclick="toHome('消息通知','newerGuide/messageList.do');">
															<h3>待查看消息</h3>
															<p>
																<cite id="messageSum">${map.messageSum}</cite>
															</p>
													</a></li>

													<li class="layui-col-xs6"><a href="javascript:void(0)"
														class="layadmin-backlog-body"
														onclick="toHome('订单管理', 'order/list.do');">
															<h3>待发货商品</h3>
															<p>
																<cite id="orderSum">${map.orderSum}</cite>
															</p>
													</a></li>

													<li class="layui-col-xs6"><a href="javascript:void(0)"
														class="layadmin-backlog-body"
														onclick="toHome('卡券核销', 'cancel/list.do');">
															<h3>待核销卡券</h3>
															<p>
																<cite id="cardSum">${map.cardSum}</cite>
															</p>
													</a></li>

													<li class="layui-col-xs6"><a href="javascript:void(0)"
														class="layadmin-backlog-body"
														onclick="toHome('查看留言', 'lm/list.do');">
															<h3>待回复留言</h3>
															<p>
																<cite id="leaveSum">${map.leaveSum}</cite>
															</p>
													</a></li>
													<!-- layer.tips('没有权限操作', this, {tips: 3}); -->
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="layui-col-md4">
								<div class="layui-card">
									<div class="layui-card-header">
										揽客产品动态
										<div class="layui-btn-group layuiadmin-btn-group"
											style="color: #666; font-size: 12px">
											<a href="<%=basePath%>productNews/selectAll.do"
												target="_blank">查看全部 </a>
										</div>
									</div>
									<div class="layui-card-body" style="height: 185px">
										<%--产品预告和产品日志只显示其一，优先显示产品预告，--%>
										<c:if test="${not empty pd1.title}">
											<div class="lk-product-box" style="padding: 10px 10px;">
												<h1 class="lk-product-title">${pd1.title}</h1>
												<div id="lk-product-body" class="lk-product-body"
													style="padding: 10px 20px 10px 0;">
													<p id="clamp-this-module">${pd1.content}</p>
												</div>
												<div style="position: relative">
													<p class="lk-product-time">
														预计上线时间：<span>${pd1.onlinetime}</span>
													</p>
												</div>
											</div>
										</c:if>
										<c:if test="${empty pd1.title}">
											<c:if test="${empty pd3}">
										          敬请期待
										    </c:if>
											<c:if test="${not empty pd3.version}">
												<div class="lanke-audit"
													style="padding: 10px; height: 150px; overflow: auto;">
													<ul class="layui-timeline">
														<li class="layui-timeline-item"><i
															class="layui-icon layui-timeline-axis">&#xe63f;</i>
															<div class="layui-timeline-content layui-text">
																<div class="layui-timeline-title">
																	<h2>${pd3.version}</h2>
																	<span class="layui-badge-rim">${pd3.updatetime}</span>
																</div>
																<ul>
																	<c:forEach items="${list}" var="var">
																		<li><c:if test="${var.type == 0}">
																				<span class="new_add">新增:</span>${var.content}</c:if> <c:if
																				test="${var.type == 1}">
																				<span class="optimize">优化:</span>${var.content}</c:if> <c:if
																				test="${var.type == 2}">
																				<span class="repair">修复:</span>${var.content}</c:if></li>
																	</c:forEach>
																</ul>
															</div></li>
													</ul>
												</div>
											</c:if>
										</c:if>
									</div>
								</div>
							</div>

							<div class="layui-col-md6">
								<div class="layui-card">
									<div class="layui-card-header">
										收入
										<div class="layui-btn-group layuiadmin-btn-group">
											<a href="javascript:;" class="layui-btn layui-btn-primary  layui-btn-xs" data-type="day">日</a> 
											<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs" data-type="week">周</a> 
											<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs" data-type="month">月</a> 
											<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs" data-type="year">年</a>
											<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs" data-type="sum">总</a>
										</div>
									</div>
									<div class="layui-card-body layuiadmin-card-list">
										<p class="layuiadmin-big-font" id="income_num">${totalMap.incomeSum}</p>
										<p>
											<span id="income_title">揽客充值总收入</span> <span
												class="layuiadmin-span-color">*** <i
												class="layui-inline layui-icon layui-icon-dollar"></i></span>
										</p>
									</div>
								</div>
								<div class="layui-card">
									<div class="layui-card-header">收入增长概览</div>
									<div class="layui-card-body">
										<div
											class="layui-carousel layadmin-carousel layadmin-dataview"
											data-anim="fade" lay-filter="LAY-index-dataview">
											<div carousel-item id="LAY-index-dataview">
												<div>
													<i class="layui-icon layui-icon-loading1 layadmin-loading"></i>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="layui-col-md6">
								<div class="layui-row layui-col-space15">
									<!-- <div class="layui-card">
			            <div class="layui-card-header">收入增长</div>
			            <div class="layui-card-body layadmin-takerates" style="padding: 19px;">
			                <div class="layui-progress" lay-showPercent="yes">
			                    <h3>增长率（日同比 28% <span class="layui-edge layui-edge-top" lay-tips="增长" lay-offset="-15"></span>）</h3>
			                    <div class="layui-progress-bar" lay-percent="50%"></div>
			                </div>
			                <div class="layui-progress" lay-showPercent="yes">
			                    <h3>签到率（日同比 11% <span class="layui-edge layui-edge-bottom" lay-tips="下降" lay-offset="-15"></span>）</h3>
			                    <div class="layui-progress-bar" lay-percent="32%"></div>
			                </div>
			            </div>
			        </div> -->
									<div class="layui-col-md6">
										<div class="layui-card">
											<div class="layui-card-header">用户增长</div>
											<div class="layui-card-body layuiadmin-card-list">
												<p class="layuiadmin-big-font" id="member_num">${totalMap.fansSum}</p>
												<p>
													<span id="member_title">总粉丝</span> <span
														class="layuiadmin-span-color">***<i
														class="layui-inline layui-icon layui-icon-user"></i></span>
												</p>
											</div>
										</div>
									</div>
									<div class="layui-col-md6">
										<div class="layui-card">
											<div class="layui-card-header">
												用户增长
												<div class="layui-btn-group layuiadmin-btn-group">
													<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs" data-type="user_week">周</a> 
													<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs" data-type="user_month">月</a> 
													<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs" data-type="user_year">年</a>
													<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs" data-type="user_sum">总</a>
												</div>
											</div>
											<div class="layui-card-body layuiadmin-card-list">
												<p class="layuiadmin-big-font" id="fans_num">${totalMap.memberSum}</p>
												<p>
													<span id="fans_title">总会员</span> <span
														class="layuiadmin-span-color">***<i
														class="layui-inline layui-icon layui-icon-user"></i></span>
												</p>
											</div>
										</div>
									</div>
									<div class="layui-col-md12">
										<div class="layui-card">
											<div class="layui-card-header">用户增长概览</div>
											<div class="layui-card-body">
												<div
													class="layui-carousel layadmin-carousel layadmin-dataview"
													data-anim="fade" lay-filter="LAY-index-user">
													<div carousel-item id="LAY-index-user">
														<div>
															<i
																class="layui-icon layui-icon-loading1 layadmin-loading"></i>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->


	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<script src="<%=basePath%>newStyle/layer/layer.js"></script>
	<script src="<%=basePath%>newStyle/js/clamp.min.js"></script>
	<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
	<script type="text/javascript">
	$(top.hangge());//关闭加载状态
		loadMessage();
		function loadMessage(){
			$.ajax({
				type	: "POST",
				url		:'<%=basePath%>newerGuide/getMessage.do',
				data	:{},
				dataType:'json',
				beforeSend: beforeSend(''),
				success: function(data) {
					layer.close(layer.index);
					if(data.result == "true"){
						//返回结果正确时
						var varList = data.varList;
						var html = '';
						for(var i=0;i<varList.length;i++){
							var time = varList[i].announce_time;
							if(time.indexOf(".") > 0){
								time = time.split(" ")[0];
							}
							html += '<div class="lk-msg-body">'+
										'<h2>'+varList[i].title+'</h2>'+
										'<span class="lk-msg-timeline">'+time+'</span>'+
										'<p class="">'+varList[i].content+'</p >'+
									'</div>';
						}
						if(html != ''){
							top.layer.open({
								type: 1,
								title: false,
								closeBtn: 0,
								area: '360px',
								skin: 'layui-layer-nobg', //没有背景色
								shadeClose: false,
								offset: '20%',
								shade:0.7,
								content: '<div id="message-box">'+
											'<image src="<%=basePath%>newStyle/images/lk-msg-header.png" id="msg_img" width="100%">'
														+ '<div class=" lk-message-content">'
														+ '<div class="lk-message-list scroll">'
														+ html
														+ '</div>'
														+ '<div class=" lk-message-btn">'
														+ '<span class="lk-msg-close" onclick="msg_close()">暂时不了</span>'
														+ '<span class="lk-msg-see" onclick="go_newMsg()">查看消息</span>'
														+ '</div>'
														+ '</div>'
														+ '</div>',
												move : "#msg_img"
											});
								}
							}
						},
						error : function() {
							layer.close(layer.index);
							message("系统繁忙，请稍后再试！");
							return;
						}
					});
		}
	</script>
	<script type="text/javascript">
	   var module = document.getElementById("clamp-this-module");
	    if(module != null || module!= undefined){
	        $clamp(module, {clamp: 3});
		}

		layui.config({ //静态资源所在路径
	    	base: '<%=basePath%>newStyle/layuiadmin/'
		}).extend({
			index : 'lib/index' //主入口模块
		}).use([ 'index', 'console' ]);
		
		//首页菜单按钮
		function toHome(name,menuUrl){
			$.ajax({
				type	: "POST",
				url		:'<%=basePath%>home.do',
				data : {
					menuUrl : menuUrl
				},
				dataType : 'json',
				success : function(data) {
					if (data.result == "true") {
						top.mainFrame.tabAddHandler(data.INTENET_ID, name,menuUrl);
					}else{
						layer.alert(data.message);
					}
				},
				error : function() {
					layer.close(layer.index);
					message("系统繁忙，请稍后再试！");
					return;
				}
			});
		}
	</script>
</body>
</html>