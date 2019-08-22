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
    <title></title>
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
        .weic .swiper-pagination-clickable .swiper-pagination-bullet {
            background: rgb(71,142,251);
            margin: 0 0.133rem;
        }
        #uesr_banner .membership {
            background: url("<%=basePath%>newStyle/images/membership.png")no-repeat center center;
            background-size: 115%;
        }
        .signIn {
            width: 100%;
            height: 9.2rem;
            background: url("<%=basePath%>newStyle/weixin/images/qiandao-bg.png")no-repeat center center;
            background-size: 125%;
        }
        .signIn span {
            line-height: 10.5rem;
            font-size: 0.7rem;
            font-weight: 600;
        }
    </style>
</head>
<body class="weic weic-body-bgf3f3f3">
    <div class="weic-userCode-item user_index">
        <h1 class="weic-codeIdex-num <c:if test="${result.pdQr.state != '2'}">internet_lable</c:if>" id="h1_id" >当前电脑号：${result.pdQr.serial_number.trim()}</h1>
        <div class="swiper-container" id="uesr_banner">
            <div class="swiper-wrapper" id="swiper">
            </div>
        </div>
        <div class="userCode-pagination" style="line-height:1.4;text-align: center"></div>
        <a class="weui-cell  weic-relative user_msg" href="javascript:;">
            <div class="weui-cell__hd weic-relative weic-my-head"
                 style="background: url('${result.pdBind.imgurl}')no-repeat center center;background-size:100%">
            </div>
            <div class="weui-cell__bd">
                <p class="weic-my-name">Hi，${result.pdBind.name}</p>
                <p class="weic-user-rank">欢迎来到${result.pdBind.store_name}</p>
            </div>
            <div class="weui-cell__ft">
                <span class="sign" id="signIn">签到</span>
            </div>
        </a>
        <div class="weui-row function_icon weui-no-gutter">
            <div class="weui-col-33 border-bottom " onclick="go_starting('${result.pdQr.state}')">
                <c:if test="${result.pdQr.state == '2'}">
                    <div class="icon icon_on"></div>
                    <p class="text">我要上机</p>
                </c:if>
                <c:if test="${result.pdQr.state == '1'}">
                    <div class="icon icon_off"></div>
                    <p class="text">我要下机</p>
                </c:if>
                <c:if test="${result.pdQr.state == '3'}">
                    <div class="icon icon_exchange"></div>
                    <p class="text">我要换机</p>
                </c:if>

            </div>
            <div class="weui-col-33 border-bottom border-left" onclick="window.location.href='<%=basePath%>indexMember/recharge'">
                <div class="icon icon_recharge"></div>
                <p class="text">我要充值</p>
            </div>
            <div class="weui-col-33 border-bottom border-left" onclick="window.location.href='<%=basePath %>indexMember/benefits'">
                <div class="icon icon_card"></div>
                <p class="text">领券上网</p>
            </div>
            <div class="weui-col-33" id="mall" onclick="window.location.href='<%=basePath%>indexMember/mall?computer_name=${result.pdQr.computer_name.trim()}'">
                <div class="icon icon_mall"></div>
                <p class="text">购买商品</p>
            </div>
            <div class="weui-col-33 border-left" id="call">
                <div class="icon icon_call"></div>
                <p class="text">呼叫网管</p>
            </div>
            <div class="weui-col-33 border-left" id="complaint" onclick="window.location.href='<%=basePath%>indexMember/gotoLm?computer_name=${result.pdQr.computer_name.trim()}'">
                <div class="icon icon_complaint"></div>
                <p class="text">一键投诉</p>
            </div>
        </div>
        <p style="text-align: center;font-size: 0.34rem;color: #999;padding: 0.7rem 0 ">揽客技术支持</p>
    </div>
    <form id="Form" action="<%=basePath%>qrCode/goUserUp.do">
        <input id="serial_number" name="serial_number" type="hidden" value="${result.pdQr.serial_number.trim()}">
        <input id="computer_name" name="computer_name" type="hidden" value="${result.pdQr.computer_name.trim()}">
        <input id="imgurl" name="imgurl" type="hidden" value="${result.pdBind.imgurl}">
        <input id="name" name="name" type="hidden" value="${result.pdBind.name}">
        <input id="store_name" name="store_name" type="hidden" value="${result.pdBind.store_name}">
        <input id="store_id" name="store_id" type="hidden" value="${result.pdBind.store_id}">
        <input id="state" name="state" type="hidden" value="${result.pdQr.state}">
    </form>
</body>

<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<%--<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>--%>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-card.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script>
    $(function(){
        FastClick.attach(document.body);
        judeCard();
    });

</script>

