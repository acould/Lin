<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=yes, initial-scale=0.5, maximum-scale=1.0, minimum-scale=0.1">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>揽客注册授权</title>
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
</head>
<body>
	<div style="text-align:center;position: absolute;width: 100%;height: 260px;margin: auto;top: 0;bottom: 0;left: 0;right: 0;">
		<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop" style="font-size: 45px;color: red;">&#xe63e;</i><br>
		<span style="padding-top:20px;font-size:20px;display:inline-block;color:#fff">服务器正在处理，请稍后...</span>
	</div>
	<div style="position: absolute;width: 100%;height: 100%;margin: auto;top: 0;bottom: 0;left: 0;right: 0;background: #333844;z-index: -1;"></div>
    <script src="<%=basePath%>static/login/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
</body>
</html>