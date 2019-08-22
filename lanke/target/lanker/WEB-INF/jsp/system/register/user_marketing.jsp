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
                    	<a class="left-p" href="<%=basePath%>register/user_pay.do">会员如何充值</a>
                    	<a class="left-p" href="<%=basePath%>register/account_check.do">如何对账</a>
                    	<a class="left-p left-p-active" href="<%=basePath%>register/user_marketing.do">如何会员营销</a>
                        <a class="left-p" href="user_agreement.do">用户协议</a>
                    </div>
                </div>
            </div>
            <div class="col-md-12 col-xs-12 widthmax" style="background-color: #fff;padding: 0;margin-left: 162px" >
                <div class="pdbianju">
                	<h1 class="content-title-r">如何玩转活动群发</h1>
                	<h3 class="content-title-x">1、设置群发</h3>
                	<div class="content-msg">
                		<p class="" ><font color="#e44548">1)、</font>揽客网吧管理后台->营销管理->活动群发，选择“群发消息”或“群发卡券”</p>
                		<div class="content-img">
                			<img src="<%=basePath%>newStyle/images/newerGuide/user-marketing1.jpg" ondragstart="return false;">
                		</div>
                		<p class="" ><font color="#e44548">2)、</font>根据揽客系统的引导，一步步设置您的群发。</p>
                		<div class="content-img">
                			<img src="<%=basePath%>newStyle/images/newerGuide/user-marketing2.jpg" ondragstart="return false;">
                		</div>
                	</div>
                	<h3 class="content-title-x">2、粉丝群发和会员群发有何区别？</h3>
                	<div class="content-msg">
                		<p class="" ><font color="#e44548">1)、</font>群发范围为“粉丝“，关注您公众号的所有人，无论是否绑定了会员、无论是从哪家门店关注的，都会收到您的群发。这样的大范围群发，适用于在所有门店统一举行通用性的营销活动、希望所有顾客都能关注到活动时。</p>
                		<div class="content-img">
                			<img src="<%=basePath%>newStyle/images/newerGuide/user-marketing3.jpg" ondragstart="return false;">
                		</div>
                		<p class="" ><font color="#e44548">2)、</font>群发范围为“会员”，则仅有绑定过Pubwin ID的门店可以向会员群发。会员群发是揽客独特的精准营销功能，适用于对单店或特定会员群组，实现有针对性的营销推送。</p>
                		<div class="content-img">
                			<img src="<%=basePath%>newStyle/images/newerGuide/user-marketing4.jpg" ondragstart="return false;">
                		</div>
                		<p><strong><font color="#e44548">注意：</font>若群发次数过多，易被微信判断为过度营销、骚扰用户，有被微信关闭群发功能的风险，请谨慎使用！揽客建议发送频率不超过一周一次。</strong></p>
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