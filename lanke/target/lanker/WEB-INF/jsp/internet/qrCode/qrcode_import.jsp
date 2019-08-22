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

</head>
<body>

    <form id="Form" method="post" class="layui-form layui-form-pane" style="text-align: center" >
        <input type="hidden" id="store_id" name="store_id" value="${pd.store_id}">

         <input type="file" id="file" name="file" style="display: none" accept="application/vnd.ms-excel">
        <div class="layui-upload-drag" id="choose" style="margin: 90px;">
            <i class="layui-icon"></i>
            <p id="font">点击上传文件</p>
        </div>
    </form>

    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-message.js"></script>

<script type="">

    $("#choose").click(function () {
        $("#file").trigger("click");
    });

    $("#file").change(function(){
        //获取FileUpload对象
        var x = document.getElementById("file").files;
        //把获得的文件名放入text里面显示
        $("#font").html(x[0].name);

    })

    //上传
    function upload() {
        if(!check()){
            return false;
        }

        $.ajax({
            type: "POST",
            url: '<%=basePath%>qrCode/importCN.do',
            data: new FormData($('#Form')[0]),
            dataType: 'json',
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: beforeSend(''),
            success: function (data) {
                layer.closeAll();
                layer.msg(data.message);
                if (data.result == "true") {
                    setTimeout(function () {
                        parent.location.reload();
                    }, 800)
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
        if($("#file").val() == null){
            layer.tips('请选择后缀为xls表格文件', '#choose', {
                tips: 3
            });
            return false;
        }
        var name = document.getElementById("file").files[0].name;
        var i = name.split(".").length-1
        if(name.split(".")[i] != 'xls'){
            layer.tips('请选择后缀为xls表格文件', '#choose', {
                tips: 3
            });
            return false;
        }

        return true;
    }

</script>



</body>
</html>
