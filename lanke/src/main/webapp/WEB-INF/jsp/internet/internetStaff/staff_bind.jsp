<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>揽客店内服务人员设置</title>
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<style>

</style>
<body class="weic weic-body-bgf3f3f3 weic-parent-mar">
<form id="Form">
    <div id="bind">
    <div class="weui-cells__title">基本信息</div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">您的姓名</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="text"  placeholder="请输入您的姓名" id="name" name="name">
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">接收门店</label></div>
            <div class="weui-cell__bd" onclick="showStore()">
                <input class="weui-input" type="text"  placeholder="请选择您要服务的门店" readonly id="storeName">
                <input id="STORE_ID" name="STORE_ID" type="hidden">
            </div>
        </div>
    </div>
    <div class="weui-cells__title">接收服务项</div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell weui-cell_switch">
            <div class="weui-cell__bd">呼叫服务通知</div>
            <div class="weui-cell__ft">
                <input class="weui-switch" type="checkbox" name="perms" value="user_call">
            </div>
        </div>
        <div class="weui-cell weui-cell_switch">
            <div class="weui-cell__bd">一键投诉通知</div>
            <div class="weui-cell__ft">
                <input class="weui-switch" type="checkbox" name="perms" value="user_complain">
            </div>
        </div>
        <div class="weui-cell weui-cell_switch">
            <div class="weui-cell__bd">购买商品通知</div>
            <div class="weui-cell__ft">
                <input class="weui-switch" type="checkbox" name="perms" value="user_order">
            </div>
        </div>
    </div>
    <div class="weic-bor" style="margin: 1.4rem 0">
        <div class="weic-dent-btn weic-btn-gradientBlue" onclick="save()">确定</div>
    </div>
</div>
</form>
<div id="success" style="display: none">
    <div class="weic-text-center" style="margin-top: 2rem">
        <i class="weui-icon-success weui-icon_msg"></i>
        <p style="font-size: 0.48rem;color: #333;margin-top: 0.4rem">设置成功</p>
        <p style="font-size: 0.34rem;color: #666;">您的微信号将接收会员请求服务通知</p>
    </div>
    <div class="weic-nobody-btn">
        <div class="weic-dent-btn weic-btn-gradientBlue" onclick="wx.closeWindow();">关闭</div>
    </div>
</div>
<div id="weic-bindWk" class="hide">
    <div class="weic-wkCity-list">
        <div class="weic-wkCity-left" id="changeArea-list"> </div>
        <div class="changeCity-list changeCity-hide" id="changeCity-list"> </div>
        <div class="weic-wkCity-right" id="changeWk-list"> </div>
    </div>
    <div class="weic-black-bg" onclick="closeStore()"></div>
</div>

</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/mobile-check.js"></script>
<script src="<%=basePath%>newStyle/js/lk-staff.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script>
    $(function (){
        FastClick.attach(document.body);
    });
</script>
<script type="text/javascript">
    //渲染门店数据
    getStore('<%=basePath%>intenetmumber/loadCityInternet.do');

</script>
</html>
