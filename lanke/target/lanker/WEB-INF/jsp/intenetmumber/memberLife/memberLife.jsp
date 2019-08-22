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
    <title>会员生活</title>
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
       .weic-member-box .weui-cell:before {display: none}

    </style>
</head>
<body class="weic weic-body-bgf3f3f3 index">

    <div class="swiper-container" id="swiper-banner">
        <div class="swiper-wrapper" id="banner_div">
            <div class="swiper-slide weic-banner-slide"
                 style="background: url(<%=basePath%>newStyle/weixin/images/ml/ml_index.png) no-repeat center center;background-size:100%">
            </div>
        </div>
    </div>


    <div class="weic-bor" id="notifyList">


    </div>



    <%@ include file="../bottom_navigation.jsp" %>
</body>

<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-card.js"></script>

<script type="text/javascript">
    var basePath = "<%=basePath%>";

    //初始化
    init();

    function init() {
        var field = new Object();
        open_loading('加载中...');
        $.post(basePath+'wxML/loadML.do', field, function (res) {
            layer.closeAll();
            console.log(res);

            if (res.errcode == 0) {
                var pdCard = res.data.pdCard;
                var notifyList = res.data.notifyList;
                var pdInternet = res.data.pdInternet;
                var store_name = res.data.store_name;

                //抵用券
                if(pdCard != null && pdCard.CARD_ID != '' && pdCard.CARD_ID != undefined){
                    var html = '<div class="swiper-slide weic-banner-slide">'+
                                '<div class="memberLife" id="rushCard">'+
                                '<h1>特大福利,充'+pdCard.recharge+'得'+pdCard.handsel+'！</h1>'+
                                '<img src="<%=basePath%>newStyle/weixin/images/rechargeCard_bg.jpg" alt="" width="100%">'+
                                '</div></div>';
                    $("#banner_div").append(html);
                    $("#rushCard").click(function () {
                        window.location.href = basePath + "wxML/goCardRushOrder.do?CARD_ID=" + pdCard.CARD_ID;
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

                //消息列表
                var htmls = '';
                for (var i = 0; i < notifyList.length; i++) {
                    var data = notifyList[i];
                    var isRead = '';
                    if(data.is_read == 1){
                        isRead = "read-box-div";
                    }
                    //赛事matches；卡券card；消息msg
                    if(data.type == 'matches'){
                        var matches_img = data.matches_img;
                        htmls += '<div class="weic-member-box weui-cell_access matches-box-div '+isRead+'" data-id="'+data.bforeign_id+'" id="'+data.id+'">'+
                                    '<a class="weui-cell" href="javascript:;" style="padding: 0;">'+
                                        '<div class="weui-cell__bd">'+
                                            '<p class="title">您有一场赛事未参加</p>'+
                                        '</div>'+
                                        '<div class="weui-cell__ft"></div>'+
                                    '</a>'+
                                    '<p class="time">'+data.create_time+'</p>'+
                                    '<a class="weui-cell" href="javascript:;" style="padding: 0.45rem 0rem">'+
                                        '<div class="weui-cell__hd">'+
                                            '<div class="img " style="background: url('+matches_img+')no-repeat center center;background-size:175%"></div>'+
                                            '</div>'+
                                            '<div class="weui-cell__bd">'+
                                            '<p class="content">'+pdInternet.INTENET_NAME+'邀您参加“<span class="red">'+data.title+'</span>”</p>'+
                                            '<p class="content">报名时间：'+data.enroll_time+'</p>'+
                                        '</div>'+
                                    '</a>'+
                                    '<p class="msg">'+data.ad_content+'</p>'+
                                '</div>';
                    }else if(data.type == 'card'){
                        var card_img = getTypeImg(data.FAV_TYPE);
                        htmls += '<div class="weic-member-box weui-cell_access card-box-div '+isRead+'" data-id="'+data.bforeign_id+'" id="'+data.id+'">'+
                                    '<a class="weui-cell" href="javascript:;" style="padding: 0;">'+
                                        '<div class="weui-cell__bd">'+
                                            '<p class="title">您有一张卡券待领取</p>'+
                                        '</div>'+
                                        '<div class="weui-cell__ft"></div>'+
                                    '</a>'+
                                    '<p class="time">'+data.create_time+'</p>'+
                                    '<a class="weui-cell" href="javascript:;" style="padding: 0.45rem 0rem">'+
                                        '<div class="weui-cell__hd">'+
                                            '<div class="img " style="background: url('+card_img+')no-repeat center center;background-size:100%"></div>'+
                                        '</div>'+
                                        '<div class="weui-cell__bd">'+
                                            '<p class="content">'+pdInternet.INTENET_NAME+'送你一张“<span class="red">'+data.title+'</span>”</p>'+
                                            '<p class="content">有效期：'+data.available_time+'</p>'+
                                        '</div>'+
                                    '</a>'+
                                    '<p class="msg">'+data.ad_content+'</p>'+
                                '</div>';
                    }else if(data.type == 'msg'){
                        htmls += '<div class="weic-member-box msg-box-div '+isRead+'" id="'+data.id+'">'+
                                    '<a class="weui-cell" href="javascript:;" style="padding: 0;">'+
                                        '<div class="weui-cell__bd">'+
                                        '<p class="title">'+data.title+'</p>'+
                                        '</div>'+
                                    '</a>'+
                                    '<p class="time">'+store_name+'&nbsp;&nbsp;&nbsp;'+data.create_time+'</p>'+
                                    '<a class="weui-cell" href="javascript:;" style="padding: 0.45rem 0rem">'+
                                        '<div class="weui-cell__bd">'+
                                            '<p class="content">'+data.content+'</p>'+
                                        '</div>'+
                                    '</a>'+
                                    '<p class="msg">'+data.ad_content+'</p>'+
                                '</div>';
                    }

                }
                if(htmls == ""){
                    htmls = '<div class="weic-noboby" >'+
                                '<div class="weic-nobody-img"></div>'+
                                '<p class="weic-nobody-text">暂无数据</p>'+
                            '</div>';
                    $("#notifyList").after(htmls);
                }else{
                    $("#notifyList").html(htmls);
                }

                $(".matches-box-div").click(function () {
                    var matches_id = $(this).data("id");
                    window.location.href = basePath+"wxMatches/goGameDetail.do?matches_id="+matches_id;
                })
                $(".card-box-div").click(function () {
                    var CARD_ID = $(this).data("id");
                    window.location.href = basePath +"wxML/goMLLqCard.do?CARD_ID="+CARD_ID;
                })

                //阅读
                $(".weic-member-box").click(function () {
                    if($(this).hasClass('read-box-div')){
                        return false;
                    }else{
                        $(this).addClass('read-box-div');
                        var id = $(this).attr("id");
                        readAct(id)
                    }
                })
            } else {
                message(res.errmsg);
            }
        });

    }



    //已阅读
    function readAct(id) {
        var field = new Object();
        field.id = id;
        $.post(basePath + 'wxML/readAct.do', field, function (res) {

        });
    }


</script>
</html>