<script>
    function judeCard(){//去判断该网吧是否有
    	$.ajax({
            type: "POST",
            url: '<%=basePath%>intenetmumber/judgeCard',
            data: '',
            success: function (data) {
            	var html ='';
            	var url="";
            	if(data.falg){
            		if(data.view.typeo == '1'){
            			url="<%=basePath%>intenetmumber/Membership0";
            			html+="<div class='swiper-slide weic-userCode-slide' onclick=\"window.location.href='"+url+"'\">"+
                                     "<div class='membership'>"+
                                        "<h1>"+data.view.advertisement+"</h1>"+
                                    "</div>"+
                                "</div>"
            		}
            	}
                if(data.result == "true"){
                	url="<%=basePath%>newStyle/images/internet_banner.png";
                	html+="<div class='swiper-slide weic-userCode-slide' onclick=openCard('"+data.cardTerm.SUB_TITLE+"','"+data.cardTerm.NOTICE+"','"+data.cardTerm.DESCRIPTION+"','"+data.cardTerm.CONTENT+"','"+data.cardTerm.CARD_ID+"','"+data.cardTerm.BEGIN_TIMESTAMP+"','"+data.cardTerm.END_TIMESTAMP+"','"+data.cardTerm.FIXED_BEGIN_TERM+"','"+data.cardTerm.FIXED_TERM+"','"+data.cardTerm.TYPE+"','"+data.cardTerm.store+"','"+data.cardTerm.cardSum_time+"','<%=basePath%>')>"+
                                "<div class='internet'>"+
                                    "<h1>"+data.cardTerm.card_ad+"</h1><img src='"+url+"' alt='' width='100%'>"+
                                "</div>"+
                           "</div>";
                }
                html+="<div class='swiper-slide weic-userCode-slide' onclick=\"window.location.href='<%=basePath%>indexMember/recharge'\">"+
                            "<img src='<%=basePath%>newStyle/weixin/images/codepay_banner.jpg' alt='' width='100%'>"+
                       "</div>";
                $("#swiper").html(html);
                var swiper1 = new Swiper('#uesr_banner', {
                    pagination: '.userCode-pagination',//开启分页器
                    //paginationHide: true,//这个选项设置为true时点击Swiper会隐藏/显示分页器
                    paginationClickable: true,//此参数设置为true时，点击分页器的指示点分页器会控制Swiper切换。
                    centeredSlides: true,//设定为true时，活动块会居中，而不是默认状态下的居左。
                    autoplay: 3000,//自动播放
                    uniqueNavElements: false,//默认为true时，仅本swiper的container内的分页器有效，设置为false后，container以外的分页器也生效了
                    autoplayDisableOnInteraction: false,//当设置为false时，用户操作之后（swipes,arrow以及pagination 点击）autoplay不会被禁掉，用户操作之后每次都会重新启动autoplay
                    onTouchMove: function (swiper) {
                        if (swiper.isBeginning) {
                            swiper.lockSwipeToPrev()
                        } else if (swiper.isEnd) {
                            swiper.lockSwipeToNext()
                        } else {
                            swiper.unlockSwipeToNext()
                            swiper.unlockSwipeToPrev()
                        }
                    }
                })
            },
            error: function (){
            }
        });
    }
</script>

<script type="text/javascript">
    var computer_name = "${result.pdQr.computer_name.trim()}";
    $(function (){
        //签到
        $('#signIn').click(function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>indexMember/singInOne.do',
                data: '',
                success: function (data) {
                    if(data.result == "true"){
                        var html = '<div class="signIn"><span>'+data.integral+'</span></div>'+
                            '<div class="weic-recharge-btn weic-btn-gradientBlue" style="margin-top: -0.433rem" onclick="layer.closeAll()">确定</div>'
                        layer.open({
                            content: html
                            ,className: 'signIn_box'
                            ,style:"background: none;box-shadow: none;width:100%"
                        });
                    }else {
                        layer.open({
                            content: data.message
                            ,skin: 'msg'
                            ,time: 2
                        });
                    }
                },
                error: function (){
                    layer.open({
                        content: "系统繁忙，请稍后再试"
                        ,skin: 'msg'
                        ,time: 2
                    });
                }
            });
        });
        //抽奖
        $('#lottery').click(function (){
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
                            ,skin: 'msg'
                            ,time: 2
                        });
                    }
                },
                error: function () {
                    layer.open({
                        content: "系统繁忙，请稍后再试"
                        ,skin: 'msg'
                        ,time: 2
                    });
                }
            });
        });
        //呼叫网管
        $('#call').click(function (){
            var obj = new Object();
            obj.computer_name = computer_name;
            $.ajax({
                type: "POST",
                url: '<%=basePath%>indexMember/callServer.do',
                data: obj,
                success: function (res) {
                    if (res.result == "true") {
                        alert("呼叫成功");
                    } else{
                        alert(res.message);
                    }
                },
                error: function () {
                    layer.open({
                        content: "系统繁忙，请稍后再试"
                        ,skin: 'msg'
                        ,time: 2
                    });
                }
            });
        });

    });


    //去上机页面
    function go_starting(state) {
        $("#state").val(state);
        $('#Form').submit();
    }


    //异步加载数据
    loadUserIndex();
    function loadUserIndex() {
        open_loading("加载中");
        var data = new Object();
        data.store_id = "${result.pdBind.store_id}";
        data.computer_name = "${result.pdQr.computer_name.trim()}"

        $.post('<%=basePath%>retrospectCenter/loadUserIndex', data, function (res) {
          if(res.result == "true"){
              var pdQr = res.data.pdQr;

          }
        })
        layer.closeAll();
    }

</script>
</html>