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
	<title>赛事详情</title>
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
		.chosen_enroll_type {position: absolute;top:0;left: 0;width: 100%;}
		.chosen_enroll_type div{width: 50%;float: left;height: 4.8rem}
		.gameMsg-box .store {width: 100%}
	</style>
</head>
<body class="weic  weic-bgfff">
<input type="hidden" id="matches_id" value="${pd.matches_id}">
<div id="game-body">
	<%--<img src="<%=basePath%>newStyle/weixin/images/game_example.jpg" alt="" width="100%" class="weic-block">
	<div class="weic-bor gameCenter">
		<div class="gameMsg-box">
			<a class="weui-cell" href="javasrcipt:" style="padding: 0">
				<div class="weui-cell__bd applyIng">
					<p class="title">艾格拉网咖全民对抗赛<span class="game-tips">27人报名</span></p>
					<p class="store">主办门店：文一店、九堡店、三墩店...</p>
				</div>
			</a>
			<div class="weic-item">
				<div class="weic-item-title">时间安排</div>
				<p class="time">报名时间：<span>2019-01-10 至 2019-10-12</span></p>
				<p class="time">比赛时间：<span>2019-01-10 至 2019-10-12</span></p>
			</div>
			<div class="weic-item">
				<div class="weic-item-title border-bottom"style="padding-bottom: 0.266rem">赛事详情</div>
				<div id="gameContent" style="padding: 0.4rem 0">
					111

				</div>
			</div>
		</div>
	</div>
	<div class="gameIng" onclick="go_apply('1','2')">
		<div class="weic-through_btn">比赛中</div>
	</div>--%>


</div>
<div class="weic-article-foot" style="margin: 0.6rem 0.4rem 2rem 0.4rem;font-size: 0.34rem;color: #999;">点击右上角进行分享</div>
</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>

