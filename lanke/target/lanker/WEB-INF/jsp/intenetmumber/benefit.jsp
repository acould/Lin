<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="viewport"
          content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>会员福利</title>
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<style>
    body {
        background: #6191f7;
    }
</style>


<body class="weic card">
    <img src="<%=basePath%>newStyle/weixin/images/cardlist_banner.png"
         alt="" width="100%" style="display: block">

    <form id="Form">
        <div class="weui-row weui-no-gutter">
            <div class="weui-col-33 active" data-type="">
                <div class="weic-navbar-text">全部卡券</div>
            </div>
            <div class="weui-col-33 " data-type="GRAB">
                <div class="weic-navbar-text">限时秒抢</div>
            </div>
            <div class="weui-col-33 " data-type="TERM">
                <div class="weic-navbar-text">满时长送</div>
            </div>
        </div>
        <%--卡券列表--%>
        <div style="padding-bottom: 0.4rem" id="cardList">

        </div>
    </form>


</body>

<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/roomes.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/mobile-check.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="<%=basePath%>newStyle/js/lk_wechat/lk_wechat.js"></script>
<script type="text/javascript">

    var basePath = "<%=basePath%>";
    //初始化页面
    $(function(){
        FastClick.attach(document.body);
        init('');
    });

    //初始化领取状态
    $(function() {
        var wx2 = '${wx}';
        if(wx2 != null && wx2 != ''){
            open_loading("加载中")
            var timestamp = "${wx.timestamp}";//时间戳
            var nonceStr = "${wx.nonceStr}";//随机串
            var signature = "${wx.signature}";//签名
            var appId = "${wx.appid}";//app_id
            wx.config({
                debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId : appId, // 必填，公众号的唯一标识
                timestamp : timestamp, // 必填，生成签名的时间戳
                nonceStr : nonceStr, // 必填，生成签名的随机串
                signature : signature,// 必填，签名，见附录1
                jsApiList : [ "addCard" ]
                // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });

            //config配置信息失败，执行error
            wx.error(function(res){
                alert("参数错误");
            });
            //config配置信息成功，执行ready
            wx.ready(function(){
                var cardId = '${wx.card_id}';
                var timestamp = '${wx.config.timestamp}';
                var signature = '${wx.config.signature}';
                var cardExt = '{"timestamp":"'+timestamp+'","signature":"'+signature+'","card_id":"'+cardId+'"}';
                wx.addCard({
                    cardList: [{
                        cardId:cardId,
                        cardExt:cardExt
                    }], // 需要添加的卡券列表
                    success: function (res){
                        var cardList = res.cardList; // 添加的卡券列表信息
                    }
                });
            });
        }
    });


    $(".weui-col-33").click(function(){
        $(this).addClass("active");
        $(this).siblings().removeClass("active")

        //切换导航栏
        var type = $(this).data('type');
        init(type);
    })

    var url = '<%=basePath%>indexMember/loadCardList.do';
    function  init(type) {
        open_loading();
        var field = new Object();
        field.type = type;

        $.post(url, field, function (res) {
            layer.closeAll();
            if(res.errcode == 0){
                var list = res.data.list;

                var htmls = buildHtml(list);
                if(htmls == ''){
                    htmls = '<div class="no-card">'
                            + '<div class="img"></div>'
                            + '<p class="text">暂无卡券</p>'
                            +'</div>';
                }
                $('#cardList').html(htmls);


            }else{
                message(res.errmsg);
            }
        })
    }
    //请求数据
    $.post("<%=basePath%>card/saveCard.do", function (data) {
        console.log(data,'111');

    })
    //打开卡券
    function openCard(f) {
        var str = $(f).data("value");
        // console.log(str);
        var data = str;
       console.log(data);
        var time = '';
        if(data.TYPE == 'DATE_TYPE_FIX_TIME_RANGE'){
            time = '<p class="weic-card-time"><font color="#333">有效期：</font>' + data.BEGIN_TIMESTAMP + ' 至 ' + data.END_TIMESTAMP + '</p>';
        }else if(data.TYPE == 'DATE_TYPE_FIX_TERM'){
            var tian = data.FIXED_BEGIN_TERM == '0' ? '当' : data.FIXED_BEGIN_TERM;
            time = '<p class="weic-card-time"><font color="#333">有效期：</font>领取后' + tian + '天生效，领取后' + data.FIXED_TERM + '天内有效</p>';
        }
        if(data.FAV_TYPE=="TERM"){
            var html = '<div class="open-card-box"><div class="card-img"><img src="' + img[data.FAV_TYPE][1] + '" alt="" width="100%"></div>' +
                '<p class="weic-card-title">' + data.SUB_TITLE + '</p>' + time +
                '<p class="weic-card-time"><font color="#333">适用门店：</font>' + data.stores + '</p>' +'<p class="weic-card-time"><font color="#333">无效累计时间段：</font>' + data.unusertime + '</p>'+
                '<p class="weic-card-time"><font color="#333">优惠说明：</font>' + data.DESCRIPTION + '</p>' +
                '<p class="weic-card-time"><font color="#333">使用提醒：</font>' + data.NOTICE + '</p>' +
                '<div style="padding: 1rem 0 0.5rem 0">' +
                '<div class="weic-recharge-btn weic-btn-gradientOrange" style="width: 75%;" onclick=lingqu("' + data.CARD_ID + '")>确定</div>' +
                '</div>' +
                '</div>';
        }else{
            var html = '<div class="open-card-box"><div class="card-img"><img src="' + img[data.FAV_TYPE][1] + '" alt="" width="100%"></div>' +
                '<p class="weic-card-title">' + data.SUB_TITLE + '</p>' + time +
                '<p class="weic-card-time"><font color="#333">适用门店：</font>' + data.stores + '</p>' +
                '<p class="weic-card-time"><font color="#333">优惠说明：</font>' + data.DESCRIPTION + '</p>' +
                '<p class="weic-card-time"><font color="#333">使用提醒：</font>' + data.NOTICE + '</p>' +
                '<div style="padding: 1rem 0 0.5rem 0">' +
                '<div class="weic-recharge-btn weic-btn-gradientOrange" style="width: 75%;" onclick=lingqu("' + data.CARD_ID + '")>确定</div>' +
                '</div>' +
                '</div>';
        }
        layer.open({
            content: html
            ,className: 'signIn_box'
            ,style:"background: none;box-shadow: none;width:85%"
            ,shadeClose:true
            ,end:function(){

            }
        });
    }

    //领取卡券
    function lingqu(id) {
        confirm("是否确定领取该商品",function(){
            var flag = true;
            if(flag){
                var flag = false;
                var field = new Object();
                field.mayId = id;
                open_loading();
                $.post('<%=basePath%>indexMember/receiveBenefits.do', field, function (res) {
                    layer.closeAll();
                    var data = res;
                    if(data.card_result == "true"){
                        sqhx(id);
                    }else{
                        message(data.message);
                    }
                })
            }
        })
    }

    //微信领取卡券页面
    function sqhx(card_id) {
        var url = window.location.href;

        var field = new Object();
        field.url = url;
        field.card_id = card_id;
        field.state = 'benefit';
        open_loading();
        $.post('<%=basePath%>indexMember/goWechatCard1.do', field, function (res) {
            layer.closeAll();
            var data = res;
            if(data.result == "true"){
                wx.config({
                    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId: data.wx.appid, // 必填，公众号的唯一标识
                    timestamp: data.wx.timestamp, // 必填，生成签名的时间戳
                    nonceStr: data.wx.nonceStr, // 必填，生成签名的随机串
                    signature: data.wx.signature,// 必填，签名，见附录1
                    jsApiList: ['addCard']
                    // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                });
                layer.closeAll();
                wx.ready(function(){
                    var cardId = data.wx.card_id;
                    var cardExt = '{"timestamp":"'+data.wx.config.timestamp+'","signature":"'+data.wx.config.signature+'","card_id":"'+cardId+'"}';
                    wx.addCard({
                        cardList: [{
                            cardId:cardId,
                            cardExt:cardExt
                        }], // 需要添加的卡券列表
                        success: function (res){
                            var cardList = res.cardList; // 添加的卡券列表信息
                            location.reload();
                        }
                    });
                });
            }else{
                message(data.message);
            }
        })

    }


    //生成html
    function buildHtml(list) {
        var htmls = '';

        for (var i = 0; i < list.length; i++) {
            var data = list[i];
            var sum = data.cardSum + data.QUANTITY;

            htmls += '<div class="weic-cardlistbox weic-bgfff">'
                        +'<a class="weui-cell weic-relative" href="javasrcipt:">'
                            +'<div class="weui-cell__hd weic-card-img">'
                                +'<img src="'+getTypeImg(data.FAV_TYPE)+'" alt="" width="100%">'
                            +'</div>'
                            +'<div class="weui-cell__bd">'
                                +'<div class="weic-card-msg">'
                                    +'<p class="weic-card-title">'+data.SUB_TITLE+'</p>'
                                    +'<p class="weic-card-time">'
                                        +getTimeRange(data)
                                    +'</p>'
                                    +'<p class="weic-card-time">'
                                       +'适用门店：'+ data.stores
                                    +'</p>'
                                +'</div>'
                                + getExtra(data)
                            +'</div>'
                            +'<div class="weui-cell__ft">'
                                +'<p class="weic-card-repertory">'
                                    +'<font color="red">'+data.QUANTITY+'</font>/'+sum
                                +'</p>'
                                +getBtn(data)
                            +'</div>'
                        +'</a>'
                    +'</div>';

        }
        return htmls;
    }



    //获取有效期
    function getTimeRange(data) {
        if(data.TYPE == 'DATE_TYPE_FIX_TIME_RANGE'){
            return '有效期：' + data.BEGIN_TIMESTAMP + ' 至 ' + data.END_TIMESTAMP;
        }else if(data.TYPE == 'DATE_TYPE_FIX_TERM'){
            var html = '有效期：';
            if(data.FIXED_BEGIN_TERM == '0'){
                html += '领取后当天生效,';
            }else{
                html += '领取后' + data.FIXED_BEGIN_TERM + '天生效,';
            }
            html += '领取后' + data.FIXED_TERM + '天内有效';
            return html;
        }

    }

    //获取额外情况的html
    function getExtra(data) {
        var html = '';
        if(data.FAV_TYPE == 'GRAB'){
            //秒抢券
            switch (data.cardState) {
                case 3:
                    html = '<p class="weic-card-tips">' + data.garbStart_time + '开抢</p>';
                case 5:
                    html = '<p class="weic-card-tips">正在抢</p>';
            }
        }else if(data.FAV_TYPE == 'TERM') {
            //时长券
            switch (data.cardSum_type) {
                case 2:
                    html = '<p class="weic-card-tips">一周上网时长满 ' + data.cardSum_time + '小时可领</p>';
                case 3:
                    html = '<p class="weic-card-tips">一月上网时长满 ' + data.cardSum_time + '小时可领</p>';
            }
        }
        return html;
    }

    //领取按钮
    function getBtn(data) {
        var str = JSON.stringify(data);
        str = str.replace(/\s/g,"");
        // console.log(str);
        var html = '';
        if(data.FAV_TYPE == 'GRAB'){
            if(data.cardState == 5){
               html = '<div class="weic-card-btn weic-btn-gradientOrange" data-type="'+data.DESCRIPTION+'"'
                            + 'onclick=openCard(this) data-value='+str+' data-id="'+data.CARD_ID+'">'
                        + '<span>抢券</span>'
                    + '</div>';
            }else {
                var name = '';
                if(data.cardState == 3){
                    name = '即将开枪';
                }else if(data.cardState == 4){
                    name = '已结束';
                }
                html = '<div class="weic-card-btn weic-btn-gradientGray">'
                            +'<span>' + name
                            +'</span>'
                        + '</div>';
            }

        }else{
            html = '<div class="weic-card-btn weic-btn-gradientOrange" data-type="'+data.DESCRIPTION+'"'
                + 'onclick="openCard(this)" data-value='+str+' data-id="'+data.CARD_ID+'">'
                + '<span>领取</span>'
                + '</div>';
        }
        return html;
    }

    var img = {
        "CURREN":["<%=basePath%>newStyle/weixin/images/card/img_9.png","<%=basePath%>newStyle/weixin/images/card/common_use.jpg"],
        "NEW":["<%=basePath%>newStyle/weixin/images/card/img_4.png","<%=basePath%>newStyle/weixin/images/card/new.jpg"],
        "OLD":["<%=basePath%>newStyle/weixin/images/card/img_6.png","<%=basePath%>newStyle/weixin/images/card/old_and_new.jpg"],
        "MAN":["<%=basePath%>newStyle/weixin/images/card/img_5.png","<%=basePath%>newStyle/weixin/images/card/man.jpg"],
        "WEM":["<%=basePath%>newStyle/weixin/images/card/img_1.png","<%=basePath%>newStyle/weixin/images/card/woman.jpg"],
        "BIRTH":["<%=basePath%>newStyle/weixin/images/card/img_7.png","<%=basePath%>newStyle/weixin/images/card/birthday.jpg"],
        "GRAB":["<%=basePath%>newStyle/weixin/images/card/img_8.png","<%=basePath%>newStyle/weixin/images/card/time_limit.jpg"],
        "APPLY":["<%=basePath%>newStyle/weixin/images/card/img_3.png","<%=basePath%>newStyle/weixin/images/card/member.jpg"],
        "TERM":["<%=basePath%>newStyle/weixin/images/card/img_2.png","<%=basePath%>newStyle/weixin/images/card/full_time.jpg"]
    }
    
</script>

</html>
