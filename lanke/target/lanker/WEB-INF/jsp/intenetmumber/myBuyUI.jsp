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
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>商品详情</title>
    <link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
<style>

</style>
<body class="weic weic-bgfff">
<form id="aaa">
    <input type="hidden" name="computer_name" id="computer_name" value="${computer_name}">
    <input type="hidden" id="ppp" value="${orderVO.payInt}">
    <input type="hidden" id="ccc" value="${orderVO.payCash}">
    <div style="height: 8rem;background: url('<%=basePath%>uploadFiles/uploadImgs/${orderVO.PATH}')no-repeat center center;background-size: 100%"></div>
    <div class="weic-bor">
        <p class="weic-buyUi-title">${orderVO.ANAME}</p>
        <p style="color: #666;font-size: 0.34rem">${orderVO.CONTENT}</p>
        <div class="weic-buyCash">
            <c:if test="${orderVO.payInt =='I'}"><span><font class="t">积分购买：</font>${orderVO.INTEGRAL}<font
                    class="s">分</font></span></c:if>
            <c:if test="${orderVO.payCash =='M'}"><span><font class="t">现金购买：</font><font
                    class="s">￥</font>${orderVO.price}</span></c:if>
        </div>
    </div>
    <div class="weui-cells weui-cells_radio">
        <label class="weui-cell weui-check__label" for="jifen" id="jjjj">
            <div class="weui-cell__bd">
                <p style="font-size: 0.38rem">积分购买</p>
            </div>
            <div class="weui-cell__ft">
                <input type="radio" class="weui-check" name="payInt" value="I" id="jifen" checked>
                <span class="weui-icon-checked"></span>
            </div>
        </label>
        <label class="weui-cell weui-check__label" for="xianjin" id="xxxx">
            <div class="weui-cell__bd">
                <p style="font-size: 0.38rem">现金购买</p>
            </div>
            <div class="weui-cell__ft">
                <input type="radio" name="payInt" value="M" class="weui-check" id="xianjin"
                       <c:if test="${orderVO.payInt !='I'}">checked</c:if>>
                <span class="weui-icon-checked"></span>
            </div>
        </label>

        <div class="weui-cell">
            <div class="weui-cell__bd">
                <p style="font-size: 0.38rem">购买数量</p>
            </div>
            <div class="weui-cell__ft">
                <div class="weui-count">
                    <a class="weui-count__btn weui-count__decrease"></a>
                    <input name="COUNT" class="weui-count__number" type="number" value="1" readonly style="font-size: 0.4rem;"/>
                    <a class="weui-count__btn weui-count__increase"></a>
                </div>
            </div>
        </div>
    </div>
    <div class="weic-bor" style="margin: 0.8rem 0">
        <div class="weic-dent-btn weic-btn-gradientBlue" onclick="buy()">立即购买</div>
    </div>
    <input id="AID" type="hidden" name="Auction_ID" value="${Auction_ID}">
</form>
</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script>
    console.log("${orderVO}")
    $(function () {
        FastClick.attach(document.body);
    });
    $(function () {
        var payint = $("#ppp").val();
        var paycash = $("#ccc").val();
        if (payint != 'I') {
            document.getElementById("jjjj").style.display = "none";
        }else if (paycash!='M') {
            document.getElementById("xxxx").style.display = "none";
        }
    })
</script>
<script type="text/javascript">
    var MAX = 99, MIN = 1;
    $('.weui-count__decrease').click(function (e) {
        var $input = $(e.currentTarget).parent().find('.weui-count__number');
        var number = parseInt($input.val() || "0") - 1
        if (number < MIN) number = MIN;
        $input.val(number)
    })
    $('.weui-count__increase').click(function (e) {
        var $input = $(e.currentTarget).parent().find('.weui-count__number');
        var number = parseInt($input.val() || "0") + 1
        if (number > MAX) number = MAX;
        $input.val(number)
    })

    function buy() {

        if (document.getElementById("xianjin").checked) {
            $("#xianjin").attr("name", "payCash");
        }

        confirm("是否确定购买该商品", function () {
            $.post("<%=basePath%>indexMember/sqdh.do", $("#aaa").serialize(), function (data) {
                layer.closeAll();
                if (data.payForCash || data.success) {
                    window.location.href = '<%=basePath%>/indexMember/successUI';
                } else {
                    message(data.message);
                }
            })
        })
    }
</script>
</html>
