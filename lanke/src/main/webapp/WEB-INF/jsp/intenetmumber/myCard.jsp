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
<title>${intenetName}</title>
<link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
<script type="text/javascript" src="<%=basePath%>assets/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/fastclick.js"></script>
<link rel="stylesheet" href="<%=basePath%>assets/js/dialog.css">
<script type="text/javascript" src="<%=basePath%>assets/js/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/iscroll.js"></script>
<link rel="stylesheet" href="<%=basePath%>assets/js/swiper-3.2.7.min.css">


<script type="text/javascript"
	src="<%=basePath%>assets/js/swiper-3.2.7.jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/mobile.js"></script>

</head>

<body style="background-color:#f3f3f3;">
	<div class="orderlist">
		<ul class="g-tab flex-box">
			<li class="active" data-type="1">已领取</li>
			<li data-type="2">已使用</li>
			<li data-type="3">已失效</li>
		</ul>
	</div>

	<ul class="g-list">
	</ul>
	<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
	<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
	<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<script>
var appendHtml=function(res,_obj){
    var btns = "";
    var htmls = "";
    $.each(res.data.subjectList,function(i,item){
        var type = $(".active").data('type');
        if(item.STATE == type){
            if(item.STATE == "1"){
                btns = '<div class="activebox" onclick=sqhx("'+item.CARD_ID+'","'+item.CARD+'")>'+
                    '<p class="btn-act">去使用</p>'+
                    '<p class="p2">请到收银台核销</p>'+
                    '</div>';
            }else{
                btns = "";
            }
            htmls += '<li class="flex-box">'+
                '<div class="info">'+
                '<p class="tit ellipsis">'+item.SUB_TITLE+'</p>'+
                '<p class="p5">有效期：'+item.BEGIN_TIMESTAMP+'至'+item.END_TIMESTAMP+'</p>'+
                '<p class="p5">适用门店：'+item.storeName+'</p>'+
                '<p class="p5">使用说明：'+item.NOTICE+'</p>'+
                '</div>'+
                btns +
                '</li>';
        }
    });
    _obj.html(htmls);
};


var argument={'url' : '<%=basePath%>myMember/myfuli.json','res' : {'type':$('.g-tab .active').data('type')},'success' : appendHtml,'mainbox':$('.g-list')};
$(function(){ 
    addData(argument);
    $(".g-tab li").click(function() {
    	$(this).parent().find('li').removeClass('active');
    	$(this).addClass('active');
        argument.res.type=$(this).data('type');
        argument.mainbox.empty();
        addData(argument);
    });
});

   function sqhx(card_id,code) {
       var url = window.location.href;
       $.ajax({
           type: "POST",
           url: '<%=basePath%>indexMember/goWechatCard1.do',
           data: {url: url, card_id: card_id,code2:code, state:'myCard'},
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
                       jsApiList: ['openCard', 'chooseCard','closeWindow']
                       // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                   });
                   layer.closeAll();
                   wx.ready(function () {
                       wx.openCard({
                           cardList: [{
                               cardId: data.pd.card_id,
                               code: data.pd.code2
                           }],// 需要打开的卡券列表
						   success: function (res) {
                               location.reload();
                           }
                       });
                   })
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

//监听卡券关闭按钮
document.addEventListener('visibilitychange',function (evt) {
    if (document.visibilityState != 'hidden') {
        // alert("2222222222");
        //window.location.reload();
        //这里执行你需要执行的代码
    }
});
	</script>
</body>

</html>
