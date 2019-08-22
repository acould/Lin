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
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layuiadmin/layui/css/layui.css" media="all">

</head>
<body>

    <form id="Form"enctype="multipart/form-data" class="layui-form layui-form-pane" style="padding: 50px">
        <input type="hidden" id="id" name="id" value="${pd.id}">
        <input type="hidden" id="store_id" name="store_id" value="${pd.store_id}">
        <div class="layui-form-item">
            <label class="layui-form-label">电脑编号:</label>
            <div class="layui-input-block">
                <input type="text" name="serial_number" id="serial_number" autocomplete="off"
                       placeholder="请输入电脑编号" class="layui-input" maxlength="60" value="${pd.serial_number}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">电脑名称:</label>
            <div class="layui-input-block">
                <input type="text" name="computer_name" id="computer_name" autocomplete="off"
                       placeholder="请输入电脑名称" class="layui-input" maxlength="60" value="${pd.computer_name}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">电脑IP:</label>
            <div class="layui-input-block">
                <input type="text" name="ip" id="ip" autocomplete="off"
                       placeholder="请输入电脑IP" class="layui-input" maxlength="20" value="${pd.ip}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">Mac地址:</label>
            <div class="layui-input-block">
                <input type="text" name="mac_address" id="mac_address" autocomplete="off"
                       placeholder="请输入电脑Mac地址" class="layui-input" maxlength="100" value="${pd.mac_address}">
            </div>
        </div>
        <div id="qrcode" style="display: none"></div>
    </form>

<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script src="<%=basePath%>js/qrcode.min.js"></script>
<script src="<%=basePath%>newStyle/js/jszip.min.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>newStyle/js/FileSaver.js"></script>--%>
<script type="text/javascript" src="https://cdn.bootcss.com/FileSaver.js/2014-11-29/FileSaver.js"></script>
<script src="<%=basePath%>newStyle/js/qrcode_img.js"></script>
<script type="">
   var qrcode = new QRCode(document.getElementById("qrcode"), { // 创建一个二维码
        width : 218,//设置宽高
        height : 218,
        correctLevel: 1 //设置级别
    })
    //保存
    function save(type) {
        if(!check()){
            return false;
        }

        $.ajax({
            type: "POST",
            url: '<%=basePath%>qrCode/saveMachine.do',
            data: $('#Form').serialize(),
            dataType: 'json',
            cache: false,
            beforeSend: beforeSend(''),
            success: function (data) {
                layer.closeAll();
                layer.msg(data.message);
                if (data.result == "true") {
                    if(type == "save"){
                        setTimeout(function () {
                            parent.location.reload();
                        }, 800)
                    }else {
                        qrcode.makeCode(data.url);
                        var canvas = document.querySelector("canvas");
                        var code_url = canvas.toDataURL("image/png");
                        var texts = data.serial_number;
                        drawAndShareImage(texts,code_url,"one")
                    }
                }
            },
            error: function () {
                layer.closeAll();
                layer.msg("系统繁忙，请稍后再试！");
                return false;
            }
        });
    }


    function check() {
        if($("#serial_number").val()==""){
            layer.tips('请输入电脑编号', '#serial_number', {
                tips: 3
            });
            return false;
        }
        if($("#computer_name").val()==""){
            layer.tips('请输入电脑名称', '#computer_name', {
                tips: 3
            });
            return false;
        }
        return true;
    }

</script>



</body>
</html>
