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
    <title>揽客客户端管理</title>
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
                    <label class="layui-form-label">揽客版本：</label>
                    <div class="layui-input-block">
                        <select name="vesion"  id="vesion">
                            <option value="">请选择</option>
                            <option value="0">轻营销版</option>
                            <option value="1">轻营销+V</option>
                            <option value="2">专业版</option>
                        </select>
                    </div>
                </div>

                <div class="layui-input-inline">
                    <label class="layui-form-label">付费状态:</label>
                    <div class="layui-input-block">
                        <select name="paystatus"  id="paystatus">
                            <option value="">请选择</option>
                            <option value="0">免费一个月</option>
                            <option value="1">付费一年</option>
                        </select>
                    </div>
                </div>


                <div class="layui-inline">
                    <div class="layui-input-block">
                        <input type="text" id="keywords" name="keywords" placeholder="请输入关键字" autocomplete="off"  class="layui-input">
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
                <div style="padding-bottom: 10px">
                    <button class="layui-btn mail-btn" data-type="toExcel">导出为Excel</button>
                </div>


            <%--表单--%>
            <table class="layui-hide" id="layTable" lay-filter="layTable"></table>

            <%--分页--%>
            <div id="layPage" style="float: right;"></div>

            <div>
                <span>公众号粉丝数合计：<a id="sumfans">0</a></span><br>
                <span>门店会员数合计:<a id="sumstore">0</a></span><br>
                <span>总充值金额合计:<a id="sumprice">0</a></span><br>
            </div>
        </div>
    </div>
</div>

</body>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>

    layui.config({
        base: '<%=basePath%>/newStyle/js/' //静态资源所在路径
    }).extend({
        cusManager : 'cusManager',
    }).use(['cusManager']);
</script>
</html>
