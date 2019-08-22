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
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
    <style>
        .signIn {
            width: 100%;
            height: 9.2rem;
            background: url("<%=basePath%>newStyle/weixin/images/qiandao-bg.png") no-repeat center center;
            background-size: 125%;
        }
        .signIn_box .layui-m-layercont {
            padding: 0;
        }
        .signIn span {
            line-height: 10.5rem;
            font-size: 0.7rem;
            font-weight: 600;
        }
    </style>
</head>
<body class="weic weic-body-bgf3f3f3 index">

<c:if test="${not empty cardGrab && cardGrab.cardState !=4}">
    <div class="weic-timeCard-tip">
        <div class="timeCard-box">
            <a class="weui-cell" href="javascript:void(0);">
                <div class="weui-cell__hd"><i class="inform_icon"></i></div>
                <div class="weui-cell__bd"
                     onclick="window.location.href='<%=basePath%>indexMember/benefits.do?FAV_TYPE=GRAB'">
                    <div class="textScrolling">
                        <ul>
                            <c:if test="${cardGrab.cardState == 5}">
                                <li>
                                    正在抢：${cardGrab.garbStart_time}准点开抢${cardGrab.SUB_TITLE}，仅${cardGrab.QUANTITY +cardGrab.cardSum}份
                                </li>
                            </c:if>
                            <c:if test="${cardGrab.cardState == 3}">
                                <li>
                                    即将开抢：${cardGrab.garbStart_time}准点开抢${cardGrab.SUB_TITLE}，仅${cardGrab.QUANTITY+cardGrab.cardSum}份
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
                <div class="weui-cell__ft" onclick="removeTips()"><i class="close_icon"></i></div>
            </a>
        </div>
    </div>
</c:if>
<div style="padding-bottom: 1.34rem">
    <div class="weic-headbg-box">
        <img src="<%=basePath%>newStyle/weixin/images/index-head-bg.png" alt="" width="100%">
    </div>
    <div class="weui-cell  weic-relative weic-head-msg" style="padding: 0.266rem 0.45rem;">
        <div class="weui-cell__hd weic-relative weic-my-head"
             style="background: url(${user.IMGURL})no-repeat center center;background-size:100%">
        </div>
        <div class="weui-cell__bd" onclick="window.location.href='<%=basePath %>myMember/gotoUserCenter'">
            <p class="weic-my-name">${user.NECK_NAME}</p>
            <p class="weic-cardNumber">会员卡号：
                <sapn id="user_cardId"><c:if test='${user.CARDED == null}'> 未绑定 </c:if><c:if
                        test='${user.CARDED != null}'> ${user.CARDED}</c:if></sapn>
            </p>
        </div>
        <div class="weic-setIcon"></div>
    </div>
    <div class="weic-body-padding20">
        <div class="index-boxShadow">
            <div class="weic-cardBalance-box">
                <h1 id="user_overage"><c:if test='${user.overage == null}'> 0.00 </c:if><c:if
                        test='${user.overage != null}'> ${user.overage}</c:if></h1>
                <p>当前卡内余额（元）</p>
                <c:if test='${user.CARDED == null}'>
                    <div class="weic-recharge-btn weic-btn-gradientBlue"
                         onclick="window.location.href='<%=basePath%>intenetmumber/bindCard?label=index'">立即绑定
                    </div>
                </c:if>
                <c:if test='${user.CARDED != null}'>
                    <div class="weic-recharge-btn weic-btn-gradientBlue"
                         onclick="window.location.href='<%=basePath%>indexMember/recharge'">立即充值
                    </div>
                </c:if>
            </div>
            <div class="weic-index-navIcon">
                <div class="weui-flex">
                    <div class="weui-flex__item weic-text-center"
                         onclick="window.location.href='<%=basePath %>wxMatches/goGamesList'">
                        <div class="weic-index-icon weic-game-icon"></div>
                        <p>热门赛事</p>
                    </div>
                    <div class="weui-flex__item weic-text-center" id="memberShip_id">
                        <div class="weic-index-icon weic-member-apply-icon"></div>
                        <p>会员申请</p>
                    </div>
                    <div class="weui-flex__item weic-text-center"
                         onclick="window.location.href='<%=basePath%>indexMember/mall'">
                        <div class="weic-index-icon weic-shopping-icon"></div>
                        <p>网吧商城</p>
                    </div>
                    <div class="weui-flex__item weic-text-center"
                         onclick="window.location.href='<%=basePath%>indexMember/gotoLm'">
                        <div class="weic-index-icon weic-complaint-icon"></div>
                        <p>投诉建议</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="weic-bgfff index-boxShadow" style="border-radius: 0px;box-shadow: 0 2px 20px rgba(0,0,0,0.03)">
        <div class="weic-body-padding20" style="padding-bottom: 0.8rem;">
            <div class="weic-banner-box">
                <div class="swiper-container" id="swiper-banner">
                    <div class="swiper-wrapper" id="banner_div">
                        <div class="swiper-slide weic-banner-slide"
                             onclick="window.location.href='<%=basePath%>indexMember/recharge'"
                             style="background: url(<%=basePath%>newStyle/weixin/images/banner.jpg) no-repeat center center;background-size:100%"></div>

                    </div>

                </div>
            </div>
            <div class="member-activity">
                <div class="weui-row weui-no-gutter">
                    <div class="weui-col-50 frist" id="signIn">
                        <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                            <div class="weui-media-box__hd weic-user-sign"></div>
                            <div class="weui-media-box__bd">
                                <h4 class="weui-media-box__title">签到有礼</h4>
                                <p class="weui-media-box__desc">每日签到赢取积分</p>
                            </div>
                        </a>
                    </div>
                    <div class="weui-col-50 second" onclick="window.location.href='<%=basePath %>indexMember/benefits'">
                        <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                            <div class="weui-media-box__hd weic-user-card"></div>
                            <div class="weui-media-box__bd">
                                <h4 class="weui-media-box__title">会员福利</h4>
                                <p class="weui-media-box__desc">海量福利卡券送不停</p>
                            </div>
                        </a>
                        <img src="<%=basePath%>newStyle/weixin/images/lable.png" alt="">
                    </div>
                    <div class="weui-col-50 seconded" id="lottery">
                        <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                            <div class="weui-media-box__hd weic-user-lottery"></div>
                            <div class="weui-media-box__bd">
                                <h4 class="weui-media-box__title">手气抽奖</h4>
                                <p class="weui-media-box__desc">积分兑换抽大奖</p>
                            </div>
                        </a>
                    </div>
                    <div class="weui-col-50 fristed" id="shareId">
                        <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                            <div class="weui-media-box__hd weic-user-share"></div>
                            <div class="weui-media-box__bd">
                                <h4 class="weui-media-box__title">邀请有礼</h4>
                                <p class="weui-media-box__desc">分享好友拿专属福利</p>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="bottom_navigation.jsp" %>
