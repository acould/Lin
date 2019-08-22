<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2019/7/29
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
    <meta charset="UTF-8">
    <title>确认页面</title>
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
        <h1><span style="font-size:.8rem;font-weight: bold">￥</span>${orders.price}</h1>
        <p>${orders.detail}</p>
    </header>
    <ul>
        <li>
            <div>订单详情</div>
            <div>自动售货机</div>
        </li>
        <li>
            <div>付款方式</div>
            <div>卡余额</div>
        </li>
    </ul>
    <footer>
        <div id="test"><img src="<%=basePath%>newStyle/images/组 108.png"></div>
        <div class="goBack"><img src="<%=basePath%>newStyle/images/组 109.png"></div>
    </footer>
    <div class="tan yebz">
        <div class="tan1">
            <img src="<%=basePath%>newStyle/images/组 106.png">
            <div class="click Yeb"></div>
        </div>
    </div>
    <div class="tan bshy">
        <div class="tan1">
            <img src="<%=basePath%>newStyle/images/组 107.png">
            <div class="click Bsh"></div>
        </div>
    </div>
    <div class="tan ygm">
        <div class="tan1">
            <img src="<%=basePath%>newStyle/images/组 111.png">
            <div class="click Ygm"></div>
        </div>
    </div>
    <div class="tan sbai">
        <div class="tan1">
            <img src="<%=basePath%>newStyle/images/组 113.png">
            <div class="click Sb"></div>
        </div>
    </div>
    <div class="tan gmcg">
        <div class="tan1">
            <img src="<%=basePath%>newStyle/images/组 110.png">
            <div class="click Gmc"></div>
        </div>
    </div>
</div>

</body>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
<script>
    console.log(${orders});
    var code =  "${orders.code}";
    var id = "${orders.id}";
    var result_url="${orders.result_url}";
    var url = "<%=basePath%>autosellmanager/getPayResult.do?id="+id+"&code="+code;

        $("#test").on("click",function(){

        $.ajax({
            type: "get",
            url: url,
            dataType: "json",
            success: function (data) {
             setTimeout(function(){
                 if(data.result_msg=="卡余额不足"){
                     $(".yebz").css("display","block");
                 }else if(data.result_msg=="不是会员"){
                     $(".bshy").css("display","block");
                 }else if(data.result_msg=="该订单已支付过"){
                     $(".ygm").css("display","block");
                 }else if(data.result_msg=="该订单支付失败"){
                     $(".sbai").css("display","block");
                 }else{
                     $(".gmcg").css("display","block");
                 }
             },500)
            }
        });
    });
    $(".Bsh").click(function(){
        window.open("<%=basePath%>intenetmumber/bindCard?label=index");
    })
    $(".Yeb").click(function(){
        window.open("<%=basePath%>indexMember/recharge");
    })
    $(".Gmc").click(function(){
        window.open("<%=basePath%>intenetmumber/index");
    })
    $(".Ygm").click(function(){
        window.open("<%=basePath%>intenetmumber/index");
    })
    $(".Sb").click(function(){
        window.open("<%=basePath%>intenetmumber/index");
    })
    $(".goBack").click(function(){
        window.open(result_url);
    })
</script>
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
    .tan{
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        z-index:100;
        background:rgba(1,1,1,.6);
        display: none;
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

