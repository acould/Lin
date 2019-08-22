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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="address=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>${intenetName}</title>

<link rel="stylesheet"
	href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
<link rel="stylesheet"
	href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
<link rel="stylesheet"
	href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
<link rel="stylesheet"
	href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
<style>
body {
	background: #fff
}

.weic-btn-gradientBlue {
	background: -moz-linear-gradient(to right, #2283e5 0%, #37a8ff 100%);
	background: -webkit-linear-gradient(to right, #2283e5 0%, #37a8ff 100%);
	background: -o-linear-gradient(to right, #2283e5 0%, #37a8ff 100%);
	background: -ms-linear-gradient(to right, #2283e5 0%, #37a8ff 100%);
	background: linear-gradient(to right, #2283e5 0%, #37a8ff 100%);
	color: #fff;
	box-shadow: 0px 4px 20px rgba(35, 133, 231, 0.5);
}

.btn-width {
	width: 140px;
	height: 140px;
	line-height: 140px;
	font-size: 20px;
	position: absolute;
	margin: auto;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	text-align: center;
	border-radius: 100px;
}
</style>
</head>
<body class="weic weic-bgfff">
	<div class="weic-item-padding30 user_index weic-martop-transmit weic-bgfff">
	    <c:if test="${state == 'RUSH'}">
			<!-- 冲送券(已领取过) -->
			<div class="up_tips card_notime" style="margin-top: 3rem"></div>
			<div style="text-align: center; font-size: 0.38rem; color: #666; margin: 1rem 0 1.2rem 0">
				该卡券已领取<br>快去使用吧
			</div>
		</c:if>
		<c:if test="${state == 'GRAB_3'}">
			<!-- 未开始 -->
			<div class="up_tips card_notime" style="margin-top: 3rem"></div>
			<div style="text-align: center; font-size: 0.38rem; color: #666; margin: 1rem 0 1.2rem 0">
				很抱歉，当前还没到抢券时间无法领取<br>晚点再来吧
			</div>
		</c:if>
		<c:if test="${state == 'GRAB_4'}">
			<!-- 已结束 -->
			<div class="up_tips card_notime" style="margin-top: 3rem"></div>
			<div style="text-align: center; font-size: 0.38rem; color: #666; margin: 1rem 0 1.2rem 0">
				很抱歉，该秒抢券抢券时间已结束<br>请参与其他活动
			</div>
		</c:if>
		<c:if test="${state == 'TERM_2'}">
			<!-- 未满足领取条件 -->
			<div class="up_tips internet_notime" style="margin-top: 3rem"></div>
			<div style="text-align: center; font-size: 0.38rem; color: #666; margin: 0.3rem 0 1.2rem 0">
				<c:if test="${cardSum_type == 2}">
	                              很抱歉，您当前一周上网不足 ${cardSum_time} 个小时<br>没达到领取条件
	           </c:if>
			<c:if test="${cardSum_type == 3}">
	                            很抱歉，您当前一月上网不足 ${cardSum_time} 个小时<br>没达到领取条件
	        </c:if>
			</div>
		</c:if>
		<div style="padding: 0.8rem 1rem">
			<div class="weic-dent-btn weic-btn-gradientBlue" onclick="window.location.href='<%=basePath%>intenetmumber/index'" style="margin-bottom: 0.8rem">去首页看看</div>
		</div>
</div>

	<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
	<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
	<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
	<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
	<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
	<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
	<script>
		
	</script>
</body>
</html>
