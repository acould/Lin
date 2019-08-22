<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <title>支付详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
</head>
<style>
    .weui-popup__modal {
        background: none!important;
    }
</style>
<body class="weic-body-bgf3f3f3 weic weic-parent-mar" >
<div class="weic-paydet-box">
    <div class="weui-cells__title weui-cell">
        <div class="weui-cell__bd">
            <h3 class="">充值信息</h3>
        </div>
    </div>
    <div class="weui-cells">
        <div class="weui-cell" id="store_div">
            <div class="weui-cell__bd">
                <p class="weic-cell-l">所属门店</p>
            </div>
            <div class="weui-cell__ft weic-cell-r" id="store_name">***</div>
        </div>
        <%--<div class="weui-cell weui-cells_checkbox active">
            <div class="weui-cell__bd">
                <p class="weic-cell-l">充值100送500</p>
                <div class="weic-cardRush-msg">
                    <p class="">充送计划：分5次送完，每次间隔时间一周</p>
                    <p class="">第1次 06-10 9:30赠送100元</p>
                    <p class="">第2次 06-10 9:30赠送100元</p>
                    <p class="">第3次 06-10 9:30赠送100元</p>
                    <p class="">第4次 06-10 9:30赠送100元</p>
                    <p class="">第5次 06-10 9:30赠送100元</p>
                </div>
            </div>
            <div class="weui-cell__ft weic-cell-r-red weui-icon-checked"></div>
        </div>--%>
    </div>
</div>
<div class="weic-paydet-box">
    <div class="weui-cells__title weui-cell">
        <div class="weui-cell__bd">
            <h3 class="">支付方式</h3>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__bd">
            <p class="weic-cell-l"><i class="iconfont weixinPay-icon">&#xe600;</i>微信支付</p>
        </div>
        <div class="weui-cell__ft weic-cell-r"><i class="iconfont payActive-icon">&#xe604;</i></div>
    </div>
</div>
</div>
<div class="weic-bor" style="margin:2rem 0;">
    <input type="hidden" id="merOrderId">
    <div class="weic-dent-btn weic-btn-gradientBlue" onclick="toPay()" id="pay-btn">确定支付</div>
</div>

<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-pay.js"></script>
<script>


    var basePath = "<%=basePath%>";
    var CARD_ID = "${pd.CARD_ID}";
    var jl_status = "${pd.jl_status}";
    $(function () {
        //判断支付后回调
        if(jl_status == "rt_success"){
            var html = '<div class="payResult-successful"></div>';
            open_oneBtns(html,'确定',"lk-pay-successful",function(){
                window.location.href = basePath + 'myMember/gotoUserCenter.do';
            })
        }else{
            //初始化
            init();
        }
    })


    function init() {
        open_loading('加载中...');

        var field = new Object();
        field.CARD_ID = CARD_ID;
        $.post(basePath+'wxML/loadRushOrder.do', field, function (res) {
            console.log(res);
            layer.closeAll();
            if (res.errcode == 0) {
                var bundUser = res.data.bundUser;
                var rushList = res.data.rushList;

                //绑定的门店
                $("#store_name").html(bundUser.store_name);

                //抵用券 列表

                var htmls = "";
                for (var i = 0; i < rushList.length; i++) {
                    var data = rushList[i];
                    var jiange = data.handsel_time + getJG(data.handsel_times);

                    //赠送的列表
                    var validRush = data.validRush;
                    var validHtmls = "";
                    var now = new Date();
                    for (var j = 0; j < validRush.length; j++) {
                        var num = j+1;

                        var send_time = "";
                        if(j == 0){
                            send_time = "当天";
                        }else{
                            nextDate = DateAdd(getJG(data.handsel_times), data.handsel_time, now);
                            send_time = nextDate.getFullYear() + "-" + addZero(nextDate.getMonth() + 1) + "-" + addZero(nextDate.getDate()) + " 9:00";
                        }
                        validHtmls += '<p class="">第'+num+'次 '+send_time+' 赠送'+validRush[j].CASH_NUMBER+'元</p>';
                    }


                    //是否选中
                    var isActive = "";
                    if(data.CARD_ID == CARD_ID){
                        isActive = "active";
                    }
                    var isHide = 'style="display: none"';
                    if(data.CARD_ID == CARD_ID){
                        isHide = '';
                    }

                    htmls += '<div class="weui-cell weui-cells_checkbox '+isActive+'" data-id="'+data.CARD_ID+'">'+
                                '<div class="weui-cell__bd">'+
                                    '<p class="weic-cell-l">充值'+data.recharge+'得'+data.handsel+'</p>'+
                                    '<div class="weic-cardRush-msg" '+isHide+'>'+
                                        '<p class="">充送计划：分'+data.handsel_sum+'次送完，每次间隔时间'+jiange+'</p>'+
                                        validHtmls +
                                    '</div>'+
                                '</div>'+
                            '<div class="weui-cell__ft weic-cell-r-red weui-icon-checked"></div>'+
                            '</div>';
                }
                $("#store_div").after(htmls);

                $(".weui-cells_checkbox").click(function () {
                    $(".weui-cells_checkbox").removeClass("active");
                    $(this).addClass("active");
                    $(".weic-cardRush-msg").hide();
                    $(this).children("div:first-child").children(":last-child").show();
                })




            }
        });
    }




    //zhifu
    function toPay() {
        if($("#merOrderId").val() != ''){
            $("#pay-btn").attr("onclick", "toPay()");
            message("订单正在处理中");
            return false;
        }
        open_loading('支付中...');
        $("#pay-btn").attr("onclick", "");
        var CARD_ID = $(".active").data("id");
        var field = new Object();
        field.CARD_ID = CARD_ID;
        $.post(basePath + 'wxML/rushPay.do',field,function (res) {
            layer.closeAll();
            if(res.errcode == 0){
                $("#merOrderId").val(res.data.merOrderId);
                window.location.href = res.data.url;
            }else{
                $("#pay-btn").attr("onclick", "toPay()");
                message(res.errmsg);
            }
        })
    }



    function getJG(handsel_times) {
       // (0-天,1-周,2-月,3-年)
        var typeInfo = "";
        switch (handsel_times) {
            case 0:
                typeInfo = "天";
                break;
            case 1:
                typeInfo = "周";
                break;
            case 2:
                typeInfo = "月";
                break;
            case 3:
                typeInfo = "年";
                break;
        }
        return typeInfo;
    }

    //1-9的数加0
    function addZero(num) {
        if(num > 0 && num < 10){
            num = "0" + num;
        }
        return num;
    }

    //计算时间，通过间隔
    function DateAdd(interval, number, date) {
        switch (interval) {
            case "年": {
                date.setFullYear(date.getFullYear() + number);
                return date;
                break;
            }
            case "季": {
                date.setMonth(date.getMonth() + number * 3);
                return date;
                break;
            }
            case "月": {
                date.setMonth(date.getMonth() + number);
                return date;
                break;
            }
            case "周": {
                date.setDate(date.getDate() + number * 7);
                return date;
                break;
            }
            case "天": {
                date.setDate(date.getDate() + number);
                return date;
                break;
            }
            case "时": {
                date.setHours(date.getHours() + number);
                return date;
                break;
            }
            case "分": {
                date.setMinutes(date.getMinutes() + number);
                return date;
                break;
            }
            case "秒": {
                date.setSeconds(date.getSeconds() + number);
                return date;
                break;
            }
            default: {
                date.setDate(d.getDate() + number);
                return date;
                break;
            }
        }
    }

</script>
</body>
</html>