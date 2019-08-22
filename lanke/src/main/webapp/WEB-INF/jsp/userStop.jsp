<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge" chrome="1">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title></title>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/more.css">
</head>
<body onload="closeWindow();">
    <div class="widthFluid">
        <p class="text-center icon-success">
            <span class="kong">
            </span>
        </p>
        <c:if test="${empty PHONE}">
        <p class="ganmeTips">该公众号暂不可用,请联系网吧</p>
        </c:if>
         <c:if test="${not empty PHONE}">
        <p class="ganmeTips">该公众号暂不可用,请联系网吧:${PHONE}</p>
        </c:if>
        <div style="margin: 0 15px">
            <p class="tipBtn" style="margin: 0 auto;margin-top: 56px; width: 85%"><span id="show">关闭页面</span></p>
        </div>
    </div>
    <p class="text-center" style="width: 100%;position: absolute;bottom: 30px;color:#999">技术支持：网晶科技“揽客”项目团队</p>

    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    
    
    
	<script type="text/javascript">
		var time = 5;
		function closeWindow(){
			window.setTimeout('closeWindow()',1000);  
			if(time>0){
				document.getElementById("show").innerHTML="<font color=red>"+time+"</font>秒后关闭页面";  
				time--;
			}else{
				WeixinJSBridge.call('closeWindow');
				//this.window.opener=null; //关闭窗口时不出现提示窗口  
				//window.close();  
			}
		}
	</script>
    
</body>
</html>