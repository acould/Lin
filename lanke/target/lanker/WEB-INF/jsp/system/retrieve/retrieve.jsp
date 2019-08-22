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
    <title>揽客找回密码</title>
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/login.css">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/bootstrap.min.css">
</head>
<body class="bg-colorf4f4f4">
    <div class="widthRegis register" style="width:840px;margin-top: 30px">
        <div class="titleBox">
            <i class="ret-logo"></i>
            <p class="text-center">找回揽客密码</p>
            <div class="text-right margina"><a href="<%=basePath%>login_toLogin" class="regPtexta regPadrig">返回首页</a></div>
        </div>
        <div class="inputBox hide1">
            <form action="zhaohui.do" name="userForm" id="userForm" method="post" class="formar" style="padding-top: 42px;">
                <div class="inputBoxStyle">
                    <i class="reg-icon reg-icon-name"></i>
                    <input type="text" class="inputStyle" placeholder="原登录名，2-8个汉字/4-16个字符" name="USERNAME" id="USERNAME" value="" >
                </div>
                 <div class="inputBoxStyle inputBoxStyled">
                    <i class="reg-icon reg-icon-phone"></i>
                    <input type="tel" class="inputStyle" placeholder="原绑定手机号" id="PHONE" name="PHONE" value="" pattern="^1[3,5,6,7,8,9][3,5,6,7,8,9]\d{8}$" >
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <i class="reg-icon reg-icon-password"></i>
                    <input type="password" class="inputStyle" placeholder="新密码长度6-20个字符" id="password" name="PASSWORD" value="" >
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <i class="reg-icon reg-icon-password"></i>
                    <input type="password" class="inputStyle" placeholder="确认新密码" id="chkpwd" name="PASSWORD" value="" >
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <span><input type="button" id="btn" value="获取验证码" onclick="yzmy()" style="background:none;border:none;"/></span>
                    <input type="text" class="inputStylePhone inputStyle" placeholder="输入验证码" name="yzm" value="" id="yzm" >
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                	<input type="button" id="zhuce" value="确认" onclick="retSave();" class="btn-affirm text-center"/>
                </div>
            </form>
        </div>
        <div class="lanke-footer">
			© <a href="http://lanke8.cc">lanke8.cc</a>&nbsp;揽客
		</div>
    </div>
    <script src="<%=basePath%>static/login/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <!-- 业务JS -->
    <script src="<%=basePath%>newStyle/js/lk-user.js"></script>
</body>
</html>