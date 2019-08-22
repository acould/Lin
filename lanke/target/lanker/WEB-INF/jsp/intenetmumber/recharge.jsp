<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<title>${intenetName}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	 <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge" chrome="1">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
	<!--移动端适配，px转化为rem-->
	<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
	<style>
		.span-span{
			color: #333;
			font-size: 14px;
			background-color: #ffe400;
			font-weight: 600;
			display: inline-block;
			padding: 2px 19px;
			border-radius: 20px;
		}
	</style>
</head>	
<body class="weic-bgfff">
<div class="weic-payHeader-box">
	<div class="weic-pay-header">
		<div id="pay-userbox">
			<div class="weic-bor" style="padding: 0 0.5rem;">
				<div class="weic-pay-bg">
					<h1><i class="iconfont">&#xe620;</i><span id="user_cardId">${pd.CARDED}</span></h1>
					<p style="padding-top: 5px;">当前充值门店： <span class="span-span">${pd.STORE_NAME}</span></p>
					<input type="hidden" value="${pd.STORE_ID}" id="store_id">
					<input type="hidden" value="${pd.internet_id}" id="internet_id">
					<input type="hidden" value="${pd.STORE_NAME}" id="store_name">
					<input type="hidden" value="${pd.CARDED}" id="carded">
					<input type="hidden" value="${pd.CARDID}" id="cardid">
				</div>
			</div>
		</div>
	</div>
	<a href="<%=basePath%>intenetmumber/member"><div class="bindIocn"><i class="iconfont">&#xe628;</i></div></a>
</div>
<div class="weic-pay-setMeal weic-bor" style="margin-top: -0.2rem;">
	<h1 id="pay_title">充值金额</h1>
	<div class="weic-row weic-col-space10" id="agreementList">
	</div>
</div>

<div class="pay-btnBox">
	<div class="weic-bor">
		<p class="weic-pay-agreement">
			<span id="agreement-icon" class=""><i class="iconfont">&#xe6c1;</i></span>
			已阅读并同意<a href="<%=basePath%>indexMember/agreement">《用户充值协议》</a>
		</p>
		<div class="weic-dent-btn weic-btn-gradientBlue" id="toRecharge" onclick="goPay_details('<%=basePath%>indexMember/generateOrder')">立即充值</div>
	</div>
</div>

<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-pay.js"></script>
<script src="<%=basePath%>newStyle/js/lk_wechat/lk_wechat.js"></script>
<script>
    //检测身份证，如果是身份证标星显示
    $(function () {
        var cardId = $("#user_cardId").html().replace(/\s+/g,"")
        var len = cardId.replace(/\s+/g,"").length;
        if(len == 18){
            var str=cardId.replace(/(\d{6})(\d+)(\d{4})/,function(x,y,z,p){
                var i="";
                while(i.length<z.length){i+="*"}
                return y+i+p
            })
            $("#user_cardId").html(str)
        }
    })
	// //检查用户绑定数据是否正确
    // checkSWUser("recharge");
	//
    // //将当前页面状态放入历史中
    // pushHistory2();
    // //点击返回时，触发popstate
    // popstate2();

	$(function(){
		agreement('<%=basePath %>indexMember/listRule');

	});
	


</script>
</body>
</html>
