<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
<link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">
<link rel="stylesheet" href="<%=basePath%>static/login/css/login.css">
<link rel="stylesheet" href="<%=basePath%>static/login/css/bootstrap.min.css">
</head>
<body>
<div class="widthRegis">
        <div class="auto">
            <div class="float-left">
                <img src="<%=basePath%>newStyle/images/suureder.png" alt="" width='230px'>
            </div>
            <div class="float-right">
				<input id="internet_id" type="hidden" value="${internet_id}">
                <img src="<%=basePath%>newStyle/images/chenggong.png" alt="">
                <p class="widthP" style="margin-bottom: 46px;line-height: 26px;">快去进行微信授权吧，请确保：<br>微信公众号已认证<span style="padding-left:16px">微信卡券功能已开通<span></p>
                	<input id="text" type="hidden" value="${url}" style="width:80%" />
                <p>
                	<span class="btncook1-reg" onclick="closeLayer()">不了</span>
                	<span onclick="goAuthoriz()" class="btncook2-reg">去授权</span>
                </p>
            </div>
        </div>
</div>
<script src="<%=basePath%>static/login/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>newStyle/js/qrcode.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script>
	function goAuthoriz(){
		var newTab=window.open('<%=basePath%>/register/goAccredit.do'); 
			$.ajax({
				type: "POST",
				url: '<%=basePath%>register/goAuthoriz.do',
				data: {internet_id:$("#internet_id").val()},
				dataType:'json',
				cache: false,
				success: function(data){
					if(data.result == "true"){
						newTab.location.href = data.url1;  
					}else{
						newTab.location.href = '<%=basePath%>register/goAccreditError.do'
					}
				},
				error:function(){
				   newTab.location.href = '<%=basePath%>register/goAccreditError.do'
				}
			});
		}
		function closeLayer(){
			var index = parent.layer.getFrameIndex(window.name);
		 	parent.layer.close(index);
		}
		// 图片禁止拖住 右键
    		var imgs=$("img");
    		imgs.on("contextmenu",function(){return false;});
    		imgs.on("dragstart",function(){return false;});
</script>
</body>
</html>
