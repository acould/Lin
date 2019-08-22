<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String flag = "";
    if(request.getServletPath().contains("index.jsp")){
        flag = "bottom_index";
    }else if(request.getServletPath().contains("wangkaList.jsp")){
        flag = "bottom_store";
    }else if(request.getServletPath().contains("userCenter.jsp")){
        flag = "bottom_user";
    }else if(request.getServletPath().contains("memberLife.jsp")){
        flag = "bottom_memberLife";
    }

%>
<html>
<body>

    <div class="weui-tabbar index">
        <a href="<%=basePath %>intenetmumber/index" class="weui-tabbar__item" id="bottom_index">
            <div class="weui-tabbar__icon weic-icon-home"></div>
            <p class="weui-tabbar__label">首页</p>
        </a>
        <a href="<%=basePath %>intenetmumber/introduction" class="weui-tabbar__item" id="bottom_store">
            <div class="weui-tabbar__icon weic-icon-store"></div>
            <p class="weui-tabbar__label">门店</p>
        </a>
        <a href="javascript:" class="weui-tabbar__item weic-relative" id="bottom_up">
            <div class="weui-tabbar__icon weic-icon-up"></div>
            <div class="weui-tabbar__icon"></div>
            <p class="weui-tabbar__label up-color"><span id="bottom_span">上机</span></p>
        </a>
        <a href="<%=basePath %>wxML/memberLife.do" class="weui-tabbar__item" id="bottom_memberLife">
            <span class="weic-badge weic-hide" style="position: absolute;right: 0.43rem;" id="bottom_ml"></span>
            <div class="weui-tabbar__icon weic-icon-memberLife"></div>
            <p class="weui-tabbar__label">会员生活</p>
        </a>
        <a href="<%=basePath %>myMember/gotoUserCenter" class="weui-tabbar__item" id="bottom_user">
            <span class="weic-badge weic-hide" style="position: absolute;right: 0.7rem;" id="bottom_my_span"></span>
            <div class="weui-tabbar__icon weic-icon-my"></div>
            <p class="weui-tabbar__label">我的</p>
        </a>
    </div>
    <script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
    <script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
    <script>
        var url = window.location.href;
        var flag = '#<%=flag%>';
        $(flag).addClass("weui-bar__item--on");
        $(flag).siblings().removeClass("weui-bar__item--on");

        $(function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>qrCode/getState.do',
                data: {},
                dataType: 'json',
                async: false,
                success: function (data) {
                    layer.closeAll();
                    if(data.up_state == '1'){
                        $("#bottom_up").addClass("weui-bar__item--on");
                        $("#bottom_span").html("下机");
                    }
                    if(data.cardSize != null && data.cardSize > 0){
                        $("#bottom_my_span").removeClass("weic-hide");
                    }
                    if(data.mlSize != null && data.mlSize > 0){
                        $("#bottom_ml").removeClass("weic-hide");
                    }


                }
            });
        })

        $("#bottom_up").click(function () {
            $.ajax({
                type: "POST",
                url: '<%=basePath%>qrCode/getWxConfig.do',
                data: {url:url},
                dataType: 'json',
                async : false,
                beforeSend:open_loading(''),
                success: function (data) {
                    if (data.result == "true") {
                        wx.config({
                            debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId : data.appid, // 必填，公众号的唯一标识
                            timestamp : data.wx.timestamp, // 必填，生成签名的时间戳
                            nonceStr : data.wx.nonceStr, // 必填，生成签名的随机串
                            signature : data.wx.signature,// 必填，签名，见附录1
                            jsApiList : ['scanQRCode']
                            // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });
                        layer.closeAll();
                        wx.ready(function() {
                            wx.scanQRCode({
                                needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                                scanType: ["qrCode"], // 可以指定扫二维码还是一维码，默认二者都有
                                success: function (res) {
                                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                                }
                            });
                        })
                    }else{
                        layer.closeAll();
                        window.location.href = data.backurl;
                    }

                },
                error: function () {
                    layer.closeAll();
                    layer.msg("系统繁忙，请稍后再试！");
                    return false;
                }
            });

        })

    </script>
</body>
</html>
