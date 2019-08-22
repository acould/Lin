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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>产品版本日志</title>
</head>
<!-- jsp文件头和头部 -->
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
<style>
.layui-text ul li {
	margin-top: 12px;
}
</style>
<body>
<div class="card-header">
	<div class="card-header-msg">
		<img src="<%=basePath%>newStyle/images/logo2.png" class="card-lankeIcon"></img><span class="card-header-title">产品版本日志</span>
		<div class="card-header-right">
			<img alt="" src="<%=basePath %>static/ace/avatars/user.jpg" class="">
			<span class="card-userName layui-elip">
					<small>Welcome</small><br>
					${INTENET_NAME}
				</span>
		</div>
	</div>
</div>
<div style="height: 370px;background: url('<%=basePath%>newStyle/images/productNews-banner.png')no-repeat center center;"> </div>
	<div class="lanke-audit" style="width: 1190px;margin: 0 auto;padding: 30px 0;">
		<c:if test="${not empty pd.title}">
			<div class="lk-product-box" style="padding: 10px 0px;margin-bottom: 30px;">
				<h1 class="lk-product-title" style="padding: 14px 0 "><font color="black">产品预告：</font>${pd.title}</h1>
				<div id="lk-product-body" class="lk-product-body"
					style="padding: 10px 20px 10px 0;">
					<p id="clamp-this-module">${pd.content}</p>
				</div>
				<div style="position: relative">
					<p class="lk-product-time">
						预计上线时间：<span>${pd.onlinetime}</span>
					</p>
				</div>
			</div>
		</c:if>

		<h1 class="lk-product-title" style="padding: 14px 0;color: black;border-bottom: 1px solid #eee;margin-bottom: 20px">揽客产品日志</h1>
		<c:forEach items="${list}" var="va">
			<ul class="layui-timeline">
				<li class="layui-timeline-item"><i
					class="layui-icon layui-timeline-axis">&#xe63f;</i>
					<div class="layui-timeline-content layui-text">
						<div class="layui-timeline-title">
							<h2>${va.pd.version}</h2>
							<span class="layui-badge-rim">${va.pd.updatetime}</span>
						</div>
						<ul>
							<c:forEach items="${va.list1}" var="var">
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
		</c:forEach>
	</div>
</body>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script type="text/javascript">
	
</script>
</html>