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
	<link rel="stylesheet" href="<%=basePath%>static/login/css/awesome-bootstrap-checkbox.css">
	<link rel="stylesheet" href="<%=basePath%>static/login/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/login.css">
	<link rel="stylesheet" href="<%=basePath%>static/login/css/bootstrap.min.css">
	<style>
		h1{margin: 0 auto;font-size: 25px;padding-top: 34px;}
		.inputBoxStyle,.checkboxBoxStyle,h1 {width:340px}
	</style>
</head>
<body class="register">
    <div class="widthRegis">
        <h1 class="move-mine">注册揽客账号</h1>
        <div class="inputBox">
            <form  class="formar" name="userForm" id="userForm" method="post">
                <div class="inputBoxStyle">
                    <i class="reg-icon reg-icon-name"></i>
                    <input type="text" class="inputStyle-reg" placeholder="用户名，4-16个字符" name="USERNAME" id="USERNAME" value="" onblur="registerVerify()">
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <i class="reg-icon reg-icon-password"></i>
                    <input type="password" class="inputStyle-reg" placeholder="密码长度6-20个字符" id="password" name="PASSWORD" value="" onblur="registerVerify()">
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <i class="reg-icon reg-icon-password"></i>
                    <input type="password" class="inputStyle-reg" placeholder="确认密码" id="chkpwd" name="PASSWORD" value="" onblur="registerVerify()">
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <i class="reg-icon reg-icon-phone"></i>
                    <input type="tel" class="inputStyle-reg" placeholder="手机号码" id="PHONE" name="PHONE" value="" pattern="^1[3,5,6,7,8,9][3,5,6,7,8,9]\d{8}$" onblur="registerVerify()">
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <i class="reg-icon reg-icon-email"></i>
                    <input type="text" class="inputStyle-reg" placeholder="邮箱" id="EMAIL" name="EMAIL" value="" onblur="registerVerify()">
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <span style="color:#f67f82;"><input type="button" id="btn" value="获取验证码" onclick="yzmy()" style="background:none;border:none;"/></span>
                    <input type="text" class="inputStylePhone inputStyle-reg" placeholder="输入验证码" name="yzm" value="" id="yzm">
                </div>
                <div class="checkboxBoxStyle checkbox checkbox-danger">
                    <input type="checkbox"  class="styled" name="rememberUser"  id="rememberUser" checked>
                    <label for="rememberUser" style="color:#999;font-size: 13px">
                       	 我已阅读并接受<a href="user_agreement.do"  target="_blank" style="color:#f67f82">用户协议</a>
                    </label>
                </div>
                <div class="inputBoxStyle inputBoxStyled">
                    <!-- <button type="submit" value="Submit" class="btna text-center">注&nbsp;&nbsp;册</button> -->
                    <input type="button" id="zhuce" value="注册" onclick="regSave();" class="btn-reg text-center"/>
                </div>
            </form>
        </div>
    </div>
    <script src="<%=basePath%>static/login/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-public.js"></script>
    <!-- 业务JS -->
    <script src="<%=basePath%>newStyle/js/lk-user.js"></script>
    <script>
    	
    </script>
</body>
</html>