<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script>
    var mv_url = window.location.href;
	var url = "<%=basePath%>wxMatches/loadGameDetail.do";
	var id = $("#matches_id").val();
    var is_enroll = 0;
    var is_system = 0;
    var flag = '${pd.flag}';
	// 初始化
	initialize(id)
	function initialize(id) {
		open_loading();
        var field = new Object();
        field.url = mv_url;
        field.matches_id = id;
        $.post(url, field, function (res) {
            layer.closeAll()
            var data = res.data.pd;
            //0：未开始，1：报名中，2：报名结束，3：比赛中，4：比赛结束；
            var style = "";
            var people = '';
            var htmls = "";
            if (data.status == 0 || data.status == 1) {
                style = "applyIng"
            } else if (data.status == 3) {
                style = "gameIng";
            } else if (data.status == 2 || data.status == 4) {
                style = "finish";
            }
            if (data.enroll_num >= 0) {
                people = '<span class="game-tips">' + data.enroll_num + '人报名</span>'
            } else {
                people = '';
            }

            var tt = data.status_info;
            is_system = data.is_system;
            is_enroll = data.is_enroll;
            if ((data.status == '1' || data.status == '2' || data.status == '3' || data.status == '4') && is_enroll == 1) {
                if (tt == "报名中") {
                    tt = "已报名"
                }
                tt = tt + '，查看信息';
            }

            htmls = '<img src="' + data.url + '" alt="" width="100%" class="weic-block">' +
                '<div class="weic-bor gameCenter">' +
                '<div class="gameMsg-box">' +
                '<a class="weui-cell" href="javasrcipt:" style="padding: 0">' +
                '<div class="weui-cell__bd applyIng">' +
                '<p class="title">' + data.name + people + '</p>' +
                '<p class="store">主办门店：' + data.stores + '</p>' +
                '</div>' +
                '</a>' +
                '<div class="weic-item">' +
                '<div class="weic-item-title">时间安排</div>' +
                '<p class="time">报名时间：<span>' + data.enroll_start_time.substring(0, 10) + ' 至 ' + data.enroll_end_time.substring(0, 10) + '</span></p>' +
                '<p class="time">比赛时间：<span>' + data.start_time.substring(0, 10) + ' 至 ' + data.end_time.substring(0, 10) + '</span></p>' +
                '</div>' +
                '<div class="weic-item">' +
                '<div class="weic-item-title border-bottom"style="padding-bottom: 0.266rem">赛事详情</div>' +
                '<div id="gameContent" style="padding: 0.4rem 0">' +
                data.content +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';

            if (flag != 'matches_share' && flag != 'matches_preview') {
                htmls += '<div class="' + style + '" onclick=go_apply(' + data.status + ',' + data.enroll_type + ') id="footBtn">' +
                    '<div class="weic-through_btn">' + tt + '</div>' +
                    '</div>';
            }
            $("#game-body").html(htmls);
            wxShare(res.data.jsapi, data);
        });
	}
	function go_apply(status,enroll_type) {
		if(status == "0"){
			message("报名未开始");
			return;
		}
        if(status == "2" && is_enroll == 0){
            message("报名已结束");
            return;
        }
        if(status == "3" && is_enroll == 0){
            message("比赛中");
            return;
        }
        if(status == "4" && is_enroll == 0){
            message("比赛已结束");
            return;
        }
        if(is_enroll == 0){
            if(enroll_type == "1"){
                window.location.href = "<%=basePath%>wxMatches/goOneEnroll?matches_id="+id;
            }else if(enroll_type == "2"){
                var html = 	'<div style="position: relative">'+
                    '<img src="<%=basePath%>newStyle/weixin/images/game_team_bg.png" alt="" width="100%">'+
                    '<div class="chosen_enroll_type">'+
                    '<div onclick="goBuildTeam()"></div>'+
                    '<div onclick="goChooseTeam()"></div>'+
                    '</div>'+
                    '</div>'
                layer.open({
                    content: html
                    , style: "background: none;box-shadow: none;width:100%"
                });
            }
        }
		if(is_enroll == 1){
			if(enroll_type == "1"){
				window.location.href = "<%=basePath%>wxMatches/goOneEnroll?matches_id="+id;
			}else if(enroll_type == "2"){
                if(is_system == 1){
                    goBuildTeam()
                }else{
                    goChooseTeam()
                }
			}
		}
	}

	//去队伍报名页面
	function goBuildTeam() {
		window.location.href = "<%=basePath%>wxMatches/goBuildTeam?matches_id="+id;
	}

	//去选择队伍报名页面
	function goChooseTeam(){
		window.location.href = "<%=basePath%>wxMatches/goChooseTeam?matches_id="+id;
	}

	function wxShare(jsapi, data) {

        wx.config({
            debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId : jsapi.appid, // 必填，公众号的唯一标识
            timestamp : jsapi.timestamp, // 必填，生成签名的时间戳
            nonceStr : jsapi.nonceStr, // 必填，生成签名的随机串
            signature : jsapi.signature,// 必填，签名，见附录1
            jsApiList : [ 'onMenuShareAppMessage','onMenuShareTimeline' ]
            // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });


        wx.ready(function(){
            //以下微信将要废弃
            wx.onMenuShareAppMessage({
                title: data.name, // 分享标题
                desc: data.description, // 分享描述
                link: jsapi.url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                imgUrl: data.url, // 分享图标
                type: 'link', // 分享类型,music、video或link，不填默认为link
                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                success: function () {
                    // 用户点击了分享后执行的回调函数
                    //alert('分享成功');
                }
            });
            wx.onMenuShareTimeline({
                title: data.name, // 分享标题
                link: jsapi.url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                imgUrl: data.url, // 分享图标
                success: function () {
                    // 用户点击了分享后执行的回调函数
                }
            });
        });
    }
    //后退返回赛事详情
    <%--var backHistoryUrl = '<%=basePath %>wxMatches/goGamesList.do';--%>
</script>
<%--<script src="<%=basePath%>newStyle/weixin/js/backHistory.js"></script>--%>

</html>