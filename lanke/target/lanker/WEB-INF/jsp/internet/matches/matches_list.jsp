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
    <title>赛事管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="icon" href="data:;base64,=">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">

    <style>
        .layui-badge {
            position: absolute;
            right: -8px;
            top: -4px;
            border-radius: 20px;
        }
        .layui-rate li i.layui-icon {
            font-size: 16px;}
    </style>
</head>
<body class="scroll" style="background-color: #f2f2f2;">

<div class="layui-fluid" style="padding-bottom: 54px;">
    <div class="layui-card">
        <div class="lk-table-seach">
            <form class="layui-form" method="post" id="excel_form">
                <div class="demoTable table-seach">
                    <div class="layui-inline" style="position: relative;top: -24px;line-height: 1">
                        <div class="layui-inline">
                            <input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="关键词搜索">
                        </div>
                        <a class="layui-btn btn-success" id="toSearch" style="margin-left: 10px;">
                            <i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
                        </a>
                    </div>
                    <div class="filtrate_module" style="line-height: 1.4;padding: 10px;margin-left: 20px" id="filtrate_module">
                        <ul class="layui-clear">
                            <li class="active" >全部</li>
                            <li data-type="0_0">未发布</li>
                            <li data-type="1_1">报名中</li>
                            <li data-type="1_2">报名结束</li>
                            <li data-type="1_3">比赛中</li>
                            <li data-type="1_4">比赛结束</li>
                        </ul>
                    </div>
                    <div style="float: right">
                        <a class="layui-btn btn-primary addMatches" style="margin-left: 10px;">新增赛事</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="match_listBox" id="matchList">
        <%--<div class="match_box">
            <div class="img">
                <img src="" alt="" width="100%" height="100%">
            </div>
            <div class="content">
                <p class="title">杭州市网吧电竞大赛LOL专场</p>
                <p class="store">主办门店：xxxxxxxx门店1111111111111111111</p>
                <p class="store">报名类型：3人组队、允许单人</p>
                <p class="store">报名时间：2019-01-10 至  2019-01-30</p>
                <p class="store">比赛时间：2019-01-10 至  2019-01-30</p>
                <p class="status">
                    <font color="#FF6633">报名中</font>
                    <span style="margin-left: 20px">已报名30人</span>
                </p>
            </div>
            <div class="card-btn-box" style="background: #fff">
                <div class="layui-row">
                    <div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">
                        <span class="bghover-success card-btn" title="编辑"><i class="layui-icon" >&#xe642;</i></span>
                    </div>
                    <div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">
                        <span class="bghover-warning card-btn" title="报名表"><i class="layui-icon" >&#xe63c;</i></span>
                    </div>
                    <div class="layui-col-xs4 layui-col-sm4 layui-col-md4 border">
                        <span class="bghover-danger card-btn" title="删除"><i class="layui-icon" >&#xe640;</i></span>
                    </div>
                </div>
            </div>
        </div>--%>
    </div>

    <div id="layPage" class="article_page"></div>
</div>



<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script>
    var matchedFlag = "list";
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/modules/internet/matches/' //静态资源所在路径
    }).extend({
        matches : 'matches',
    }).use(['matches']);
</script>


</body>
</html>
