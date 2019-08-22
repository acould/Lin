<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>群发设置</title>

    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
    <style>
        .layui-form-checkbox span {
            height: auto;
        }
    </style>
</head>
<body>

    <form id="Form" method="post" class="layui-form" style="padding: 30px">
        <input id="id" name="id" value="${id}" type="hidden">
        <%--<div class="layui-form-item">
            <label class="layui-form-label">定时群发：</label>
            <div class="layui-input-block">
                <input type="text" name="time" id="time" lay-verify="title" autocomplete="off" placeholder="请选择时间" class="layui-input">
            </div>
        </div>--%>
        <div class="layui-form-item">
            <label class="layui-form-label">选择门店：</label>
            <div class="layui-input-block">
                <c:forEach items="${result.list}" var="var" varStatus="vs">
                    <input type="checkbox"  name="internet_id" lay-skin="primary"
                           value="${var.INTENET_ID }" title="${var.INTENET_NAME }" lay-filter="internet">
                </c:forEach>
            </div>
        </div>
    </form>

    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-message.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/layuiadmin/modules/groupSend.js"></script>

    <script>
        var basePath = '<%=basePath%>';
        layui.use(['form', 'layer', 'element', 'laydate'], function () {
            var form = layui.form,
            laydate = layui.laydate;

            laydate.render({
                elem: '#time',
                type: 'datetime'
            });
        });
        function groupSend() {
            layer.confirm("确定要群发图文吗？", {
                btn: ['确定', '取消']
            }, function () {
                $.ajax({
                    type: "POST",
                    url:  "<%=basePath%>groupSend/groupSend.do",
                    data: $('#Form').serialize(),
                    dataType: 'json',
                    beforeSend: loading('提交中...'),
                    success: function (data) {
                        if(data.errcode == 0){
                            message("群发成功");
                            setTimeout(function () {
                                parent.layer.closeAll();
                            },800)
                        }else{
                            message(data.errmsg);
                        }
                    },
                    error: function () {
                        message("系统繁忙，请稍后再试");
                    }
                });
            });
        }

        function sendMessage() {
            layer.confirm("确定要群发模板消息吗？", {
                btn: ['确定', '取消']
            }, function () {
                $.ajax({
                    type: "POST",
                    url:  "<%=basePath%>groupSend/sendMessage.do",
                    data: $('#Form').serialize(),
                    dataType: 'json',
                    beforeSend: loading(''),
                    success: function (data) {
                        layer.closeAll();
                        if(data.errcode == 0){
                            message("发送成功");
                            setTimeout(function () {
                                parent.layer.closeAll();
                                loadMessageIndex('');
                            },800);
                        }else{
                            message(data.errmsg);
                        }
                    },
                    error: function () {
                        layer.closeAll();
                        message("系统繁忙，请稍后再试");
                    }
                });
            });
        }
    </script>
</body>
</html>
