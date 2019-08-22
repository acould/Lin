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
    <title>赛事详情</title>
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
        .weic .weui-cell:before {display: none}
        .share_box {border-radius: 5px;border: 1px solid #eee;padding: 10px;margin-right: 10px}
        .share_cell {align-items: inherit;position: absolute; bottom: 50px; right: 0px;}
    </style>
</head>
<body class="weic  weic-body-bgf3f3f3">
    <div class="weui-cell share_cell" style="padding: 0.35rem 0.266rem;">
        <div class="weui-cell__hd" id="matchesDiv">
            <div class="weic-bgfff share_box">
                <p style="font-size: 0.35rem;color: #333;width: 5.626rem;" id="name"></p>
                <div class="weui-cell" style="padding: 0.133rem 0 0 0;align-items: inherit;">
                    <div class="weui-cell__hd" style="font-size: 0.133rem;color: #999;width: 4.36rem;" id="description">

                    </div>
                    <div class="weui-cell__hd weic-text-right" id="head_img">

                    </div>
                </div>
            </div>
        </div>
        <div class="weui-cell__hd" id="internet_img">

        </div>
    </div>
    <img src="<%=basePath%>newStyle/weixin/images/share_foot.jpg" alt="" width="100%" class="weic-block weic-fixed-bottom">
</body>

<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>

<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>

<script>
    var id = "${pd.matches_id}";

    init();
    function init() {
        var field = new Object();
        field.flag = 'matches_share';
        field.id = id;
        $.post('<%=basePath%>matches/getById.do',field,function (res) {
            if(res.errcode == 0){
                var data = res.data.pd;
                // console.log(data);
                $("#name").html(data.name);
                $("#description").html(data.description);
                $("#head_img").html('<img src="'+data.url+'" alt="" style="width: 1rem;width: 1rem;margin-left: 10px;margin-right: 0px">');
                $("#internet_img").html('<img src="' + data.internet_img + '" alt="" style="width: 1.2rem">');
            }
        })
    }

    //查看详情
    $("#matchesDiv").click(function(){
        window.location.href = '<%=basePath%>wxMatches/goGameDetail.do?matches_id=' + id + "&flag=matches_share";
    })

</script>
</html>