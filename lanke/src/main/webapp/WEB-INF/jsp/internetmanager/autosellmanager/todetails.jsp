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
    <title>订单详情</title>
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
    <%--    <meta charset="UTF-8">--%>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <%--    <meta http-equiv="X-UA-Compatible" content="ie=edge">--%>

    <%--    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.10/dist/vue.js"></script>--%>
</head>
<body>
<div id="zhifu">
    <header>
        <h1>-${data.principal}</h1>
        <p>${data.detail}</p>
    </header>
    <ul>
        <li>
            <div class="left">订单详情</div>
            <div>自动售货机</div>
        </li>
        <li>
            <div class="left">付款方式</div>
            <div>卡余额</div>
        </li>
        <li>
            <div class="left">下单时间</div>
            <div>${data.order_time}</div>
        </li>
        <li>
            <div class="left">订单号</div>
            <div>${data.lanke_order_no}</div>
        </li>
        <li>
            <div class="left">交易对象</div>
            <div>${data.store_name}</div>
        </li>
    </ul>

</div>

</body>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
<%--<script>--%>
<%--    new Vue({--%>
<%--        el: '#zhifu',--%>
<%--        data: {--%>
<%--            msg: '111',--%>
<%--        }--%>
<%--    })--%>
<%--</script>--%>
<style>
    * {
        margin: 0;
        padding: 0;
    }

    /*html {*/
    /*    font-size: 16px;*/
    /*}*/

    /*body {*/
    /*    overflow: hidden;*/
    /*    font-size: 1rem;*/
    /*}*/

    li {
        list-style: none;
    }

    #zhifu {
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
    }

    header {
        padding-top: 4.375rem;
        text-align: center;
    }

    header p {
        text-align: center;
        padding-top: 1.375rem;
        padding-bottom: 6.25rem;
    }

    ul {
        padding: 0 .9375rem 5.125rem;
    }

    ul li {
        border-bottom: 1px solid rgb(234, 234, 234);
        font-size: .84375rem;
        line-height: 2.3125rem;
        display: flex;
        justify-content: space-between;
    }
    .left{
        color:rgba(1,1,1,.6)
    }

    footer div {
        margin: 0 auto;
        width: 13.75rem;
        height: 2.5625rem;
        margin-bottom: .925rem;
    }

    footer div img {
        width: 13.75rem;
        height: 2.5625rem;
        display: block;
    }
</style>