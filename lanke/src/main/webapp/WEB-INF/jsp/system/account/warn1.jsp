<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>揽客授权失败</title>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
</head>
<body>
    <div class="lanke-noboby warn">
          <div class="lanke-nobody-img"></div>
          <div class="lanke-nobody-content">
          	 <p class="lanke-Loser">很遗憾，授权失败！</p>
          	 <p class="lanke-nobody-text" style="margin-bottom: 10px;">请不要自定义修改权限,该操作会导致部分功能失效,请取消授权后再次授权!</p>
          	 <p class="lanke-nobody-text">如有疑问,请联系客服:4000965099</p>
         	 <p style="margin: 40px 0 0 10px;"><a class="lanke-regular-btn" href="<%=basePath%>login_toLogin">返回揽客</a></p>
          </div>
       </div>
    <p style="width: 100%;position: absolute;bottom: 30px;color:#999;text-align: center;">技术支持：网晶科技“揽客”项目团队</p>

    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    
</body>
</html>