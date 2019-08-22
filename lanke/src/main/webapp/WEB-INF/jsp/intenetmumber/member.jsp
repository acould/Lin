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

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge" chrome="1">
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="address=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>${intenetName}</title>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/index.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/member.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/iconfont.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
    <script src="<%=basePath%>newStyle/weixin/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="container-fluid my-bj-next">
    <div class="row text-center">
        <a href="javascript:void(0);">
            <div class="col-xs-6 margin-top my-border">
                <p class="text-p1">会员卡号</p>
                <p class="text-p2">${pdBind.CARDED}</p>
            </div>
        </a>
        <c:if test="${pdBind.STORE_STATE == 1}">
            <a href="javascript:void(0);">
                <div class="col-xs-6 margin-top">
                    <p class="text-p1">当前绑定</p>
                    <p class="text-p2">${pdBind.STORE_NAME}</p>
                </div>
            </a>
        </c:if>
        <c:if test="${pdBind.STORE_STATE != 1 and size != 0}">
            <div class="col-xs-6 margin-top">
                <p class="text-p1">当前绑定</p>
                <p class="text-p2">${pdBind.STORE_NAME }已被关闭，请联系商户或更换门店</p>
            </div>
        </c:if>
        <c:if test="${pdBind.STORE_STATE != 1 and size == 0}">
            <div class="col-xs-6 margin-top">
                <p class="text-p1">当前绑定</p>
                <p class="text-p2">${pdBind.STORE_NAME}已被关闭，该商户暂无其他门店信息</p>
            </div>
        </c:if>
    </div>
</div>
<c:if test="${size > 0}">
   <div class="container-fluid">
       <div class="container">
           <div class="row">
               <div class="col-xs-12 text-center">
                   <!--<  class="btn btn-primary  test-btn" >模态框测试</>-->
                   <div class="animatedh" id="bundCardId">
                       <div class="mem-imgWid">
                           <img src="<%=basePath%>newStyle/weixin/images/replace-icon@2x.png" alt="">
                       </div>
                       <p class="mem-textP">更换门店</p>
                   </div>
               </div>
           </div>
       </div>
   </div>
</c:if>

<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script>
    $('#bundCardId').click(function () {
        $.ajax({
            type: "POST",
            url: '<%=basePath%>intenetmumber/checkTime.do',
            data: '',
            success: function (data) {
                if (data.result == "false") {
                    message(data.message);
                } else if (data.result == "success") {
                    confirm("更换门店后系统将自动保存上家门店的数据，但是不会显示。重新绑定该门店数据将自动恢复",function(){
                        window.location.href = "<%=basePath%>intenetmumber/goEdit"
                    })
                }
            },
            error: function () {
                message("ajax提交失败！");
                return;
            }
        });
    });
</script>
</body>
</html>