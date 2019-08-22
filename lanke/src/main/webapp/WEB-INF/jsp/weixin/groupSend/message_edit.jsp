<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>编辑模板</title>

    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">

    <style>
        .lanke-sign{
            color: red;
        }
    </style>
</head>
<body>

    <form id="Form" method="post" class="" style="padding: 25px">
        <input id="id" name="id" value="${pd.id}" type="hidden">
        <div class="lk-msg-set">
            <div class="msg-content">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="lanke-sign">*</span>消息标题：</label>
                    <div class="layui-input-block">
                        <input type="text" name="first_data" class="layui-input" placeholder="请输入消息标题"
                               id="first_data" value="${pd.first_data}" maxlength="20">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="lanke-sign">*</span>维护时间：</label>
                    <div class="layui-input-block">
                        <input type="text" name="keyword1" class="layui-input" readonly="readonly" placeholder="请选择维护时间"
                               placeholder="" id="keyword1" value="${pd.keyword1}" maxlength="30">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="lanke-sign">*</span>恢复时间：</label>
                    <div class="layui-input-block">
                        <input type="text" name="keyword2" class="layui-input" readonly="readonly" placeholder="请选择恢复时间"
                               id="keyword2" value="${pd.keyword2}" maxlength="30">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="lanke-sign">*</span>维护内容：</label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入维护内容" class="layui-textarea" style="width: 100%" id="keyword3" name="keyword3" rows="6"
                                  maxlength="300">${pd.keyword3}</textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注：</label>
                    <div class="layui-input-block">
                        <input type="text" name="remark_data" class="layui-input" placeholder="请输入备注"
                               id="remark_data" value="${pd.remark_data}" maxlength="20">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">跳转链接：</label>
                    <div class="layui-input-block">
                        <input type="text" name="url" class="layui-input" placeholder="请输入跳转链接"
                               id="url" value="${pd.url}" maxlength="100">
                    </div>
                </div>
            </div>
        </div>
    </form>


    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <script src="<%=basePath%>newStyle/js/lk-message.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script>
        var $;
        layui.use([ "form", "layer", 'laydate' ],function() {
            var laydate = layui.laydate;
                $ = layui.jquery;
            laydate.render({
                elem: '#keyword1',
                type: 'datetime'
            });
            laydate.render({
                elem: '#keyword2',
                type: 'datetime'
            });
        });

        function save() {
            if(!check()){
                return false;
            }

            $.ajax({
                type: "POST",
                url:  '<%=basePath%>groupSend/saveMessage.do',
                data: $('#Form').serialize(),
                dataType: 'json',
                beforeSend: loading('提交中...'),
                success: function (data) {
                    layer.closeAll();
                    if(data.errcode == 0){

                        $("#id").val(data.id);
                        message("保存成功")
                    }else{
                        message(data.errmsg);
                    }
                },
                error: function () {
                    layer.closeAll();
                    message("系统繁忙，请稍后再试");
                }
            });
        }

        function sendMessage() {
            if(!check()){
                return false;
            }
            save();
            setTimeout(function () {
                parent.layer.open({
                    btn: ['确定', '关闭'],
                    btn1: function(index, layero){
                        //按钮的回调
                        var res = parent.window["layui-layer-iframe" + index].sendMessage();
                    },
                    btnAlign: 'c',
                    type: 2,
                    title: '群发设置',
                    shadeClose: false,
                    shade: 0.8,
                    area: ['620px', '65%'],
                    content: ['<%=basePath%>groupSend/goGroupSend.do?id=' + $("#id").val()],
                });
            },800)
        }

        function check() {
            if($("#first_data").val().trim() == ''){
                message('请输入消息标题');
                return false;
            }
            if($("#keyword1").val().trim() == ''){
                message('请选择维护时间');
                return false;
            }
            if($("#keyword2").val().trim() == ''){
                message('请选择恢复时间');
                return false;
            }
            if($("#keyword3").val().trim() == ''){
                message('请输入维护内容');
                return false;
            }
            return true;
        }


    </script>

</body>
</html>
