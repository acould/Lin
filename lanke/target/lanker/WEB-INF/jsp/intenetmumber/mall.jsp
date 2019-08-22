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
    <title>网吧商城</title>
    <link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
    <link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
</head>
<style>

</style>
<body class="weic">
<div class="wrap">
    <div class="shoptop">
        <img src="<%=basePath%>assets/images/shoptop.jpg" alt="">
        <div class="topbox" style="margin-left: -110px;">
            <img src="<%=basePath%>assets/images/shopt.png" alt="">
            <p class="p1">您的积分余额</p>
            <p class="p2 font-size18">${INTEGRAL}</p>
        </div>
        <a class="look" href="<%=basePath %>myMember/myDingDan"><img src="<%=basePath%>assets/images/shoplook.png" alt=""></a>
    </div>
    <div style="padding: 0.266rem">
        <div class="weui-row">
            <c:choose>
                <c:when test="${not empty varList}">
                    <c:forEach items="${varList}" var="var" varStatus="vs">
                        <div class="weui-col-50 weic-bgfff weic-mallBox">
                            <a href="<%=basePath%>/indexMember/buyUI?PATH=${var.PATH}&ANAME=${var.ANAME}&CONTENT=${var.CONTENT}&INTEGRAL=${var.INTEGRAL}&price=${var.price}&payInt=${var.payInt}&payCash=${var.payCash}&Auction_ID=${var.Auction_ID}&computer_name=${computer_name}">
                                <img src="<%=basePath %>uploadFiles/uploadImgs/${var.PATH }" alt="" width="100%" style="height:4.16rem">
                                <div style="padding: 0.266rem">
                                    <p class="weic-mallTitle">${var.ANAME}</p>
                                    <div class="weic-mallCash">
                                        <c:if test="${var.payInt =='I'}"><span><svg><use xlink:href="#jifen"></use></svg>
                                                ${var.INTEGRAL}</span></c:if>
                                        <c:if test="${var.payCash =='M'}"><span>¥${var.price}</span></c:if>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
     style="position:absolute;width:0;height:0;visibility:hidden">
    <defs>
        <symbol viewBox="0 0 1024 1024" id="jifen">
            <path d="M958.367699 388.635321l0 292.188712-2.966565 0c-7.875365 128.758526-228.616838 228.312916-462.294948 228.312916-233.810117 0-417.59284-99.688443-425.213401-228.312916l-2.916423 0L64.976362 388.635321c-2.24411-10.252505 0.083911-20.572548 0.083911-31.229259 0-135.975904 185.838545-242.546082 428.046936-242.546082 242.209414 0 465.178625 106.570177 465.178625 242.546082C958.284811 368.063797 960.611809 378.38384 958.367699 388.635321zM493.105163 185.815009c-209.950709 0-367.365902 93.351108-367.365902 171.592077 0 78.241992 157.415192 165.453263 367.365902 165.453263 209.968106 0 405.263024-87.211271 405.263024-165.453263C898.368186 279.166117 703.074292 185.815009 493.105163 185.815009zM902.146233 486.906485c-76.723406 64.886823-256.975722 108.951411-409.040047 108.951411-152.063301 0-294.41952-44.065612-371.159298-108.951411l0 48.092321c20.216437 83.772962 190.868095 156.787906 371.159298 156.787906 180.357717 0 388.923894-73.083505 409.040047-156.855444L902.146233 486.906485zM902.146233 641.60173c-74.816986 65.966411-237.33336 111.99984-409.040047 111.99984-167.007665 0-296.324916-46.033429-371.159298-111.864764l0 37.761022c20.216437 83.7044 171.405835 160.879084 371.159298 160.879084 195.242729 0 388.923894-77.174684 409.040047-160.943552L902.146233 641.60173z"
                  p-id="5640" fill="#fea0ab"></path>
        </symbol>
    </defs>
</svg>
</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/swiper.min.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/weic-swiper.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script src="<%=basePath%>newStyle/js/lk_wechat/lk_wechat.js"></script>

<script type="text/javascript">
    var computer_name = "${computer_name}";



</script>
</html>
