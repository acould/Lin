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
	<link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
	<link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/zepto.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/fastclick.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/js/dialog.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/dialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/iscroll.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/js/swiper-3.2.7.min.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/swiper-3.2.7.jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/mobile.js"></script>
</head>

<body>
<div class="fixed-bottom laodaixin">
    <div class="wjx_box1">
        <div class="wjx_box1_title2"><img src="${urlImg}" class="avatar">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${nickname}邀请您加入${intenetName}</div>
        <div class="wjx_box1_title1">您可获得<span class="red">${SUB_TITLE}</span></div>
    </div>
   <%--  <div id="container" style="border:1px solid #fff;padding:0px 30px;">
        <div class="info_main" style="margin-top:0;border:1px solid #fff;">
            <div class="line">
                <div class="title">&nbsp;姓<span style="color:#fff;">机号</span>名：</div>
                 <input  type="hidden" id="userId" name="userId" value="${userId}"/>
                <div class="info">
                    <div class="inner"><input type="text" id="realname" placeholder="" value=""></div>
                </div>
            </div>
            <div class="line">
                <div class="title">&nbsp;身份证号：</div>
                <div class="info">
                    <div class="inner"><input type="text" id="idCard" placeholder="" value=""></div>
                </div>
            </div>
        </div>
    </div> --%>
    <div class="wjx_box3">
        <div id="wjx_btn" class="wjx_btn weixin-btn">
         <a class="btn" href="javascript:void(0)" rel="${url}" ><i class="c-icon" style="color">获取邀请码</i></a>
        </div>
    </div>
</div>
<script>
$(".weixin-btn").click(function(){
    window.location.href = $(".btn").attr("rel");
}); 
</script>
</body>

</html>
