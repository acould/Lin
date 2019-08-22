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
    <!-- 下拉框 -->
    <link rel="stylesheet" href="<%=basePath%>static/ace/css/chosen.css"/>
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>

    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
    <script src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
    <script src="<%=basePath%>newStyle/layui/layui.js"></script>
    <script src="<%=basePath%>newStyle/layer/layer.js"></script>
    <title>Document</title>
    <style>
        .borPadding {
            padding: 20px 30px;
        }

        .layui-form-checkbox[lay-skin=primary] span {
            padding-right: 6px;
        }

        .layui-form-item .layui-form-checkbox[lay-skin=primary] {
            margin-top: 2px;
            margin-right: -8px;
            margin-left: 18px;
        }

        .foot {
            border: 1px solid #e6e6e6;
            text-align: center;
            cursor: pointer;
            padding: 10px 0;
            margin-top: 15px;
        }

        .foot img {
            width: 9%;
            margin: 10px 0;
        }

        .cente {
            background: #E6E6E6;
            padding: 30px;
            padding-bottom: 40px;
            margin-top: 15px;
        }
        .btn-default{
            color: #ffffff;
        }
        .btn-default:hover{
            color: #ffffff;
        }
        .btm{
            float: right;
            margin-top: -7px;
        }
    </style>
</head>
<body class="no-skin scroll">
<!-- /section:basics/navbar.layout -->
<div class="borPadding">
    <form class="layui-form layui-form-pane" name="Form" id="Form" method="post" action="">
        <input type="hidden" name="LOTTERY_ID" id="LOTTERY_ID" value="${pd.LOTTERY_ID}"/>
        <div class="layui-form-item">
            <p>可选门店</p>
            <div style="display: flex">
                <input type="checkbox" class="checkboxAll" lay-skin="primary" lay-filter="allChoose">
                <div>揽客网咖九堡店</div>
                <input type="checkbox" class="checkboxAll" lay-skin="primary" lay-filter="allChoose">
                <div>网晶网咖直营店</div>
            </div>
            <hr>
        </div>

        <div class="cente">
            <p>奖品一：</p>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">奖品名称:</label>
                <div class="layui-input-block">
                    <input type="text" name="LOTTERY_NAME" id="LOTTERY_NAME" value="${pd.LOTTERY_NAME}"
                           autocomplete="off" placeholder="这里输入奖品名称,限10字" title="中奖名称" maxlength="10"
                           class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">是否有奖:</label>
                <div class="layui-input-block">
                    <select name="STATE" id="STATE" style="width:98%;">
                        <option value="0" <c:if test="${pd.STATE=='0'}">selected="selected"</c:if>>有</option>
                        <option value="1" <c:if test="${pd.STATE =='1'}">selected="selected"</c:if>>没有</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">中奖概率:</label>
                <div class="layui-input-block">
                    <input type="number" min="1" max="100" maxlength="3" name="LOTTERY_NUMBER" id="LOTTERY_NUMBER"
                           value="${pd.LOTTERY_NUMBER}" class="layui-input" autocomplete="off"
                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           placeholder="例如：输入50等于50/总数的概率" title="中奖概率"/>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="margin:0">奖品有效期:</label>
                <div class="layui-input-block">
                    <input class="layui-input" type="number" min="1" name="EXPIRY_DATE" id="EXPIRY_DATE"
                           value="${pd.EXPIRY_DATE}"
                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           maxlength="32" placeholder="这里输入奖品有效期" title="奖品有效期"/>
                    <small style="font-size: x-small;color: red;">自中奖后()日内兑奖有效,至少为1</small>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">奖品使用说明:</label>
                <div class="layui-input-block">
                    <textarea class="layui-textarea" name="CONTENT" id="CONTENT" placeholder="这里输入奖品使用说明,限50字"
                              title="奖品使用说明" maxlength="50" onkeyup="checkNum()">${pd.CONTENT }</textarea>
                    <div>
                        <p id="zishu" style="color: #999;text-align: right;font-size: 12px;">50/剩余字数</p>
                    </div>
                </div>
            </div>
            <input type="hidden" id="USING_STATE" name="USING_STATE">

            <div class="btm">
                <span class="btn btn-sm btn-default" title="禁用" onclick="usingState('');">
                    <i class="layui-icon" style="padding-right: 4px">&#x1006;</i>禁用
                </span>
                <span class="btn btn-sm btn-danger" onclick="del();">
                    <i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
                </span>
            </div>

        </div>

        <div class="foot" onclick="Addprizes()">
            <img src="../../../../static/images/add.png" alt="">
            <p>添加奖品</p>
        </div>
    </form>
