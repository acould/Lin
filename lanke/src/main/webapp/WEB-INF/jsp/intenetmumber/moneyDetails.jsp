<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>明细详情</title>
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
    <style>

    </style>
</head>
<body class="weic weic-money">
<div class="weic-full" style="padding: 0.8rem 0;">
    <c:if test="${recharge!=null}"><div class="weic-bg-blue weic-money-leble weic-mauto">充值</div></c:if>
	<c:if test="${(result.flow_type == '16' || result.flow_type == '8') && result.amount > 0}"><div class="weic-bg-blue weic-money-leble weic-mauto">充值</div></c:if>
	<c:if test="${result.memo=='揽客优惠券'}"><div class="weic-bg-orange weic-money-leble weic-mauto">奖励</div></c:if>
	<c:if test="${result.flow_type == '2' || result.flow_type == '4' || result.flow_type == '32'}"><div class="weic-bg-green weic-money-leble weic-mauto">消费</div></c:if>
    <h1 class="weic-money-sum weic-text-center <c:if test="${(((result.flow_type == '16' || result.flow_type == '8') && result.amount > 0) || result.memo=='揽客优惠券') || recharge!=null}">weic-red</c:if>" style="font-size: 1rem">
	<c:if test="${result.amount > 0}">+${result.amount}</c:if>
	${result.amount < 0 ?result.amount:""}
	<c:if test="${result.reward > 0 && result.amount == 0}">+${result.reward}</c:if>
	${result.reward < 0 ?result.reward:""}
	<c:if test="${recharge!=null}">+${recharge.rincipal_balance}</c:if>
	</h1>
    <p class="weic-money-time weic-text-center">${recharge == null?"交易成功":"充值处理中，在确认交易成功前请勿重复支付"}</p>
</div>
<div>
    <a class="weui-cell  weic-relative" href="javascript:;">
        <div class="weui-cell__bd">
            <p class="weic-money-title weic-color666">明细类型</p>
        </div>
        <div class="weic-cell-ft">
            <span class="weic-money-title weic-color666">${result.memo}${result.flow_type == '8'?"收银端充值":""}${recharge!=null?"揽客充值":""}</span>
        </div>
    </a>
    <c:if test="${result.memo!='揽客优惠券' && result.flow_type != '2' && result.flow_type != '4' && result.flow_type != '32'}">
    <a class="weui-cell  weic-relative" href="javascript:;">
        <div class="weui-cell__bd">
            <p class="weic-money-title weic-color666">奖励金额</p>
        </div>
        <div class="weic-cell-ft">
            <span class="weic-money-title weic-color666">${result.reward}${recharge!=null?recharge.reward_balance:""}</span>
        </div>
    </a>
    </c:if>
    <a class="weui-cell  weic-relative" href="javascript:;">
        <div class="weui-cell__bd">
            <p class="weic-money-title weic-color666">交易对象</p>
        </div>
        <div class="weic-cell-ft">
            <span class="weic-money-title weic-color666">${result.store_name}${recharge!=null?recharge.store_name:""}</span>
        </div>
    </a>
    <c:if test="${result.flow_type != '2' && result.flow_type != '4' && result.flow_type != '32'}">
    <a class="weui-cell  weic-relative" href="javascript:;">
        <div class="weui-cell__bd">
            <p class="weic-money-title weic-color666">付款方式</p>
        </div>
        <div class="weic-cell-ft">
            <span class="weic-money-title weic-color666">
			${result.memo=="揽客充值"?"微信支付":""}
			${result.memo=="揽客优惠券"?"揽客优惠券支付":""}
			${result.flow_type == '8'?"收银台支付":""}
			${recharge!=null?"微信支付":""}
			</span>
        </div>
    </a>
    </c:if>
    <a class="weui-cell  weic-relative" href="javascript:;">
        <div class="weui-cell__bd">
            <p class="weic-money-title weic-color666">创建时间</p>
        </div>
        <div class="weic-cell-ft">
            <span class="weic-money-title weic-color666">${result.flow_time}${recharge!=null?recharge.createtime:""}</span>
        </div>
    </a>
    <c:if test="${result.flow_type != '2' && result.flow_type != '4' && result.flow_type != '32'}">
    <a class="weui-cell  weic-relative" href="javascript:;">
        <div class="weui-cell__bd">
            <p class="weic-money-title weic-color666">订单号</p>
        </div>
        <div class="weic-cell-ft">
            <span class="weic-money-title weic-color666">${result.order_id}${recharge!=null?recharge.merOrderId:""}</span>
        </div>
    </a>
    </c:if>
</div>
<a href="tel://057188382520"><p class="weic-query">对订单有疑问？</p></a>
</body>

<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="http://at.alicdn.com/t/font_622913_aswvqplxm0lik9.js"></script>


<script>
    $(document).on("click", ".show-alert", function () {
        $.alert("程序员大哥正在飞速开发中，敬请期待！");
    });
    var date = new Date;
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var month1 = date.getMonth()
    var month2 = date.getMonth() - 1
    month = (month < 10 ? "0" + month : month);
    month1 = (month1 < 10 ? "0" + month1 : month1);
    month2 = (month2 < 10 ? "0" + month2 : month2);
    $("#weic-time").val(month)
    $("#weic-time").picker({
        title: '当前只能查询最近三个月',
        cols: [
            {textAlign: 'center', values: [month2, month1, month],}
        ],
        onClose: function (p, v, dv) {
            var str = $("#weic-time").val()
            str = str.replace(/\s+/g, "");
            $("#weic-ym").text(str)
        }
    });

    ${result}

</script>
</html>