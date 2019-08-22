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
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <title>会员中心</title>
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
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<body class="weic  weic-body-bgf3f3f3 user">
<div style="padding-bottom: 1.34rem">
    <div class="weic-bgfff" style="box-shadow: 0 2px 20px rgba(0,0,0,0.03);">
        <a class="weui-cell  weic-relative" href="javascript:;">
            <div class="weui-cell__hd weic-relative weic-my-head"
                 style="background: url(${user.IMGURL})no-repeat center center;background-size:100%">
            </div>
            <div class="weui-cell__bd">
                <p class="weic-my-name">${user.NECK_NAME}</p>
                <p class="weic-user-rank">会员等级：
                    <sapn>${user.member_level}</sapn>
                </p>
            </div>
        </a>
        <div class="weic-userMsg-box" style="margin-bottom: 0.7rem;">
            <div class="weic-userCard-bg">
                <div class="weic-user-top">
                    <p>会员卡号</p>
                    <h1 id="user_cardId">
                        <c:if test='${user.CARDED == null}'> 未绑定 </c:if>
                        <c:if test='${user.CARDED != null}'> ${user.CARDED}</c:if>
                    </h1>
                    <div class="weic-user-store">
                        <i></i>
                        <p>
                            <c:if test='${user.CARDED == null}'>当前未绑定门店</c:if>
                            <c:if test='${user.CARDED != null}'>${user.STORE_NAME}</c:if>
                        </p>
                    </div>
                    <c:if test='${user.CARDED == null}'>
                        <span class="change-store-btn" onclick="window.location.href='<%=basePath%>intenetmumber/bindCard?label=user'">绑定门店</span>
                    </c:if>
                    <c:if test='${user.CARDED != null}'>
                        <span class="change-store-btn" onclick="window.location.href='<%=basePath%>intenetmumber/member?label=user'">更换门店</span>
                    </c:if>
                </div>
                <div class="weic-user-bottom">
                    <div class="weui-cell weic-borderNone" style="padding: 0">
                        <div class="weui-cell__bd weic-border-left">
                            <p class="weic-text-center weic-num-p"><c:if test='${user.reward == null}'> 0.00 </c:if><c:if test='${user.reward != null}'> ${user.reward}</c:if></p>
                            <p class="weic-text-center weic-jf-p">奖励金额</p>
                        </div>
                        <div class="weui-cell__bd weic-border-left">
                            <p class="weic-text-center weic-num-p"><c:if test='${user.INTEGRAL == null}'> 0 </c:if><c:if test='${user.INTEGRAL != null}'> ${user.INTEGRAL}</c:if></p>
                            <p class="weic-text-center weic-jf-p">揽客积分</p>
                        </div>
                        <div class="weui-cell__bd weic-border-left">
                            <p class="weic-text-center weic-num-p"><c:if test='${user.usable_integral == null}'> 0 </c:if><c:if test='${user.usable_integral != null}'> ${user.usable_integral}</c:if></p>
                            <p class="weic-text-center weic-jf-p">网咖积分</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="weic-index-navIcon" style="padding: 0 0 0.333rem 0">
            <div class="weui-flex">
                <div class="weui-flex__item weic-text-center" onclick="window.location.href='<%=basePath%>myMember/mySpoil'">
                    <div class="weic-my-icon my-lottery-icon"></div>
                    <p>我的抽奖</p>
                </div>
                
                <div class="weui-flex__item weic-text-center weic-relative" onclick="window.location.href='<%=basePath%>myMember/myCard'">
                    <div class="weic-my-icon my-card-icon" id="cardSize">

                    </div>
                    <p>我的卡券</p>
                </div>
                
                <div class="weui-flex__item weic-text-center" onclick="window.location.href='<%=basePath%>myMember/myDingDan'">
                    <div class="weic-my-icon my-order-icon"></div>
                    <p>我的订单</p>
                </div>
                <div class="weui-flex__item weic-text-center" onclick="window.location.href='<%=basePath%>wxMatches/goMyEnroll'">
                    <div class="weic-my-icon my-sign-icon"></div>
                    <p>我的报名</p>
                </div>
            </div>
        </div>
    </div>
    <div class="weic-bgfff my-icon-list" style="margin-top: 0.266rem;box-shadow: 0 2px 20px rgba(0,0,0,0.03)">
        <a class="weui-cell weui-cell_access weic-relative show-alert" href="javascript:;">
            <div class="weui-cell__hd"><i class="weic-cell-icon my-booker-icon"></i></div>
            <div class="weui-cell__bd">
                <p>我的订座</p>
            </div>
            <div class="weui-cell__ft"></div>
        </a>
        <a class="weui-cell weui-cell_access weic-relative" href="<%=basePath%>myMember/myLm">
            <div class="weui-cell__hd"><i class="weic-cell-icon my-complaint-icon"></i></div>
            <div class="weui-cell__bd">
                <p>我的投诉</p>
            </div>
            <div class="weui-cell__ft">
                <span id="lmSize" class="weic-badge weic-hide"></span>
            </div>
        </a>
        <a class="weui-cell weui-cell_access weic-relative" href="<%=basePath%>myMember/myStatements">
            <div class="weui-cell__hd"><i class="weic-cell-icon my-account-icon"></i></div>
            <div class="weui-cell__bd">
                <p>我的明细</p>
            </div>
            <div class="weui-cell__ft"></div>
        </a>
        <a class="weui-cell weui-cell_access weic-relative" href="<%=basePath%>myMember/internetRecord">
            <div class="weui-cell__hd"><i class="weic-cell-icon my-internet-icon"></i></div>
            <div class="weui-cell__bd">
                <p>上网记录</p>
            </div>
            <div class="weui-cell__ft"></div>
        </a>
        <a class="weui-cell weui-cell_access weic-relative" href="javascript:;" id="unbind">
            <div class="weui-cell__hd"><i class="weic-cell-icon unbind-user"></i></div>
            <div class="weui-cell__bd">
                <p>解绑账号</p>
            </div>
            <div class="weui-cell__ft"></div>
        </a>
    </div>
</div>
<%--底部菜单--%>
<%@ include file="bottom_navigation.jsp"%>

</body>


<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="http://at.alicdn.com/t/font_622913_88brb16egs2u766r.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
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

    $(document).on("click", ".show-alert", function () {
        $.alert("程序员大哥正在飞速开发中，敬请期待！");
    });

    loadMyData();
    function loadMyData() {
        open_loading("加载中");
        $.post('<%=basePath%>myMember/loadUserCenter.do', '', function (res) {
            if(res.result == "true"){
                if(res.data.cardSize > 0){
                    $("#cardSize").html('<span class="weic-card-lable">'+res.data.cardSize+'张</span>');
                }
                if(res.data.lmSize != 0){
                    $("#lmSize").removeClass("weic-hide");
                }
            }
        });
        layer.closeAll();
    }
    var url = "<%=basePath%>intenetmumber/unbindCard.do";
    $("#unbind").click(function (){
        confirm("是否确定解绑当前账号", function () {
            $.post(url, '', function (res){
                message(res.errmsg);
                if(res.errcode == 0){
                    setTimeout(function () {
                        location.reload();
                    }, 800)
                }
            })
        })
    })
</script>
</html>