</div>
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp" %>
<!-- 下拉框 -->
<script src="<%=basePath%>static/ace/js/chosen.jquery.js"></script>
<script type="text/javascript">
        function Addprizes() {
            var counter=$("[class=cente]").size()
            console.log(counter)
            if (counter<6){
            $('.foot').before('<div class="cente">\n' +
                '            <p>奖品一：</p>\n' +
                '            <div class="layui-form-item">\n' +
                '                <label class="layui-form-label" style="margin:0">奖品名称:</label>\n' +
                '                <div class="layui-input-block">\n' +
                '                    <input type="text" name="LOTTERY_NAME" id="LOTTERY_NAME" value="${pd.LOTTERY_NAME}"\n' +
                '                           autocomplete="off" placeholder="这里输入奖品名称,限10字" title="中奖名称" maxlength="10"\n' +
                '                           class="layui-input">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '\n' +
                '            <div class="layui-form-item">\n' +
                '                <label class="layui-form-label" style="margin:0">是否有奖:</label>\n' +
                '                <div class="layui-input-block">\n' +
                '                    <select name="STATE" id="STATE" style="width:98%;">\n' +
                '                        <option value="0" <c:if test="${pd.STATE==\'0\'}">selected="selected"</c:if>>有</option>\n' +
                '                        <option value="1" <c:if test="${pd.STATE ==\'1\'}">selected="selected"</c:if>>没有</option>\n' +
                '                    </select>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '            <div class="layui-form-item">\n' +
                '                <label class="layui-form-label" style="margin:0">中奖概率:</label>\n' +
                '                <div class="layui-input-block">\n' +
                '                    <input type="number" min="1" max="100" maxlength="3" name="LOTTERY_NUMBER" id="LOTTERY_NUMBER"\n' +
                '                           value="${pd.LOTTERY_NUMBER}" class="layui-input" autocomplete="off"\n' +
                '                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,\'\')}else{this.value=this.value.replace(/\\D/g,\'\')}"\n' +
                '                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,\'\')}else{this.value=this.value.replace(/\\D/g,\'\')}"\n' +
                '                           placeholder="例如：输入50等于50/总数的概率" title="中奖概率"/>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '            <div class="layui-form-item">\n' +
                '                <label class="layui-form-label" style="margin:0">奖品有效期:</label>\n' +
                '                <div class="layui-input-block">\n' +
                '                    <input class="layui-input" type="number" min="1" name="EXPIRY_DATE" id="EXPIRY_DATE"\n' +
                '                           value="${pd.EXPIRY_DATE}"\n' +
                '                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,\'\')}else{this.value=this.value.replace(/\\D/g,\'\')}"\n' +
                '                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,\'\')}else{this.value=this.value.replace(/\\D/g,\'\')}"\n' +
                '                           maxlength="32" placeholder="这里输入奖品有效期" title="奖品有效期"/>\n' +
                '                    <small style="font-size: x-small;color: red;">自中奖后()日内兑奖有效,至少为1</small>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '            <div class="layui-form-item layui-form-text">\n' +
                '                <label class="layui-form-label">奖品使用说明:</label>\n' +
                '                <div class="layui-input-block">\n' +
                '                    <textarea class="layui-textarea" name="CONTENT" id="CONTENT" placeholder="这里输入奖品使用说明,限50字"\n' +
                '                              title="奖品使用说明" maxlength="50" onkeyup="checkNum()">${pd.CONTENT }</textarea>\n' +
                '                    <div>\n' +
                '                        <p id="zishu" style="color: #999;text-align: right;font-size: 12px;">50/剩余字数</p>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '            <input type="hidden" id="USING_STATE" name="USING_STATE">\n' +
                '\n' +
                '            <div class="btm">\n' +
                '                <span class="btn btn-sm btn-default" title="禁用" onclick="usingState(\'\');">\n' +
                '                    <i class="layui-icon" style="padding-right: 4px">&#x1006;</i>禁用\n' +
                '                </span>\n' +
                '                <span class="btn btn-sm btn-danger" onclick="del();">\n' +
                '                    <i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除\n' +
                '                </span>\n' +
                '            </div>\n' +
                '\n' +
                '        </div>')
            }else {
                alert("奖品不能多于6件！" )
            }
        };
        function del(){
            // var parent = document.querySelectorAll("#Form")[0];
            // var child =  document.querySelectorAll(".cente")[2];
            // document.writeln(child.innerHTML)
            // child.parentNode.removeChild(child);
            // console.log(321123)
            var counter=$("[class=cente]").size()
                console.log(1231)
                // $('.cente'+$(this).attr('data')).remove()
                var counter=$("[class=cente]").size()
                // var aa = --counter
                $('.cente').remove();
                 console.log(aa)


        };
    $(top.hangge());
    $(function () {
        var content = $("#CONTENT").val();
        var num = 50 - parseInt(content.length);
        $("#zishu").text(num + "/剩余字数");

        //修改时，只能更改中奖概率
        if ($("#LOTTERY_ID").val() != "") {
            $("#LOTTERY_NAME").attr("disabled", true);
            $("#STATE").attr("disabled", true);
            $("#EXPIRY_DATE").attr("disabled", true);
            $("#CONTENT").attr("disabled", true);
        }
    })

    function checkNum() {
        var content = $("#CONTENT").val();
        if (content.length > 50) {
            layer.tips('超出字数限制', '#CONTENT', {
                tips: 3
            });
            $("#CONTENT").focus();
        } else {
            var num = 50 - parseInt(content.length);
            $("#zishu").text(num + "/剩余字数");
        }
    }

    //保存
    var baocun = function (type) {
        if ($("#LOTTERY_NAME").val() == "") {
            layer.tips('请输入转盘名称', '#LOTTERY_NAME', {
                tips: 3
            });
            alert(233);
            $("#LOTTERY_NAME").focus();
            return false;
        }
        if ($("#LOTTERY_NUMBER").val() == "" || $("#LOTTERY_NUMBER").val() < 0) {
            layer.tips('请输入有效中奖概率', '#LOTTERY_NUMBER', {
                tips: 3
            });
            $("#LOTTERY_NUMBER").focus();
            return false;
        }
        if ($("#LOTTERY_NUMBER").val() > 100) {
            layer.tips('中奖概率最大可设置为100', '#LOTTERY_NUMBER', {
                tips: 3
            });
            $("#LOTTERY_NUMBER").val(100);
            $("#LOTTERY_NUMBER").focus();
            return false;
        }

        if ($("#EXPIRY_DATE").val() == "" || $("#EXPIRY_DATE").val() <= 0) {
            layer.tips('有效期至少为1', '#EXPIRY_DATE', {
                tips: 3
            });
            $("#EXPIRY_DATE").focus();
            return false;
        }
        if ($("#CONTENT").val() == "") {
            layer.tips('请输入奖品使用说明', '#CONTENT', {
                tips: 3
            });
            $("#CONTENT").focus();
            return false;
        }
        if (type == "1") {
            $("#USING_STATE").val("1");
        } else if (type == "2") {
            $("#USING_STATE").val("2");
            if ($("#LOTTERY_ID").val() != "") {
                //修改，仅保存时，不修改状态
                $("#USING_STATE").val("");
            }
        }


        $.ajax({
            type: "POST",
            url: '<%=basePath%>lottery/saveLottery.do',
            data: $('#Form').serialize(),
            dataType: 'json',
            cache: false,
            success: function (data) {
                layer.msg(data.message);
                if (data.result == "true") {
                    setTimeout(function () {
                        parent.location.reload();
                    }, 800)
                }
            },
            error: function () {
                layer.msg("系统繁忙，请稍后再试！");
                return false;
            }
        });



    }

    layui.use('form', function () {

    });
</script>
</body>
</html>