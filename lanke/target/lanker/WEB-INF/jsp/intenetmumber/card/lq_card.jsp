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
<html>
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
    <title>卡券领取</title>
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<body>

<h3 id="h1_id" style="text-align: center;margin: 2rem 1rem;"></h3>

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
<script>

    //初始化领取状态
    $(function() {
        var status = '${status}';
        if(status == 1){
            alert("该卡券您已领取");
            $("#h1_id").html("该卡券您已领取")
            setTimeout(function () {
                WeixinJSBridge.call('closeWindow');
            }, 1000)
        }


        var wx2 = '${wx}';
        var order_id = '${pd.order_id}';
        var OuterStr = order_id;
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
                jsApiList : [ "addCard" ,'closeWindow']
                // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
            layer.closeAll();
            //config配置信息失败，执行error
            // wx.error(function(res){
            //     alert("参数错误");
            // });
            //config配置信息成功，执行ready
            wx.ready(function(){
                var cardId = '${wx.card_id}';
                var timestamp = '${wx.config.timestamp}';
                var signature = '${wx.config.signature}';
                var cardExt = '{"timestamp":"'+timestamp+'","signature":"'+signature+'","card_id":"'+cardId+'","outer_str":"'+OuterStr+'"}';
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
    //监听卡券关闭按钮
    document.addEventListener('visibilitychange',function (evt) {
        setTimeout(function () {
            WeixinJSBridge.call('closeWindow');
        }, 500)
        if (document.visibilityState != 'hidden') {
            // alert("2222222222");
            //window.location.reload();
            //这里执行你需要执行的代码
        }
    });

</script>
</html>
