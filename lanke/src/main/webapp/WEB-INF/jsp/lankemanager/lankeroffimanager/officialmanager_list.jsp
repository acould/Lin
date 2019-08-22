<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>公众号管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
</head>
<style>
    body {background-color: #f2f2f2; min-width: 600px; overflow: auto}
    /*.laytable-cell-1-HEAD_IMG{
        height: 100%;
        max-width: 100%;
    }*/
</style>
<div class="layui-fluid">
    <div class="layui-card">

        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">

                <div class="layui-inline">
                    <label class="layui-form-label">关键词</label>
                    <div class="layui-input-block">
                        <input type="text" id="keywords" name="keywords" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">状态</label>
                    <div class="layui-input-block">
                        <select name="state"  id="state">
                            <option value="">请选择</option>
                            <option value="0">注册</option>
                            <option value="2">正式</option>
                            <option value="3">禁用</option>
                            <option value="4">流失</option>
                            <option value="5">订阅号</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <button type="button" class="layui-btn layui-btn-sm" id="toSearch">搜索</button>
                </div>

            </div>
        </div>

        <div class="layui-card-body" style="padding: 20px 20px">

            <%--表单--%>
            <table class="layui-hide" id="layTable" lay-filter="layTable"></table>


            <div class="page-header position-relative" style="padding:20px 0 0 0">
                <table style="width:100%;">
                    <tr>
                        <td style="vertical-align:top;">
                            <div id="layPage"  style="text-align: right"></div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<%--添加行操作功能--%>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit" id="update" >更新公众号</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit2" id="update2" >更新token</a>
</script>
<script>
    var basePath = '<%=basePath%>';
    layui.config({
        base: basePath +'newStyle/js/lankermanager/' //静态资源所在路径
    }).extend({
        officialmanager : 'officialmanager',
    }).use(['officialmanager']);
</script>

</html>