</body>

<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-card.js"></script>
<script>

    $(".show-alert").click(function (){
        $.alert("程序员大哥正在飞速开发中，敬请期待！");
    })
    //检测身份证，如果是身份证标星显示
    $(function () {
        var cardId = $("#user_cardId").html().replace(/\s+/g, "")
        var len = cardId.replace(/\s+/g, "").length;
        if (len == 18) {
            var str = cardId.replace(/(\d{6})(\d+)(\d{4})/, function (x, y, z, p) {
                var i = "";
                while (i.length < z.length) {
                    i += "*"
                }
                return y + i + p
            })
            $("#user_cardId").html(str)
        }
    })

    function removeTips() {
        $(".weic-timeCard-tip").remove();
    }

    $(function () {
        FastClick.attach(document.body);
    });
    if (window.history.length == 1) {
        var url = window.location.href;
        window.history.pushState(null, null, url);
    }
    textScrolling() //文字滚动


    /* 	$(function(){
            pushHistory();
            var bool=false;
            setTimeout(function(){
                bool=true;
            },50);
            window.addEventListener("popstate", function(e) {
                if(bool){
                    //这个可以关闭安卓系统的手机
                      document.addEventListener('WeixinJSBridgeReady', function(){ WeixinJSBridge.call('closeWindow'); }, false);
                      //这个可以关闭ios系统的手机
                      WeixinJSBridge.call('closeWindow');
                }
            }, false);
        });
        function pushHistory() {
            var state = {
                title: "myCenter",
                url: null
            };
            window.history.pushState(state, state.title, state.url);
        }*/

</script>

