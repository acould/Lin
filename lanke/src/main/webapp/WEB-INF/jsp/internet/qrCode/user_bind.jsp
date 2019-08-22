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
    <title>${internetName}</title>
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
<div class="weic-item-padding30 user_index weic-martop-transmit weic-bgfff">
    <a class="weui-cell  weic-relative user_msg" href="javascript:;">
        <div class="weui-cell__hd weic-relative weic-my-head"
             style="background: url('${result.pdBind.imgurl}')no-repeat center center;background-size:100%">
        </div>
        <div class="weui-cell__bd">
            <p class="weic-my-name">Hi，${result.pdBind.neck_name}</p>
            <p class="weic-user-rank">欢迎来到${result.pdBind.store_name}</p>
        </div>
    </a>
    <div class="up_tips bindMember"></div>
    <div style="text-align: center;font-size: 0.38rem;color: #666;margin: 0.13rem 0 1.2rem 0">
        很抱歉，当前系统检测到<br>您未绑定会员
    </div>
    <div style="padding: 0.8rem 1rem">
        <div class="weic-dent-btn weic-btn-gradientBlue"
             onclick="window.location.href='<%=basePath%>intenetmumber/bindCard?label=qr_bind&computer_name=${result.pdQr.computer_name}&appid=${result.pd.appid}&state=${result.pd.state}&code=${result.pd.code}'"
             style="margin-bottom: 0.8rem">去绑定</div>
        <div class="weic-dent-btn weic-btn-gradientOrange" onclick="window.location.href='<%=basePath%>intenetmumber/Membership0.do'">办会员</div>
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
    /*$(function(){
        FastClick.attach(document.body);
    });*/
</script>

<script type="text/javascript">

</script>
</html>