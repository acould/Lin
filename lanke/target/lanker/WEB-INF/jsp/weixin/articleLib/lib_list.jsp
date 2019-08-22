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
    <title>Title</title>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp"%>
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/style/admin.css" media="all">
</head>
<style>
    .layui-badge {
             position: absolute;
             right: -8px;
             top: -4px;
             border-radius: 20px;
         }
    td .layui-table-cell {
        height: auto!important;
        line-height: initial!important;
        padding: 0px 15px;
        overflow : hidden;
        white-space: initial;
    }
    .layui-rate li i.layui-icon {
        font-size: 16px;}
    .layui-table-view .layui-table td, .layui-table-view .layui-table th {
        padding: 2px 0;
    }
</style>
<body style="background-color: #f2f2f2;" class="scroll">
    <input type="hidden" value="${role}" id="role">
    <div class="layui-fluid" style="padding-bottom: 54px;">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="lk-table-seach">
                        <form class="layui-form" method="post" id="excel_form">
                            <div class="demoTable table-seach">
                                <div class="layui-inline">
                                    <input class="layui-input" name="keywords" id="keywords" autocomplete="off" placeholder="关键词搜索">
                                </div>
                                <a class="btn btn-xm btn-success" data-type="search" id="toSearch" style="margin-left: 10px;">
                                    <i class="layui-icon" style="padding-right: 4px;">&#xe615;</i>搜索
                                </a>
                                <div style="float: right">
                                    <c:if test="${role == 'lanker'}">
                                    <a class="btn btn-xm btn-primary"   style="margin-left: 10px;" onclick="article_edit('','add')">新增文章</a>
                                    <a class="btn btn-xm btn-success"  style="margin-left: 10px;"  onclick="goCategory()">分类管理</a>
                                    <a class="btn btn-xm layui-btn-primary"  style="margin-left: 10px;position: relative" onclick="window.location.href='<%=basePath%>articleLib/draftsArticle.do'">草稿箱<span class="layui-badge">${drafts}</span></a>
                                    </c:if>
                                    <c:if test="${role == 'internet'}">
                                    <a class="btn btn-xm layui-btn-primary"  style="margin-left: 10px;position: relative;top: 12px;" onclick="synthesizer_show()">图文合成器<span class="layui-badge layui-anim" id="synthesizer-badge">${synthesizer}</span></a>
                                    </c:if>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="layui-card-body">
                        <div class="article_category">
                            <ul>
                                <li class="active casethis" id="">文章总榜</li>
                                <c:forEach items="${categoryList}" var="var" varStatus="vs">
                                    <li class="casethis" id="${var.id}">${var.name}</li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div style="padding:12px 12px 20px 12px">
                            <table id="article_list" lay-filter="article"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="article_page" class="article_page">

    </div>
    <div class="lk-synthesizer-box layui-anim-upbit layui-anim" id="synthesizer">
        <div class="lk-synthesizer-title">图文合成器</div>
        <div class="lk-synthesizer-content">
            <div class="lk-synthesizer-contentPd" id="synthesizer-body">

            </div>
        </div>
        <div class="lk-synthesizer-foot">
            <div class="synthesizer-btn-box">
                <button class="layui-btn layui-btn-primary layui-btn-xs" onclick="synthesizer_colse()">关闭</button>
                <button class="layui-btn layui-btn-primary layui-btn-xs" onclick="synthesizer_del('','','','delAll')">清空</button>
                <button class="layui-btn layui-btn-normal layui-btn-xs" onclick="dddd()">确定</button>
            </div>
        </div>
    </div>
</body>
<%@ include file="../../system/index/foot.jsp"%>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="title">
    <div class="article" style="min-width:400px;">
        <a href="{{ d.article_url}}" target="_blank">
            <div class="picture_url" style="background:#eee url({{ d.picture_url}})no-repeat center center;background-size:100% "></div>
            <div class="title">{{ d.title}}</div>
        </a>
    </div>
</script>
<script type="text/html" id="popularity">
    <div class="popularityBox">{{ d.popularity}}</div>
</script>
<script type="text/html" id="barDemo">
    {{# if($("#role").val() == "lanker"){ }}
    <div class="article_table_icon">
        <i class="iconfont lk_table_icon_success lk_table_icon" title="编辑" lay-event="edit">&#xe637;</i>
        <i class="iconfont lk_table_icon lk_table_icon_danger" title="删除" style="font-size: 18px" lay-event="del">&#xe645;</i>
    </div>
    {{# }else{ }}
    <div class="article_table_icon">
        <i class="iconfont lk_table_icon lk_table_icon_success" title="添加到图文合成器" style="font-size: 20px;" lay-event="add">&#xe638;</i>
        <i class="iconfont lk_table_icon lk_table_icon_warning" title="查看文章" style="position: relative;top: -2px;font-size: 15px;" lay-event="see" onclick="window.open('{{ d.article_url}}')">&#xe683;</i>
    </div>
    {{# } }}
</script>
<script>
    $(top.hangge());//关闭加载状态
    layui.config({
        base: '<%=basePath%>newStyle/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'article']);
</script>
</html>
