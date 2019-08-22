<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp"%>
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lk-layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <title>Document</title>
    <style>
        .layui-form-switch i {
            top:6px;
        }
        .layui-form-switch {
            height: 28px;
            line-height: 28px;
        }
    </style>
</head>
<body class="scroll">
    <div style="padding: 30px 80px">
        <form class="layui-form layui-form-pane" action="" id="Form">
            <input type="hidden" name="id" id="id" value="${pd.id}">
            <div class="layui-form-item">
                <p>人员基础信息</p>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">通知接收员</label>
                <div class="layui-input-block">
                    <input type="text" name="name" value="${pd.name}"
                           autocomplete="off"  class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">微信昵称</label>
                <div class="layui-input-block">
                    <input type="text" name="username" lay-verify="required" value="${pd.neck_name}"
                           autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">接收门店</label>
                <div class="layui-input-block">
                    <select name="STORE_ID" lay-filter="aihao" id="STORE_ID">
                        <c:forEach items="${storeList}" var="var" varStatus="vs">
                            <option value="${var.STORE_ID}" <c:if test="${var.STORE_ID == pd.store_id}">selected="selected"</c:if>>${var.STORE_NAME}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item" style="padding-top: 20px">
                <p>接收服务类型：</p>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">呼叫服务通知</label>
                <div class="layui-input-block">
                    <input type="checkbox"  lay-skin="switch" lay-text="关闭|开启" value="user_call" name="perms"
                           <c:if test="${pd.perms.contains('user_call')}">checked=""</c:if>>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">一键投诉通知</label>
                <div class="layui-input-block">
                    <input type="checkbox"  lay-skin="switch" lay-text="关闭|开启" value="user_complain" name="perms"
                           <c:if test="${pd.perms.contains('user_complain')}">checked=""</c:if>>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">购买商品通知</label>
                <div class="layui-input-block">
                    <input type="checkbox"  lay-skin="switch" lay-text="关闭|开启" value="user_order" name="perms"
                           <c:if test="${pd.perms.contains('user_order')}">checked=""</c:if>>
                </div>
            </div>
        </form>
    </div>
</body>
<!-- /.main-container -->
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<!-- 业务js -->
<script src="<%=basePath%>newStyle/js/lk-staff.js"></script>
<script type="text/javascript">

    //保存
    function save() {
        if(!check()){
            return false;
        }
        var data = $('#Form').serialize();
        $.post(getRealPath() +'/internetStaff/saveStaff.do', data, function (res) {
            // console.log(res)
            if(res.result == "true"){
                parent.location.reload();
            }
        })
    }


    function check() {
        if($("#STORE_ID").val()==""){
            message("请选择门店")
            return false;
        }
        var flag = 0;
        $.each($("input[type='checkbox']"),function () {
            if( $(this)[0].checked){
                flag = flag + 1;
            }
        });
        if(flag < 1) {
            message("请选择接收服务")
            return false;
        }
        return true;
    }
</script>
</body>
</html>