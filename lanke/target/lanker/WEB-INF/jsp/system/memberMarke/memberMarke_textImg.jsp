<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>消息详情</title>
</head>
<style>
body {
	background: #ffffff;
	margin: 0;
	padding: 0;
	line-height: 1.6
}

.border-padding {
	padding: 15px;
}

p {
	margin: 6px 0;
}

h1 {
	margin: 0;
	font-size: 18px;
	margin-bottom: 4px;
}

.lable {
	font-size: 10px;
	color: #999;
	border: 1px solid #d8d8d8;
	padding: 2px 10px;
	border-radius: 20px;
}

.title i {
	width: 4px;
	height: 14px;
	background: red;
	display: inline-block;
	margin-right: 10px;
}

.title {
	font-size: 16px;
	color: #000;
	margin-top: 14px;
}

#msg_content, #msg_time, .content_text, .remark_text {
	color: #333;
	font-size: 14px;
	line-height: 1.7878;
}

#msg_time {
	color: #4aa5f5;
	font-size: 13px;
}

#details img {
	width: 100%
}

.remark_text {
	color: #999
}
</style>
<body onload="closeWindow();">
	<div class="border-padding">
		<c:if test="${pd.type != 'card'}">
			<h1>${pd.title}</h1>
			<span class="lable">${pd.activtyType}</span>
			<p class="title">
				<i></i>活动内容：
			</p>
			<p id="msg_content">${pd.activtyContent}</p>
			<p class="title">
				<i></i>活动时间：
			</p>
			<p id="msg_time">${pd.createtime}</p>
			<p class="title">
				<i></i>活动详情：
			</p>
			<div id="details">
				<c:if test="${pd.type == 'image'}">
					<img src="<%=basePath%>uploadFiles/uploadImgs/${pd.path}" alt="">
				</c:if>
				<c:if test="${pd.type == 'text'}">
					<p class="content_text">${pd.content}</p>
				</c:if>
			</div>
			<c:if test="${not empty pd.remark}">
				<p class="title">
					<i></i>活动备注：
				</p>
				<p class="remark_text">${pd.remark}</p>
			</c:if>
		</c:if>
		<!-- 卡券 -->
		<c:if test="${pd.type == 'card'}">
		<h1 style="text-align:center">1s后页面自动关闭</h1>
		</c:if>
	</div>
	<script type="text/javascript">
	    var type = '${pd.type}';
		var time = 1;
		var message = '${pd.message}';
		function closeWindow() {
			if(type == 'card'){
			window.setTimeout('closeWindow()', 1000);
			if (time > 0) {
				alert(message);
				time--;
			} else {
				WeixinJSBridge.call('closeWindow');
			}
			}
		}
	</script>
</body>
</html>