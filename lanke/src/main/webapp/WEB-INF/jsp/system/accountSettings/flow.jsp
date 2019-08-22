<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="/lanker/newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="/lanker/newStyle/css/lanke.css" media="all">
<link rel="icon" type="text/css" href="/lanker/newStyle/images/lk-icon.png">
<title></title>
<style>
</style>
</head>
<body>
	<h1 class="lk-flow-title">
        	计费系统开通流程
        <i class="layui-icon close">&#x1006;</i>
    </h1>
    <div class="lk-flow-body layui-clear">
        <div class="lk-flow-img">
            <img src="/lanker/newStyle/images/flow-chart.jpg" alt="">
        </div>
        <div class="lk-flow-text">
            	填写绑定信息
        </div>
        <div class="lk-flow-text">
            	审核通过
        </div>
        <div class="lk-flow-text">
           	 在审核通过页面中获取下载揽客网吧接口程序链接以及激活码
        </div>
        <div class="lk-flow-text">
           	 安装并使用揽客网吧接口程序
        </div>
    </div>
    <div class="lk-flow-btn">
        <span class="btn-close">关闭</span>
        <span class="btn-sure">确定</span>
    </div>
</body>
<script src="/lanker/newStyle/js/jquery-1.11.1.min.js"></script>
<script src="/lanker/newStyle/layui/layui.js"></script>
<script>
	
</script>
</html>