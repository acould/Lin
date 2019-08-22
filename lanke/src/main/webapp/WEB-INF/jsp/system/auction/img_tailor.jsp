<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>
</head>
<style>
    body {
        margin: 0;
    }
    #clipArea {
        margin: 20px;
        height: 240px;
        width: 370px;
    }
    .photo-clip-mask-left,.photo-clip-mask-right,.photo-clip-mask-top,.photo-clip-mask-bottom {
        background-color: rgba(0,0,0,0.3)!important;
    }
    #file,
    #clipBtn {
        margin: 20px;
    }
    #view {
        width: 200px;
        height: 180px;
        float: left;
        margin: 40px 0 10px 0 ;
        background-color: #eee!important;
    }
</style>
<body style="background: #fff;">
    <input type="hidden" id="backUrl">
    <div style="padding: 30px">
        <div style="width: 420px;float: left;margin-right: 50px">
            <p style="color: #666;padding: 0 20px">鼠标拖拽移动、滚动缩放、双击旋转</p>
            <div id="clipArea"></div>
            <input type="file" id="file" style="display: inline-block;">
            <button id="clipBtn" class="layui-btn layui-btn-primary layui-btn-sm">截取</button>
        </div>
        <div style="width: 200px;float: left;margin: 20px;">
            <div id="view"></div>
            <p style="color: #666;">商品尺寸：345*310</p>
        </div>
    </div>
</body>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp" %>
<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script src="<%=basePath%>newStyle/js/img_tailor/iscroll-zoom.js"></script>
<script src="<%=basePath%>newStyle/js/img_tailor/hammer.js"></script>
<script src="<%=basePath%>newStyle/js/img_tailor/lrz.all.bundle.js"></script>
<script src="<%=basePath%>newStyle/js/img_tailor/jquery.photoClip.js"></script>
<script type="text/javascript">
    layui.use(['form','layer'],function () {
        var form = layui.form,
            layer = layui.layer;

    })
    var clipArea = new bjj.PhotoClip("#clipArea", {
        size: [200, 180],
        outputSize: [345, 310],
        file: "#file",
        view: "#view",
        ok: "#clipBtn",
        loadStart: function() {
            loading("图片读取中");
        },
        loadComplete: function() {
            layer.closeAll();
        },
        clipFinish: function(dataURL) {
            $("#backUrl").val(dataURL)
        }
    });
    var save  = function(){
        if( $("#backUrl").val() == ""){
            message("请裁剪商品图片");
        }else {
            return $("#backUrl").val();
        }
    }
</script>
</body>
</html>