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
	<script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
	<link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
	<script type="text/javascript" src="<%=basePath%>assets/js/fastclick.js"></script>
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<body class="weic weic-bgfff">
<div class="weic-wkCenter-head" style="background-image: url('${varList[0].picture_url}')">
	<div class="weic-head-back">
		<div class="weic-wk-logo" style="background-image: url('${pd.HEAD_IMG}')"></div>
		<div class="weic-wkCenter-msg">
			<p class="name">${pd.STORE_NAME}</p>
			<p class="adress">${pd.PROVINCE}&nbsp;${pd.CITY}&nbsp;${pd.COUNTY}&nbsp;${pd.ADDRESS}</p>
		</div>
	</div>
	<div class="weic-body-padding20">
		<div class="weic-index-navIcon" style="padding: 0.7rem 0 0.6rem 0;">
			<div class="weui-flex">
				<div class="weui-flex__item weic-text-center">
					<c:if test="${pd.TELEPHONE != ''}">
					<a href="tel://${pd.TELEPHONE}">
						<div class="weic-wkPhone weic-gradient-blue"><i class="icon-phone r-icon"></i></div>
					</a>
					</c:if>
					<c:if test="${pd.TELEPHONE == ''}">
						<a href="javascript:;" onclick="message('号码未填写')">
							<div class="weic-wkPhone weic-gradient-blue"><i class="icon-phone r-icon"></i></div>
						</a>
					</c:if>
					<p style="font-size: 0.373rem;color: #333">一键拨号</p>
				</div>
				<div class="weui-flex__item weic-text-center show-alert">
					<div class="weic-wkLocation weic-gradient-orange "><i class="icon-location r-icon"></i></div>
					<p style="font-size: 0.373rem;color: #333">暂未开放</p>
				</div>
			</div>
		</div>
	</div>
	<div class="weic-cut-off"></div>
	<div class="weic-section-title"><div class="leftText">网咖环境</div></div>
	<div style="padding: 0.246rem 0.133rem">
		<div class="weui-row weui-no-gutter">
			<c:choose>
				<c:when test="${not empty varList}">
					<c:forEach items="${varList}" var="var" varStatus="vs">
						<div class="weui-col-50 pb1" onclick="opening(${vs.index})">
							<div class="weic-wkcenter-img" style="background-image: url('${var.picture_url}')"></div>
							<input type="hidden" name = "img" value="${var.picture_url}">
						</div>
					</c:forEach>
				</c:when>
			</c:choose>
		</div>
	</div>
</div>
</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script type="text/javascript">
    var picList = document.getElementsByName("img");
    var picArr = new Array(picList.length);
    for(var i=0;i<picList.length;i++){
        picArr[i] = picList[i].value;
    }
    var pb1 = $.photoBrowser({
        items: picArr
    });
   function opening(i){
       pb1.open(i);
   }
</script>
</html>
