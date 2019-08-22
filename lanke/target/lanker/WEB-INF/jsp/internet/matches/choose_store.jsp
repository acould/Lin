<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>选择门店</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="icon" href="data:;base64,=">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <style>
        .layui-form-checkbox span {
            height: auto;
        }
    </style>
</head>
<body>

<input id="type" name="type" type="hidden" value="${pd.type}">
<div class="layui-form" lay-filter="chooseStoreFilter" id="chooseStoreFilter" style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">门店列表</label>
        <div class="layui-input-block" id="storeList">

        </div>
    </div>

    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="laySubmit" id="laySubmit" value="确认">
    </div>

</div>



<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>

    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/internet/' //静态资源所在路径
    }).extend({
        chooseStore : 'chooseStore',
    }).use(['chooseStore']);
</script>

</body>
</html>
