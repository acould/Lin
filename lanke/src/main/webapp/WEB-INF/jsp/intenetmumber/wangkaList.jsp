<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-touch-fullscreen" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="address=no">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
	<title>${intenetName}</title>
	<link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/fastclick.js"></script>
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
	<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
	<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
	<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
</head>
<style>
	.weic .weui-cell {
		padding: 0.266rem;
	}
</style>
<body class="weic-bgfff weic noStore" ontouchstart>
	<%--搜索框--%>
	<div class="weui-search-bar" id="searchBar">
		<form class="weui-search-bar__form" action="#" id="form">
			<div class="weui-search-bar__box">
				<i class="weui-icon-search"></i>
				<input type="search" class="weui-search-bar__input" id="keywords" name="keywords" placeholder="搜索" required="" onkeyup="loadInternetStore('seach')">
				<a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
			</div>
			<label class="weui-search-bar__label" id="searchText" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
				<i class="weui-icon-search"></i>
				<span>搜索</span>
			</label>
		</form>
		<a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel" onclick="quxiao()">取消</a>
	</div>

	<%--门店列表--%>
	<div style="padding-bottom: 1.34rem" id="storeList">

	</div>


	<%--底部菜单--%>
	<%@ include file="bottom_navigation.jsp"%>
<%--	<div class="weui-tabbar index">
		<a href="<%=basePath %>intenetmumber/index" class="weui-tabbar__item ">
			<div class="weui-tabbar__icon weic-icon-home"></div>
			<p class="weui-tabbar__label">首页</p>
		</a>
		<a href="<%=basePath %>intenetmumber/introduction" class="weui-tabbar__item weui-bar__item--on">
			<div class="weui-tabbar__icon weic-icon-store"></div>
			<p class="weui-tabbar__label">门店</p>
		</a>
		<a href="<%=basePath %>intenetmumber/goToMiniWeb" class="weui-tabbar__item">
			<div class="weui-tabbar__icon weic-icon-internet"></div>
			<p class="weui-tabbar__label">微官网</p>
		</a>
		<a href="<%=basePath %>myMember/gotoUserCenter" class="weui-tabbar__item">
			<div class="weui-tabbar__icon weic-icon-my"></div>
			<p class="weui-tabbar__label">我的</p>
		</a>
	</div>--%>
</body>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>

<script>
    $(function() {
        loadInternetStore();
        FastClick.attach(document.body);
    });
    $(function () {
        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);
            if (sTop >= 350) {
                $("#searchBar").addClass("animateTop")
            }
            else {
                $("#searchBar").removeClass("animateTop")
            }
        });
    })
	function quxiao() {
        $("#keywords").val("");
        loadInternetStore();
    }
	function loadInternetStore(fang) {
        $.ajax({
            type: "POST",
            url: '<%=basePath%>intenetmumber/loadInternetStore.do',
            data: $('#form').serialize(),
            success: function (data) {
                if (data.result == "true") {
                    var varList = data.varList;
                    var html = '';
                    var text = "该商家暂无门店";
                    if(fang == "seach") {
                        text = "未搜索到该门店";
					}
                    for (var i = 0; i < varList.length; i++) {
                        var pd = varList[i];
                        var picture_url = "<%=basePath%>newStyle/weixin/images/normal-img.jpg";
						if(pd.picture_url.indexOf("null") < 0){
                            picture_url = pd.picture_url;
						}
						html += '<a class="weui-cell weui-cell_access weic-relative" href="<%=basePath %>intenetmumber/wangkaCenter?storeId='+pd.STORE_ID+'">'+
									'<div class="weui-cell__hd weic-storelist-img" style="background:#eee url('+picture_url+')no-repeat center center;background-size: 100%"></div>'+
									'<div class="weui-cell__bd weic-store-list">'+
										'<p class="title">'+pd.STORE_NAME+'</p>'+
										'<p class="adress">'+pd.PROVINCE+'&nbsp;'+pd.CITY+'&nbsp;'+pd.COUNTY+'&nbsp;'+pd.ADDRESS+'</p>'+
										'<p class="icon">'+
											'<i class="icon-location blue"></i>'+
											'<i class="icon-phone orange"></i>'+
										'</p>'+
									'</div>'+
									'<div class="weui-cell__ft"></div>'+
                            	'</a>';
                    }
                    if(html == ''){
                        html = '<div class="weic-noboby">'+
									'<div class="weic-nobody-img"></div>'+
									'<div class="weic-nobody-text">'+text+'</div>'+
								'</div>';
					}
                    $("#storeList").html(html);
                }else{
                	layer.msg(data.message);
				}
            },
            error: function (){
                layer.msg("系统繁忙，请稍后再试");
                return ;
            }
        });
    }
</script>
</html>
