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
    <meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=0.5, maximum-scale=1.0, minimum-scale=0.1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>揽客帮助中心</title>
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
    <link rel="stylesheet" href="<%=basePath%>static/login/css/login.css">
	<link rel="stylesheet" href="<%=basePath%>static/login/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>

</head>
<body class="cp-bj-color" style="padding:0;border:none;">
	<!--头部-->
   <div class="container-fluid borBot la-nav-bg">
        <header class="lk-container">
            <div class="lanke-logo"></div>
            <span class="lanke-ggy">客服热线：4000965099 </span>
            <p class="nav_p">
                <a href="<%=basePath%>login_toLogin" class="active">首页</a>
                <a href="<%=basePath%>login_toLogin" class="click-a">揽客介绍</a>
                <a href="<%=basePath%>register/about.do" style="margin: 0" class="">关于我们</a>
            </p>
        </header>
    </div>
	
    <div class="container" style="position: relative;margin-top: 13px">
        <div class="row">
            <div class="" style="position: relative;padding: 0">
                <div class="fixedleft" id="leftList" style="position: fixed;top:84px">
                    <div class="p-left-width">
                    	<a class="left-p" href="<%=basePath%>register/pay_set.do">如何设置充值</a>
                    	<a class="left-p left-p-active" href="<%=basePath%>register/user_pay.do">会员如何充值</a>
                    	<a class="left-p" href="<%=basePath%>register/account_check.do">如何对账</a>
                    	<a class="left-p" href="<%=basePath%>register/user_marketing.do">如何会员营销</a>
                        <a class="left-p" href="user_agreement.do">用户协议</a>
                    </div>
                </div>
            </div>
            <div class="col-md-12 col-xs-12 widthmax" style="background-color: #fff;padding: 0;margin-left: 162px" >
            	<!-- <h1 class="lankeTitle">揽客充值</h1> -->
                <div class="pdbianju" style="padding:30px;">
                	<h1 class="content-title-r" style="margin-bottom:20px;">会员如何进行网费充值：</h1>
                	<div class="content-img center">
                		<img src="<%=basePath%>newStyle/images/newerGuide/user_pay1.png" ondragstart="return false;" width="880px">
                	</div>
                	<div class="content-img center">
                		<img src="<%=basePath%>newStyle/images/newerGuide/user_pay2.png" ondragstart="return false;" width="880px">
                	</div>
                	<div class="content-img center">
                		<img src="<%=basePath%>newStyle/images/newerGuide/user_pay3.png" ondragstart="return false;" width="880px">
                	</div>
                </div>
        	</div>
        </div>
    </div>
    <script>
        $(function () {
            $(".widthmax").width();
            var widths = $(".widthmax").width()-162;
            $(".widthmax").width(widths);
            
            $(window).scroll(function (){
				if ($(window).scrollTop() >= 84) {
					$("#leftList").css({position: "fixed",top:"0"});
				}else{
					$("#leftList").css({position: "fixed",top:"84px"});
				}
            })
        })

    </script>
</body>
</html>