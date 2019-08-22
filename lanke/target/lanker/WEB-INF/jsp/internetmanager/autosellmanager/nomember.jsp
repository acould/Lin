<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2019/7/18
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>不是会员</title>
</head>
<body>
<div id="nomember">
    <div class="content">
        <div class="top">
            <img src="<%=basePath%>newStyle/images/vip_picture.png" alt="">
        </div>
        <div class="mid">
            <img src="<%=basePath%>newStyle/images/vip_button1.png" alt="">
        </div>

        <footer>
            <img src="<%=basePath%>newStyle/images/vip_button2.png" alt="">
        </footer>
    </div>
</div>
</body>
<script>
    new Vue({
        el:'#nomember',
        data:{
            msg:'111'
        },
    })
</script>
<style>
    #nomember{
        width: 100%;
    }
    #nomember .content{
        width:18.75rem;
        margin:0 auto;
        overflow:hidden;
    }
    #nomember .content .top{
        width: 8.9rem;
        height:8.56rem;
        margin:6.37rem auto 3.25rem;
    }
    #nomember .content .top img{
        display: block;
        width: 100%;
        height:100%;
    }
    #nomember .content .mid{
        width: 13.75rem;
        height:2.56rem;
        margin:0 auto 1.25rem;
    }
    #nomember .content .mid img{
        display: block;
        width: 100%;
        height:100%;
    }
    #nomember .content footer{
        width: 13.75rem;
        height:2.56rem;
        margin:0 auto;
    }
    #nomember .content footer img{
        display: block;
        width: 100%;
        height:100%;
    }
</style>
</html>
