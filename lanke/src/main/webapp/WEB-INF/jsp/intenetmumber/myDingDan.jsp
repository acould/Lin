<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="viewport"
          content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
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
<ul class="g-tab flex-box">
    <li class="active" data-type="1">待提货</li>
    <li data-type="2">发货中</li>
    <li data-type="3">已提货</li>
</ul>
<ul class="g-list orderlist"></ul>
</body>
<script>

    var appendHtml = function (res, _obj) {
        var btns = "";
        var tips = ""
        var htmls = "";
        $.each(res.data.subjectList, function (i, item) {
            var type = $(".active").data('type');
            if (item.STATE == type) {
                if (item.STATE == 1) {
                    btns = '<div class="btn-act" onclick="sqth(&apos;' + item.ORDER_ID + '&apos;)">去提货</div>';
                } else if (item.STATE == 2) {
                    btns = '<div class="btn-disabled btn-act">发货中</div>';
                } else if (item.STATE == 3) {
                    btns = '<div class="btn-disabled btn-act">' +
                        '<p class="p1" >已提货</p>' +
                        '</div>'
                } else {
                    btns = "";
                }

                var ht = "";
                if(item.payInt == 'I'){
                    //积分
                    ht = '<p class="p2">已消费积分：' + item.intTotal + '</p>';
                }else if(item.payCash == 'M'){
                    //金额
                    ht = '<p class="p2">需支付金额：' + item.cashTotal + '</p>';
                }

                htmls += '<li class="flex-box">' +
                    '<div class="img flex-box"><img src="<%=basePath%>uploadFiles/uploadImgs/' + item.PATH + '"></div>' +
                    '<div class="info">' +
                    '<p class="p1 gray2">商品名称：' + item.ANAME + '</p>' +
                    '<p class="p2">购买数量：' + item.COUNT + '</p>' +
                     ht+
                    '</div>' +
                    btns +
                    '</li>'
            }
        });
        _obj.html(htmls);
    }

    var argument = {
        'url': '<%=basePath%>myMember/dingdan.json',
        'res': {'type': $('.g-tab .active').data('type')},
        'success': appendHtml,
        'mainbox': $('.g-list')
    };
    $(function () {
        addData(argument);
        $(".g-tab li").click(function () {
            $(this).parent().find('li').removeClass('active');
            $(this).addClass('active');
            argument.res.type = $(this).data('type');
            argument.mainbox.empty();
            addData(argument);
        });
    });

    function sqth(id) {
        var _this = this;
        $.confirm("确认要申请提货？", [{yes: '确定'}], function (type) {
            if (type == "yes") {
                $.ajax({
                    type: "POST",
                    url: '<%=basePath%>myMember/sqth.do',
                    data: {ORDER_ID: id},
                    success: function (data) {
                        if (data.result == "success") {
                            $.alert("申请成功！");
                            setInterval(go, 1000);
                        }

                    },
                    error: function () {
                        $.alert("系统繁忙，请稍后再试");
                        removeDis();
                    }
                });
                this.hide();
            }
        }, {className: 'ui-qiandao showClose ui-alert', width: '270px'});
    };

    function go() {
        window.location.reload();
    };
</script>
</html>