<script type="text/javascript">
    function jumpTo(url) {
        window.location.href = url;
    }

    $(function () {
        var needRefresh = sessionStorage.getItem("need-refresh");
        if (needRefresh) {
            sessionStorage.removeItem("need-refresh");
            location.reload();
        }
        //签到
        $('#signIn').click(function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>indexMember/singInOne.do',
                data: '',
                success: function (data) {
                    if (data.result == "true") {
                        var html = '<div class="signIn"><span>' + data.integral + '</span></div>' +
                            '<div class="weic-recharge-btn weic-btn-gradientBlue" style="margin-top: -0.433rem" onclick="layer.closeAll()">确定</div>'
                        layer.open({
                            content: html
                            , className: 'signIn_box'
                            , style: "background: none;box-shadow: none;width:100%"
                            , end: function () {
                                // window.location.href=window.location.href+"?id="+10000*Math.random();
                            }
                        });
                    } else {
                        layer.open({
                            content: data.message
                            , skin: 'msg'
                            , time: 2
                        });
                    }
                },
                error: function () {
                    layer.open({
                        content: "系统繁忙，请稍后再试"
                        , skin: 'msg'
                        , time: 2
                    });
                }
            });
        });
        //邀请好友
        $('#shareId').click(function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>indexMember/canShare.do',
                data: '',
                success: function (data) {
                    if (data.result == "true") {
                        window.location.href = '<%=path%>' + data.backUrl;
                    } else if (data.result == "false") {
                        layer.open({
                            content: data.message
                            , skin: 'msg'
                            , time: 2
                        });
                    }
                },
                error: function () {
                    layer.open({
                        content: "系统繁忙，请稍后再试"
                        , skin: 'msg'
                        , time: 2
                    });
                    removeDis();
                }
            });
        });
        //抽奖
        $('#lottery').click(function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>indexMember/canChouJiang.do',
                data: '',
                success: function (data) {
                    if (data.result == "success") {
                        window.location.href = '<%=path%>' + data.backUrl;
                    } else if (data.result == "false") {
                        layer.open({
                            content: data.message
                            , skin: 'msg'
                            , time: 2
                        });
                    }
                },
                error: function () {
                    layer.open({
                        content: "系统繁忙，请稍后再试"
                        , skin: 'msg'
                        , time: 2
                    });
                    removeDis();
                }
            });
        });


    });


    //加载首页数据
    // setTimeout(loadIndex(),2000);
    loadIndex();
    function loadIndex() {
        open_loading("加载中");
         $.post('<%=basePath%>intenetmumber/loadIndex.do', '', function (res) {
            if (res.result == "true") {
                $("#user_overage").html(res.pdUser.overage);
                var basePath = '<%=basePath%>';

                //抵用券
                if(res.pdCard != null){
                    var pdCard = res.pdCard;
                    var rushHtml = '<div class="swiper-slide weic-banner-slide small" style="background: #fff;" id="rushCard">' +
                        '<div class="memberLife">' +
                        '<h1>特大福利,充'+pdCard.recharge+'得'+pdCard.handsel+'！</h1>' +
                        '<img src="<%=basePath%>newStyle/weixin/images/rechargeCard_bg.jpg" alt="" width="100%">' +
                        '</div>' +
                        '</div>';
                    $("#banner_div").append(rushHtml);
                    $("#rushCard").click(function () {
                        window.location.href = basePath + "wxML/goCardRushOrder.do?CARD_ID=" + pdCard.CARD_ID;
                    })
                }

                //会员申请
                if(res.view != null && res.view.typeo == "1"){
                    var view = res.view;
                    var memberApplyHtml = '<div class="swiper-slide weic-banner-slide small" style="background: #fff;" id="memberApplyId">' +
                        '                                    <div class="membership">' +
                        '                                        <h1>'+view.advertisement+'</h1>' +
                        '                                        <img src="'+basePath+'newStyle/weixin/images/member_banner_small.png" alt=""' +
                        '                                             width="100%">' +
                        '                                    </div>' +
                        '                                </div>';
                    $("#banner_div").append(memberApplyHtml);

                    $("#memberShip_id").click(function () {
                        window.location.href = basePath + 'intenetmumber/Membership0.do';
                    })
                    $("#memberApplyId").click(function () {
                        window.location.href = basePath + 'intenetmumber/Membership0.do';
                    })
                }else{
                    //尚未开通会员申请
                    $("#memberShip_id").click(function () {
                        layer.open({
                            content: '门店尚未开通会员申请'
                            , skin: 'msg'
                            , time: 3
                        });
                    })
                }

                //满时长券
                if(res.cardTerm != null){
                    var cardTerm = res.cardTerm;
                    var termHtml = '<div class="swiper-slide weic-banner-slide small" style="background: #fff;" id="termId">' +
                        '                                <div class="internet">' +
                        '                                    <h1>'+cardTerm.card_ad+'</h1>' +
                        '                                    <img src="'+basePath+'newStyle/weixin/images/internet_banner_small.png" alt=""  width="100%">' +
                        '                                </div>' +
                        '                            </div>';
                    $("#banner_div").append(termHtml);
                    $("#termId").click(function () {
                        openCard(cardTerm.SUB_TITLE, cardTerm.NOTICE, cardTerm.DESCRIPTION, cardTerm.CONTENT, cardTerm.CARD_ID, cardTerm.BEGIN_TIMESTAMP, cardTerm.END_TIMESTAMP
                            , cardTerm.FIXED_BEGIN_TERM, cardTerm.FIXED_TERM, cardTerm.TYPE, cardTerm.store, cardTerm.cardSum_time, basePath);
                    })
                }

                $("#banner_div").after('<div class="swiper-pagination" style="bottom: 0.02rem;line-height:1.4;"></div>')
                var swiper = new Swiper('.swiper-container', {
                    autoplay: 1500,
                    autoplayStopOnLast:false,
                    pagination: '.swiper-pagination',
                    paginationClickable:true,
                    loop:true,
                });
            }
        });
        layer.closeAll();
    }

    eval()
</script>
</html>