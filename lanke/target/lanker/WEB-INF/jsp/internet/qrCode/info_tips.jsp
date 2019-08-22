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
    <title>无效扫码</title>
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
    <style>

    </style>
</head>
<body class="weic weic-bgfff">
<div class="weic-item-padding30 user_index weic-martop-transmit weic-bgfff" >
    <div class="up_tips noWeixin" style="margin-top: 4rem"></div>
    <div style="text-align: center;font-size: 0.38rem;color: #666;margin: 0.3rem 0 1.2rem 0">
        <c:if test="${result.err_type == 'qr_err'}">很抱歉，当前平台扫码无效，请前往<br><font color="red">微信平台扫码</font></c:if>
        <c:if test="${result.err_type == 'qrr_expired_err'}">很抱歉，该二维码已失效</c:if>
        <c:if test="${result.err_type == 'qrr_baned_err'}">很抱歉，该二维码已被禁用</c:if>
        <c:if test="${result.err_type == 'qrr_used_err'}">很抱歉，该二维码已被占用</c:if>
        <c:if test="${result.err_type == 'lanke_client_stop_err'}">很抱歉，揽客客户端已断开连接，扫码上机功能无法使用</c:if>
        <c:if test="${result.err_type == 'bind_store_err'}">会员绑定门店与二维码所在的门店不一致<br><br><br><br>
            <div class="weic-dent-btn weic-btn-gradientBlue"
                 onclick="window.location.href='<%=basePath%>intenetmumber/bindCard?label=qr_bind&computer_name=${result.pdQr.computer_name}&appid=${result.pd.appid}&state=${result.pd.state}&code=${result.pd.code}'"
                 style="margin-bottom: 0.8rem;width:80%;margin:0 auto">重新绑定</div>
        </c:if>
        <c:if test="${result.result == 'false' && result.err_type == null}">${result.message}</c:if>
    </div>
</div>
<p style="text-align: center;font-size: 0.34rem;color: #999;position: absolute;width: 100%;bottom: 1rem;">揽客技术支持</p>
</body>

<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script>
    $(function(){
        FastClick.attach(document.body);
    });

</script>

<script type="text/javascript">

</script>
</html>