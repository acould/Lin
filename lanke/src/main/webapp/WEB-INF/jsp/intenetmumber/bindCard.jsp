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
    <title>绑定会员</title>
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
	</style>
<body class="weic bingCard">
<input type="hidden" value="<%=path%>${backurl}" id="backurl">
<div class="weic-bindCard-header">
    <div class="weui-cell  weic-relative" style="padding: 0.75rem 0rem">
        <div class="weui-cell__hd weic-relative weic-my-head"
             style="background: url(${user.IMGURL})no-repeat center center;background-size:100%">
        </div>
        <div class="weui-cell__bd">
            <p class="weic-my-name">${user.NECK_NAME}</p>
            <p class="weic-cardbind-tip">
                <c:if test="${pdBind.STORE_ID == null || label == 'rebind'}">
                    <sapn>请绑定会员</sapn>
                </c:if>
                <c:if test="${pdBind.STORE_ID != null && label != 'rebind'}">
                    <span style="font-size: 0.45rem" id="user_cardId">${pdBind.CARDED}</span>
                </c:if>
            </p>
        </div>
    </div>
</div>
<form action="" id="form">
    <input type="hidden" id="STORE_ID" name="STORE_ID" value="${pdBind.STORE_ID}" data-isblur="msg.store_id($(this))" data-group-state="false">
    <div class="weic-bindCard-body">
        <div class="weic-bindMsg-item" onclick="showStore()">
            <i class="store"></i>
            <input type="text" placeholder="请选择门店" readonly value="${pdBind.STORE_NAME}" id="storeName">
        </div>

        <c:if test="${pdBind.STORE_ID == null || label == 'rebind'}">
            <div class="weic-bindMsg-item">
                <i class="name"></i>
                <input type="text" placeholder="请输入姓名" name="name" id="name" data-group-state="false" data-isblur="msg.name($(this))">
            </div>
            <div class="weic-bindMsg-item">
                <i class="card"></i>
                <input type="text" placeholder="请输入会员卡号" id="sfz" name="sfz" data-isblur="msg.sfz($(this))" data-group-state="false">
            </div>
        </c:if>


        <div class="weic-bindMsg-item">
            <i class="phone"></i>
            <input type="text" placeholder="请输入手机号" id="phone" name="phone" value="${pdBind.PHONE}" data-isblur="msg.phone($(this))" data-group-state="false">
        </div>
        <div class="weic-bindMsg-item">
            <i class="msg"></i>
            <input type="text" placeholder="请输入验证码"  id="verificationCode" name="verificationCode" maxlength="6"
                   data-isblur="msg.code($(this))"  data-group-state="false">
            <a class="btn-send" data-url="<%=basePath%>intenetmumber/getcode.do" id="getcode">获取验证码</a>
        </div>
        <div class="weic-bindMsg-item">
            <c:if test="${pdBind.STORE_ID == null || label == 'rebind'}">
                <div class="weic-dent-btn weic-btn-gradientBlue" onclick="bindSave('<%=basePath%>intenetmumber/bindings.do')" >确定绑定</div>
				<c:if test="${view.typeo=='1'}">
                <p class="weic-text-center" style="margin-top: 0.8rem;font-size: 0.38rem"><a href="<%=basePath%>intenetmumber/Membership0" style="color:#2283e5">没有会员？去办理</a></p>
				</c:if>
            </c:if>
            <c:if test="${pdBind.STORE_ID != null && label != 'rebind'}">
                <div class="weic-dent-btn weic-btn-gradientBlue" onclick="bindSave('<%=basePath%>intenetmumber/changeCard.do')" >确定更换</div>
            </c:if>
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
	$(function() {
    	FastClick.attach(document.body);
  	});
	//渲染门店数据
    getStore('<%=basePath%>intenetmumber/loadCityInternet.do');

</script>
</html>
