<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-touch-fullscreen" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="address=no">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
	<title>微官网</title>
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
	<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
	<script type="text/javascript" src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
</head>
<body class="minWeb weic-bgfff">
	<div class="weic-noboby">
		<div class="weic-nobody-img"></div>
		<div class="weic-nobody-text">微官网尚未创建</div>
		<div class="weic-nobody-btn">
			<div class="weic-dent-btn weic-btn-gradientBlue" onclick="javascript:history.go(-1)">返回</div>
		</div>
	</div>
</body>
</html>
