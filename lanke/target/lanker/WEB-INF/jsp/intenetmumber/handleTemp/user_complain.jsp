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
    <title>处理投诉</title>
    <!--移动端适配，px转化为rem-->
    <script src="<%=basePath%>newStyle/weixin/lib-flexible-2.0/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/WeChatEnd.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/jquery-weui/css/jquery-weui.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<style>
    .weui-label {
        color: #666;
    }
</style>
<body class="weic weic-body-bgf3f3f3 weic-parent-mar">
<form id="Form">
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__bd">
                <p class="user_msg_title weui-input" style="color: red">${title}</p>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">投诉人</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="text"  value="${lm.LM_USERNAME}" readonly>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">投诉时间</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="text"  value="${lm.LM_DATE}" readonly>
            </div>
        </div>
        <div class="weui-cells__title" style="font-size: 0.42rem;color: #666">备注内容</div>
        <div class="weui-cell weui-cells_form">
            <div class="weui-cell__bd">
                <textarea class="weui-textarea" rows="3" readonly>${lm.LM_CONTENT}</textarea>
            </div>
        </div>
    </div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cells__title" style="font-size: 0.42rem;color: #666">回复内容</div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea class="weui-textarea" placeholder="请输入回复内容" rows="5" id="reply" maxlength="200">${lm.BACKCONTENT}</textarea>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${lm.LM_STATE == '2'}">
        <div class="weic-bor" style="margin: 1.4rem 0">
            <div class="weic-dent-btn weic-btn-gradientBlue" onclick="save()">确定回复</div>
        </div>
    </c:if>
</form>

</body>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-weui.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/mobile-check.js"></script>
<script src="<%=basePath%>newStyle/js/lk-staff.js"></script>
<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
<script>
    $(function (){
        FastClick.attach(document.body);
    });
</script>
<script type="text/javascript">
    var record_id = "${record_id}";
    var id = "${lm.LM_ID}";

    function save() {
       var reply = $("#reply").val();
       if( reply  == ""){
           message("请输入回复内容")
       }else {
           var data = new Object();
           data.BACKCONTENT = reply;
           data.record_id = record_id;
           data.id = id;
           data.type = "user_complain";
           $.ajax({
               type: "POST",
               url: '<%=basePath%>indexMember/handleTamplete',
               data: data,
               success: function (res) {
                   if(res.result == "true"){
                       message("处理成功");
                       setTimeout(function () {
                           wx.closeWindow();
                       },800)
                   }else{
                       message(res.message);
                   }
               },
               error: function (){
                   message('系统繁忙，请稍后再试')
               }
           });
       }
    }
</script>
</html>
