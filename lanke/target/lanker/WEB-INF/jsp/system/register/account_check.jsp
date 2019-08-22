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
                    	<a class="left-p left-p-active" href="<%=basePath%>register/account_check.do">如何对账</a>
                    	<a class="left-p" href="<%=basePath%>register/user_marketing.do">如何会员营销</a>
                        <a class="left-p" href="user_agreement.do">用户协议</a>
                    </div>
                </div>
            </div>
            <div class="col-md-12 col-xs-12 widthmax" style="background-color: #fff;padding: 0;margin-left: 162px" >
                <div class="pdbianju">
                	 <h1 class="content-title-r">如何对账</h1>
                	 <h3 class="content-title-x" style="font-size:15px;">揽客在线充值的资金，是通过线上电子资金入账，不与吧台现金收入产生任何的交叉，可以视作网吧老板的另外一份收入。</h3>
                	 <p class="" ><font color="#e44548">一、</font>通过揽客网吧管理后台->充值报表，查询所需时间段内，通过揽客在线充值所收入的资金。</p>
                	 <div class="content-img">
               			<img src="<%=basePath%>newStyle/images/newerGuide/account_check1.jpg" ondragstart="return false;">
               		</div>
               		<p class="" ><font color="#e44548">二、</font>登入嘉联支付平台，进行对账，查询每笔交易资金的结算到账情况。</p>
					<p class="" ><font color="#e44548">1）、</font>商户审核通过后，在审核通过页面获取嘉联支付平台的登入账号以及初始密码。进入<a href="https://www.jlpay.com/">嘉联支付平台</a>，选择“我是商户”。</p>
               		<div class="content-img">
               			<img src="<%=basePath%>newStyle/images/newerGuide/duizhang1.png" ondragstart="return false;" width="700px">
               		</div>
               		<p class="" ><font color="#e44548">2）、</font>使用“揽客审核通过页面的嘉联账号”进行登入，初始密码为123456 </p>
               		<div class="content-img">
               			<img src="<%=basePath%>newStyle/images/newerGuide/duizhang2.png" ondragstart="return false;" width="700px">
               		</div>
               		<p class="" ><font color="#e44548">3）、</font>初次登入需要修改密码，按照要求进行修改</p>
               		<div class="content-img">
               			<img src="<%=basePath%>newStyle/images/newerGuide/duizhang3.png" ondragstart="return false;" width="700px">
               		</div>
					<p class="" ><font color="#e44548">4）、</font>修改密码完成后，主页里可以查看账户余额。点击收支记录进行线上充值对账。</p>
					<div class="content-img">
						<img src="<%=basePath%>newStyle/images/newerGuide/duizhang4.png" ondragstart="return false;" width="700px">
					</div>
					<p class="" ><font color="#e44548">4）、</font>可根据订单类型、交易时间、订单号进行查询</p>
					<div class="content-img">
						<img src="<%=basePath%>newStyle/images/newerGuide/duizhang5.png" ondragstart="return false;" width="700px">
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