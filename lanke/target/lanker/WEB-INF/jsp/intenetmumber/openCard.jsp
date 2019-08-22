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
    <title>办理会员</title>
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<style>
    body {background: #fff!important;}
    #uesr_banner {
        border-radius: 0;
    }
    #uesr_banner .membership {
        background: url("<%=basePath%>newStyle/images/membership.png")no-repeat center center;
        background-size: 105%;
    }
</style>
<body class="weic bingCard">
<div id="uesr_banner">
    <div class="membership">
        <h1>0元办会员再送一张10元上网券 , 超值！</h1>
    </div>
</div>
<form action="" id="form">
    <input type="hidden" id="STORE_ID" name="STORE_ID" value="${pdBind.STORE_ID}" data-isblur="msg.store_id($(this))" data-group-state="false">
    <div class="weic-bindCard-body">
        <div class="weic-bindMsg-item" onclick="showStore()" style="margin-top: 0.7rem">
            <i class="store"></i>
            <input type="text" placeholder="请选择门店" readonly value="${pdBind.STORE_NAME}" id="storeName">
        </div>
        <div class="weic-bindMsg-item">
            <i class="name"></i>
            <input type="text" placeholder="请输入姓名" name="name" id="name" data-group-state="false" data-isblur="msg.name($(this))">
        </div>
        <div class="weic-bindMsg-item">
            <i class="card"></i>
            <input type="text" placeholder="请输入身份证" id="sfz" name="sfz" data-isblur="msg.sfz($(this))" data-group-state="false">
        </div>

        <div class="weic-bindMsg-item">
            <i class="phone"></i>
            <input type="text" placeholder="请输入手机号" id="phone" name="phone" value="${pdBind.PHONE}" data-isblur="msg.phone($(this))" data-group-state="false">
        </div>
        <div class="weic-bindMsg-item">
            <i class="msg"></i>
            <input type="text" placeholder="请输入验证码"  id="verificationCode" name="verificationCode" data-isblur="msg.code($(this))"  data-group-state="false">
            <a class="btn-send" data-url="<%=basePath%>intenetmumber/getcode.do" id="getcode">获取验证码</a>
        </div>
        <div class="weic-bindMsg-item">
            <div class="weic-dent-btn weic-btn-gradientBlue" onclick="do_member('<%=basePath%>intenetmumber/Membership1.do')" id="save">确定办理</div>
        </div>
    </div>
</form>
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
<script src="<%=basePath%>newStyle/weixin/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/roomes.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/mobile-check.js"></script>
<script type="text/javascript">
    $(function() {
        FastClick.attach(document.body);
    });
    //渲染门店数据
    getStore('<%=basePath%>intenetmumber/loadCityInternet.do');

    function do_member(url) {
        if( msg.checkstate()){//检查所有状态
            $("#save").attr("onclick"," ");
            $.ajax({
                type: "POST",
                url: url,
                data: $("#form").serialize(),
                dataType: 'json',
                beforeSend:beforeSend,
                success: function(data){
                    layer.closeAll();
                    $("#save").attr("onclick","bindSave('"+url+"');");
                    if(data.message == "success"){
                        window.location.href = '<%=basePath%>intenetmumber/succeed';
                    }else if(data.message = "false"){
                        message(data.result);
                    }
                },
                error: function(){
                    layer.closeAll();
                    message("系统繁忙，请稍后再试");
                    $("#save").attr("onclick","bindSave('"+url+"');");
                }
            });
        }
    }
</script>
</html>
