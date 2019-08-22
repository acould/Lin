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
	<title>热门赛事</title>
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

	</style>
</head>
<body class="weic  weic-bgfff">
	<div class="weic-gameList-content">
		<div class="weui-row weui-no-gutter weic-nav-module">
			<div class="weui-col-33 active" data-type="all">全部比赛</div>
			<div class="weui-col-33" data-type="ing">进行中</div>
			<div class="weui-col-33" data-type="end">已结束</div>
		</div>
		<div id="gameList">

		</div>

	</div>
</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script>
	$(".weic-nav-module div").click(function () {
		var othis = $(this),type = othis.data("type");
		$(".weic-nav-module div").removeClass("active");
		$(this).addClass("active");
		initialize(type)
	})
	var url = "<%=basePath%>wxMatches/loadGameList.do?flag=matches";

	// 初始化
	initialize("all")
	function initialize(type) {
		open_loading();
		$.post(url,{'type':type}, function (res){
            layer.closeAll();
			var list = res.data.list;
			//0：未开始，1：报名中，2：报名结束，3：比赛中，4：比赛结束；
			var htmls = "";
			if(list.length >0){
				for (var i = 0; i < list.length; i++) {
					var data = list[i];
					var style = "";
					if(data.status == 0 || data.status == 1){
						style = "applyIng"
					}else if(data.status == 3){
						style = "gameIng";
					}else if(data.status == 2 || data.status == 4){
						style = "finish";
					}
					var people = '';
					if(data.enroll_num >= 0){
						people = "报名人数:"+data.enroll_num+"人";
					}else {
						people = '';
					}

                    var tt = data.status_info;
					htmls += '<div class="weic-gameList-box" data-id="'+data.id+'">'+
							'<img src="'+data.url+'" alt="" width="100%">'+
							'<div class="gameMsg-box '+style+'">'+
							'<a class="weui-cell" style="padding: 0">'+
							'<div class="weui-cell__bd">'+
							'<p class="title">'+data.name+'</p>'+
							'<p class="store weic-line-overflow">主办门店：'+data.stores+'</p>'+
							'<p class="people">'+people+'</p>'+
							'</div>'+
							'<div class="weui-cell__ft">'+
							'<div class="weic-card-btn">'+
							'<span>'+tt+'</span>'+
							'</div>'+
							'</div>'+
							'</a>'+
							'</div>'+
							'</div>'
				}
			}else {
				htmls = '<div class="weic-noboby gamesList" style="height: 6.2rem;">'+
						'<div class="weic-nobody-img"></div>'+
						'<div class="weic-nobody-text">暂无赛事，晚点再来吧</div>'+
						'</div>'
			}
            $("#gameList").html(htmls);

			$(".weic-gameList-box").click(function () {
				window.location.href = "<%=basePath%>wxMatches/goGameDetail.do?matches_id=" + $(this).data("id");
			})
		})
	}

	//前往赛事详情页面
	function go_gameDetails(id) {
		window.location.href = "<%=basePath%>wxMatches/goGameDetail.do?matches_id="+id;
	}
</script>

</html>