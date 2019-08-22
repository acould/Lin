<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/jquery.lineProgressbar.css" media="all">
    <link rel="icon" type="text/css" href="<%=basePath%>newStyle/images/lk-icon.png">

    <style>
        .general_details {
            float: right;
            color: #1E9FFF;
            font-size: 14px;
            font-weight: 500;
            padding: 6px 20px 0px 0px;
            cursor: pointer;
        }
        #loading {
            text-align: center;
            color: #1E9FFF;
            position: absolute;
            width: 100%;
            top: 115px;
        }
        #loading i {
            font-size: 30px;
        }
    </style>
</head>
<body>
    <div class="general_code" id="general_code">
        <div class="code_img" id="code_img">
            <h1 id="num"></h1>
            <div class="qrcode" id="qrcode"></div>
        </div>
        <div class="code_msg">
            <p>建议制作尺寸：13cm*20cm</p>
            <p style="margin-top: 10px;">若生成图片失败，请更换浏览器：ie9+、谷歌、火狐等</p>
            <div id="loading">
                <i class="layui-icon layui-icon-loading layui-icon layui-anim layui-anim-rotate layui-anim-loop"></i>
                <p class="t">图片正在生成，请稍后...</p>
            </div>
            <div class="code_zipIcon">
                <img src="<%=basePath%>newStyle/images/zip_icon.png" alt="" id="zipimg">
            </div>
            <div class="code_btn">
                <button class="layui-btn layui-btn-lg layui-btn-disabled" id="tt" >正在合成</button>
                <button class="layui-btn layui-btn-lg layui-btn-normal " onclick="packageImages()" style="display: none" id="dd">一键下载</button>
                <button class="layui-btn layui-btn-lg layui-btn-primary " onclick="packageImages()" style="display: none" id="downQr">仅二维码下载</button>
                <button class="layui-btn layui-btn-lg layui-btn-primary"  id="close">关闭</button>
                <p style="margin-top: 10px;color: red;display: none" id="tip">下载时若遇浏览器卡顿，请稍等几秒</p>
            </div>
        </div>
    </div>

    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-message.js"></script>
    <script src="<%=basePath%>js/qrcode.min.js"></script>

    <script src="<%=basePath%>newStyle/js/jszip.min.js"></script>
    <%--<script type="text/javascript" src="<%=basePath%>newStyle/js/FileSaver.js"></script>--%>
    <script type="text/javascript" src="https://cdn.bootcss.com/FileSaver.js/2014-11-29/FileSaver.js"></script>
    <script src="<%=basePath%>newStyle/js/qrcode_img.js"></script>
<script type="">

    layui.use(['element','layer'], function(){
        var $ = layui.jquery
            ,element = layui.element //Tab的切换功能，切换事件监听等，需要依赖element模块
            ,layer = layui.layer
            ,qrcode = new QRCode(document.getElementById("qrcode"), { // 创建一个二维码
               width : 132,//设置宽高
               height : 132,
               correctLevel: 1 //设置级别
           })
        var qrList = ${result.qrList};
        var texts = "";
        var code_url = "";

       <%--if('${result.result}' == 'false'){--%>
       <%--    layer.alert("${result.message}",function () {--%>
       <%--        parent.layer.closeAll();--%>
       <%--    });--%>
       <%--    return false--%>
       <%--}--%>

        var index = parent.layer.getFrameIndex(window.name);
        // 开始合成
        for(var i = 0; i < qrList.length; i++){
            $("#num").html(qrList[i].serial_number);
            texts = qrList[i].serial_number;
            qrcode.makeCode(qrList[i].url);
            n = Math.round(((i + 1) / qrList.length) * 100);
            var canvas = document.querySelector("canvas");
            code_url = canvas.toDataURL("image/png");
            drawAndShareImage(texts,code_url,"more");
        }

        $("#dd").attr("onclick", "packageImages('1'," + qrList.length + ")");
        $("#downQr").attr("onclick", "packageImages('2'," + qrList.length + ")");
        $("#loading").hide();
        $("#tt").hide();
        $("#dd").show();
        $("#downQr").show();
        $("#tip").show();
        $("#zipimg").attr("style","opacity:1");


        $("#close").click(function (){
            parent.layer.close(index);
        })


    })

</script>



</body>
</html>
