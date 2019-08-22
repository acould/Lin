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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
<style>
    .auto { width:770px; margin: 0 auto; padding-top: 140px;}
    .left { float:left;}
    .right {float:right; }
    .widthP {  width:370px; margin-top: 20px; margin-bottom: 80px; color:#999;}
    .paddingTop { padding-top: 40px;}
    .btncook2 {padding: 5px 30px; color:#fff; background-color: #41a7f0; border-radius: 4px;text-decoration: none}
    .btncook2:hover {color:#fff!important;text-decoration:none;}
</style>
</head>
<body>
    <div class="auto">
        <div class="left">
            <img src="<%=basePath%>static/login/images/suureder.png" alt="">
        </div>
        <div class="right" style="margin-top: 30px;">
            <h1>修改密码成功</h1>
            <p class="widthP">快去登入，体验揽客吧~</p>
            <p><a href="<%=basePath%>login_toLogin" class="btncook2">去登入</a></p>
        </div>
    </div>
</body>
</html>