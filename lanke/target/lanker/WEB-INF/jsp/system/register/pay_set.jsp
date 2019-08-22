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
                    	<a class="left-p left-p-active" href="<%=basePath%>register/pay_set.do">如何设置充值</a>
                    	<a class="left-p" href="<%=basePath%>register/user_pay.do">会员如何充值</a>
                    	<a class="left-p" href="<%=basePath%>register/account_check.do">如何对账</a>
                    	<a class="left-p" href="<%=basePath%>register/user_marketing.do">如何会员营销</a>
                        <a class="left-p" href="user_agreement.do">用户协议</a>
                    </div>
                </div>
            </div>
            <div class="col-md-12 col-xs-12 widthmax" style="background-color: #fff;padding: 0;margin-left: 162px" >
            	<!-- <h1 class="lankeTitle">揽客充值</h1> -->
                <div class="pdbianju" style="padding:30px;">
                	<h1 class="content-title-r">网吧如何进行充值设置：</h1>
                	<div class="content-msg">
                		<p class="" ><font color="#e44548">1)、</font>进入揽客后台-揽客充值-充值设置，点击新增充值规则，打开设置页面。</p>
                		<div class="content-img">
                			<img src="<%=basePath%>newStyle/images/newerGuide/pay_set1.png" ondragstart="return false;">
                		</div>
                		<p class="" ><font color="#e44548">2)、</font>选择需要批量设置规则的门店，已绑定计费系统并开通在线支付的门店才能选择。
						若选中的门店已设置充值规则，就不需要新增，到规则列表中去修改规则即可。</p>
						<div class="content-img">
                			<img src="<%=basePath%>newStyle/images/newerGuide/pay_set2.png" width="500px" ondragstart="return false;">
                		</div>
                		<p class="" ><font color="#e44548">3)、</font>点击新增规则，在下方的表格中填写充值、赠送金额，每家门店最多可设置9条。
							您可以为规则选用标签，也可以自己生成有个性化的标签。 设置后的规则可以在左方的手机图标中预览效果。</p>
						<div class="content-img">
                			<img src="<%=basePath%>newStyle/images/newerGuide/pay_set3.png" ondragstart="return false;">
                		</div>
                		<p class="" ><font color="#e44548">4)、</font>保存设置后，规则立即生效，您可以在规则列表中按门店查看并修改规则。</p>
                		<div class="content-img">
                			<img src="<%=basePath%>newStyle/images/newerGuide/pay_set4.png" ondragstart="return false;">
                		</div>
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