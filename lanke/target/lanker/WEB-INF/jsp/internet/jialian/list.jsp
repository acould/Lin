<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>嘉联支付列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="icon" href="data:;base64,=">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">

        <%--搜索框--%>
        <div class="layui-form layui-card-header">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-input-block">
                        <input type="text" id="keywords" name="keywords" placeholder="请输入" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-input-inline">
                    <label class="layui-form-label">状态</label>
                    <div class="layui-input-block">
                        <select name="status" id="status">
                            <option value="">请选择</option>
                            <option value="1">已开通</option>

                            <option value="2">待提交</option>
                            <option value="3">审核中</option>
                            <option value="4">未通过</option>
                        </select>
                    </div>
                </div>


                <div class="layui-inline">
                    <button class="layui-btn" id="toSearch">
                        <i class="layui-icon layui-icon-search"></i>
                    </button>
                </div>


            </div>
        </div>

        <div class="layui-card-body">
            <%--按钮--%>
            <%--<div style="padding-bottom: 10px">
                <button class="layui-btn jialian-btn" data-type="deljialian">删除</button>
                <button class="layui-btn jialian-btn" data-type="addjialian">添加</button>
            </div>--%>

            <%--表单--%>
            <table class="layui-hide" id="jialianTable" lay-filter="jialianTable"></table>
        </div>
    </div>
</div>

<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script>

    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/jl_pay/' //静态资源所在路径
    }).extend({
        jl_pay : 'jl_pay',
    }).use(['jl_pay']);
</script>

</body>
</html>
