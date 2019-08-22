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
	<title>支付详情</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	 <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge" chrome="1">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>	
<style>
	.weui-popup__modal {
		background: none!important;
	}
</style>
<body class="weic-body-bgf3f3f3 weic weic-parent-mar" >
	<input type="hidden" value="${status}" id="status">
	<input type="hidden" value="${id}" id="id">
	<input type="hidden" value="${errMsg}" id="errMsg">
	<div class="weic-paydet-box">
		<div class="weui-cells__title weui-cell">
			<div class="weui-cell__bd">
	           	<h3 class="">充值信息</h3>
	        </div>
		</div>
	    <div class="weui-cells">
	        <div class="weui-cell">
	            <div class="weui-cell__bd">
	              	<p class="weic-cell-l">所属门店</p>
	            </div>
	            <div class="weui-cell__ft weic-cell-r">${store_name}</div>
	        </div>
	        <div class="weui-cell">
	             <div class="weui-cell__bd">
	              	 <p class="weic-cell-l">支付金额</p>
	             </div>
	             <div class="weui-cell__ft weic-cell-r-red">￥<span id="pay_actualbalance">${pay_actualbalance}</span></div>
	         </div>
	    </div>
	</div>
	<div class="weic-paydet-box">
	 	<div class="weui-cells__title weui-cell">
			<div class="weui-cell__bd">
	           	<h3 class="">支付方式</h3>
	        </div>
		</div>   
	        <div class="weui-cell">
	            <div class="weui-cell__bd">
	              	<p class="weic-cell-l"><i class="iconfont weixinPay-icon">&#xe600;</i>微信支付</p>
	            </div>
	            <div class="weui-cell__ft weic-cell-r"><i class="iconfont payActive-icon">&#xe604;</i></div>
	        </div>
	    </div>
	</div>
	<div class="weic-bor" style="margin:2rem 0;">
		<c:if test="${third_payment == '1'}">
			<div class="weic-dent-btn weic-btn-gradientBlue" onclick="payResult('<%=basePath%>indexMember/publicpay')" id="pay-btn">确定支付</div>
		</c:if>
		<c:if test="${third_payment == '2'}">
			<div class="weic-dent-btn weic-btn-gradientBlue" onclick="payResult('<%=basePath%>indexMember/jlPay')" id="pay-btn">确定支付</div>
		</c:if>
	</div>

	<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-pay.js"></script>
<script>

    var id = $("#id").val();
    init('<%=basePath %>indexMember/recharge','<%=basePath %>indexMember/publicpay',id,'<%=basePath%>intenetmumber/index');
    
    var url = '<%=basePath %>indexMember/recharge';
	$(function(){  
	    pushHistory();  
	    var bool=false;  
	    setTimeout(function(){  
	        bool=true;  
	    },100);  
	    window.addEventListener("popstate", function(e) {  
	        if(bool){  
	        	window.location.href= url;
	        } 
	    }, false);  
	});  
	
 	function pushHistory() {
	    var state = {
	        title: "myCenter",
	        url: url
	    };
	    window.history.pushState(state, state.title, state.url);
	} 
    
</script>
</body>
</html>