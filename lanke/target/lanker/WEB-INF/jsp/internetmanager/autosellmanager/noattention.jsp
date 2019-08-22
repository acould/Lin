<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2019/8/8
  Time: 10:01
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
    <meta charset="UTF-8">
    <title>没有关注公众号</title>
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
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <%--    <meta charset="UTF-8">--%>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <%--    <meta http-equiv="X-UA-Compatible" content="ie=edge">--%>

    <%--    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.10/dist/vue.js"></script>--%>
</head>
<style>
    .tan{
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        z-index:100;
        background:rgba(1,1,1,.6);
    }
    .tan .tan1{
        width: 16rem;
        height:19.375rem;
        margin:6.25rem auto 0;
        position:relative;
    }
    .tan .tan1 img{
        width: 16rem;
        height:19.375rem;
        display: block;
    }
    .click{
        position:absolute;
        bottom:1rem;
        left:0;
        right:0;
        height:6rem;
        z-index:1000;
    }
</style>
<body>
<div class="tan gmcg">
    <div class="tan1">
        <img src="<%=basePath%>newStyle/images/组 114.png">
        <div class="click goBack"></div>
    </div>
</div>
</body>
<script>

    var result_url="${data.result_url}";

    $(".goBack").click(function(){
        window.open(result_url);
    })
</script>
</html>
