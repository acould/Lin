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
    <link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<style>

</style>
<body class="weic weic-body-bgf3f3f3 card">
<div class="weic-item-padding30  weic-martop-transmit weic-bgfff">
    <div class="succeed_icon"></div>
    <p style="text-align: center;font-size: 0.4rem;padding-bottom: 0.8rem">恭喜您，申请成功！<br>别忘了去网吧<font color="red">收银台激活</font>哦</p>
</div>
<c:if test="${not empty pdInternet}">
<div class="weic-bgfff weic-martop-20">
    <a class="weui-cell weic-relative" href="javasrcipt:">
        <div class="weui-cell__hd weic-card-img">
            <img src="<%=basePath%>newStyle/weixin/images/card/img_3.png" alt="" width="100%">
        </div>
        <div class="weui-cell__bd">
            <div class="weic-card-msg">
                <p class="weic-card-title">${pdInternet.SUB_TITLE}</p>
                <p class="weic-card-time">有效期：${pdInternet.AVAILABLE_TIME}</p>
                <p class="weic-card-time">适用门店：${pdInternet.STORE_NAME}</p>
            </div>
        </div>
        <div class="weui-cell__ft">
            <p class="weic-card-repertory"><font color="red">${pdInternet.QUANTITY}</font>/${pdInternet.cardSum + pdInternet.QUANTITY}</p>
            <div class="weic-card-btn weic-btn-gradientOrange" id="lq" onclick="openCard()"><span id="lqz">领取</span></div>
        </div>
    </a>
</div>
</c:if>
<div style="padding: 2rem 1rem">
    <div class="weic-dent-btn weic-btn-gradientBlue"  onclick="window.location.href='<%=basePath%>intenetmumber/index'" style="margin-bottom: 0.8rem">返回首页</div>
</div>
</body>
<!--移动端适配，px转化为rem-->
<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/roomes.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/mobile-check.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">
    $(function(){
        FastClick.attach(document.body);
	
    });
    function openCard(){
        var html = '<div class="open-card-box"><div class="card-img"><img src="<%=basePath%>newStyle/weixin/images/card/member.jpg" alt="" width="100%"></div>'+
            '<p class="weic-card-title">${pdInternet.SUB_TITLE}</p>'+
            '<p class="weic-card-time"><font color="#333">有效期：</font>${pdInternet.AVAILABLE_TIME}</p>'+
            '<p class="weic-card-time"><font color="#333">适用门店：</font>${pdInternet.STORE_NAME}</p>'+
            '<p class="weic-card-time"><font color="#333">优惠说明：</font>${pdInternet.DESCRIPTION}</p>'+
            '<p class="weic-card-time"><font color="#333">使用提醒：</font>${pdInternet.NOTICE}</p>'+
            '<div style="padding: 2rem 0 0.5rem 0">'+
            '<div class="weic-recharge-btn weic-btn-gradientOrange" style="width: 75%;" onclick=lingqu("${pdInternet.CARD_ID}")>确定</div>'+
            '</div>'+
            '</div>'
        layer.open({
            content: html
            ,className: 'signIn_box'
            ,style:"background: none;box-shadow: none;width:85%"
            ,end:function(){

            }
        });
    }
	
	
	function lingqu(id){
		confirm("是否确定领取该商品",function(){
				var _this=this;
				var flag = true;
				if(flag){
					var flag = false;
					$.ajax({
 		            type: "POST",
 		            async:false,
 		            url:'<%=basePath%>indexMember/receiveBenefits.do',
					data : {mayId : id,falgs:'T'},
					success: function(data) {
 		                data.message = data.data || data.message;
 		                message(data.message);
 		                flag = true;
 		                if(data.card_result == "true"){
							$("#lq").removeAttr("onclick");
							$("#lq").css({"background": "#CCCCCC"});
							$("#lqz").html("已领取");
							sqhx(id);
							//var url = '<%=basePath%>'+data.backurl;
                            //window.location.href = url;
						}else{
	                        layer.close(index);
						}
 		            },
 		            error:function(){
                        flag = true;
 		                message("系统繁忙，请稍后再试");
 		                removeDis();
 		            }
				})
			}
		})
	};
	
	function sqhx(card_id) {
        var url = window.location.href;
        $.ajax({
            type: "POST",
            url: '<%=basePath%>indexMember/goWechatCard1.do',
            data: {url: url, card_id: card_id,state:'benefit'},
            dataType: 'json',
            async: false,
            beforeSend: open_loading(''),
            success: function (data) {
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
                            }
                        });
                    });
                }else{
                    message(data.message);
                }

            },
            error: function () {
                layer.closeAll();
                message("系统繁忙，请稍后再试！");
                return false;
            }
        });
    }
</script>
</html>
