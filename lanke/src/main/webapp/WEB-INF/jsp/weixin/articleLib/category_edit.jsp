<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>分类管理</title>
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
</head>
<style>
    .layui-input:focus{
        border-color: #1e9fff!important;
    }
    .layui-input {
        height: 30px!important;
        text-align: center;
        padding-left: 0;
    }
    .layui-table td, .layui-table th {text-align: center}
    #add,.confirm {
        color: #fff;
        cursor: pointer;
        text-align: center;
        height: 30px;
        line-height: 30px;
        background: #37a8ff;
        border-radius: 2px;
    }
    .confirm {background: #1ab394}
    .input_disabled {border:none;}
    input[type="number"] {
        width: 100px;
        padding-left: 14px;
    }
</style>
<body>
    <div style="padding: 20px">
        <div class="layui-form">
            <table class="layui-table">
                <thead>
                    <tr>
                        <th width="100px">排序</th>
                        <th>分类名称</th>
                        <th width="140px">编辑</th>
                    </tr>
                </thead>
                <tbody>
                    <tr id="addBox">
                        <td><input type="number"  placeholder="请输入序号"  class="layui-input"  min="1" step="1" id="new_num"></td>
                        <td><input type="text"   placeholder="请输入分类名称"  class="layui-input" maxlength="4" id="new_name"></td>
                        <td>
                            <div id="add" class="category_btn" data-type="add" onclick="category_btn(this,'')">添加</div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
    <%@ include file="../../system/index/foot.jsp"%>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-message.js"></script>
    <script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
    <script>
        layui.config({
            base: '<%=basePath%>newStyle/layuiadmin/' //静态资源所在路径
        }).extend({
            index: 'lib/index' //主入口模块
        }).use(['index', 'category']);
    </script>
</html>
